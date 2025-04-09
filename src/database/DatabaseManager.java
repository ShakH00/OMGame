package database;

import account.Account;
import account.AccountStorageUtility;
import account.statistics.AStatistics;
import account.statistics.StatisticType;
import account.statistics.StatisticsCheckers;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import game.GameType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {

    /**
     * @return Returns all accounts that are active and not a guest account
     * <p>
     * queryAllAccounts returns all account in the database
     * NOTE: the return value can be anything but for stubs I just made it return a list of account
     */


    public static ArrayList<Account> queryAllAccounts() {
        String sql = "SELECT * FROM accounts";
        Connection conn = DatabaseConnection.getConnection();
        ArrayList<Account> accounts = new ArrayList<>();

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String username = rs.getString("Username");
                    String email = rs.getString("Email");
                    String password = rs.getString("Password");

                    String friendsString = rs.getString("Friends");
                    ArrayList<Integer> friends = AccountStorageUtility.friendIDsFromString(friendsString);

                    String statisticsString = rs.getString("Statistics");
                    HashMap<GameType, AStatistics> statistics = AccountStorageUtility.statisticsFromString(statisticsString);

                    String matchHistoryString = rs.getString("MatchHistory");
                    String[][] matchHistory = AccountStorageUtility.matchHistoryFromString(matchHistoryString);

                    accounts.add(new Account(id, username, email, password, friends, statistics, matchHistory));
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return accounts;
    }

    /**
     * @param game which GameType the returned accounts are queued for
     * @return Returns all players that are currently queueing for a match of a certain game
     * <p>
     * queryPlayerPool returns a list of all players that are currently queued for a match of a specific game
     * @author Logan Olszak
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


    /**
     * @return Returns an account from the database given a phone number
     * get account returns an account from the database
     */
    public static Account queryAccountByID(Integer id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        Account account = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String username = rs.getString("Username");
                    String email = rs.getString("Email");
                    String password = rs.getString("Password");

                    String friendsString = rs.getString("Friends");
                    ArrayList<Integer> friends = AccountStorageUtility.friendIDsFromString(friendsString);

                    String statisticsString = rs.getString("Statistics");
                    HashMap<GameType, AStatistics> statistics = AccountStorageUtility.statisticsFromString(statisticsString);

                    String matchHistoryString = rs.getString("MatchHistory");
                    String[][] matchHistory = AccountStorageUtility.matchHistoryFromString(matchHistoryString);

                    account = new Account(id, username, email, password, friends, statistics, matchHistory);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return account;
    }


    /**
     * @return Returns an account from the database
     * get account returns an account from the database
     */
    public static Account queryAccountByEmail(String email) {
        String sql = "SELECT * FROM accounts WHERE email = ?";
        Connection conn = DatabaseConnection.getConnection();
        Account account = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    int id = rs.getInt("ID");

                    String friendsString = rs.getString("Friends");
                    ArrayList<Integer> friends = AccountStorageUtility.friendIDsFromString(friendsString);

                    String statisticsString = rs.getString("Statistics");
                    HashMap<GameType, AStatistics> statistics = AccountStorageUtility.statisticsFromString(statisticsString);

                    String matchHistoryString = rs.getString("MatchHistory");
                    String[][] matchHistory = AccountStorageUtility.matchHistoryFromString(matchHistoryString);

                    account = new Account(id, username, email, password, friends, statistics, matchHistory);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return account;
    }

    /**
     * @return Returns an account from the database given a username
     * get account returns an account from the database
     */
    public static Account queryAccountByUsername(String username) {
        String sql = "SELECT * FROM accounts WHERE username = ?";
        Connection conn = DatabaseConnection.getConnection();
        Account account = null;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String password = rs.getString("Password");
                    String email = rs.getString("Email");
                    int id = rs.getInt("ID");

                    String friendsString = rs.getString("Friends");
                    ArrayList<Integer> friends = AccountStorageUtility.friendIDsFromString(friendsString);

                    String statisticsString = rs.getString("Statistics");
                    HashMap<GameType, AStatistics> statistics = AccountStorageUtility.statisticsFromString(statisticsString);

                    String matchHistoryString = rs.getString("MatchHistory");
                    String[][] matchHistory = AccountStorageUtility.matchHistoryFromString(matchHistoryString);

                    account = new Account(id, username, email, password, friends, statistics, matchHistory);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return account;
    }



    /**
     * @return returns true if an account was saved to the database
     * saves an account to the database if user doesnt already exist
     * updates current information if user exists.
     */

    public static Boolean saveAccount(Account account) {
        String username = account.getUsername();
        String email = account.getEmail();
        String password = account.getPassword();
        String friends = AccountStorageUtility.friendIDsToString(account.getFriendIDs());
        String statistics = AccountStorageUtility.statisticsToString(account.getStatisticsHashMap());
        String matchHistory = AccountStorageUtility.matchHistoryToString(account.getMatchHistory());

        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            try {
                // Step 1: Check if account exists using username and email
                String checkSql = "SELECT id FROM Accounts WHERE username = ? AND email = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, username);
                    checkStmt.setString(2, email);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        // Account exists → Update
                        int id = rs.getInt("id");
                        String updateSql = "UPDATE Accounts SET password = ?, friends = ?, statistics = ?, matchhistory = ? WHERE id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setString(1, password);
                            updateStmt.setString(2, friends);
                            updateStmt.setString(3, statistics);
                            updateStmt.setString(4, matchHistory);
                            updateStmt.setInt(5, id);

                            int rowsUpdated = updateStmt.executeUpdate();
                            System.out.println("Account updated successfully.");
                            return rowsUpdated > 0;
                        }
                    } else {
                        // Account doesn't exist → Insert
                        String insertSql = "INSERT INTO Accounts (username, email, password, friends, statistics, matchhistory) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setString(1, username);
                            insertStmt.setString(2, email);
                            insertStmt.setString(3, password);
                            insertStmt.setString(4, friends);
                            insertStmt.setString(5, statistics);
                            insertStmt.setString(6, matchHistory);

                            int rowsInserted = insertStmt.executeUpdate();
                            System.out.println("New account inserted successfully.");
                            return rowsInserted > 0;
                        }
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("Connection failed");
            return false;
        }
    }

    public static Account loginAccount(String username, String password) throws DecryptionFailedException, EncryptionFailedException {
        Account accountFromDB = queryAccountByUsername(username);
        //Decrypt the account before checking the password
        accountFromDB = DecryptionAuthentication.decryptAccount(accountFromDB);

        if (accountFromDB != null) {
            if (accountFromDB.getPassword().equals(password)) {
                //Encrypt the account again
                accountFromDB = EncryptionAuthentication.encryptAccount(accountFromDB);
                System.out.println("Account logged in successfully");
                return accountFromDB;
            }
        }

        //Encrypt the account even if not verified
        accountFromDB = EncryptionAuthentication.encryptAccount(accountFromDB);
        return null;
    }




    /**
     * Deletes an account from the database based on the email.
     *
     * @param email the email of the account to be deleted
     * @return true if an account was deleted, false otherwise
     */
    public static Boolean deleteAccount(String email) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }
        try {
            String sql = "DELETE FROM Accounts WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Account with email " + email + " deleted successfully.");
                    return true;
                } else {
                    System.out.println("No account found with email " + email + ".");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    /**
     * Deletes an account from the database based on the userID.
     *
     * @param userID the ID of the account to be deleted
     * @return true if an account was deleted, false otherwise
     */

    public static Boolean deleteAccount(Integer userID) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }
        try {
            String sql = "DELETE FROM Accounts WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Account with ID " + userID + " deleted successfully.");
                    return true;
                } else {
                    System.out.println("No account found with ID " + userID + ".");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }

    }


    /**
     * Get a temporary ID for storing a guest Account in the matchmaking table
     * @return  int unique ID which should be overwritten if the guest is converted to an Account
     */
    public static int getTempID(){
        String sql = "SELECT GREATEST((SELECT MAX(id) FROM accounts), (SELECT MAX(id) FROM matchmaking)) + 100 AS temp_id";
        Connection conn = DatabaseConnection.getConnection();
        int tempID = -1;

        if (conn != null) {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    tempID = rs.getInt("temp_id");
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                DatabaseConnection.closeConnection(conn);
            }
        } else {
            System.out.println("No connection available");
        }
        return tempID;
    }
}
