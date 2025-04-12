package account;

import account.SignUpValidator;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest {

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

}