package database;

import account.Account;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;


public class EncryptionAuthenticationTest {
    @Test
    public void testEncryptionDriver_validInput() throws EncryptionFailedException {
        String input = "abcXYZ123";
        String expected = "def[\\]456";
        assertEquals(expected, EncryptionAuthentication.encryptionDriver(input));
    }

    @Test
    public void testDecryptionDriver_nullInput() {
        assertThrows(DecryptionFailedException.class, () -> {
            DecryptionAuthentication.decryptionDriver(null);
        });
    }

    @Test
    public void testDecryptAccount_validEncryptedAccount() throws DecryptionFailedException {
        Account encrypted = new Account(
                1,
                "testuser",
                "ghu#hadpso#frp",
                "sdvv456"
        );

        Account decrypted = DecryptionAuthentication.decryptAccount(encrypted);

        assertNotEquals("ghu#hadpso#frp", decrypted.getEmail());
        assertNotEquals("sdvv456", decrypted.getPassword());
    }

    @Test
    public void testDecryptAccount_nullFields() throws Exception {
        Account account = new Account(); // guest account

        // Use reflection to bypass setter and directly set the private fields
        Field emailField = Account.class.getDeclaredField("email");
        Field passwordField = Account.class.getDeclaredField("password");

        emailField.setAccessible(true);
        passwordField.setAccessible(true);

        emailField.set(account, null);
        passwordField.set(account, null);

        assertThrows(DecryptionFailedException.class, () -> {
            DecryptionAuthentication.decryptAccount(account);
        });
    }

    @Test
    public void testEncryptionDriver_specialCharacters() throws EncryptionFailedException {
        String input = "!@#ABCxyz";
        String expected = "$C&DEF{|}";  // Each char +3
        assertEquals(expected, EncryptionAuthentication.encryptionDriver(input));
    }

    @Test
    public void testDecryptionDriver_specialCharacters() throws DecryptionFailedException {
        String input = "$C&DEF{|}";
        String expected = "!@#ABCxyz";  // Each char -3
        assertEquals(expected, DecryptionAuthentication.decryptionDriver(input));
    }

}

