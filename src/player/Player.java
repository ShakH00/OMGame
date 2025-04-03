package player;

public class Player {
    /**
     * Account that the Player is currently logged in to
     */
    private Account account;

    private int elo;

    /**
     * Initialize a new Player who is using a guest Account
     */
    public Player(){
        this.account = new Account();   // initialize a guest account
        this.elo = 1000; // Default Elo rating
    }
    /**
     *
     * Get the Elo rating of this Player
     * @return  int representing the Player's Elo rating
     */

    public int getElo() {
        return elo;
    }
    /**
     * Set the Elo rating of this Player
     * @param elo  int representing the new Elo rating to set for the Player
     */

    public void setElo(int elo) {
        this.elo = elo;
    }

    /**
     * Try to log in with a given username and password. Updates Player Account and returns true if successful.
     * @param username              String username to try to log in with
     * @param password              String password to try to log in with
     * @return                      true only if the username and password match an existing Account in the database
     */
    public boolean TryLoginWithUsernameAndPassword(String username, String password){

        return false;
    }

    /**
     * Get the Account that is associated with this Player
     * @return  Account that is associated with this Player
     */
    public Account getAccount(){
        return account;
    }

    private long joinTimestamp;

    public void setJoinTimestamp(long timestamp) {
        this.joinTimestamp = timestamp;
    }

    public long getJoinTimestamp() {
        return this.joinTimestamp;
    }


}
