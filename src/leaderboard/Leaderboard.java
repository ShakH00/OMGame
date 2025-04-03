package leaderboard;

import game.GamesEnum;
import player.Account;
import player.statistics.AStatistics;
import database.DatabaseManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Leaderboard {

    /**
     * @param games,                Game to sort by
     * @param gameStats,            Stats of all players in the game chosen
     * @param isAscending,          If they want ascending or descending order.
     * @return                      Sorted list
     *
     * Getting global leaderboard (max = 100 or 500 if needed), from all players in the database, depending on the game
     * the player sorted by, returning the sorted result 0 = 1st (highest player), 99 = 100th (highest player), if
     * isAscending is true, else it would be reversed...
     *
     */


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
    public String[][] getFriendsLeaderboard(Account playerAccount, GamesEnum games, AStatistics gameStats, boolean isAscending) {
        return new String[0][0];
    }


    /**
     * @param list,                 List given to be sorted
     * @param left,                 Starting point          Mostly starting at 0
     * @param right,                ending point            Mostly ending of the list length -1
     *
     *  the sort method allows the to sort a list array in ascending order.
     *  This sorting algorithm is called quick sort.
     */
    public static void sort(List<Integer> list, int left, int right) {
        if (right > left) {
            int partitionIndex = partition(list, left, right);
            sort(list, left, partitionIndex - 1);
            sort(list, partitionIndex + 1, right);
        }
    }

    /**
     * @param list,                 List given to be sorted
     * @param left,                 Starting point          Mostly starting at 0
     * @param right,                ending point            Mostly ending of the list length -1
     * @return,                     Returned sorted array
     *
     * Another word for partition is division, as this method does the heavy lifting by dividing the list into two
     * sections to check and sort to from ascending order.
     */
    static int partition(List<Integer> list, int left, int right) {
        int median = list.get(left);
        int leftSide = left;
        int rightSide = right + 1;

        // infinite loop
        for (;;) {
            // left side of the median
            while (list.get(++leftSide) < median) {
                if (leftSide >= right)
                    break;
            }
            // right side of the median
            while (list.get(--rightSide) > median) {
                if (rightSide <= left)
                    break;
            }
            // catch
            if (leftSide >= rightSide) {
                // catch if left side goes beyond the right side
                break;
            } else {
                // swap the list
                Collections.swap(list, leftSide, rightSide);
            }
        }
        // return if the sorting is completed
        if (rightSide == left) {
            return rightSide;
        }
        // Swap the list
        Collections.swap(list, left, rightSide);
        return rightSide    ;
    }

}
