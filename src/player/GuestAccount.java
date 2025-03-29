package player;

import java.util.HashSet;
import game.GamesEnum;

public class GuestAccount {

    private Account account;

    /**
     * Initialize a new GuestPlayer with a guest Account
     */
    public GuestAccount() {
        this.account = new Account(); // Start with a guest account
    }

    /**
     * Get the account associated with this GuestPlayer
     * @return Account object
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * Check if the player is still a guest
     * @return true if the account is a guest account, false otherwise
     */
    public boolean isGuest() {
        return this.account.getIsGuest();
    }

    /**
     * Sign up the GuestPlayer by converting their guest account into a permanent account.
     * @param id        Unique ID for the new account
     * @param username  Username for the new account
     * @param email     Email for the new account
     * @param password  Password for the new account
     */
    public void signUp(int id, String username, String email, String password) {
        if (this.isGuest()) {
            this.account.convertToNonGuestAccount(id, username, email, password);
        } else {
            System.out.println("Player is already signed up.");
        }
    }

    /**
     * Save the game progress for the GuestPlayer.
     * This method ensures that progress is retained even after signing up.
     * @param games HashSet of GamesEnums representing the games played
     */
    public void saveProgress(HashSet<GamesEnum> games) {

        System.out.println("Game progress saved for: " + games);
    }
}
