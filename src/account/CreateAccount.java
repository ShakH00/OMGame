package account;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Handles account creation, including username, email, and password validation.
 */
public class CreateAccount {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Initiates the account creation process.
     *
     * @return A new Account object with a unique ID, valid username, email, and password.
     */
    public static Account createAccount() {
        System.out.println("\n=== Account Creation ===");

        int id = generateAccountId();
        String username = getValidUsername();
        String email = getValidEmail();
        String password = getValidPassword();

        Account newAccount = new Account(id, username, email, password);
        System.out.println("\nAccount created successfully!");
        System.out.println("Welcome, " + newAccount.getUsername() + "!");

        return newAccount;
    }

    /**
     * Generates a unique 6-digit account ID.
     *
     * @return A randomly generated 6-digit integer.
     */
    private static int generateAccountId() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000; // Generates a 6-digit ID
    }

    /**
     * Prompts the user to enter a valid username and validates the input.
     *
     * @return A valid username.
     */
    private static String getValidUsername() {
        while (true) {
            System.out.print("Enter username (4-20 alphanumeric characters): ");
            String username = scanner.nextLine().trim();

            if (isValidUsername(username)) {
                return username;
            }
            showError("Invalid username. Must be 4-20 alphanumeric characters.");
        }
    }

    /**
     * Prompts the user to enter a valid email address and validates the input.
     *
     * @return A valid email address.
     */
    private static String getValidEmail() {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            if (isValidEmail(email)) {
                return email;
            }
            showError("Invalid email format. Please try again.");
        }
    }

    /**
     * Prompts the user to enter and confirm a valid password.
     * The password must be at least 8 characters long and contain both letters and numbers.
     *
     * @return A valid password.
     */
    private static String getValidPassword() {
        while (true) {
            System.out.print("Enter password (min 8 chars with letter and number): ");
            String password = scanner.nextLine();

            System.out.print("Confirm password: ");
            String confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match. Please try again.");
                continue;
            }

            if (isValidPassword(password)) {
                return password;
            }
            showError("Password must be at least 8 characters with both letters and numbers.");
        }
    }

    /**
     * Validates a username based on predefined criteria.
     *
     * @param username The username to validate.
     * @return True if the username is valid, otherwise false.
     */
    private static boolean isValidUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9]{4,20}$", username);
    }

    /**
     * Validates an email address based on predefined criteria.
     *
     * @param email The email to validate.
     * @return True if the email is valid, otherwise false.
     */
    private static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }

    /**
     * Validates a password based on predefined criteria.
     * The password must contain at least one letter and one number.
     *
     * @param password The password to validate.
     * @return True if the password is valid, otherwise false.
     */
    private static boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$", password);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    private static void showError(String message) {
        System.out.println("❌ " + message);
    }
}
