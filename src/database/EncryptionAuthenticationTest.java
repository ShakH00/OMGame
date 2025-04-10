package database;

import account.Account;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class EncryptionAuthenticationTest {

    @org.junit.Test
    @Test
    public void testEncryptionDriver_validInput() throws EncryptionFailedException {
        String input = "abcXYZ123";
        String expected = "def[\\]456";
        assertEquals(expected, EncryptionAuthentication.encryptionDriver(input));
    }
    @org.junit.Test
    @Test
    public void testDecryptionDriver_nullInput() {
        assertThrows(DecryptionFailedException.class, () -> {
            DecryptionAuthentication.decryptionDriver(null);
        });
    }

    @org.junit.Test
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






}

