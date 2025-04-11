package authentication.Authentication;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.DatabaseManager;
import account.Account;
import database.DecryptionAuthentication;
import database.EncryptionAuthentication;

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
    public static void updatePassword(Integer id, String newPassword){
        try {
            newPassword = EncryptionAuthentication.encryptionDriver(newPassword);
            DatabaseManager.updateAccountPassword(id, newPassword);
        }catch (EncryptionFailedException e){
            System.out.println(e);
        }
    }

    /**
     * Sets the password for the account.
     *
     * @param id the id of the player being edited
     * @param newUsername the new username to set
     */
    public static void updateUsername(Integer id, String newUsername){
        DatabaseManager.updateAccountUsername(id, newUsername);
    }

    /**
     * Sets the password for the account.
     *
     * @param id the id of the player being edited
     * @param newEmail the new email to set
     */
    public static void updateEmail(Integer id, String newEmail){
        try {
            newEmail = EncryptionAuthentication.encryptionDriver(newEmail);
            DatabaseManager.updateAccountEmail(id, newEmail);
        }catch (EncryptionFailedException e){
            System.out.println(e);
        }
    }

    /**
     * Sets the password for the account.
     *
     * @param id the id of the player being deleted
     */
    public static boolean deleteUser(int id){
        return DatabaseManager.deleteAccount(id);
    }
}
