package account;

import database.DatabaseManager;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Handles account creation, including username, email, and password validation.
 */
public class CreateAccount {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Interactive account creation using console prompts.
     *
     * @return A new Account object with valid username, email, and password.
     */
    public static Account createAccountWithPrompt() {
        System.out.println("\n=== Account Creation ===");

        String username = getValidUsername();
        String email = getValidEmail();
        String password = getValidPassword();

        Account newAccount = new Account(1, username, email, password); // 1 = placeholder ID
        System.out.println("\nAccount created successfully!");
        System.out.println("Welcome, " + newAccount.getUsername() + "!");

        return newAccount;
    }

    /**
     * Non-interactive account creation method.
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

        // Optional: check for existing account
        if (DatabaseManager.queryAccountByEmail(email) != null) {
            showError("Username already exists.");
            return false;
        }
        if (DatabaseManager.queryAccountByEmail(email) != null) {
            showError("Email already in use.");
            return false;
        }

        // Create and store account
        Account newAccount = new Account(-1, username, email, password); // -1 as placeholder
        DatabaseManager.saveAccount(newAccount);
        return true;
    }

    private static String getValidUsername() {
        while (true) {
            System.out.print("Enter username (4-20 alphanumeric characters): ");
            String username = scanner.nextLine().trim();
            if (isValidUsername(username)) return username;
            showError("Invalid username. Must be 4-20 alphanumeric characters.");
        }
    }

    private static String getValidEmail() {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            if (isValidEmail(email)) return email;
            showError("Invalid email format. Please try again.");
        }
    }

    private static String getValidPassword() {
        while (true) {
            System.out.print("Enter password (min 8 chars with uppercase, lowercase, number, and special character): ");
            String password = scanner.nextLine();
            if (isValidPassword(password)) return password;
            showError("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.");
        }
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
