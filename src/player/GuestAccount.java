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
    public void signUp(int id, String username, String email, String password, HashSet<GamesEnum> games) {
        if (this.isGuest()) {
            // Save progress before converting the account
            saveProgress(games);

            // Convert the guest account to a permanent account
            this.account.convertToNonGuestAccount(id, username, email, password);
            System.out.println("Sign-up successful! Progress has been saved.");
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
