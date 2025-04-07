package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("Connection successful!");

            String sql = "INSERT INTO accounts (name, email, phone, password) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "Elijah");
                stmt.setString(2, "nebila@example.com");
                stmt.setString(3, "1234567890");
                stmt.setString(4, "securepassword123");

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Row inserted successfully!");
                } else {
                    System.out.println("Insert failed.");
                }

            } catch (SQLException e) {
                System.err.println("SQL Error: " + e.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close connection.");
                }
            }

        } else {
            System.out.println("Connection failed!");
        }
    }
}
