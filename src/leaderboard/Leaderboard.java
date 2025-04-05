package leaderboard;

import account.NoAccountError;
import account.statistics.StatisticType;
import game.GameType;
import account.Account;
import database.DatabaseManager;

import java.util.*;

import static account.statistics.StatisticType.*;

public class Leaderboard {
    /**
     * Get a Leaderboard String[][] that includes all Accounts in the database to be displayed by the GUI
     * @param game                  GameType game for leaderboard
     * @param sortStatistic         StatisticType statistic to rank accounts by
     * @param additionalStatistic   StatisticType statistic to show in the last column
     * @param isAscending           boolean; if true, the lowest value in a given stat will be ranked highest
     * @param accountsPerPage       int number of accounts to show per page
     * @param pageNumber            int current page, starting at 1
     * @return                      String[][] including the header row for the leaderboard
     */
    public String[][] getGlobalLeaderboard(
            GameType game,
            StatisticType sortStatistic,
            StatisticType additionalStatistic,
            boolean isAscending,
            int accountsPerPage,
            int pageNumber)
    {
        ArrayList<Account> accountsList = DatabaseManager.queryAllAccounts();
        return getLeaderboard(accountsList, game, sortStatistic, additionalStatistic, isAscending, accountsPerPage, pageNumber);
    }

    /**
     * Get a Leaderboard String[][] that includes some account and their friends to be displayed by the GUI
     * @param account               Account of user who is requesting the leaderboard
     * @param game                  GameType game for leaderboard
     * @param sortStatistic         StatisticType statistic to rank accounts by
     * @param additionalStatistic   StatisticType statistic to show in the last column
     * @param isAscending           boolean; if true, the lowest value in a given stat will be ranked highest
     * @param accountsPerPage       int number of accounts to show per page
     * @param pageNumber            int current page, starting at 1
     * @return                      String[][] including the header row for the leaderboard
     */
    public String[][] getFriendsLeaderboard(
            Account account,
            GameType game,
            StatisticType sortStatistic,
            StatisticType additionalStatistic,
            boolean isAscending,
            int accountsPerPage,
            int pageNumber)
            throws NoAccountError
    {
        // If the account is a guest, throw an error
        if (account.getIsGuest()) { throw new NoAccountError(); }

        // Create a new ArrayList including the requesting Account and all of their friends
        ArrayList<Account> accountsList = new ArrayList<>();
        accountsList.add(account);
        accountsList.addAll(account.getFriends());

        // Get leaderboard composed of these players
        return getLeaderboard(accountsList, game, sortStatistic, additionalStatistic, isAscending, accountsPerPage, pageNumber);
    }

    /**
     * Get a Leaderboard String[][] based on the given parameters to be displayed by the GUI
     * @param accounts              Account arraylist for Accounts to include in the leaderboard
     * @param game                  GameType game for leaderboard
     * @param sortStatistic         StatisticType statistic to rank accounts by
     * @param additionalStatistic   StatisticType statistic to show in the last column
     * @param isAscending           boolean; if true, the lowest value in a given stat will be ranked highest
     * @param accountsPerPage       int number of accounts to show per page
     * @param pageNumber            int current page, starting at 1
     * @return                      String[][] including the header row for the leaderboard
     */
    private String[][] getLeaderboard(
            ArrayList<Account> accounts,
            GameType game,
            StatisticType sortStatistic,
            StatisticType additionalStatistic,
            boolean isAscending,
            int accountsPerPage,
            int pageNumber)
    {
        // Initialize comparator that sorts Accounts by the sortStatistic in ascending or descending order
        Comparator<Account> filterGlobalLeaderboard;
        if (isAscending) {
            filterGlobalLeaderboard = (a,b) -> Double.compare((Double) a.getStatistic(game, sortStatistic), (Double) b.getStatistic(game, sortStatistic));
        }
        else {
            filterGlobalLeaderboard = (a,b) -> Double.compare((Double) b.getStatistic(game, sortStatistic), (Double) a.getStatistic(game, sortStatistic));
        }

        // Sort the Accounts using the comparator
        List<Account> sortedAccounts = accounts.stream().sorted(filterGlobalLeaderboard).toList();

        // Initialize leaderboard String[][]. Rows = header + players. Columns = rank, name, elo, win%, wins, other
        String[][] leaderboard = new String[1 + accountsPerPage][6];

        // Add header row to leaderboard
        leaderboard[0] = getLeaderboardHeader(additionalStatistic);

        // Add player rows to leaderboard
        int startRank = 1 + (pageNumber - 1) * accountsPerPage;
        for (int rank = startRank; rank <= startRank + accountsPerPage; rank++) {
            int row = rank - 1; // Current row of leaderboard String[][]

            // Try to find the Player at this rank and add them to the leaderboard String[][]
            try {
                Account account = sortedAccounts.get(rank);
                leaderboard[row] = new String[]{
                        String.valueOf(rank),                                       // Rank
                        account.getUsername(),                                      // Username
                        account.getStatistic(game, ELO).toString(),                 // Elo/rating
                        account.getStatistic(game, WIN_RATE).toString(),            // Win rate
                        account.getStatistic(game, WINS).toString(),                // Total wins
                        account.getStatistic(game, additionalStatistic).toString()  // Chosen statistic
                };
            }
            // If there are no more Players, add an empty row.
            catch (IndexOutOfBoundsException e) {
                leaderboard[row] = new String[]{"", "", "", "", "", ""};
            }
        }

        return leaderboard;
    }

    /**
     * Get a String[] for the header of the leaderboard
     * @param additionalStatistic   The additional statistic that is selected in the last column of the Leaderboard GUI
     * @return                      String[] for the header row of the leaderboard
     */
    private String[] getLeaderboardHeader(StatisticType additionalStatistic) {
        String[] headers = new String[6];
        headers[0] = "RANK";
        headers[1] = "USERNAME";
        headers[2] = StatisticType.ELO.toString();
        headers[3] = StatisticType.WIN_RATE.toString();
        headers[4] = StatisticType.WINS.toString();
        headers[5] = additionalStatistic.toString();
        return headers;
    }
}
