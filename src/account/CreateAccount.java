package account;

import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.DatabaseManager;
import database.EncryptionAuthentication;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Handles account creation, including username, email, and password validation.
 */
public class CreateAccount {

    /**
     *
     *
     * @param username the desired username
     * @param email the user's email
     * @param password the desired password
     * @return the new Account if account is created successfully; null otherwise
     */
    public static Account createAccount(String username, String email, String password) throws EncryptionFailedException {
        Account newAccount = new Account(username, email, password);
        EncryptionAuthentication.encryptAccount(newAccount);
        DatabaseManager.saveAccount(newAccount);
        newAccount = DatabaseManager.queryAccountByUsername(username);
        return newAccount;
    }

    public static boolean isValidUsername(String username) {
        // Regular expression for validating username (at least 4 characters,
        // only alphanumeric and underscores are allowed)
        String usernameRegex = "^[a-zA-Z0-9_]{4,}$";

        // Return true if username matches the regex, else false
        return Pattern.matches(usernameRegex, username);
    }

    public static boolean isValidEmail(String email) {
        // Regular expression for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Return true if email matches the regex, else false
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String password) {
        // Regular expression for validating password (minimum 8 characters,
        // at least one uppercase letter, one lowercase letter, one number, one special character)
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";

        // Return true if password matches the regex, else false
        return Pattern.matches(passwordRegex, password);
    }

}
