package networking.test;

import java.net.ServerSocket;
import java.util.*;

/**
 * Stubs class containing all required networking stubs for P3
 */
public class stubs {

    /**
     * Player Database Stubs
     */
    public static class PlayerDatabaseStubs {
        private boolean isConnected = false;
        private HashMap<String, Object> stubDatabase = new HashMap<>();
        private HashMap<String, Map<String, Integer>> playerElos = new HashMap<>();

        // Database connection stub
        public boolean connect() {
            System.out.println("[STUB] Connecting to database...");
            isConnected = true;
            return true;
        }

        // Database disconnection stub
        public boolean disconnect() {
            System.out.println("[STUB] Disconnecting from database...");
            isConnected = false;
            return true;
        }

        // Check connection status
        public boolean isConnected() {
            return isConnected;
        }

        // Player management stubs
        public void addPlayer(String globalUserId, Object playerData) {
            System.out.println("[STUB] Adding player: " + globalUserId);
            stubDatabase.put(globalUserId, playerData);
            playerElos.put(globalUserId, new HashMap<>());
        }

        public void removePlayer(String globalUserId) {
            System.out.println("[STUB] Removing player: " + globalUserId);
            stubDatabase.remove(globalUserId);
            playerElos.remove(globalUserId);
        }

        public Object getPlayerData(String globalUserId) {
            System.out.println("[STUB] Getting player data: " + globalUserId);
            return stubDatabase.get(globalUserId);
        }

        // Elo rating system stubs
        public int getElo(String globalUserId, String gameMode) {
            System.out.println("[STUB] Getting Elo for " + globalUserId + " in " + gameMode);
            return playerElos.getOrDefault(globalUserId, new HashMap<>()).getOrDefault(gameMode, 1000);
        }

        public void setElo(String globalUserId, String gameMode, int newElo) {
            System.out.println("[STUB] Setting Elo for " + globalUserId + 
                             " in " + gameMode + " to " + newElo);
            playerElos.computeIfAbsent(globalUserId, k -> new HashMap<>())
                     .put(gameMode, newElo);
        }

        public void printAllPlayers(int n) {
            System.out.println("[STUB] Printing first " + n + " players:");
            int count = 0;
            for (String userId : stubDatabase.keySet()) {
                if (count >= n) break;
                System.out.println("userID: " + userId);
                count++;
            }
        }

        public List<Object> findTopNPlayersByElo(int n, String gameMode) {
            System.out.println("[STUB] Finding top " + n + " players for " + gameMode);
            List<Object> topPlayers = new ArrayList<>();
            return topPlayers;
        }
    }


    /**
     * Game Server Stubs
     */
    public static class GameServerStubs {
        private boolean isRunning = false;
        private int port = 30000;
        private ServerSocket serverSocket;

        // Server hosting stub
        public boolean startServer() {
            System.out.println("[STUB] Starting server on port " + port);
            isRunning = true;
            return true;
        }

        // Shutting down server stub
        public boolean stopServer() {
            System.out.println("[STUB] Stopping server");
            isRunning = false;
            return true;
        }

        public boolean isRunning() {
            return isRunning;
        }

        public boolean acceptConnection() {
            System.out.println("[STUB] Accepting new connection");
            return true;
        }
    }

    /**
     * Player Data Stubs
     */
    public static class PlayerDataStubs {
        private Map<String, Integer> gameElos = new HashMap<>();
        private String username;
        private int userID;

        public void setUsername(String username) {
            System.out.println("[STUB] Setting username: " + username);
            this.username = username;
        }

        public String getUsername() {
            System.out.println("[STUB] Getting username");
            return username;
        }

        public void setUserID(int userID) {
            System.out.println("[STUB] Setting user ID: " + userID);
            this.userID = userID;
        }

        public int getUserID() {
            System.out.println("[STUB] Getting user ID");
            return userID;
        }

        public int getAPlayerElo(String gameMode) {
            System.out.println("[STUB] Getting Elo for game mode: " + gameMode);
            return gameElos.getOrDefault(gameMode, 1000);
        }

        public void setAPlayerElo(String gameMode, int elo) {
            System.out.println("[STUB] Setting Elo for game mode " + gameMode + " to " + elo);
            gameElos.put(gameMode, elo);
        }

        public String getAllPlayerElosString() {
        System.out.println("[STUB] Getting all player Elos as string:\n");
        return gameElos.toString();
        }
    }

    /**
     * Chat Feature Stubs
     */
    public static class ChatStubs {
        private List<String> chatHistory = new ArrayList<>();

        public boolean sendMessage(String sender, String message) {
            System.out.println("[STUB] " + sender + " sending chat message: " + message);
            chatHistory.add(sender + ": " + message);
            return true;
        }

        public List<String> getChatHistory() {
            return new ArrayList<>(chatHistory);
        }

        public boolean clearChat() {
            System.out.println("[STUB] Clearing chat history");
            chatHistory.clear();
            return true;
        }
    }
}