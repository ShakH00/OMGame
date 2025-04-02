package player;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CreateAccount {

    private static final Scanner scanner = new Scanner(System.in);

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

    private static int generateAccountId() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000; // Generates a 6-digit ID
    }

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

    private static boolean isValidUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9]{4,20}$", username);
    }

    private static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }

    private static boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$", password);
    }

    private static void showError(String message) {
        System.out.println("❌ " + message);
    }
}
