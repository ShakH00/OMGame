package matchmaking;

import account.Account;
import database.DatabaseConnection;
import database.DatabaseManager;
import game.GameType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MatchmakingHandler {
    MatchmakingState state;
    final String networkingInfo;

    public MatchmakingHandler(Account account, String networkingInfo){
        this.state = MatchmakingState.ONLINE;
        this.networkingInfo = networkingInfo;
    }

    public void startMatchmaking(int selfID, GameType game, int elo) throws InterruptedException {
        double starting_time = System.currentTimeMillis();
        state = MatchmakingState.MATCHMAKING;

        // Add info to matchmaking table
        addToMatchmakingTable(selfID, game, elo);

        // Matchmaking loop
        while (state == MatchmakingState.MATCHMAKING){
            // Check own information to see if another account is already requesting a match
            if (queryState(selfID) == MatchmakingState.FOUND_MATCH){
                int opponentID = queryOpponentID(selfID);
                String opponentNetworkingInformation = queryNetworkingInfo(opponentID);
                startMatch(selfID, game, true, opponentID, opponentNetworkingInformation);
                break;
            }

            // Update own information
            setRecentTime(selfID, System.currentTimeMillis());
            setEloRange(selfID, getNewMatchmakingRange(game, starting_time));

            // Search for other players who this can match with
            if (!queryAllOtherIDs(selfID).isEmpty()) {
                System.out.println("ID: " + queryAllOtherIDs(selfID).getFirst());
                for (int opponentID : queryAllOtherIDs(selfID)) {
                    double msSinceLastCommunicated = (System.currentTimeMillis() - queryRecentTime(opponentID));
                    if (msSinceLastCommunicated > 5000) {
                        System.out.printf("Disconnecting account with id %s because they have been inactive for >5 seconds\n", opponentID);
                        removeFromMatchmakingTable(opponentID);
                    } else if (canMatch(selfID, opponentID)) {
                        // Notify the other that they are going to match with you
                        System.out.printf("Found acceptable match with id %s. Updating their information...\n", opponentID);
                        setState(opponentID, MatchmakingState.FOUND_MATCH);
                        setOpponentID(opponentID, selfID);

                        // Start the match on your client
                        String opponentNetworkingInformation = queryNetworkingInfo(opponentID);
                        startMatch(selfID, game, true, opponentID, opponentNetworkingInformation);
                        break;
                    }
                }
            } else {
                System.out.println("No other players found in matchmaking query...");
            }

            // After each matchmaking query, wait 1 second before repeating
            TimeUnit.SECONDS.sleep(1);
        }

        TimeUnit.SECONDS.sleep(1);  // Stay in database for an additional second to let opponent get details
        removeFromMatchmakingTable(selfID);
    }

    public void startHosting(int selfID, GameType game, String roomCode) throws InterruptedException {
        state = MatchmakingState.HOSTING;

        // If the given room code is not unique, generate a new one.
        if (queryRoomCodeInTable(roomCode)){
            roomCode = getUniqueRoomCode();
        }

        // Add info to matchmaking table
        addToMatchmakingTable(selfID, game, roomCode);

        // Matchmaking loop
        while (state == MatchmakingState.HOSTING){
            // Check own information to see if another account is already requesting a match
            if (queryState(selfID) == MatchmakingState.FOUND_MATCH){
                int opponentID = queryOpponentID(selfID);
                String opponentNetworkingInformation = queryNetworkingInfo(opponentID);
                startMatch(selfID, game, false, opponentID, opponentNetworkingInformation);
                break;
            }

            // Update own information
            setRecentTime(selfID, System.currentTimeMillis());

            // Wait 1 second before checking again if someone has joined
            TimeUnit.SECONDS.sleep(1);
        }

        removeFromMatchmakingTable(selfID);
    }

    public boolean tryJoinHost(int selfID, String roomCode) {
        if (queryRoomCodeInTable(roomCode)) {
            // Get opponent details
            int opponentID = queryHostIDByRoomCode(roomCode);
            String opponentNetworkingInformation = queryNetworkingInfo(opponentID);

            // Tell opponent that you are ready to match
            setState(opponentID, MatchmakingState.FOUND_MATCH);
            setOpponentID(opponentID, selfID);

            // Start match
            GameType game = queryGame(opponentID);
            startMatch(selfID, game, false, opponentID, opponentNetworkingInformation);
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void stopMatchmaking(){
        state = MatchmakingState.ONLINE;
    }

    /**
     *
     */
    public void stopHosting(){
        state = MatchmakingState.ONLINE;
    }


    /**
     *
     * @param game
     * @param affectsElo
     * @param opponentID
     */
    public void startMatch(int id, GameType game, boolean affectsElo, int opponentID, String opponentNetworkingInformation){
        // Update self state (both locally and in matchmaking table)
        state = MatchmakingState.PLAYING;
        setState(id, MatchmakingState.PLAYING); // update state in database, preventing others from matching with this

        // Get info for game logic/GUI
        Account self = DatabaseManager.queryAccountByID(id);
        String selfUsername = self != null ? self.getUsername() : "Guest";

        Account opponent = DatabaseManager.queryAccountByID(opponentID);
        String opponentUsername = opponent != null ? opponent.getUsername() : "Guest";

        System.out.printf("Match found: You (ID %s) vs. %s (ID %s)\n", id, opponentUsername, opponentID);

        // Load game, handle networking

    }

    /**
     *
     * @param game
     * @param startTime
     * @return
     */
    private static int getNewMatchmakingRange(GameType game, double startTime) {
        // Get time (in ms) that the account has been waiting
        int timeWaitSec = (int) (System.currentTimeMillis() - startTime) / 1000;

        // Get the rate of threshold increase per 30 seconds (dependent on game)
        int thresholdIncreaseRate;
        switch (game) {
            case CHESS              ->  thresholdIncreaseRate = 50;
            case CHECKERS, CONNECT4 ->  thresholdIncreaseRate = 75;
            case TICTACTOE          ->  thresholdIncreaseRate = 100;
            default                 ->  thresholdIncreaseRate = 0;
        }

        // Calculate final threshold
        int new_range;
        if (timeWaitSec < 120) {
            new_range = (1 + timeWaitSec / 30) * thresholdIncreaseRate;
        }
        // After 2 minutes, match anyone regardless of skill
        else {
            new_range = Integer.MAX_VALUE;
        }

        return new_range;
    }

    private boolean canMatch(int id1, int id2){
        return  (
                queryState(id1) == MatchmakingState.MATCHMAKING                  // Self is matchmaking
                && queryState(id2) == MatchmakingState.MATCHMAKING               // Other is matchmaking
                && queryGame(id1) == queryGame(id2)                              // Both are matchmaking for same game
                && Math.abs(queryElo(id1) - queryElo(id2)) < queryEloRange(id1)  // Elo difference acceptable for self
                && Math.abs(queryElo(id1) - queryElo(id2)) < queryEloRange(id2)  // Elo difference acceptable for other
        );
    }


    /**
     * @author Logan Olszak
     * @return Unique ID string of length 6
     * getUniqueRoomCode is a function that generates random 6 character room IDs until a unique ID is found
     */
    public String getUniqueRoomCode() {
        String potentialCode = generateRandomRoomCode();
        return !queryRoomCodeInTable(potentialCode) ? potentialCode : getUniqueRoomCode();  // Recursive call if there is a collision.
    }

    /**
     * generateRandomID is a function that generates a random 6 character room ID using characters A-Z and 0-9
     * @author Logan Olszak
     * @return String random string of length 6
     */
    private String generateRandomRoomCode() {
        StringBuilder roomCodeString = new StringBuilder ();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            String possibleIDCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            int randIndex = rand.nextInt(possibleIDCharacters.length());
            roomCodeString.append(possibleIDCharacters.charAt(randIndex));
        }
        return roomCodeString.toString();
    }

    private void addToMatchmakingTable(int id, GameType gameType, int elo){
        String stateString = MatchmakingState.MATCHMAKING.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int eloRange = getNewMatchmakingRange(gameType, startTime);
        int opponentID = -1;
        int room_code = -1;

        String sql = "INSERT INTO " +
                "matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, stateString);
                stmt.setString(3, gameTypeString);
                stmt.setDouble(4, startTime);
                stmt.setDouble(5, recentTime);
                stmt.setInt(6, elo);
                stmt.setInt(7, eloRange);
                stmt.setInt(8, opponentID);
                stmt.setString(9, networkingInfo);
                stmt.setInt(10, room_code);

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    private void addToMatchmakingTable(int id, GameType gameType, String roomCode){
        String stateString = MatchmakingState.HOSTING.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int elo = -1;
        int eloRange = -1;
        int opponentID = -1;

        String sql = "INSERT INTO " +
                "matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, stateString);
                stmt.setString(3, gameTypeString);
                stmt.setDouble(4, startTime);
                stmt.setDouble(5, recentTime);
                stmt.setInt(6, elo);
                stmt.setInt(7, eloRange);
                stmt.setInt(8, opponentID);
                stmt.setString(9, networkingInfo);
                stmt.setString(10, roomCode);

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    private Integer queryOpponentID(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        Integer opponentID = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    opponentID = rs.getInt("Opponent_ID");
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return opponentID;
    }

    private void setState(int id, MatchmakingState newState){
        String sql = "UPDATE matchmaking SET state = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        String newStateString = newState.toString();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newStateString);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    private void setRecentTime(int id, double newRecentTime){
        String sql = "UPDATE matchmaking SET recent_time = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, newRecentTime);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    private void setEloRange(int id, int newRange){
        String sql = "UPDATE matchmaking SET elo_range = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newRange);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    private void setOpponentID(int id, int newOpponentID){ //
        String sql = "UPDATE matchmaking SET opponent_ID = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newOpponentID);
                stmt.setInt(2, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    private void removeFromMatchmakingTable(int id){
        String sql = "DELETE FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
        }
    }

    // QUERY OTHERS
    private ArrayList<Integer> queryAllOtherIDs(int id){
        String sql = "SELECT * FROM matchmaking";
        Connection conn = DatabaseConnection.getConnection();
        ArrayList<Integer> ids = new ArrayList<>();

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int foundID = rs.getInt("ID");
                    if (foundID != id){
                        ids.add(foundID);
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return ids;
    }

    private MatchmakingState queryState(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        MatchmakingState state = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    state = MatchmakingState.fromString(rs.getString("State"));
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return state;
    }

    private GameType queryGame(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        GameType game = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    game = GameType.fromString(rs.getString("Game"));
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return game;
    }

    private Double queryRecentTime(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        Double recentTime = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    recentTime = rs.getDouble("RECENT_TIME");
                    recentTime = recentTime != null ? recentTime : 0.00;
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return recentTime;
    }

    private Integer queryElo(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        Integer elo = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    elo = rs.getInt("Elo");
                    elo = elo != null ? elo : 0;
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return elo;
    }

    private Integer queryEloRange(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        Integer eloRange = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    eloRange = rs.getInt("Elo_Range");
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return eloRange;
    }

    private String queryNetworkingInfo(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        String networkingInfo = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    networkingInfo = rs.getString("Networking_Info");
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return networkingInfo;
    }

    private boolean queryRoomCodeInTable(String roomCode){
        String sql = "SELECT * FROM matchmaking WHERE room_code = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, roomCode);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return false;
    }

    private int queryHostIDByRoomCode(String roomCode){
        String sql = "SELECT * FROM matchmaking WHERE room_code = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, roomCode);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return -1;
    }
}
