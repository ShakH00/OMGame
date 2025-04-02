package leaderboard;

import game.GameType;
import player.Account;
import player.statistics.AStatistics;
import database.DatabaseManager;

import java.util.*;
import java.util.stream.Collectors;

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
    public String[][] getGlobalLeaderboard(Account playerAccount, GameType games, AStatistics gameStats, boolean isAscending) {
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
        /**

             TODO: FIND A WAY to get combined stats without passing any parameter so this filter can do the job

                 To get descending values just switch the ones being compared
                  i.e a.getusername() ==> b.getUsername and b.getUsername() ==> a.getUserName();
                  BEFORE:
         Comparator<Account> filterGlobalLeaderboard = (a,b) -> CharSequence.compare(a.getUsername(), b.getUsername());

                  AFTER:
         Comparator<Account> filterGlobalLeaderboard = (a,b) -> CharSequence.compare(b.getUsername(), a.getUsername());


         |this part you can change the beginning to do what ever you need
                 Comparator<Account> globalLeaderboard = (a,b) -> Double.compare(a.getStats(), b.getStats());
         **/
        Comparator<Account> filterGlobalLeaderboard = (a,b) -> CharSequence.compare(a.getUsername(), b.getUsername());

        // Even though it turns into a set you can modify like a hashset but this one is sorted and intern much cleaner to look at.
        Set<Account> sortedGlobalLeaderboard = unsortedLeaderboardPlayers.stream().sorted(filterGlobalLeaderboard).collect(Collectors.toSet());


//        int lastIndex = firstIndex + Math.min(lastIndex - firstIndex, leaderboardPlayers.size());
//
//        ArrayList<String[]> leaderboardRows = new ArrayList<>();
//
//        if (gameStats == null) {
//            for (Account players: leaderboardPlayers){
//                                    //                          get this                fuck do I do with this
//                                    //                                                  like how???
//                //    public String[] getCombinedStatistics(HashSet<GamesEnum> games, StatisticsType[] order){
//                leaderboardRows.add(players.getCombinedStatistics().toStringArray());
//            }
//            int sortPropertyIndex = totalPlayersAccount.getCombinedStatistics().getIndexOfPropertyInStringArray(sortProperty);
//            leaderboardRows.sort( (a,b) -> {return  (-1)^ascendingSort*Integer.parseInt((a[sortPropertyIndex]).compareTo(Integer.parseInt(b[sortPropertyIndex])));});
//            leaderboardRows = leaderboardRows.subList(firstIndex, lastIndex + 1);
//            leaderboardRows.add(0, totalPlayersAccount[0].getCombinedStatistics().getStringArrayHeaders());
//        } else {
//            for (Account players: totalPlayersAccount) {
//                leaderboardRows.add(player.getGameStatistics(games).toStringArray());
//                leaderboardRows.sort( (a,b) -> {return  (-1)^ascendingSort*Integer.parseInt((a[sortPropertyIndex]).compareTo(Integer.parseInt(b[sortPropertyIndex])));});
//                leaderboardRows = leaderboardRows.subList(firstIndex, lastIndex + 1);
//                leaderboardRows.add(0, totalPlayersAccount[0].getGameStatistics(games).getStringArrayHeaders());
//
//            }
//        }

        return new String[0][0];
    }

}
