package matchmaking;

import account.Account;
import database.DatabaseConnection;
import database.DatabaseManager;
import game.GameType;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MatchMakingHandler {
    MatchmakingState state = MatchmakingState.ONLINE;
    final int selfID;
    String networkingInfo;

    public MatchMakingHandler(int id, String networkingInfo){
        this.selfID = id;
        this.networkingInfo = networkingInfo;
    }

    public void startMatchmaking(GameType game, int elo) throws InterruptedException {
        double starting_time = System.currentTimeMillis();
        state = MatchmakingState.MATCHMAKING;

        // Add info to matchmaking table
        addToMatchmakingTable(game, elo);

        // Matchmaking loop
        while (state == MatchmakingState.MATCHMAKING){
            // Check own information to see if another account is already requesting a match
            if (querySelfState() == MatchmakingState.FOUND_MATCH){
                int opponent_ID = querySelfOpponentID();
                startMatch(game, opponent_ID);

                // Update state and break loop
                state = MatchmakingState.PLAYING;
                break;
            }

            // Update own information
            setSelfRecentTime(System.currentTimeMillis());
            setSelfEloRange(getNewMatchmakingRange(game, starting_time));

            // Search for other players who this can match with
            for (int id : queryAllOtherIDs()){
                double msSinceLastCommunicated = (System.currentTimeMillis() - queryRecentTime(id));
                if (msSinceLastCommunicated > 5000){
                    removeFromMatchmakingTable(id);
                }
                else if (canMatchWith(id)) {
                    // Notify the other that they are going to match with you
                    setState(id, MatchmakingState.FOUND_MATCH);
                    setOpponentID(id, selfID);

                    // Start the match on your client
                    startMatch(game, id);
                    break;
                }
            }

            // After each matchmaking query, wait 1 second before repeating
            TimeUnit.SECONDS.sleep(1);
        }

        removeFromMatchmakingTable(selfID);
    }

    /**
     *
     * @param game
     * @param opponentID
     */
    public void startMatch(GameType game, int opponentID){
        // Update self state (both locally and in matchmaking table)
        state = MatchmakingState.PLAYING;
        setSelfState(MatchmakingState.PLAYING);

        // Start match of <game> with opponent <opponentID>
        Account opponent = DatabaseManager.queryAccountByID(opponentID);

        // ... (integration)

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
            new_range = (timeWaitSec / 30) * thresholdIncreaseRate;
        }
        // After 2 minutes, match anyone regardless of skill
        else {
            new_range = Integer.MAX_VALUE;
        }

        return new_range;
    }

    private boolean canMatchWith(int id){
        return  (
                querySelfState() == MatchmakingState.MATCHMAKING                 // Self is matchmaking
                && queryState(id) == MatchmakingState.MATCHMAKING                // Other is matchmaking
                && querySelfGame() == queryGame(id)                              // Both are matchmaking for same game
                && Math.abs(querySelfElo() - queryElo(id)) < querySelfEloRange() // Elo difference acceptable for self
                && Math.abs(querySelfElo() - queryElo(id)) < queryEloRange(id)   // Elo difference acceptable for other
        );
    }

    // MODIFY SELF
    private void addToMatchmakingTable(GameType gameType, int elo){
        String stateString = MatchmakingState.MATCHMAKING.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = 0.0;
        int eloRange = getNewMatchmakingRange(gameType, startTime);
        int opponentID = -1;

        String sql = "INSERT INTO " +
                "matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, selfID);
                stmt.setString(2, stateString);
                stmt.setString(3, gameTypeString);
                stmt.setDouble(4, startTime);
                stmt.setDouble(5, recentTime);
                stmt.setInt(6, elo);
                stmt.setInt(7, eloRange);
                stmt.setInt(8, opponentID);
                stmt.setString(9, networkingInfo);

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

    private void setSelfState(MatchmakingState newState){
        String sql = "UPDATE matchmaking SET state = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        String newStateString = newState.toString();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newStateString);
                stmt.setInt(2, selfID);
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

    private void setSelfRecentTime(double newRecentTime){
        String sql = "UPDATE matchmaking SET recent_time = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, newRecentTime);
                stmt.setInt(2, selfID);
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

    private void setSelfEloRange(int newRange){
        String sql = "UPDATE matchmaking SET elo_range = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newRange);
                stmt.setInt(2, selfID);
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

    private void setSelfOpponentID(int newOpponentID){
        String sql = "UPDATE matchmaking SET opponent_ID = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newOpponentID);
                stmt.setInt(2, selfID);
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


    // QUERY SELF
    private MatchmakingState querySelfState(){
        return queryState(selfID);
    }

    private GameType querySelfGame(){
        return queryGame(selfID);
    }

    private int querySelfElo(){
        return queryElo(selfID);
    }

    private int querySelfEloRange(){
        return queryEloRange(selfID);
    }

    private Integer querySelfOpponentID(){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        Integer opponentID = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, selfID);
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


    // MODIFY OTHERS
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
    private ArrayList<Integer> queryAllOtherIDs(){
        String sql = "SELECT * FROM accounts";
        Connection conn = DatabaseConnection.getConnection();
        ArrayList<Integer> ids = new ArrayList<>();

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("ID");
                    if (id != selfID){
                        ids.add(id);
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


}
