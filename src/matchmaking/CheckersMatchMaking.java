package matchmaking;

import matchmaking.Matchmaking;
public class CheckersMatchMaking extends Matchmaking {
    private long startingTime;
    private int findElo;
    private int increment;


    public boolean increaseRange(Player player1, Player player2) {
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



    public void findTwoPlayers() {

    }
}
