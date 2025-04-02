package matchmaking;

import account.Account;

import java.util.ArrayList;

public class Matchmaking {
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
}