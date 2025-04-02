package matchmaking;

import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Matchmaking {




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