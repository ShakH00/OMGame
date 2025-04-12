package account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.DatabaseManager;
import database.EncryptionAuthentication;
import game.GameType;
import account.statistics.*;
import matchmaking.MatchmakingHandler;

import static database.EncryptionAuthentication.encryptionDriver;

public class Account {
    /**
     * GameType queuedFor; shows the current game the account is queued for, null if not queued
     */
    private GameType queuedFor;

    /**
     * boolean for isGuest; true if the Account is a guest Account, otherwise false
     */
    private boolean isGuest;

    /**
     * Integer ID; unique for this Account, given by the Database. 16 chars long, or is Null for a guest Account
     */
    private Integer id;

    /**
     * String for the username associated with this Account
     */
    private String username;

    /**
     * String for the email associated with this Account; may be needed for password recovery
     */
    private String email;

    /**
     * String for the password associated with this Account
     */
    private String password;

    /**
     * Accounts ArrayList for other Accounts on the friends list of this Account
     */
    private ArrayList<Integer> friends;

    public HashMap<GameType, AStatistics> statistics;

    private String[][] matchHistory;

    private final MatchmakingHandler matchmakingHandler = new matchmaking.MatchmakingHandler();

    /**
     * Initialize a guest Account
     */
    public Account() {
        // Properties only possessed by permanent Accounts
        this.isGuest = true;
        this.id = null;
        this.username = null;
        this.email = null;
        this.password = null;
        this.friends = null;

        // Properties possessed by both guest and permanent Accounts
        this.statistics = new HashMap<>();
        statistics.put(GameType.CHESS, new StatisticsChess());
        statistics.put(GameType.CHECKERS, new StatisticsCheckers());
        statistics.put(GameType.CONNECT4, new StatisticsConnect4());
        statistics.put(GameType.TICTACTOE, new StatisticsTicTacToe());
        this.matchHistory = new String[10][6];  // Store information about the past 10 matches, each with 6 fields.
        this.queuedFor = null;
    }


    /**
     * Initialize an Account with a given id, username, email, and password
     * @param username String username for the account
     * @param email    String email for account
     * @param password String password for the account
     */
    public Account(String username, String email, String password) {
        // Properties only possessed by permanent Accounts
        this.isGuest = false;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();

        // Properties possessed by both guest and permanent Accounts
        this.statistics = new HashMap<>();
        statistics.put(GameType.CHESS, new StatisticsChess());
        statistics.put(GameType.CHECKERS, new StatisticsCheckers());
        statistics.put(GameType.CONNECT4, new StatisticsConnect4());
        statistics.put(GameType.TICTACTOE, new StatisticsTicTacToe());
        this.matchHistory = new String[10][6];  // Store information about the past 10 matches, each with 6 fields.
        this.queuedFor = null;
    }

    /**
     * Initialize an Account with all parameters. Only use when loading from database.
     * @param id            int unique id (primary key)
     * @param username      String unique username
     * @param email         String unique email
     * @param password      String unique password (decrypted!)
     * @param friends       ArrayList of friend IDs
     * @param statistics    HashSet of game types and their corresponding statistics
     * @param matchHistory  String array with summaries of previous matches
     */
    public Account(
            int id,
            String username,
            String email,
            String password,
            ArrayList<Integer> friends,
            HashMap<GameType, AStatistics> statistics,
            String[][] matchHistory)
    {
        this.id = id;
        this.isGuest = false;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = friends;
        this.statistics = statistics;
        this.matchHistory = matchHistory;
        this.queuedFor = null;
    }

    /**
     * If the Player started with a guest Account, update it to form a permanent (i.e. non-guest) Account
     * @param username String username for the account
     * @param email    String email for account
     * @param password String password for the account
     */
    public void convertToNonGuestAccount(int id, String username, String email, String password) {
        // Properties only possessed by permanent Accounts
        this.isGuest = false;
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();
    }

    /**
     * Add a HashMap of statistics to
     *
     * @param game          GamesEnum game to update statistics for
     * @param addStatistics HashMap which assigns each statistic an Integer value to increase by
     */
    public void updateStatistics(GameType game, HashMap<StatisticType, Integer> addStatistics) {
        statistics.get(game).addStatistics(addStatistics);
    }

    /**
     * Update this account's Elo for some game in GamesEnum.
     *
     * @param game   GameType game to update Elo for
     * @param newElo Integer value to assign Elo
     */
    public void updateElo(GameType game, Integer newElo) {
        statistics.get(game).updateElo(newElo);
    }

    /**
     * Get the player's Elo for some game. This is a wrapper function for getStatistic()
     *
     * @param game GamesEnum game to get Elo for
     * @return int Elo for the game
     */
    public int getElo(GameType game) {
        return (int) getStatistic(game, StatisticType.ELO);
    }


    /**
     * Set the player's queuedFor variable, this is used to define what game a player is queueing for
     *
     * @param type GameType that the player is queuing for
     */
    public void setQueuedFor(GameType type) {
        this.queuedFor = type;
    }

    /**
     * Empty the player's queuedFor variable, done whenever a player leaves a queue
     */
    public void clearQueuedFor() {
        this.queuedFor = null;
    }

    /**
     * Getter for the player's queuedFor variable.
     *
     * @return GameType that the player is currently queued for
     */
    public GameType getQueuedFor() {
        return queuedFor;
    }

    /**
     * Get a certain Statistic for the player from a given game.
     *
     * @param game      GamesEnum game to get statistic for
     * @param statistic StatisticType which statistic to get
     * @return Number (Integer or Double) for the statistic
     */
    public Number getStatistic(GameType game, StatisticType statistic) {
        return statistics.get(game).getStatistic(statistic);
    }

    /**
     * Adds a String[5] representing the most recent match results to the match history String[][]
     *
     * @param strings String[] containing details about the player's last match
     */
    public void logMatch(String[] strings) {
        // Move each match history String[] up by 1 index.
        for (int i = 8; i >= 0; i--) {
            if (matchHistory[i] != null) {
                matchHistory[i + 1] = matchHistory[i];
            }
        }
        // Set the first index as the most recent match history String[]
        matchHistory[0] = strings;
    }

    /**
     * Get the match history for this Account to display on the profile. Includes header row
     * @return String[][] for MatchHistory, including header row
     */
    public String[][] getMatchHistoryWithHeader() {
        String[][] matchHistoryOutput = new String[1 + matchHistory.length][6];

        // Add header to the output array
        matchHistoryOutput[0] = new String[]{"Result", "Game", "Opponent Name", "Opponent Elo", "Opponent ID", "Date"};

        // Add rows to the output array
        System.arraycopy(matchHistory, 0, matchHistoryOutput, 1, matchHistory.length);

        return matchHistoryOutput;
    }

    /**
     * Get the match history for this Account to display on the profile. Does not include header row.
     * @return String[][] for MatchHistory, including header row
     */
    public String[][] getMatchHistory() {
        return matchHistory;
    }

    /**
     * Get a String[] containing the Account's statistics for the game in a predetermined order
     *
     * @param game GamesEnum for which game to get statistics for
     * @return String[] containing statistic values as strings
     */
    public String[] getGameStatistics(GameType game) {
        StatisticType[] order = statistics.get(game).getAcceptedStatistics();
        return getGameStatistics(game, order);
    }

    /**
     * Get a String[] containing the Account's statistics for the game in the specified order
     *
     * @param game  GamesEnum for which game to get statistics for
     * @param order StatisticType array that determines which statistics will be returned and in what order
     * @return String[] containing the specified statistics in the same order
     */
    public String[] getGameStatistics(GameType game, StatisticType[] order) {
        String[] output = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            StatisticType statistic = order[i];
            Number value = statistics.get(game).getStatistic(statistic);

            if (value instanceof Integer) {
                output[i] = value.toString();
            } else if (value instanceof Double) {
                output[i] = String.format("%.2f", value);   // Two decimals for a double.
            }
        }
        return output;
    }


    /**
     * Get a String[] containing the Account's combined generic statistics (wins, losses, draws) for the specified games
     *
     * @param games HashSet of GamesEnums for which games to include in the combined stats
     * @return String[] containing the combined statistics
     */
    public String[] getCombinedStatistics(HashSet<GameType> games) {
        StatisticType[] order = StatisticType.values();
        return getCombinedStatistics(games, order);
    }

    /**
     * Get a String[] containing the Account's combined statistics (in a specific order) for the specified games
     *
     * @param games HashSet of GamesEnums for which games to include in the combined stats
     * @param order StatisticType array that determines which statistics will be returned and in what order
     * @return String[] containing the combined statistics
     */
    public String[] getCombinedStatistics(HashSet<GameType> games, StatisticType[] order) {
        // Create a new CombinedStatistics object
        HashSet<AStatistics> setOfStatistics = new HashSet<>();
        for (GameType game : games) {
            setOfStatistics.add(statistics.get(game));
        }
        StatisticsCombined statisticsCombined = new StatisticsCombined(setOfStatistics);

        // Get the string for combined statistics from CombinedStatistics object
        String[] output = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            StatisticType statistic = order[i];
            Number value = statisticsCombined.getStatistic(statistic);

            if (value instanceof Integer) {
                output[i] = value.toString();
            } else if (value instanceof Double) {
                output[i] = String.format("%.2f", value);   // Two decimals for a double.
            }
        }
        return output;
    }

    /**
     * Checks to see if user is either guest or not.
     *
     * @return Boolean if guest is valid or not.
     */

    public boolean getIsGuest() {
        return isGuest;
    }

    /**
     * Return the username of the Account, or "Guest" if it is a guest account
     *
     * @return String username of the Account
     */
    public String getUsername() {
        return isGuest ? "Guest" : username;
    }

    /**
     * Return the password of the Account
     *
     * @return String password of the Account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the ID of the Account, or "-1" if it is a guest account
     *
     * @return int ID of the Account
     */
    public int getID() {
        return isGuest ? -1 : id;
    }

    /**
     * Return the statistics hashmap of the Account
     *
     * @return HashMap that relates GameType to a Statistics object in this Account
     */
    public HashMap<GameType, AStatistics> getStatisticsHashMap() {
        return statistics;
    }

    /**
     * Sets the username (display name) for the account.
     *
     * @param username the new username to set
     * username must be 1-64 characters and may not contain any characters
     * from the disallowed characters list
     * @return boolean if the username change was successful or not
     */
    public boolean setUsername(String username) {
        if(isValidUsername(username)) {
            this.username = username;
            return true;
        }
        return false;
    }

    /**
     * Sets the email address associated with the account.
     *
     * @param email the new email address to set
     */
    public boolean setEmail(String email) {
        if(isValidEmail(email)){
            this.email = email;

            return true;
        }
        return false;
    }

    /**
     * Sets the password for the account.
     *
     * @param password the new password to set
     * @return boolean, true if the password change was successful
     */

    public boolean setPassword(String password) {
        if(isValidPassword(password)){
            this.password = password;
            return true;
        }
        return false;
    }

    /**
     * Checks whether the given username is valid (not null or empty).
     *
     * @param username the username to validate
     * @return true if the username is valid; false otherwise
     */

    private boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Checks whether the given email is valid (must contain "@" and ".").
     *
     * @param email the email to validate
     * @return true if the email is valid; false otherwise
     */

    private boolean isValidEmail(String email) {
        String[] disallowedChars = {" ", "#", ",", "!", "=", "+"};
        for(String character: disallowedChars){
            if (email.contains(character)){
                return false;
            }
        }
        String[] requiredChars = {"@", "."};
        for (String character : requiredChars){
            if (!email.contains(character)){
                return false;
            }
        }
        return email.length() <= 64 && email.length() >= 1;
    }

    /**
     * Checks whether the password is valid (at least 6 characters).
     *
     * @param password the password to validate
     * @return true if the password is valid; false otherwise
     */

    private boolean isValidPassword(String password) {
        if(password.contains(" ")){return false;}
        if(password == null){return false;}
        if(password.length() < 6 || password.length() > 64){return false;}
        return true;
    }


    /**
     * Updates the account information after validating all provided fields.
     * Throws IllegalArgumentException if any input is invalid.
     *
     * @param username the new display name for the account
     * @param email    the new email address for the account
     * @param password the new password (must be at least 6 characters)
     */
    public void updateAccountInfo(String username, String email, String password) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }

        setUsername(username);
        setEmail(email);
        setPassword(password);
    }

    /**
     * @param friendID, adds the friend to the friends-list of the player.
     */
    public void addFriend(int friendID) {
        this.friends.add(friendID);
    }

    /**
     * Removes a Friend from the Account's friend list
     * @param friend    Account belonging to friend that is to be removed from friends list
     * @return          true if friend was in friends list; false otherwise
     */
    public boolean removeFriend(int friend) {
        if (this.friends.contains(friend)){
            this.friends.remove((Integer) friend);
            return true;
        }
        return false;
    }

    /**
     * Get references to the Accounts of all friends
     * @return  ArrayList containing the Account of each friend
     */
    public ArrayList<Account> getFriends(){
        ArrayList<Account> friendsList = new ArrayList<>();
        for (int friendID : friends){
            friendsList.add(DatabaseManager.queryAccountByID(friendID));
        }
        return friendsList;
    }

    /**
     * Get the IDs of all friends
     * @return  ArrayList containing the ID of each friend
     */
    public ArrayList<Integer> getFriendIDs(){
        return friends;
    }

    /**
     * Try to log in with a given username and password. Updates Player Account and returns true if successful.
     * @param username              String username to try to log in with
     * @param password              String password to try to log in with
     * @return                      true only if the username and password match an existing Account in the database
     */
    public boolean TryLoginWithUsernameAndPassword(String username, String password) throws EncryptionFailedException {
        Account accountFromDB = DatabaseManager.queryAccountByUsername(username);
        String encrypted_pass = encryptionDriver(password);
        if (accountFromDB != null && accountFromDB.password.equals(encrypted_pass)) {
            // Update current account object with data from DB
            this.isGuest = false;
            this.id = accountFromDB.id;
            this.username = accountFromDB.username;
            this.email = accountFromDB.email;
            this.password = accountFromDB.password;
            this.friends = accountFromDB.friends;
            this.statistics = accountFromDB.statistics;
            this.matchHistory = accountFromDB.matchHistory;
            return true;
        }
        return false;
    }
    public Account guestToPermanentAccount(String username, String email, String password) {
        // Attempt to create the permanent account using the CreateAccount helper class
        Account newAccount;
        try {
            newAccount = CreateAccount.createAccount(username, email, password);
        }catch(EncryptionFailedException e){
            newAccount = null;
        }


        if (newAccount != null) {
            // If creation succeeded, pull the new account data from the DB
            Account createdAccount = DatabaseManager.queryAccountByUsername(username);

            if (createdAccount != null) {
                // Update the current Account object with the new permanent data
                this.id = createdAccount.getID();
                this.username = createdAccount.getUsername();
                this.email = createdAccount.getEmail();
                this.password = createdAccount.getPassword();
                this.isGuest = false; // Mark this as a permanent account
                this.friends = createdAccount.getFriendIDs();
                this.matchHistory = createdAccount.getMatchHistory();
                this.statistics = createdAccount.getStatisticsHashMap();
            }
        }

        return newAccount;
    }

    public String getEmail() {
        return  email;
    }

    // Setter for id
    public void setID(Integer id) {
        this.id = id;
    }

    /**
     * Get the object that is in charge of matchmaking for this Account.
     * @return
     */
    public matchmaking.MatchmakingHandler getMatchmakingHandler(){
        return matchmakingHandler;
    }

    public void setIsGuest(boolean isGuest) {this.isGuest = isGuest;}
}