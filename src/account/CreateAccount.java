package account;

import database.DatabaseManager;

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
     * @return true if account is created successfully; false otherwise
     */
    public static boolean createAccount(String username, String email, String password) {
        // Validate fields
        if (!isValidUsername(username)) {
            showError("Invalid username. Must be 4-20 alphanumeric characters.");
            return false;
        }
        if (!isValidEmail(email)) {
            showError("Invalid email format.");
            return false;
        }
        if (!isValidPassword(password)) {
            showError("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.");
            return false;
        }

        Account newAccount = new Account(-1, username, email, password);
        DatabaseManager.saveAccount(newAccount);
        return true;
    }

    private static boolean isValidUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9]{4,20}$", username);
    }

    private static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }

    private static boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", password);
    }

    private static void showError(String message) {
        System.out.println("❌ " + message);
    }
}
