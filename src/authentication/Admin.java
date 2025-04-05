package authentication;
import database.DatabaseManager;
import account.Account;

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
        Account account = db.queryAccountByID(id);
        account.setPassword(newPassword);
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
        Account account = db.queryAccountByID(id);
        account.setPassword(newUsername);
        db.saveAccount(account);
    }

}
