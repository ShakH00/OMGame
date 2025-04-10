package authentication.Authentication;

import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;

import java.util.concurrent.ThreadLocalRandom;

/** This class is for MFA Authentication for when a user logs into their account
 *  Email is being used as a secondary verification method
 */
public class MFAAuthentication {

    /**
     * protected boolean checking if the system is running a test mode or not
     */
    public static boolean testMode = false;

    /**
     * Sends authentication code via email and then asks user to input the code sent
     * Checking if the code is correct or not is done through a pop-up window
     *
     * @param email - The verification authentication code will be sent to the email
     * @return A string indicating whether the code was verified successfully
     * @throws MFAAuthenticationFailedException - Exception created for when verification is not successful
     */
    public static String emailAuthenticatorDriver(String email) throws MFAAuthenticationFailedException {

        /**
         * checking if the email entered by user exists in the database or not
         */
        if(database.DatabaseManager.queryAccountByEmail(email) == null){
            throw new MFAAuthenticationFailedException("Email not found in the database.");
        }

        String code;

        // Generate the MFA code
        if (!testMode) {
            code = generateRandomCode();
            // Simulate sending the code via email
            EmailSender.sendEmail(email, code); // Ensure this sends the email
        } else {
            code = "123456"; // Test mode uses a fixed code
            System.out.println("Test mode. Your code is: " + code);
        }

        // Show the MFA input pop-up
        MFAInputPopup popup = new MFAInputPopup();
        String userInput = popup.showPopup(code);

        // Handle cancellation or invalid input
        if (userInput == null) {
            return "Verification canceled or invalid input.";
        }

        // Verify the input
        if (userInput.equals(code)) {
            System.out.println("Code verified successfully!");
            return "Code verified";
        } else {
            throw new MFAAuthenticationFailedException("Code Entered is Invalid!");
        }
    }

    /**
     * Method created to generate a random code for authentication
     * @return - String which is representing a 6-digit code
     */
    public static String generateRandomCode() {
        int randomCode = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(randomCode); // Return as a string
    }
}