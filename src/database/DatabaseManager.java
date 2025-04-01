package database;

import player.Account;
import player.Player;

import java.util.ArrayList;

public class DatabaseManager {

    /**
     * @return          Returns all accounts that are active and not a guest account
     *
     * queryALlAccounts returns all account in the database
     * NOTE: the return value can be anything but for stubs I just made it return a list of account
     */
    public static ArrayList<Account> queryAllAccounts() {
        return null;
    }

    /**
     * @author Logan Olszak
     * @return             Returns all players that are currently queueing for a match
     *
     * queryPlayerPool returns a list of all players that are currently queued for a match
     */
    public static ArrayList<Player> queryPlayerPool() {
        return null;
    }

    /**
     * @return Returns an account from the database given a phone number
     * get account returns an account from the database
     */
    public static Account getAccount(Integer phoneNumber) {return new Account();}


    /**
     * @return Returns an account from the database
     * get account returns an account from the database
     */
    public static Account getAccount(String email) {return new Account();}

    /**
     * @return returns true if an account was saved to the database
     * saves an account to the database
     */
    public static Boolean saveAccount(Account account) {return true;}

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
