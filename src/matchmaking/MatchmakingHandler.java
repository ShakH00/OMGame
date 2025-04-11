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
    // Current matchmaking state of this object
    public MatchmakingState state;

    // Variables for the MatchTypeController (GUI) to watch when waiting to start a match
    public boolean startGame = false;
    public GameType m_game;
    public boolean m_affectsElo;
    public int m_selfID;
    public String m_selfUsername;
    public int m_selfElo;
    public String m_selfNetworkingInformation;
    public int m_selfPlayerNo;
    public int m_opponentID;
    public String m_opponentUsername;
    public int m_opponentElo;
    public String m_opponentNetworkingInformation;
    public int m_opponentPlayerNo;



    public MatchmakingHandler(){
        this.state = MatchmakingState.ONLINE;
    }

    public void startMatchmaking(int selfID, GameType game, int elo, String networkingInfo) throws InterruptedException {
        MatchmakingThread matchmakingThread = new MatchmakingThread(this, selfID, game, elo, networkingInfo);
        matchmakingThread.start();
    }

    public void startHosting(int selfID, GameType game, int elo, String roomCode, String networkingInfo) throws InterruptedException {
        System.out.println("ID " + selfID + " is now hosting.");
        HostingThread hostingThread = new HostingThread(this, selfID, game, elo, roomCode, networkingInfo);
        hostingThread.start();
    }

    public boolean tryJoinHost(int selfID, Account account, String roomCode, String networkingInformation) {
        System.out.println("Trying to join room with code: " + roomCode);
        if (queryRoomCodeInTable(roomCode)) {
            System.out.println("Success!");
            // Get opponent details
            int opponentID = queryHostIDByRoomCode(roomCode);
            GameType game = queryGame(opponentID);

            // Decide player number
            int playerNo = MatchmakingHandler.getRandomPlayerNo();

            // Tell opponent that you are ready to match
            setState(opponentID, MatchmakingState.FOUND_MATCH);
            setOpponentID(opponentID, selfID);
            int opponentPlayerNo = playerNo == 1 ? 2 : 1;
            setPlayerNo(opponentID, opponentPlayerNo);

            // Add self to table so opponent can reference self if necessary
            addToTable(selfID, game, account.getElo(game), MatchmakingState.PLAYING, networkingInformation);
            setPlayerNo(selfID, playerNo);

            // Start match (GUI will read variables set by this function)
            startMatch(selfID, game, false, opponentID);
            return true;
        }
        System.out.println("Could not find room by code: " + roomCode);
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
    public void startMatch(int id, GameType game, boolean affectsElo, int opponentID){
        // Update self state (both locally and in matchmaking table)
        state = MatchmakingState.PLAYING;
        setState(id, MatchmakingState.PLAYING); // update state in database, preventing others from matching with this one

        // Set variables for GUI to read
        startGame = true;   // flag tells the MatchTypeController (matching GUI) that a match is found
        m_game = game;
        m_affectsElo = affectsElo;
        m_selfID = id;
        m_selfUsername = DatabaseManager.queryAccountByID(id) != null ? DatabaseManager.queryAccountByID(id).getUsername() : "Guest";
        m_selfElo = queryElo(id);
        m_selfNetworkingInformation = queryNetworkingInfo(id);
        m_selfPlayerNo = queryPlayerNo(id);
        m_opponentID = opponentID;
        m_opponentUsername = DatabaseManager.queryAccountByID(opponentID) != null ? DatabaseManager.queryAccountByID(opponentID).getUsername() : "Guest";
        m_opponentElo = queryElo(opponentID);
        m_opponentNetworkingInformation = queryNetworkingInfo(opponentID);
        m_opponentPlayerNo = queryPlayerNo(opponentID);

        System.out.printf("Match found: You (ID %s) as P%d vs. %s (ID %s) as P%d\n", id, m_selfPlayerNo, m_opponentUsername, opponentID, m_opponentPlayerNo);
    }

    /**
     *
     * @param game
     * @param startTime
     * @return
     */
    protected static int getNewMatchmakingRange(GameType game, double startTime) {
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

    protected boolean canMatch(int id1, int id2){
        return  (
                queryState(id1) == MatchmakingState.MATCHMAKING                  // Self is matchmaking
                && queryState(id2) == MatchmakingState.MATCHMAKING               // Other is matchmaking
                && queryGame(id1) == queryGame(id2)                              // Both are matchmaking for same game
                && Math.abs(queryElo(id1) - queryElo(id2)) < queryEloRange(id1)  // Elo difference acceptable for self
                && Math.abs(queryElo(id1) - queryElo(id2)) < queryEloRange(id2)  // Elo difference acceptable for other
        );
    }

    public MatchmakingState getState() {
        return state;
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
            String possibleIDCharacters = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
            int randIndex = rand.nextInt(possibleIDCharacters.length());
            roomCodeString.append(possibleIDCharacters.charAt(randIndex));
        }
        return roomCodeString.toString();
    }

    /**
     * Returns either 1 or 2 randomly
     */
    public static int getRandomPlayerNo() {
        return Math.random() < 0.5 ? 1 : 2;
    }

    // Player starts matchmaking
    protected void addToTable(int id, GameType gameType, int elo, String networkingInfo){
        String stateString = MatchmakingState.MATCHMAKING.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int eloRange = getNewMatchmakingRange(gameType, startTime);
        int opponentID = -1;
        String roomCode = "";
        int playerNo = -1;

        String sql = "INSERT INTO " +
                "matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code, player_no) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                stmt.setInt(11, playerNo);

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

    // Player starts hosting
    protected void addToTable(int id, GameType gameType, int elo, String roomCode, String networkingInfo){
        String stateString = MatchmakingState.HOSTING.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int eloRange = -1;
        int opponentID = -1;
        int playerNo = -1;

        String sql = "INSERT INTO " +
                "matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code, player_no) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                stmt.setInt(11, playerNo);

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

    // Player joins a host
    protected void addToTable(int id, GameType gameType, int elo, MatchmakingState state, String networkingInfo){
        String stateString = state.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int eloRange = -1;
        int opponentID = -1;
        String roomCode = "";
        int playerNo = -1;

        String sql = "INSERT INTO " +
                "matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code, player_no) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                stmt.setInt(11, playerNo);

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


    protected Integer queryOpponentID(int id){
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

    protected void setState(int id, MatchmakingState newState){
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

    protected void setNetworkingInfo(int id, String newNetworkingInfo){
        String sql = "UPDATE matchmaking SET networking_info = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newNetworkingInfo);
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

    protected void setRecentTime(int id, double newRecentTime){
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

    protected void setEloRange(int id, int newRange){
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

    protected void setOpponentID(int id, int newOpponentID){ //
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

    protected void setPlayerNo(int id, int newPlayerNo){ //
        String sql = "UPDATE matchmaking SET player_no = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newPlayerNo);
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

    protected void removeFromMatchmakingTable(int id){
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
    protected ArrayList<Integer> queryAllOtherIDs(int id){
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

    protected MatchmakingState queryState(int id){
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

    protected GameType queryGame(int id){
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

    protected Double queryRecentTime(int id){
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

    protected Integer queryElo(int id){
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

    protected Integer queryEloRange(int id){
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

    protected String queryNetworkingInfo(int id){
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

    protected boolean queryRoomCodeInTable(String roomCode){
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

    protected int queryHostIDByRoomCode(String roomCode){
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

    protected int queryPlayerNo(int id){
        String sql = "SELECT * FROM matchmaking WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("player_no");
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

class HostingThread extends Thread {
    private final MatchmakingHandler handler;
    private final int selfID;
    private final GameType game;
    private final int elo;
    private final String roomCode;
    private final String networkingInfo;

    public HostingThread(MatchmakingHandler handler, int selfID, GameType game, int elo, String roomCode, String networkingInfo){
        this.handler = handler;
        this.selfID = selfID;
        this.game = game;
        this.elo = elo;
        this.roomCode = roomCode;
        this.networkingInfo = networkingInfo;
    }

    public void run(){
        try {
            handler.state = MatchmakingState.HOSTING;

            // Add info to matchmaking table
            handler.addToTable(selfID, game, elo, roomCode, networkingInfo);

            // Matchmaking loop
            while (handler.state == MatchmakingState.HOSTING){
                // Check own information to see if another account is already requesting a match
                if (handler.queryState(selfID) == MatchmakingState.FOUND_MATCH){
                    int opponentID = handler.queryOpponentID(selfID);
                    handler.startMatch(selfID, game, false, opponentID);
                    break;
                }

                // Update own information
                handler.setRecentTime(selfID, System.currentTimeMillis());

                // Wait 1 second before checking again if someone has joined
                TimeUnit.SECONDS.sleep(1);
            }

            handler.removeFromMatchmakingTable(selfID);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

class MatchmakingThread extends Thread {
    private final MatchmakingHandler handler;
    private final int selfID;
    private final GameType game;
    private final int elo;
    private final String networkingInfo;

    public MatchmakingThread(MatchmakingHandler handler, int selfID, GameType game, int elo, String networkingInfo){
        this.handler = handler;
        this.selfID = selfID;
        this.game = game;
        this.elo = elo;
        this.networkingInfo = networkingInfo;
    }

    public void run(){
        double starting_time = System.currentTimeMillis();
        handler.state = MatchmakingState.MATCHMAKING;

        // Add info to matchmaking table
        handler.addToTable(selfID, game, elo, networkingInfo);

        // Matchmaking loop
        while (handler.state == MatchmakingState.MATCHMAKING){
            // Check own information to see if another account is already requesting a match
            if (handler.queryState(selfID) == MatchmakingState.FOUND_MATCH){
                int opponentID = handler.queryOpponentID(selfID);
                handler.startMatch(selfID, game, true, opponentID);
                break;
            }

            // Update own information
            handler.setRecentTime(selfID, System.currentTimeMillis());
            handler.setEloRange(selfID, MatchmakingHandler.getNewMatchmakingRange(game, starting_time));
            handler.setNetworkingInfo(selfID, networkingInfo);

            // Search for other players who this can match with
            if (!handler.queryAllOtherIDs(selfID).isEmpty()) {
                for (int opponentID : handler.queryAllOtherIDs(selfID)) {
                    double msSinceLastCommunicated = (System.currentTimeMillis() - handler.queryRecentTime(opponentID));
                    if (msSinceLastCommunicated > 5000) {
                        System.out.printf("Disconnecting account with id %s because they have been inactive for >5 seconds\n", opponentID);
                        handler.removeFromMatchmakingTable(opponentID);
                    } else if (handler.canMatch(selfID, opponentID)) {
                        // Decide which player you are.
                        int thisPlayerNo = MatchmakingHandler.getRandomPlayerNo();
                        handler.setPlayerNo(selfID, thisPlayerNo);

                        // Notify the other that they are going to match with you
                        System.out.printf("Found acceptable match with id %s. Updating their information...\n", opponentID);
                        handler.setState(opponentID, MatchmakingState.FOUND_MATCH); // Set FOUND_MATCH
                        handler.setOpponentID(opponentID, selfID);                  // Set their opponent as your ID
                        int opponentPlayerNo = thisPlayerNo == 1 ? 2 : 1;
                        handler.setPlayerNo(opponentID, opponentPlayerNo);          // Set their player no. as the opposite of yours

                        // Start the match on your client
                        handler.startMatch(selfID, game, true, opponentID);
                        break;
                    }
                }
            } else {
                System.out.println("No other players found in matchmaking query...");
            }

            // After each matchmaking query, wait 1 second before repeating
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            TimeUnit.SECONDS.sleep(1);  // Stay in database for an additional second to let opponent get details
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        handler.removeFromMatchmakingTable(selfID);
    }
}