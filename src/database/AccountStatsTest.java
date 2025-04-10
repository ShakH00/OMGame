package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class AccountStatsTest {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            System.err.println("Failed to connect to database.");
            return;
        }

        try {
            insertDummyAccount(conn, 100003001, "saqib", "saqib@email.com", "pass1234");
//            insertDummyAccount(conn, 100004002, "notsaqib", "te2@email.com", "pass5678");
//            insertDummyAccount(conn, 100002203, "perhapssaqib", "te@email.com", "pass9012");

            System.out.println("✅ Dummy accounts seeded successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting account: " + e.getMessage());
        }
    }

    private static void insertDummyAccount(Connection conn, int id, String username, String email, String password) throws SQLException {
        String statistics = generateStatsString();
        String matchHistory = generateMatchHistoryString(username);
        String friends = "100001002,100001003"; // For simplicity, each account is friends with the other two

        String sql = "INSERT INTO accounts (id, Username, Email, Password, Statistics, MatchHistory, friends) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, statistics);
            stmt.setString(6, matchHistory);
            stmt.setString(7, friends);
            stmt.executeUpdate();
        }
    }

    private static String generateStatsString() {
        return "Tictactoe#Elo=i9000, Win Rate=d0.999000, Wins=i9832, Losses=i0, Draws=i1, Matches Played=i100000" +
                "%Chess#Elo=i8322, Win Rate=d1.000000, Wins=i113, Losses=i0, Draws=i4, Matches Played=i117" +
                "%Connect4#Elo=i4200, Win Rate=d0.8634000, Wins=i10, Losses=i5, Matches Played=i15";
    }

    private static String generateMatchHistoryString(String username) {
        // Only one real match, rest are "null"
        StringBuilder sb = new StringBuilder();
        sb.append("Win&Chess&").append(username).append("&1&1000&123");
        for (int i = 1; i < 10; i++) {
            sb.append("#null&null&null&null&null&null");
        }
        return sb.toString();
    }

}
