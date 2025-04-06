import static org.junit.Assert.*;

import networking.test.stubs;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.HashMap;

public class StubsTest {
    private stubs.PlayerDatabaseStubs dbStubs;
    private stubs.GameServerStubs serverStubs;
    private stubs.PlayerDataStubs playerStubs;
    private stubs.ChatStubs chatStubs;

    @Before
    public void setUp() {
        dbStubs = new stubs.PlayerDatabaseStubs();
        serverStubs = new stubs.GameServerStubs();
        playerStubs = new stubs.PlayerDataStubs();
        chatStubs = new stubs.ChatStubs();
    }

    // Database Stub Tests
    @Test
    public void testDatabaseConnection() {
        assertTrue("Database should connect successfully", dbStubs.connect());
        assertTrue("Database should be connected", dbStubs.isConnected());
        assertTrue("Database should disconnect successfully", dbStubs.disconnect());
        assertFalse("Database should be disconnected", dbStubs.isConnected());
    }

    @Test
    public void testPlayerManagement() {
        String testId = "player123";
        playerStubs.setUsername("TestPlayer");
        
        dbStubs.addPlayer(testId, playerStubs);
        assertNotNull("Should retrieve added player", dbStubs.getPlayerData(testId));
        
        dbStubs.removePlayer(testId);
        assertNull("Should not find removed player", dbStubs.getPlayerData(testId));
    }

    @Test
    public void testEloSystem() {
        String testId = "player123";
        String gameMode = "chess";
        int newElo = 1500;

        // First add a player to the database
        playerStubs.setUsername("TestPlayer");
        dbStubs.addPlayer(testId, playerStubs);
        
        dbStubs.setElo(testId, gameMode, newElo);
        assertEquals("Should return set Elo", newElo, dbStubs.getElo(testId, gameMode));
    }

    // Game Server Stub Tests
    @Test
    public void testServerOperations() {
        assertTrue("Server should start", serverStubs.startServer());
        assertTrue("Server should be running", serverStubs.isRunning());
        assertTrue("Server should accept connections", serverStubs.acceptConnection());
        assertTrue("Server should stop", serverStubs.stopServer());
        assertFalse("Server should not be running", serverStubs.isRunning());
    }

    // Player Data Stub Tests
    @Test
    public void testPlayerData() {
        String testUsername = "testUser";
        int testId = 123;
        String gameMode = "chess";
        int testElo = 1600;

        playerStubs.setUsername(testUsername);
        assertEquals("Username should match", testUsername, playerStubs.getUsername());

        playerStubs.setUserID(testId);
        assertEquals("UserID should match", testId, playerStubs.getUserID());

        playerStubs.setAPlayerElo(gameMode, testElo);
        assertEquals("Elo should match", testElo, playerStubs.getAPlayerElo(gameMode));
        
        assertNotNull("Should get Elo string", playerStubs.getAllPlayerElosString());
    }

    // Chat Feature Stub Tests
    @Test
    public void testChatFeatures() {
        String sender = "player1";
        String message = "Hello!";

        assertTrue("Message should send", chatStubs.sendMessage(sender, message));
        
        List<String> history = chatStubs.getChatHistory();
        assertFalse("Chat history should not be empty", history.isEmpty());
        assertEquals("Chat history should contain message", 
                    sender + ": " + message, 
                    history.get(0));

        assertTrue("Chat should clear", chatStubs.clearChat());
        assertTrue("Chat history should be empty after clear", 
                  chatStubs.getChatHistory().isEmpty());
    }

    // Integration Test Example
    @Test
    public void testGameSession() {
        try {
            // Setup
            assertTrue("Server should start", serverStubs.startServer());
            assertTrue("Database should connect", dbStubs.connect());
            
            // Player joins
            String playerId = "player1";
            playerStubs.setUsername(playerId);
            dbStubs.addPlayer(playerId, playerStubs);
            
            // Game actions
            assertTrue("Server should accept player", serverStubs.acceptConnection());
            assertTrue("Chat should work", chatStubs.sendMessage(playerId, "Ready!"));
            
            // Cleanup
            dbStubs.removePlayer(playerId);
            assertTrue("Server should stop", serverStubs.stopServer());
            assertTrue("Database should disconnect", dbStubs.disconnect());
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        }
    }
}