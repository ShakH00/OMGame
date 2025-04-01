package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import game.GamesEnum;
import player.statistics.*;

public class Account {
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
     * String for the phone number associated with this Account
     */
    private String phoneNumber;

    /**
     * Accounts ArrayList for other Accounts on the friends list of this Account
     */
    private ArrayList<Account> friends;

    private final HashMap<GamesEnum, AStatistics> statistics;

    private final String[][] matchHistory;

    /**
     * Initialize a guest Account
     */
    public Account(){
        // Properties only possessed by permanent Accounts
        this.isGuest = true;
        this.id = null;
        this.username = null;
        this.email = null;
        this.password = null;
        this.friends = null;

        // Properties possessed by both guest and permanent Accounts
        this.statistics = new HashMap<>();
        statistics.put(GamesEnum.CHESS, new StatisticsChess());
        statistics.put(GamesEnum.CHECKERS, new StatisticsCheckers());
        statistics.put(GamesEnum.CONNECT4, new StatisticsConnect4());
        statistics.put(GamesEnum.TICTACTOE, new StatisticsTicTacToe());
        this.matchHistory = new String[10][5];  // Store information about the past 10 matches, each with 5 fields.
    }

    /**
     * Initialize an Account with full details including the phone number.
     *
     * @param id           the account ID
     * @param username     the account username
     * @param email        the account email
     * @param password     the account password
     * @param phoneNumber  the account phone number
     */
    public Account(int id, String username, String email, String password, String phoneNumber) {
        this(id, username, email, password); // Call the 4-param constructor
        this.phoneNumber = phoneNumber;
    }

    /**
     * Initialize an Account with a given id, username, email, and password
     * @param id            int id for the account, unique in database
     * @param username      String username for the account
     * @param email         String email for account
     * @param password      String password for the account
     */
    public Account(int id, String username, String email, String password){
        // Properties only possessed by permanent Accounts
        this.isGuest = false;
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();

        // Properties possessed by both guest and permanent Accounts
        this.statistics = new HashMap<>();
        statistics.put(GamesEnum.CHESS, new StatisticsChess());
        statistics.put(GamesEnum.CHECKERS, new StatisticsCheckers());
        statistics.put(GamesEnum.CONNECT4, new StatisticsConnect4());
        statistics.put(GamesEnum.TICTACTOE, new StatisticsTicTacToe());
        this.matchHistory = new String[10][6];  // Store information about the past 10 matches, each with 5 fields.
    }

    /**
     * If the Player started with a guest Account, update it to form a permanent (i.e. non-guest) Account
     * @param id            int id for the account, unique in database
     * @param username      String username for the account
     * @param email         String email for account
     * @param password      String password for the account
     */
    public void convertToNonGuestAccount(int id, String username, String email, String password){
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
     * @param game              GamesEnum game to update statistics for
     * @param addStatistics     HashMap which assigns each statistic an Integer value to increase by
     */
    public void updateStatistics(GamesEnum game, HashMap<StatisticsEnum, Integer> addStatistics){
        statistics.get(game).addStatistics(addStatistics);
    }

    /**
     * Update this account's Elo for some game in GamesEnum.
     * @param game      GamesEnum game to update Elo for
     * @param newElo    Integer value to assign Elo
     */
    public void updateElo(GamesEnum game, Integer newElo){
        statistics.get(game).updateElo(newElo);
    }

    /**
     * Get the player's Elo for some game. This is a wrapper function for getStatistic()
     * @param game  GamesEnum game to get Elo for
     * @return      int Elo for the game
     */
    public int getElo(GamesEnum game){
        return (int) getStatistic(game, StatisticsEnum.ELO);
    }

    /**
     * Get a certain Statistic for the player from a given game.
     * @param game          GamesEnum game to get statistic for
     * @param statistic     StatisticsEnum which statistic to get
     * @return              Number (Integer or Double) for the statistic
     */
    public Number getStatistic(GamesEnum game, StatisticsEnum statistic){
        return statistics.get(game).getStatistic(statistic);
    }

    /**
     * Adds a String[5] representing the most recent match results to the match history String[][]
     * @param strings   String[] containing details about the player's last match
     */
    public void logMatch(String[] strings) {
        // Move each match history String[] up by 1 index.
        for (int i = 8; i >= 0; i--){
            if (matchHistory[i] != null){
                matchHistory[i + 1] = matchHistory[i];
            }
        }
        // Set the first index as the most recent match history String[]
        matchHistory[0] = strings;
    }

    /**
     * Get the match history for this Account
     * @return  String[][] for MatchHistory. Get the header row using getMatchHistoryHeader()
     */
    public String[][] getMatchHistory(){
        return matchHistory;
    }

    /**
     * Gives the header row for the String[][] given by getMatchHistory().
     * @return  String[6] of Strings for the match history headers
     */
    public String[] getMatchHistoryHeader(){
        return new String[]{"Result", "Game", "Opponent Name", "Opponent Elo", "Opponent ID", "Match ID"};
    }

    /**
     * Get a String[] containing the Account's statistics for the game in a predetermined order
     * @param game  GamesEnum for which game to get statistics for
     * @return      String[] containing statistic values as strings
     */
    public String[] getGameStatistics(GamesEnum game){
        StatisticsEnum[] order = statistics.get(game).getAcceptedStatistics();
        return getGameStatistics(game, order);
    }

    /**
     * Get a String[] containing the Account's statistics for the game in the specified order
     * @param game  GamesEnum for which game to get statistics for
     * @param order StatisticsEnum array that determines which statistics will be returned and in what order
     * @return      String[] containing the specified statistics in the same order
     */
    public String[] getGameStatistics(GamesEnum game, StatisticsEnum[] order){
        String[] output = new String[order.length];
        for (int i = 0; i < order.length; i++){
            StatisticsEnum statistic = order[i];
            Number value = statistics.get(game).getStatistic(statistic);

            if (value instanceof Integer){
                output[i] = value.toString();
            }
            else if (value instanceof Double){
                output[i] = String.format("%.2f", value);   // Two decimals for a double.
            }
        }
        return output;
    }

    /**
     * Get a String[] with the names for the statistics corresponding to those given by getGameStatistics(game)
     * @param game  GamesEnum for which game to get statistics for
     * @return      String[] containing the names of each statistic given by getGameStatistics(...) with same parameters
     */
    public String[] getGameStatisticsHeader(GamesEnum game){
        StatisticsEnum[] order = statistics.get(game).getAcceptedStatistics();
        return getGameStatisticsHeader(order);
    }

    /**
     * Get a String[] with the names for the statistics corresponding to those given by getGameStatistics(game, order)
     * @param order StatisticsEnum array that determines which statistics will be returned and in what order
     * @return      String[] containing the names of each statistic given by getGameStatistics(...) with same parameters
     */
    public String[] getGameStatisticsHeader(StatisticsEnum[] order) {
        String[] headers = new String[order.length];
        for (int i = 0; i < order.length; i++){
            headers[i] = order[i].toString();
        }
        return headers;
    }

    /**
     * Get a String[] containing the Account's combined generic statistics (wins, losses, draws) for the specified games
     * @param games HashSet of GamesEnums for which games to include in the combined stats
     * @return      String[] containing the combined statistics
     */
    public String[] getCombinedStatistics(HashSet<GamesEnum> games){
        StatisticsEnum[] order = StatisticsEnum.values();
        return getCombinedStatistics(games, order);
    }

    /**
     * Get a String[] containing the Account's combined statistics (in a specific order) for the specified games
     * @param games HashSet of GamesEnums for which games to include in the combined stats
     * @param order StatisticsEnum array that determines which statistics will be returned and in what order
     * @return      String[] containing the combined statistics
     */
    public String[] getCombinedStatistics(HashSet<GamesEnum> games, StatisticsEnum[] order){
        // Create a new CombinedStatistics object
        HashSet<AStatistics> setOfStatistics = new HashSet<>();
        for (GamesEnum game : games){
            setOfStatistics.add(statistics.get(game));
        }
        StatisticsCombined statisticsCombined = new StatisticsCombined(setOfStatistics);

        // Get the string for combined statistics from CombinedStatistics object
        String[] output = new String[order.length];
        for (int i = 0; i < order.length; i++){
            StatisticsEnum statistic = order[i];
            Number value = statisticsCombined.getStatistic(statistic);

            if (value instanceof Integer){
                output[i] = value.toString();
            }
            else if (value instanceof Double){
                output[i] = String.format("%.2f", value);   // Two decimals for a double.
            }
        }
        return output;
    }

    /**
     * Get a String[] with the names for the statistics corresponding to those given by getCombinedStatistics(games)
     * @return      String[] containing the names of each statistic given by getCombinedStatistics(...)
     */
    public static String[] getCombinedStatisticsHeader(){
        StatisticsEnum[] order = StatisticsEnum.values();
        String[] headers = new String[order.length];
        for (int i = 0; i < order.length; i++){
            headers[i] = order[i].toString();
        }
        return headers;
    }

    public boolean getIsGuest(){
        return isGuest;
    }

    /**
     * Return the username of the Account, or "Guest" if it is a guest account
     * @return  String username of the Account
     */
    public String getUsername(){
        return isGuest ? "Guest" : username;
    }

    /**
     * Return the ID of the Account, or "-1" if it is a guest account
     * @return  int ID of the Account
     */
    public int getID(){
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
     * Sets the phone number associated with the account.
     *
     * @param phoneNumber the new phone number to set
     */

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
     * Checks whether the phone number is valid (only digits and at least 10 characters).
     *
     * @param phoneNumber the phone number to validate
     * @return true if the phone number is valid; false otherwise
     */

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10,}");
    }

    /**
     * Updates the account information after validating all provided fields.
     * Throws IllegalArgumentException if any input is invalid.
     *
     * @param username     the new display name for the account
     * @param email        the new email address for the account
     * @param password     the new password (must be at least 6 characters)
     * @param phoneNumber  the new phone number (must be at least 10 digits)
     */
    public void updateAccountInfo(String username, String email, String password, String phoneNumber) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Phone number must be at least 10 digits.");
        }

        setUsername(username);
        setEmail(email);
        setPassword(password);
        setPhoneNumber(phoneNumber);
    }
    /**
     * Returns the phone number associated with this account.
     *
     * @return the phone number as a String
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
