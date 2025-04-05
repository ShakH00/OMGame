package account;

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
     * @return A new Account object with valid username, email, and password.
     */
    public static Account createAccount() {
        System.out.println("\n=== Account Creation ===");

        String username = getValidUsername();
        String email = getValidEmail();
        String password = getValidPassword();

         Account newAccount = new Account( 1,username, email, password); // 1 is placeholder waiting for the database to be implemented
        System.out.println("\nAccount created successfully!");
        System.out.println("Welcome, " + newAccount.getUsername() + "!");

        return newAccount;
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
     * Prompts the user to enter a valid password.
     * The password must be at least 8 characters long and contain a mix of uppercase and lowercase letters, numbers, and special characters.
     *
     * @return A valid password.
     */
    private static String getValidPassword() {
        while (true) {
            System.out.print("Enter password (min 8 chars with uppercase, lowercase, number, and special character): ");
            String password = scanner.nextLine();

            if (isValidPassword(password)) {
                return password;
            }
            showError("Password must be at least 8 characters long and include a mix of uppercase and lowercase letters, numbers, and special characters.");
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
