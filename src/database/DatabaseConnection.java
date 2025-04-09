package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/omgamedb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
//    private static final String URL = "jdbc:mysql://database.omgame.club:3307/Games";
//    private static final String USER = "tetriscat";
//    private static final String PASSWORD = "ellendusk";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
        }
        return null;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
}
