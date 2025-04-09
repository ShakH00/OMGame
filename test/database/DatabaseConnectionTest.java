package database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class DatabaseConnectionTest {

        // Test case to check if a valid connection is established.
        @Test
        void testGetConnectionSuccess() {
            Connection conn = DatabaseConnection.getConnection();
            assertNotNull(conn, "Connection should not be null when database is reachable.");
            try {
                assertTrue(conn.isValid(2), "Connection should be valid.");
            } catch (SQLException e) {
                fail("SQLException should not occur when checking connection validity: " + e.getMessage());
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        }

        // Test case to check if an invalid connection (e.g., wrong URL or credentials) will return null.
        @Test
        void testGetConnectionFailure() {
            // DatabaseConnection with wrong credentials for the purpose of this test
            String invalidURL = "jdbc:mysql://invalid-host:3306/omgamedb";
            String invalidUser = "invalidUser";
            String invalidPassword = "invalidPassword";

            try {
                Connection conn = DriverManager.getConnection(invalidURL, invalidUser, invalidPassword);
                assertNull(conn, "Connection should be null for invalid credentials or URL.");
            } catch (SQLException e) {
                assertTrue(e.getMessage().contains("Communications link failure") || e.getMessage().contains("Access denied"));
            }
        }

        // Test case to ensure that the closeConnection method works properly.
        @Test
        void testCloseConnection() {
            Connection conn = DatabaseConnection.getConnection();
            assertNotNull(conn, "Connection should not be null before closing.");

            try {
                DatabaseConnection.closeConnection(conn);
                assertTrue(conn.isClosed(), "Connection should be closed.");
            } catch (SQLException e) {
                fail("SQLException should not occur when closing the connection: " + e.getMessage());
            }
        }
    }

