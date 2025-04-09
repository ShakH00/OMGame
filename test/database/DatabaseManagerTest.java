package database;

import account.Account;
import account.statistics.StatisticType;
import game.GameType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {

    static void resetDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (
                    PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM Accounts");
                    PreparedStatement resetStmt = conn.prepareStatement("ALTER TABLE Accounts AUTO_INCREMENT = 1")
            ) {
                deleteStmt.executeUpdate();
                resetStmt.executeUpdate();
                System.out.println("Database reset successfully.");
            }
        } catch (Exception e) {
            fail("Failed to reset database: " + e.getMessage());
        }
    }

    @Test
    void testStoreAccount() {
        resetDatabase();
        Account account = new Account(1,"testuser", "test@example.com", "pass123");
        account.addFriend(1);
        account.addFriend(2);

        GameType game = GameType.CHESS;
        HashMap<StatisticType, Integer> stats = new HashMap<>();
        stats.put(StatisticType.WINS, 3);
        stats.put(StatisticType.MATCHES_PLAYED, 3);
        stats.put(StatisticType.CHECKMATES, 1);

        DatabaseManager.saveAccount(account);
        Account stored = DatabaseManager.queryAccountByID(1);
        assertNotNull(stored);
        assertEquals("testuser", stored.getUsername());
    }

    @Test
    void testRetrieveAccount() {
        Account retrieved = DatabaseManager.queryAccountByID(1);
        assertNotNull(retrieved, "Account should exist in DB");
        assertEquals("testuser", retrieved.getUsername());
        assertEquals("test@example.com", retrieved.getEmail());
        assertEquals("pass123", retrieved.getPassword());

        assertEquals(2, retrieved.getFriendIDs().size());
        assertTrue(retrieved.getFriendIDs().contains(1));
        assertTrue(retrieved.getFriendIDs().contains(2));

    }

}
