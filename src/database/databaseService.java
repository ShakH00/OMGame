package database;

import player.Account;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class databaseService {

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

}
