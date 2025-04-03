package authentication.Authentication;
import authentication.CAPTCHAAuthentication;
import authentication.MFAAuthentication;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    @Test
    public void MFATest1() {
        MFAAuthentication.testMode = true; // Enable test mode to use code "246810"
        String simulatedUserInput = "246810\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        assertDoesNotThrow(() -> MFAAuthentication.emailAuthenticatorDriver("user@example.com"));
    }


}
