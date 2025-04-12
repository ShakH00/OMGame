package account;

import account.SignUpValidator;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.DatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static account.CreateAccount.createAccount;
import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest {
    //Make sure the database is running for test if the duplicate email, username to work
    @BeforeAll
    public static void setUp() throws EncryptionFailedException {
        // Create an account to simulate an already existing username/email in the database
        String email = "test@email.com";
        String username = "user123";
        String password = "Password!123";
        createAccount(username, email, password); // This will create the test account in the database
    }

    @Test
    public void testEmptyEmail() throws EncryptionFailedException {
        String result = SignUpValidator.validateFields("", "user123", "Password@123");
        assertEquals("Please enter an email address!", result);
    }

    @Test
    public void testEmptyUsername() throws EncryptionFailedException {
        String result = SignUpValidator.validateFields("test@email.com", "", "Password@123");
        assertEquals("Please enter a username!", result);
    }

    @Test
    public void testInvalidEmailFormat() throws EncryptionFailedException {
        String result = SignUpValidator.validateFields("invalid-email", "user123", "Password@123");
        assertEquals("Invalid email address!", result);
    }

    @Test
    public void testInvalidUsername() throws EncryptionFailedException {
        String result = SignUpValidator.validateFields("test@email.com", "u$", "Password@123");
        assertEquals("Username must have at least 4 characters! (only letters, numbers, underscores)", result);
    }

    @Test
    public void testInvalidPassword() throws EncryptionFailedException {
        String result = SignUpValidator.validateFields("test@email.com", "user123", "weak");
        assertEquals("Password must have at least 8 characters! (contains uppercase, lowercase, number, special character)", result);
    }

    @Test
    public void testAlreadyExistEmail() throws EncryptionFailedException {
        // Use the email created in setUp() and check for email existence
        String existingEmail = "test@email.com"; // Email already created in setUp()
        String username = "newUser";
        String password = "Password@123";

        // This will call the real method that interacts with the database
        String result = SignUpValidator.validateFields(existingEmail, username, password);
        assertEquals("Email already exists!", result);
    }

    @Test
    public void testAlreadyExistUsername() throws EncryptionFailedException {
        // Use the username created in setUp() and check for username existence
        String email = "test1@email.com"; // Different email
        String username = "user123"; // Username created in setUp()
        String password = "Password@123";

        // This will call the real method that interacts with the database
        String result = SignUpValidator.validateFields(email, username, password);
        assertEquals("Username already exists!", result);
    }
}
