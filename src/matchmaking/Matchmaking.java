package matchmaking;

import account.Account;
import game.GameType;

public abstract class Matchmaking {
    /**
     * @author Logan Olszak
     * @param account     Account object to add to the queue
     * @param game        GameType that the account is queueing for
     *
     * joinQueue adds a given player object into the list of player objects that are queued for a match
     */
    public void joinQueue(Account account, GameType game) {
        account.setQueuedFor(game);
        account.setJoinTimestamp(System.currentTimeMillis());
        updateMatchmakingRange(account);    // TODO: handle this through a server so that matchmaking can actually occur
    }

    /**
     * @author Logan Olszak
     * @param account     Account object to remove from queue
     *
     * removeFromQueue removes a given Account from the list of Accounts that are queued for a match
     */
    public void removeFromQueue(Account account) {
        account.clearQueuedFor();
    }

    /**
     * Update the matchmaking range for the Account based on the game and time waited.
     * @author Nebila Wako, Elijah Mickelson
     * @param account      Account to recalculate Matchmaking range for
     * @return             true if the Account matchmaking range was updated; false otherwise
     */
    public boolean updateMatchmakingRange(Account account) {
        // If the user is not matchmaking, return false.
        if (account.getQueuedFor() == null) { return false; }

        // Get the game that the user is matchmaking for and their elo in that game
        GameType game = account.getQueuedFor();
        int elo = account.getElo(game);

        // Base matchmaking threshold based on account elo
        int baseThreshold = (elo < 1000) ? 150 : 100;

        // Get time (in ms) that the account has been waiting
        int timeWaitSec = (int) (System.currentTimeMillis() - account.getJoinTimestamp()) / 1000;

        // Get the rate of threshold increase per 30 seconds (dependent on game)
        int thresholdIncreaseRate;
        switch (game) {
            case CHESS              ->  thresholdIncreaseRate = 50;
            case CHECKERS, CONNECT4 ->  thresholdIncreaseRate = 75;
            case TICTACTOE          ->  thresholdIncreaseRate = 100;
            default                 ->  thresholdIncreaseRate = 0;
        }

        // Calculate final threshold
        int new_threshold;
        if (timeWaitSec < 120) {
            new_threshold = baseThreshold + (timeWaitSec / 30) * thresholdIncreaseRate;
        }
        // After 2 minutes, match anyone regardless of skill
        else {
            new_threshold = Integer.MAX_VALUE;
        }

        // If threshold changed, return true and update matchmaking threshold for the Account
        if (new_threshold != account.getMatchmakingThreshold()) {
            account.setMatchmakingThreshold(new_threshold);
            return true;
        }
        // Otherwise, return false
        return false;
    }
}