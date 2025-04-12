package account;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.DatabaseManager;

public class SignUpValidator {

    public static String validateFields(String email, String username, String password) throws EncryptionFailedException {
        // Check for empty fields
        if (email.isEmpty()) return "Please enter an email address!";
        if (username.isEmpty()) return "Please enter a username!";
        if (password.isEmpty()) return "Please enter a password!";

        // Validate email format
        if (!isValidEmail(email)) return "Invalid email address!";

        // Validate username format
        if (!isValidUsername(username)) {
            return "Username must have at least 4 characters! (only letters, numbers, underscores)";
        }

        // Validate password format
        if (!isValidPassword(password)) {
            return "Password must have at least 8 characters! (contains uppercase, lowercase, number, special character)";
        }

        // Check for duplicate username
        if (DatabaseManager.isUsernameExist(username)) {
            return "Username already exists!";
        }

        // Check for duplicate email (encrypted)
        if (DatabaseManager.isEmailExist(email)) {
            return "Email already exists!";
        }

        return "OK";
    }

    public static boolean isValidEmail(String email) {
        return CreateAccount.isValidEmail(email);
    }
    public static boolean isValidUsername(String username) {
        return CreateAccount.isValidUsername(username);
    }
    public static boolean isValidPassword(String password) {
        return CreateAccount.isValidPassword(password);
    }
}