package player;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CreateAccount {
    public static Account createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Account Creation ===");

        // Get account details
        int id = generateAccountId(); // In real system, this would come from database
        String username = getValidUsername(scanner);
        String email = getValidEmail(scanner);
        String password = getValidPassword(scanner);

        // Create and return the new account
        Account newAccount = new Account(id, username, email, password);
        System.out.println("\nAccount created successfully!");
        System.out.println("Welcome, " + newAccount.getUsername() + "!");

        scanner.close();
        return newAccount;
    }
    public void generateAccountId(){}
    private static String getValidUsername(Scanner scanner) {
        while (true) {
            System.out.print("Enter username (4-20 alphanumeric characters): ");
            String username = scanner.nextLine().trim();

            if (Pattern.matches("^[a-zA-Z0-9]{4,20}$", username)) {
                // In real system, check if username is available
                return username;
            }
            System.out.println("Invalid username. Must be 4-20 alphanumeric characters.");
        }
    }
    public void getValidEmail(){}
    public String getValidPassword(Scanner scanner) {
        while (true) {
            System.out.print("Enter password (min 8 chars with letter and number): ");
            String password = scanner.nextLine();

            System.out.print("Confirm password: ");
            String confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
                continue;
            }

            if (Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$", password)) {
                return password;
            }
            System.out.println("Password must be at least 8 characters with both letters and numbers.");

        }
    }
}
