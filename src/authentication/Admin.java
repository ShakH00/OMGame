package authentication;
import database.DatabaseManager;
import player.Account;

public class Admin {


    /**
     * Integer ID; unique for this Account, given by the Database. 16 chars long
     */
    private Integer id;

    /**
     * String for the username associated with this Account
     */
    private String username;

    /**
     * String for the email associated with this Account
     */
    private String email;

    /**
     * String for the password associated with this Account
     */
    private String password;

    /**
     * Sets the password for the account.
     *
     * @param db the databaseManager being used
     * @param id the id of the player being edited
     * @param newPassword the new password to set
     */
    private void updatePassword(DatabaseManager db, int id, String newPassword){
        Account account = db.getAccount(id);
        account.setPassword(newPassword);
        db.saveAccount(account);
    }

    /**
     * Sets the password for the account.
     *
     * @param db the databaseManager being used
     * @param id the id of the player being edited
     * @param newNumber the new phone number to set
     */
    private void updatePhoneNumber(DatabaseManager db, int id, String newNumber){
        Account account = db.getAccount(id);
        account.setPhoneNumber(newNumber);
        db.saveAccount(account);
    }

    /**
     * Sets the password for the account.
     *
     * @param db the databaseManager being used
     * @param id the id of the player being edited
     * @param newEmail the new email to set
     */
    private void updateEmail(DatabaseManager db, int id, String newEmail){
        Account account = db.getAccount(id);
        account.setPassword(newEmail);
        db.saveAccount(account);
    }

    /**
     * Sets the password for the account.
     *
     * @param db the databaseManager being used
     * @param id the id of the player being edited
     * @param newUsername the new username to set
     */
    private void updateUsername(DatabaseManager db, int id, String newUsername){
        Account account = db.getAccount(id);
        account.setPassword(newUsername);
        db.saveAccount(account);
    }

}
