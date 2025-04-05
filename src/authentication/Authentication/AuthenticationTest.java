package authentication.Authentication;
import authentication.ExceptionsAuthentication.CAPTCHAAuthenticationFailedException;
import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;
import authentication.MFAAuthentication;
import authentication.CAPTCHAAuthentication;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;

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

    @Test
    public void textCAPTCHA_correctAnswer() {
        try {
            CAPTCHAAuthentication.captchaAuthenticatorDriver("computer science", "text", "computer science");
        } catch (CAPTCHAAuthenticationFailedException e) {
            fail("Exception should not have been thrown for correct text CAPTCHA input.");
        }
    }

    @Test
    public void textCAPTCHA_wrongAnswer() {
        try {
            CAPTCHAAuthentication.captchaAuthenticatorDriver("computer science", "text", "cpsc");
            fail("Expected CAPTCHAAuthenticationFailedException was not thrown.");
        } catch (CAPTCHAAuthenticationFailedException e) {
            assertEquals("Incorrect text CAPTCHA!", e.getMessage());
        }
    }

    @Test
    public void RandomImageTest() {
        File img = CAPTCHAAuthentication.chooseImage();
        System.out.printf(img.getName());
    }

    @Test
    public void verifyCaptchaTest1() {
        File img = new File("authentication/CAPTCHAImages/4f8yp.png");
        String input = "4f8yp";
        assertEquals(true, CAPTCHAAuthentication.verifyCAPTCHA(input, img));
    }

    @Test
    public void verifyCaptchaTest2() {
        File img = new File("authentication/CAPTCHAImages/4f8yp.png");
        String input = "4F8YP";
        assertEquals(true, CAPTCHAAuthentication.verifyCAPTCHA(input, img));
    }

    @Test
    public void verifyCaptchaTest3() {
        File img = new File("authentication/CAPTCHAImages/4f8yp.png");
        String input = "4F8YP.png";
        assertEquals(false, CAPTCHAAuthentication.verifyCAPTCHA(input, img));
    }

    @Test
    public void verifyCaptchaTest4() {
        File img = new File("authentication/CAPTCHAImages/4f8yp.png");
        String input = "meepmorp";
        assertEquals(false, CAPTCHAAuthentication.verifyCAPTCHA(input, img));
    }




}

