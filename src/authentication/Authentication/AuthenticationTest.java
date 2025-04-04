package authentication.Authentication;
import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;
import authentication.MFAAuthentication;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    //test if user inputs in the correct verification code
    @Test
    public void MFATest1() {
        MFAAuthentication.testMode = true;
        String userInput = "246810\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        assertDoesNotThrow(() -> MFAAuthentication.emailAuthenticatorDriver("user@ucalgary.ca"));
    }

    //test if user inputs in the wrong verification code
    @Test
    public void MFATest2() {
        MFAAuthentication.testMode = true; // force known code "123456"
        String userInput = "000000\n"; // user inputting wrong input
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        assertThrows(MFAAuthenticationFailedException.class, () -> MFAAuthentication.emailAuthenticatorDriver("user@ucalgary.ca")
        );
    }

    //test for if user does not put in anything for the verification code
    @Test
    public void MFATest3() {
        MFAAuthentication.testMode = true;
        String userInput = "\n"; // user hits Enter without any input
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        assertThrows(MFAAuthenticationFailedException.class, () -> MFAAuthentication.emailAuthenticatorDriver("user@ucalgary.ca")
        );
    }

    //test for if user enters letters instead of numbers for verification code
    @Test
    public void MFATest4() {
        MFAAuthentication.testMode = true;
        String userInput = "abc123\n"; // entering alphabet letters instead of numbers
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        assertThrows(MFAAuthenticationFailedException.class, () -> MFAAuthentication.emailAuthenticatorDriver("user@ucalgary.ca")
        );
        assertEquals("Code Entered is Invalid!", "Please enter digits only!");
    }




}
