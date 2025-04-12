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

    /**
     * @param selfID                    Id of the player swapping their status to matchmaking
     * @param game                      Game the player is matchmaking for
     * @param elo                       Elo rating of the player for the game they are matchmaking for
     * @param networkingInfo            Networking status of the player
     * @throws InterruptedException     If interrupted while managing the players data throws exception
     *
     * startMatchmaking is a function that when called, changes the status of the account that did so to matchmaking
     */
    public void startMatchmaking(int selfID, GameType game, int elo, String networkingInfo) throws InterruptedException {
        MatchmakingThread matchmakingThread = new MatchmakingThread(this, selfID, game, elo, networkingInfo);
        matchmakingThread.start();
    }

    /**
     * @param selfID                    Id of the player swapping their status to matchmaking
     * @param game                      Game the player is matchmaking for
     * @param elo                       Elo rating of the player for the game they are matchmaking for
     * @param roomCode                  Room code the players private match
     * @param networkingInfo            Networking status of the player
     * @throws InterruptedException     If interrupted while managing the players data throws exception
     *
     * startHosting is a function that when called, changes the status of the account that did so to hosting
     */
    public void startHosting(int selfID, GameType game, int elo, String roomCode, String networkingInfo) throws InterruptedException {
        System.out.println("ID " + selfID + " is now hosting.");
        HostingThread hostingThread = new HostingThread(this, selfID, game, elo, roomCode, networkingInfo);
        hostingThread.start();
    }

    /**
     * @param selfID                    ID of player trying to join
     * @param account                   account of joining player
     * @param roomCode                  Room code the players private match
     * @param networkingInformation     Networking status of the player
     * @return                          returns true if the room is found, otherwise return false
     *
     * tryJoinHost is a function that attempts to join a private match based on the input
     */
    public boolean tryJoinHost(int selfID, Account account, String roomCode, String networkingInformation) {
        System.out.println("Trying to join room with code: " + roomCode);
        if (queryRoomCodeInTable(roomCode) && queryHostIDByRoomCode(roomCode) != selfID) {
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
     * stopMatchmaking changes the players state to online
     */
    public void stopMatchmaking(){
        state = MatchmakingState.ONLINE;
    }

    /**
     * stopHosting changes the players state to online
     */
    public void stopHosting(){
        state = MatchmakingState.ONLINE;
    }


    /**
     * @param game                  gameType of which type of game to start
     * @param affectsElo            boolean value of if a match effects the players elo values
     * @param opponentID            ID value of player matched against
     *
     * startMatch takes a player out of matchmaking and into a match
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
     * @param game              Game the player is matchmaking for
     * @param startTime         time that the player started matchmaking
     * @return                  returns the  (+-) amount of elo the player can be matched with
     *
     * getNewMatchmakingRange increases the range of elo they can be matched with, increasing at certain time intervals
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

    /**
     * @param id1       id of the player
     * @param id2       id of the player they are trying to match with
     * @return          returns boolean value of if the other player falls in their elo range, and they fall in the other players elo range
     *
     * canMatch is a method that takes two players and returns whether or not they are able to match with eachother
     */
    protected boolean canMatch(int id1, int id2){
        return  (
                queryState(id1) == MatchmakingState.MATCHMAKING                  // Self is matchmaking
                && queryState(id2) == MatchmakingState.MATCHMAKING               // Other is matchmaking
                && queryGame(id1) == queryGame(id2)                              // Both are matchmaking for same game
                && Math.abs(queryElo(id1) - queryElo(id2)) < queryEloRange(id1)  // Elo difference acceptable for self
                && Math.abs(queryElo(id1) - queryElo(id2)) < queryEloRange(id2)  // Elo difference acceptable for other
        );
    }

    /**
     * @return  returns the matchmaking state of the player
     *
     * getter method for the players matchmaking state
     */
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
     * Returns either 1 or 2 randomly, signifies if the player is player 1 or 2
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

        String sql = "REPLACE INTO " +
                "Matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code, player_no) " +
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

    /**
     * @param id                the player's account id number
     * @param gameType          game type the player is matchmaking for
     * @param elo               elo rating for the player for the game they are matchmaking for
     * @param roomCode          room code of the hosting player
     * @param networkingInfo    Networking status of the player
     *
     * Adds a player into the matchmaking database as a player who is hosting
     */
    protected void addToTable(int id, GameType gameType, int elo, String roomCode, String networkingInfo){
        String stateString = MatchmakingState.HOSTING.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int eloRange = -1;
        int opponentID = -1;
        int playerNo = -1;

        String sql = "REPLACE INTO " +
                "Matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code, player_no) " +
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

    /**
     * @param id                the player's account id number
     * @param gameType          game type the player is matchmaking for
     * @param elo               elo rating for the player for the game they are matchmaking for
     * @param state             matchmaking state of the player
     * @param networkingInfo    Networking status of the player
     *
     * Adds a player into the matchmaking database as a player who is matchmaking
     */
    protected void addToTable(int id, GameType gameType, int elo, MatchmakingState state, String networkingInfo){
        String stateString = state.toString();
        String gameTypeString = gameType.toString();
        double startTime = System.currentTimeMillis();
        double recentTime = System.currentTimeMillis();
        int eloRange = -1;
        int opponentID = -1;
        String roomCode = "";
        int playerNo = -1;

        String sql = "REPLACE INTO " +
                "Matchmaking (id, state, game, start_time, recent_time, elo, elo_range, opponent_id, networking_info, room_code, player_no) " +
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


    /**
     * @return      returns the id of the opponent player if found
     */
    protected Integer queryOpponentID(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id            id of player whose state is being changed
     * @param newState      state the player is being changed to
     *
     * Updates the matchmaking state of the player
     */
    protected void setState(int id, MatchmakingState newState){
        String sql = "UPDATE Matchmaking SET state = ? WHERE id = ?";
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

    /**
     * @param id                    id of the player whose info is being updated/changed
     * @param newNetworkingInfo     networking info the player is being updated to have
     *
     * Updates the players networking information
     */
    protected void setNetworkingInfo(int id, String newNetworkingInfo){
        String sql = "UPDATE Matchmaking SET networking_info = ? WHERE id = ?";
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

    /**
     * @param id                id of the player whose time is being updated
     * @param newRecentTime     time that the player most recently interacted with the server
     *
     * Sets the most recent time the player has made an interaction with the server
     */
    protected void setRecentTime(int id, double newRecentTime){
        String sql = "UPDATE Matchmaking SET recent_time = ? WHERE id = ?";
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

    /**
     * @param id            id of the player whose elo range is being changed
     * @param newRange      amount (+-) elo the player can match with
     *
     * Updates the elo range of the player
     */
    protected void setEloRange(int id, int newRange){
        String sql = "UPDATE Matchmaking SET elo_range = ? WHERE id = ?";
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

    /**
     * @param id                id of the player whose opponent id is being changed
     * @param newOpponentID     id of the opponent
     *
     * Updates the opponent ID of the player
     */
    protected void setOpponentID(int id, int newOpponentID){ //
        String sql = "UPDATE Matchmaking SET opponent_ID = ? WHERE id = ?";
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

    /**
     * @param id                id of the player whose player number is being updated
     * @param newPlayerNo       new player number of the player
     *
     * Updates the player number of the player
     */
    protected void setPlayerNo(int id, int newPlayerNo){ //
        String sql = "UPDATE Matchmaking SET player_no = ? WHERE id = ?";
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

    /**
     * @param id    id of the account being removed from the matchmaking table
     *
     * removes the specified player from the matchmaking table in the database
     */
    protected void removeFromMatchmakingTable(int id){
        String sql = "DELETE FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id        id of the player querying for other ids
     * @return          returns all other player ids
     *
     * returns the id of all other players
     */
    protected ArrayList<Integer> queryAllOtherIDs(int id){
        String sql = "SELECT * FROM Matchmaking";
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

    /**
     * @param id        input id value to check the state of the account corresponding to it
     * @return          returns the current matchmaking state of the account
     *
     * returns the state of the account corresponding to the input id
     */
    protected MatchmakingState queryState(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id        input id value to check the gameType of the account corresponding to it
     * @return          returns the current gameType of the account
     *
     * returns the gameType of the account corresponding to the input id
     */
    protected GameType queryGame(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id        input id value to check the most recent server interaction of the account corresponding to it
     * @return          returns the most recent server interaction of the account
     *
     * returns the time of the most recent server interaction of the account corresponding to the input id
     */
    protected Double queryRecentTime(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id        input id value to check the elo of the account corresponding to it
     * @return          returns the current elo of the account for the game being queued for
     *
     * returns the elo of the account corresponding to the input id for the game being matchmaked for
     */
    protected Integer queryElo(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id        input id value to check the elo range of the account corresponding to it
     * @return          returns the current elo range of the account
     *
     * returns the elo range of the account corresponding to the input id
     */
    protected Integer queryEloRange(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param id        input id value to check the networking information of the account corresponding to it
     * @return          returns the current networking information of the account
     *
     * gets the networking information of the account corresponding to the input id
     */
    protected String queryNetworkingInfo(int id){
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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

    /**
     * @param roomCode  input room code value to check if it exists in the matchmaking database
     * @return          returns boolean of if the room code exists or not
     */
    protected boolean queryRoomCodeInTable(String roomCode){
        String sql = "SELECT * FROM Matchmaking WHERE room_code = ?";
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

    /**
     * @param roomCode  input room code value to check use to look for the host of the room
     * @return          returns host id if found from input room code
     */
    protected int queryHostIDByRoomCode(String roomCode){
        String sql = "SELECT * FROM Matchmaking WHERE room_code = ?";
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
        String sql = "SELECT * FROM Matchmaking WHERE id = ?";
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