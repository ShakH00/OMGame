package authentication.Authentication;
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
     * @param id the id of the player being edited
     * @param newPassword the new password to set
     */
    public static boolean updatePassword(Integer id, String newPassword){
        Account account = DatabaseManager.queryAccountByID(id);
        boolean success = account.setPassword(newPassword);
        if(success){
            DatabaseManager.saveAccount(account);
        }
        return success;
    }

    /**
     * Sets the password for the account.
     *
     * @param id the id of the player being edited
     * @param newUsername the new username to set
     */
    public static boolean updateUsername(Integer id, String newUsername){
        Account account = DatabaseManager.queryAccountByID(id);
        boolean success = account.setUsername(newUsername);
        if(success) {
            DatabaseManager.saveAccount(account);
        }
        return success;
    }

    /**
     * Sets the password for the account.
     *
     * @param id the id of the player being edited
     * @param newEmail the new email to set
     */
    public static void updateEmail(Integer id, String newEmail){
        Account account = DatabaseManager.queryAccountByID(id);
        boolean success = account.setEmail(newEmail);
        if(success) {
            DatabaseManager.saveAccount(account);
        }
    }

    /**
     * Sets the password for the account.
     *
     * @param id the id of the player being deleted
     */
    public static boolean deleteUser(int id){
        Account account = DatabaseManager.queryAccountByID(id);
        boolean success = DatabaseManager.deleteAccount(id);
        if(success) {
            DatabaseManager.saveAccount(account);
        }
        return success;
    }

}
