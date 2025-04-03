package authentication.Authentication;
import ExceptionsAuthentication.MFAAuthenticationFailedException;
import authentication.CAPTCHAAuthentication;
import authentication.MFAAuthentication;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    @Test
    public void MFATest1() {
        MFAAuthentication.testMode = true;
        String userInput = "246810\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        assertDoesNotThrow(() -> MFAAuthentication.emailAuthenticatorDriver("user@ucalgary.ca"));
    }

    @Test
    public void MFATest2() {
        MFAAuthentication.testMode = true; // force known code "123456"
        String userInput = "000000\n"; // user inputting wrong input
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        assertThrows(MFAAuthenticationFailedException.class, () -> MFAAuthentication.emailAuthenticatorDriver("user@ucalgary.ca")
        );
    }




}
