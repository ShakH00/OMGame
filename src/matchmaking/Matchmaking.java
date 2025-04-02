package matchmaking;

import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Matchmaking {



    public boolean isMatchSuitable(Player player1, Player player2) {
        int rating1 = player1.getElo();
        int rating2 = player2.getElo();

        // Base threshold
        int baseThreshold = (rating1 < 1000 || rating2 < 1000) ? 150 : 100;

        long now = System.currentTimeMillis();
        long oldestJoinTime = Math.min(player1.getJoinTimestamp(), player2.getJoinTimestamp());
        long waitTimeMillis = now - oldestJoinTime;

        if (waitTimeMillis > 2 * 60 * 1000) { // after 2 minutes
            baseThreshold = Integer.MAX_VALUE; // match with anyone
        } else if (waitTimeMillis > 1 * 60 * 1000) { // after 1 minute
            baseThreshold += 100;
        } else if (waitTimeMillis > 30 * 1000) { // after 30 seconds
            baseThreshold += 50;
        }
        //TEMP VALUE
        return false;
    }

    /**
     * Finds the best match for a player from a pool
     *
     * @param targetPlayer
     * @param playerPool
     * @return
     */

    public Player findBestMatch(Player targetPlayer, List<Player> playerPool) {
        return null;
    }

    /**
     * @author Logan Olszak
     * @param player     Player object to add to the playerPool
     * @param playerPool List of player objects that are currently queued
     * joinQueue adds a given player object into the list of player objects that are queued for a match
     */
    public void joinQueue(Player player, ArrayList<Player> playerPool) {
        playerPool.add(player);
    }

    /**
     * @author Logan Olszak
     * @param player     Player object to remove from the playerPool
     * @param playerPool List of player objects that are currently queued
     * removeFromQueue removes a given player object from the list of player objects that are queued for a match
     */
    public void removeFromQueue(Player player, ArrayList<Player> playerPool) {
        playerPool.remove(player);
    }
}