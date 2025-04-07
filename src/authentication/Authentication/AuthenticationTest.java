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

    /** MATH BASED CAPTCHA TEST CASES */

    
    /**
     * test case checking if user has entered correct answer to math equation
     * catching if there is an exception thrown even if user has entered the correct answer
     * then a fail will occur stating an Exception should not have occured
     */
    @Test
    public void mathCAPTCHA_correctAnswer() {
        try {
            CAPTCHAAuthentication.captchaAuthenticatorDriver("100", "math", "100");
        } catch (CAPTCHAAuthenticationFailedException e) {
            fail("Exception should not have been thrown for correct math input.");
        }
    }

    @Test
    public void mathCAPTCHA_wrongAnswer() {
        try {
            CAPTCHAAuthentication.captchaAuthenticatorDriver("100", "math", "-100");
            fail("Expected CAPTCHAAuthenticationFailedException was not thrown.");
        } catch (CAPTCHAAuthenticationFailedException e) {
            assertEquals("Invalid answer entered for Math CAPTCHA Equation!", e.getMessage());
        }
    }

    @Test
    public void mathCAPTCHA_invalidInputFormat() {
        try {
            CAPTCHAAuthentication.captchaAuthenticatorDriver("ten", "math", "10");
            fail("Expected CAPTCHAAuthenticationFailedException was not thrown.");
        } catch (CAPTCHAAuthenticationFailedException e) {
            assertEquals("Invalid input format! Please enter a number.", e.getMessage());
        }
    }

    @Test
    public void checkIfGeneratedProblemIsNotEmpty() {
        String mathProblem = String.valueOf(CAPTCHAAuthentication.generateProblem());
        assertFalse(mathProblem.trim().isEmpty(), "The generated math CAPTCHA should not be empty!");
    }

    @Test
    public void checkIfGeneratedProblemIsNotNull() {
        String mathProblem = String.valueOf(CAPTCHAAuthentication.generateProblem());
        assertNotNull(mathProblem, "The generated math CAPTCHA should not be null!");
    }

    /** TEXT BASED CAPTCHA TEST CASES */

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
    public void checkInvalidCaptchaMode() {
        try {
            CAPTCHAAuthentication.captchaAuthenticatorDriver("cpsc", "cpsc", "cpsc");
            fail("Expected CAPTCHAAuthenticationFailedException was not thrown.");
        } catch (CAPTCHAAuthenticationFailedException e) {
            assertEquals("Invalid CAPTCHA mode.", e.getMessage());
        }
    }

    @Test
    public void checkIfGeneratedTextIsNotEmpty() {
        String CAPTCHA = CAPTCHAAuthentication.generateTextProblem();
        assertFalse(CAPTCHA.trim().isEmpty(), "The generated CAPTCHA text should not be empty!");
    }

    @Test
    public void checkIfGeneratedTextIsNotNull() {
        String captcha = CAPTCHAAuthentication.generateTextProblem();
        assertNotNull(captcha, "The generated CAPTCHA text should not be null!");
    }

    /** IMAGE BASED CAPTCHA TEST CASES */

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


    /** MFA TEST CASES */

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

