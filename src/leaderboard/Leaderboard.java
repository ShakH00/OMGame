package leaderboard;

import account.statistics.StatisticsType;
import game.GameType;
import account.Account;
import account.statistics.AStatistics;
import database.DatabaseManager;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static account.statistics.StatisticsType.*;

public class Leaderboard {

    /**
     * @param ,                Game to sort by
     * @param ,            Stats of all players in the game chosen
     * @param isAscending,          If they want ascending or descending order.
     * @return                      Sorted list
     *
     * Getting global leaderboard (max = 100 or 500 if needed), from all players in the database, depending on the game
     * the player sorted by, returning the sorted result 0 = 1st (highest player), 99 = 100th (highest player), if
     * isAscending is true, else it would be reversed...
     *
     */
    public String[][] getGlobalLeaderboard(Account playerAccount, GameType game, StatisticsType sortStatistic, StatisticsType additionalStats, boolean isAscending, int numberOfPlayerPerPage) {
        ArrayList<Account> accountsList = DatabaseManager.queryAllAccounts();

        HashSet<Account> unsortedLeaderboardPlayers = new HashSet<>();

        // checks to see if the leaderboard is going to be friends only or global
        if (playerAccount == null) {
            assert DatabaseManager.queryAllAccounts() != null;
            unsortedLeaderboardPlayers.addAll(DatabaseManager.queryAllAccounts());
        } else{
            unsortedLeaderboardPlayers.add(playerAccount);
            assert playerAccount.getFriends() != null;
            unsortedLeaderboardPlayers.addAll(playerAccount.getFriends());

        }

        // Filter for ascending and descending order.
        Comparator<Account> filterGlobalLeaderboard = null;
        if (isAscending) {
            filterGlobalLeaderboard = (a,b) -> Double.compare((Double) a.getStatistic(game, sortStatistic), (Double) b.getStatistic(game, sortStatistic));

        } else {
            filterGlobalLeaderboard = (a,b) -> Double.compare((Double) b.getStatistic(game, sortStatistic), (Double) a.getStatistic(game, sortStatistic));
        }
        // Even though it turns into a set you can modify like a hashset but this one is sorted and intern much cleaner to look at.

        List<Account> sortedAccounts = unsortedLeaderboardPlayers.stream().sorted(filterGlobalLeaderboard).toList();

        StatisticsType[] order = new StatisticsType[]{ELO, WIN_RATE, WINS, additionalStats};

        String[][] leaderboard = new String[numberOfPlayerPerPage + 1][6];

        leaderboard[0] = Account.getLeaderboardHeader(additionalStats);
        for (int i = 1; i < numberOfPlayerPerPage; i++) {
            leaderboard[i] = new String[]{String.valueOf(i),
                    sortedAccounts.get(i).getUsername(), sortedAccounts.get(i).getStatistic(game, ELO).toString(), sortedAccounts.get(i).getStatistic(game, WIN_RATE).toString(), sortedAccounts.get(i).getStatistic(game, WINS).toString(),
            };
        }



        return new String[0][0];
    }

}
