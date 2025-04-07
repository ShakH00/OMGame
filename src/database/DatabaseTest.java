package database;

import account.Account;
import account.AccountStorageUtility;
import account.statistics.MatchOutcomeHandler;
import account.statistics.MatchOutcomeInvalidError;
import account.statistics.StatisticType;
import game.GameType;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static account.AccountStorageUtility.*;

public class DatabaseTest {
    public static void main(String[] args) throws MatchOutcomeInvalidError {
//
//        // To delete all entries from accounts table for testing purpsoes
//        try (Connection conn = DatabaseConnection.getConnection()) {
//            try (PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM Accounts");
//                 PreparedStatement resetAIStmt = conn.prepareStatement("ALTER TABLE Accounts AUTO_INCREMENT = 1")) {
//
//                deleteStmt.executeUpdate();
//                resetAIStmt.executeUpdate();
//                System.out.println("All records deleted and auto-increment reset in Accounts table.");
//            }
//        } catch (SQLException e) {
//            System.err.println("Failed to reset table: " + e.getMessage());
//        }
//        int accountID=0;
//        String username="test";
//        String email="nebilawako@gmail.com";
//        String password="test@123";
//
//        int accountID1=1;
//        String username1="test1";
//        String email1="nebilawako@gmail.com1";
//        String password1="test@123";
//
//        Account testAccount = new Account(accountID, username, email, password);
//        Account testAccount1 = new Account(accountID1, username1, email1, password1);
//
//        testAccount.addFriend(1);
//        testAccount.addFriend(2);
//        testAccount.addFriend(3);
//
//        GameType game = GameType.CHESS;
//
//        HashMap<StatisticType, Integer> testAccountResults = new HashMap<>();
//        testAccountResults.put(StatisticType.WINS, 1);
//        testAccountResults.put(StatisticType.MATCHES_PLAYED, 1);
//        testAccountResults.put(StatisticType.NUMBER_OF_TURNS, 10);
//        testAccountResults.put(StatisticType.PIECES_CAPTURED, 11);
//        testAccountResults.put(StatisticType.CHECKMATES, 1);
//
//        HashMap<StatisticType, Integer> testAccountResults1 = new HashMap<>();
//        testAccountResults1.put(StatisticType.LOSSES, 1);
//        testAccountResults1.put(StatisticType.MATCHES_PLAYED, 1);
//        testAccountResults1.put(StatisticType.NUMBER_OF_TURNS, 10);
//        testAccountResults1.put(StatisticType.PIECES_CAPTURED, 11);
//        testAccountResults1.put(StatisticType.CHECKMATES, 1);
//
//        MatchOutcomeHandler.RecordMatchOutcome(game, 123, testAccount, testAccountResults, testAccount1, testAccountResults1);
//
//        DatabaseManager.saveAccount(testAccount);


        // Test to retrieve from database
        int testAccountId = 1;

        Account account = DatabaseManager.queryAccountByID(testAccountId);

        if (account != null) {
            System.out.println("Account Retrieved:");
            System.out.println("Username: " + account.getUsername());
            System.out.println("Email: " + account.getEmail());
            System.out.println("Password: " + account.getPassword());
            System.out.println("Friend IDs: " + account.getFriendIDs());
            System.out.println("Statistics: " + account.getStatisticsHashMap());
            System.out.println("Match History:");
            for (String[] match : account.getMatchHistory()) {
                if (match != null) {
                    System.out.println(String.join(", ", match));
                }
            }
        } else {
            System.out.println("No account found with ID: " + testAccountId);
        }



    }
}
