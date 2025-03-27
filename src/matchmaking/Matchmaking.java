package matchmaking;

import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Matchmaking {

    /**
     * Updates players rating after match as we discussed in planning document
     *
     * @param player1
     * @param player2
     * @param result
     */
    public void updateElo(Player player1, Player player2, int result) {
    }

    /**
     * To calculate win probabiliy
     *
     * @param ratingA
     * @param ratingB
     * @return
     */

    public double calculateExpectedScore(double ratingA, double ratingB) {
        return 0;
    }

    /**
     * To check if two players have acceptable skill proximity
     *
     * @param player1
     * @param player2
     * @return
     */

    public boolean isMatchSuitable(Player player1, Player player2) {
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
     * @return Returns all accounts that are currently queueing for a match
     * joinQueue adds a given player object into the list of player objects that are queued for a match
     */
    public void joinQueue(Player player, ArrayList<Player> playerPool) {
    }

}