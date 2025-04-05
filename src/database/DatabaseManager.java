package database;

import account.Account;
import account.AccountStorageUtility;
import game.GameType;
import java.util.ArrayList;

public class DatabaseManager {

    /**
     * @return          Returns all accounts that are active and not a guest account
     *
     * queryAllAccounts returns all account in the database
     * NOTE: the return value can be anything but for stubs I just made it return a list of account
     */
    public static ArrayList<Account> queryAllAccounts() {
        return null;
    }

    /**
     * @author Logan Olszak
     * @param game         which GameType the returned accounts are queued for
     * @return             Returns all players that are currently queueing for a match of a certain game
     *
     * queryPlayerPool returns a list of all players that are currently queued for a match of a specific game
     */
    public static ArrayList<Account> queryAccountPool(GameType game) {
        ArrayList<Account> gameQueue = new ArrayList<Account>();
        ArrayList<Account> allAccounts = queryAllAccounts();
        for (Account current : allAccounts) {
            if (current.getQueuedFor() != null) {
                if (current.getQueuedFor() == game) {
                    gameQueue.add(current);
                }
            }
        }
        return gameQueue;
    }

    /**
     * @return Returns an account from the database given a phone number
     * get account returns an account from the database
     */
    public static Account queryAccountByID(Integer id)
    {
        return new Account();
    }


    /**
     * @return Returns an account from the database
     * get account returns an account from the database
     */
    public static Account queryAccountByEmail(String email) {return new Account();}

    /**
     * @return returns true if an account was saved to the database
     * saves an account to the database
     */
    public static Boolean saveAccount(Account account) {
        int id = account.getID();
        String username = account.getUsername();
        String password = account.getPassword();
        String friends = AccountStorageUtility.friendIDsToString(account.getFriendIDs());
        String statistics = AccountStorageUtility.statisticsToString(account.getStatisticsHashMap());
        String matchHistory = AccountStorageUtility.matchHistoryToString(account.getMatchHistory());

        return true; //TODO: decide purpose of return function
    }

    /**
     * @return returns true if an account was deleted from the database
     *
     */
    public static Boolean deleteAccount(String email) {return true;}

    /**
     * @return returns true if an account was deleted from the database
     *
     */
    public static Boolean deleteAccount(Integer userID) {return true;}


}
