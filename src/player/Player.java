package player;

public class Player {
    /**
     * Account that the Player is currently logged in to
     */
    private Account account;

    /**
     * Initialize a new Player who is using a guest Account
     */
    public Player(){
        this.account = new Account();   // initialize a guest account
    }

    /**
     * Try to log in with a given username and password. Updates Player Account and returns true if successful.
     * @param username              String username to try to log in with
     * @param password              String password to try to log in with
     * @return                      true only if the username and password match an existing Account in the database
     */
    public boolean TryLoginWithUsernameAndPassword(String username, String password){

    }

    /**
     * Get the Account that is associated with this Player
     * @return  Account that is associated with this Player
     */
    public Account getAccount(){
        return account;
    }
}
