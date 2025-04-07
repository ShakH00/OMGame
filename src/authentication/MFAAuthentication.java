package authentication;

import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/** This class is for MFA Authentication for when a user log's into their account
 *  Email is being used as a secondary verification method
 */
public class MFAAuthentication {

    /**
     * protected boolean checking if the system is running a test mode or not
     */
    public static boolean testMode = false;

    /**
     * Sends authentication code via email and then asks user to input the code sent
     * Checking if the code is correct or not is done through conditional statements
     *
     * @param email - The verification authentication code will be sent to the email
     * @return
     * @throws MFAAuthenticationFailedException - Exception created for when verification is not successful
     */
    public static String emailAuthenticatorDriver(String email) throws MFAAuthenticationFailedException {

    Scanner sc = new Scanner(System.in);
    String code;
    if (testMode == false) {
        code = generateRandomCode();
    } else {
        code = "123456";
    }

    /**
     * Prompts for verification code to be inputted by user
     */

    System.out.printf("Verification code: %s", code, '\n');
    System.out.println("Please enter verification code: ");
    String userInput = sc.nextLine();

    /**
     * Conditional statements checking if the code is verified or not
     */
    if (userInput.equals(code)) {
        return "Code verified";
    } else {
        throw new MFAAuthenticationFailedException("Code Entered is Invalid!");
        }
    }

    /**
     * Method created to generate a random code for authentication
     * @return - String which is representing a 6-digit code
     */
    public static String generateRandomCode () {
        int randomCode = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(randomCode);  // Return as a string
    }
}