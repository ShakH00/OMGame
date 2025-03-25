package leaderboard;

import game.GamesEnum;
import player.Account;
import player.GameStatistics;
import database.databaseService;

import java.util.ArrayList;

public class leaderboard {

    /**
     * @param games,            Game to sort by
     * @param gameStats,        Stats of all players in the game chosen
     * @param isAscending,      If they want ascending or descending order.
     * @return                  Sorted list
     *
     * Getting global leaderboard (max = 100 or 500 if needed), from all players in the database, depending on the game the
     * player sorted by, returning the sorted result 0 = 1st (highest player), 99 = 100th (highest player), if
     * isAscending is true, else it would be reversed...
     *
     */
    public String[][] getGlobalLeaderboard(GamesEnum games, GameStatistics gameStats, boolean isAscending) {
        ArrayList<Account> accountsList = databaseService.queryAllAccounts();
        return new String[0][0];
    }

    /**
     * @param playerAccount,        Player's friends list
     * @param games,                Game to sort by
     * @param gameStats,            Stats of friends and player
     * @param isAscending           Order of viewing...
     * @return                      Sorted friends list
     *
     * Getting local leaderboard (max = friends added) from the player's friends list. Sorted by the chosen game
     * selected by player. Returning the sorted result into a String[][] to be displayed properly
     */

    public String[][] getFriendsLeaderboard(Account playerAccount, GamesEnum games, GameStatistics gameStats, boolean isAscending) {
        return new String[0][0];
    }

}
