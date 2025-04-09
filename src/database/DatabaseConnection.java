package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is a class for managing MySQL database connections.
 * Provides methods to establish and close connections.
 */

public class DatabaseConnection {

    // Connection through XAMMPP
//    private static final String URL = "jdbc:mysql://localhost:3306/OMGAMEDB";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

    //Connection through Nova's server
    private static final String URL = "jdbc:mysql://database.omgame.club:3307/OMGAMEDB";
    private static final String USER = "tetriscat";
    private static final String PASSWORD = "ellendusk";


    /**
     * Establishes a connection to the database.
     *
     * @return A Connection object if the connection is successful, null otherwise.
     */
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

    /**
     * Closes the provided database connection.
     *
     * @param conn The connection to be closed.
     */
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
