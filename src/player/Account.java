package player;

import java.util.ArrayList;
import java.util.HashMap;
import game.GamesEnum;
import game.MatchOutcome;

public class Account {
    /**
     * boolean for isGuest; true if the Account is a guest Account, otherwise false
     */
    private boolean isGuest;

    /**
     * Integer ID; unique for this Account, given by the Database. 16 chars long, or is Null for a guest Account
     */
    private Integer id;

    /**
     * String for the username associated with this Account
     */
    private String username;

    /**
     * String for the email associated with this Account; may be needed for password recovery
     */
    private String email;

    /**
     * String for the password associated with this Account
     */
    private String password;    // TODO: Handle encryption/decryption in Account class

    /**
     * Accounts ArrayList for other Accounts on the friends list of this Account
     */
    private ArrayList<Account> friends;

    private final HashMap<GamesEnum, GameStatistics> statistics;
    private final ArrayList<MatchOutcome> matchHistory;

    /**
     * Initialize a guest Account
     */
    public Account(){
        // Properties only possessed by permanent Accounts
        this.isGuest = true;
        this.id = null;
        this.username = null;
        this.email = null;
        this.password = null;
        this.friends = null;

        // Properties possessed by both guest and permanent Accounts
        this.statistics = new HashMap<>();
        this.matchHistory = new ArrayList<>();
    }

    /**
     * Initialize an Account with a given id, username, email, and password
     * @param id            int id for the account, unique in database
     * @param username      String username for the account
     * @param email         String email for account
     * @param password      String password for the account
     */
    public Account(int id, String username, String email, String password){
        // Properties only possessed by permanent Accounts
        this.isGuest = false;
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();

        // Properties possessed by both guest and permanent Accounts
        this.statistics = new HashMap<>();
        this.matchHistory = new ArrayList<>();
    }

    /**
     * If the Player started with a guest Account, update it to form a permanent (i.e. non-guest) Account
     * @param id            int id for the account, unique in database
     * @param username      String username for the account
     * @param email         String email for account
     * @param password      String password for the account
     */
    public void convertToNonGuestAccount(int id, String username, String email, String password){
        // Properties only possessed by permanent Accounts
        this.isGuest = false;
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();
    }
}
