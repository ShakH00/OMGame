package account;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import game.GameType;
import account.statistics.*;

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
    private String password;    // TODO: Handle encryption/decryption in Account class

    /**
     * Accounts ArrayList for other Accounts on the friends list of this Account
     */
    private ArrayList<Account> friends;

    private final HashMap<GameType, AStatistics> statistics;

    private final String[][] matchHistory;

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
     *
     * @param id       int id for the account, unique in database
     * @param username String username for the account
     * @param email    String email for account
     * @param password String password for the account
     */
    public Account(int id, String username, String email, String password) {

        // Properties only possessed by permanent Accounts
        this.isGuest = false;
        this.id = id;
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
    }

    /**
     * If the Player started with a guest Account, update it to form a permanent (i.e. non-guest) Account
     *
     * @param id       int id for the account, unique in database
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
    public void updateStatistics(GameType game, HashMap<StatisticsType, Integer> addStatistics) {
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
        return (int) getStatistic(game, StatisticsType.ELO);
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
     * @param statistic StatisticsType which statistic to get
     * @return Number (Integer or Double) for the statistic
     */
    public Number getStatistic(GameType game, StatisticsType statistic) {
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
     * Get the match history for this Account
     *
     * @return String[][] for MatchHistory. Get the header row using getMatchHistoryHeader()
     */
    public String[][] getMatchHistory() {
        return matchHistory;
    }

    /**
     * Gives the header row for the String[][] given by getMatchHistory().
     *
     * @return String[6] of Strings for the match history headers
     */
    public String[] getMatchHistoryHeader() {
        return new String[]{"Result", "Game", "Opponent Name", "Opponent Elo", "Opponent ID", "Match ID"};
    }

    /**
     * Get a String[] containing the Account's statistics for the game in a predetermined order
     *
     * @param game GamesEnum for which game to get statistics for
     * @return String[] containing statistic values as strings
     */
    public String[] getGameStatistics(GameType game) {
        StatisticsType[] order = statistics.get(game).getAcceptedStatistics();
        return getGameStatistics(game, order);
    }

    /**
     * Get a String[] containing the Account's statistics for the game in the specified order
     *
     * @param game  GamesEnum for which game to get statistics for
     * @param order StatisticsType array that determines which statistics will be returned and in what order
     * @return String[] containing the specified statistics in the same order
     */
    public String[] getGameStatistics(GameType game, StatisticsType[] order) {
        String[] output = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            StatisticsType statistic = order[i];
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
     * Get a String[] with the names for the statistics corresponding to those given by getGameStatistics(game)
     *
     * @param game GamesEnum for which game to get statistics for
     * @return String[] containing the names of each statistic given by getGameStatistics(...) with same parameters
     */
    public String[] getGameStatisticsHeader(GameType game) {
        StatisticsType[] order = statistics.get(game).getAcceptedStatistics();
        return getGameStatisticsHeader(order);
    }

    /**
     * Get a String[] with the names for the statistics corresponding to those given by getGameStatistics(game, order)
     *
     * @param order StatisticsType array that determines which statistics will be returned and in what order
     * @return String[] containing the names of each statistic given by getGameStatistics(...) with same parameters
     */
    public String[] getGameStatisticsHeader(StatisticsType[] order) {
        String[] headers = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            headers[i] = order[i].toString();
        }
        return headers;
    }

    /**
     * Get a String[] containing the Account's combined generic statistics (wins, losses, draws) for the specified games
     *
     * @param games HashSet of GamesEnums for which games to include in the combined stats
     * @return String[] containing the combined statistics
     */
    public String[] getCombinedStatistics(HashSet<GameType> games) {
        StatisticsType[] order = StatisticsType.values();
        return getCombinedStatistics(games, order);
    }

    /**
     * Get a String[] containing the Account's combined statistics (in a specific order) for the specified games
     *
     * @param games HashSet of GamesEnums for which games to include in the combined stats
     * @param order StatisticsType array that determines which statistics will be returned and in what order
     * @return String[] containing the combined statistics
     */
    public String[] getCombinedStatistics(HashSet<GameType> games, StatisticsType[] order) {
        // Create a new CombinedStatistics object
        HashSet<AStatistics> setOfStatistics = new HashSet<>();
        for (GameType game : games) {
            setOfStatistics.add(statistics.get(game));
        }
        StatisticsCombined statisticsCombined = new StatisticsCombined(setOfStatistics);

        // Get the string for combined statistics from CombinedStatistics object
        String[] output = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            StatisticsType statistic = order[i];
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
     * Get a String[] with the names for the statistics corresponding to those given by getCombinedStatistics(games)
     *
     * @return String[] containing the names of each statistic given by getCombinedStatistics(...)
     */
    public static String[] getCombinedStatisticsHeader() {
        StatisticsType[] order = StatisticsType.values();
        String[] headers = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            headers[i] = order[i].toString();
        }
        return headers;
    }

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
     * Return the ID of the Account, or "-1" if it is a guest account
     *
     * @return int ID of the Account
     */
    public int getID() {
        return isGuest ? -1 : id;
    }

    /**
     * Sets the username (display name) for the account.
     *
     * @param username the new username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the email address associated with the account.
     *
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password for the account.
     *
     * @param password the new password to set
     */

    public void setPassword(String password) {
        this.password = password;
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
        return email != null && email.contains("@") && email.contains(".");
    }

    /**
     * Checks whether the password is valid (at least 6 characters).
     *
     * @param password the password to validate
     * @return true if the password is valid; false otherwise
     */

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
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
     * @param friend, adds the friend to the friends-list of the player.
     */
    public void addFriend(Account friend) {
        this.friends.add(friend);
    }

    /**
     * Removes a Friend from the Account's friend list
     * @param friend    Account belonging to friend that is to be removed from friends list
     * @return          true if friend was in friends list; false otherwise
     */
    public boolean removeFriend(Account friend) {
        if (this.friends.contains(friend)){
            this.friends.remove(friend);
            return true;
        }
        return false;
    }

    /**
     * @return returns all the friends from the players list.
     */
    public ArrayList<Account> getFriends(){
        return this.friends;
    }

    /**
     * Try to log in with a given username and password. Updates Player Account and returns true if successful.
     * @param username              String username to try to log in with
     * @param password              String password to try to log in with
     * @return                      true only if the username and password match an existing Account in the database
     */
    public boolean TryLoginWithUsernameAndPassword(String username, String password){
        return false; // TODO: add login functionality here
    }

    private long joinTimestamp;

    public void setJoinTimestamp(long timestamp) {
        this.joinTimestamp = timestamp;
    }

    public long getJoinTimestamp() {
        return this.joinTimestamp;
    }
}
