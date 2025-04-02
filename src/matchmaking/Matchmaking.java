package matchmaking;

import account.Account;
import game.GameType;

import java.util.ArrayList;

public abstract class Matchmaking {
    final private GameType gameType = null;

    /**
     * @author Logan Olszak
     * @param account     Account object to add to the accountPool
     * @param accountPool List of player objects that are currently queued
     * joinQueue adds a given player object into the list of player objects that are queued for a match
     */
    public void joinQueue(Account account, ArrayList<Account> accountPool) {
        accountPool.add(account);
    }

    /**
     * @author Logan Olszak
     * @param account     Account object to remove from the accountPool
     * @param accountPool List of Account objects that are currently queued
     * removeFromQueue removes a given Account from the list of Accounts that are queued for a match
     */
    public void removeFromQueue(Account account, ArrayList<Account> accountPool) {
        accountPool.remove(account);
    }

    /**
     * @author Nebila Wako
     * @param account1      ?
     * @param account2      ?
     * @return              ?
     */
    public boolean increaseRange(Account account1, Account account2) {
        int rating1 = account1.getElo(gameType);
        int rating2 = account2.getElo(gameType);

        // Base threshold
        int baseThreshold = (rating1 < 1000 || rating2 < 1000) ? 150 : 100;

        long now = System.currentTimeMillis();
        long oldestJoinTime = Math.min(account1.getJoinTimestamp(), account2.getJoinTimestamp());
        long waitTimeMillis = now - oldestJoinTime;

        if (waitTimeMillis > 2 * 60 * 1000) { // after 2 minutes
            baseThreshold = Integer.MAX_VALUE; // match with anyone
        } else if (waitTimeMillis > 60 * 1000) { // after 1 minute
            baseThreshold += 100;
        } else if (waitTimeMillis > 30 * 1000) { // after 30 seconds
            baseThreshold += 50;
        }
        //TEMP VALUE
        return false;
    }
}