package database;

import account.Account;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EncryptionAuthenticationTest {

    @org.junit.Test
    @Test
    public void testEncryptionDriver_validInput() throws EncryptionFailedException {
        String input = "abcXYZ123";
        String expected = "def[\\]456";  // Each character shifted by +3
        assertEquals(expected, EncryptionAuthentication.encryptionDriver(input));
    }
}

