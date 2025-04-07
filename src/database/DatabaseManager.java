package database;

import account.Account;
import account.AccountStorageUtility;
import game.GameType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {

    /**
     * @return          Returns all accounts that are active and not a guest account
     *
     * queryAllAccounts returns all account in the database
     * NOTE: the return value can be anything but for stubs I just made it return a list of account
     */


    public static ArrayList<Account> queryAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String statisticsString = rs.getString("statistics");
                String friendsString = rs.getString("friends");
                String matchHistoryString = rs.getString("matchhistory");

                // Parse stats, friends, match history using your utility
                var statistics = AccountStorageUtility.statisticsFromString(statisticsString);
                var friends = AccountStorageUtility.friendIDsFromString(friendsString);
                var matchHistory = AccountStorageUtility.matchHistoryFromString(matchHistoryString);

                // Use the full constructor (you have one for this!)
                Account account = new Account(id, username, email, password, friends, statistics, matchHistory);

                accounts.add(account);
            }

        } catch (SQLException e) {
            System.err.println("❌ queryAllAccounts failed: " + e.getMessage());
        }

        return accounts;
    }

    /**
     * @author Logan Olszak
     * @param game         which GameType the returned accounts are queued for
     * @return             Returns all players that are currently queueing for a match of a certain game
     *
     * queryPlayerPool returns a list of all players that are currently queued for a match of a specific game
     */
    public static ArrayList<Account> queryAccountPool(GameType game) {
        ArrayList<Account> gameQueue = new ArrayList<Account>();
        ArrayList<Account> allAccounts = queryAllAccounts();
        for (Account current : allAccounts) {
            if (current.getQueuedFor() != null) {
                if (current.getQueuedFor() == game) {
                    gameQueue.add(current);
                }
            }
        }
        return gameQueue;
    }

    public static Account queryAccountByID(Integer id) {
        return new Account();
    }


    /**
     * @return Returns an account from the database given a phone number
     * get account returns an account from the database
     */
    public static Account getAccount(Integer phoneNumber) {return new Account();}


    /**
     * @return Returns an account from the database
     * get account returns an account from the database
     */
    public static Account getAccount(String email) {return new Account();}

    /**
     * @return returns true if an account was saved to the database
     * saves an account to the database
     */
    public static Boolean saveAccount(Account account) {return true;}

    /**
     * @return returns true if an account was deleted from the database
     *
     */
    public static Boolean deleteAccount(String email) {return true;}

    /**
     * @return returns true if an account was deleted from the database
     *
     */
    public static Boolean deleteAccount(Integer userID) {return true;}


}
