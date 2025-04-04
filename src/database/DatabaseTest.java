package database;

import java.sql.Connection;

public class DatabaseTest {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("Connection successful!");
            try {
                conn.close();
            } catch (Exception e) {
                System.err.println("Failed to close connection.");
            }
        } else {
            System.out.println("Connection failed!");
        }
    }
}
