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
     * Accounts ArrayList for other Accounts on the friends list of this Account
     */
    private ArrayList<Account> friends;

    private final HashMap<GamesEnum, Statistics> statistics;
    private final ArrayList<MatchOutcomeHandler> matchHistory;

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
        this.matchHistory = new ArrayList<>();
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
        this.matchHistory = new ArrayList<>();
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
        HashSet<Statistics> setOfStatistics = new HashSet<>();
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
}
