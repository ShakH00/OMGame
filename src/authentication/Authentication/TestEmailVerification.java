package authentication.Authentication;

import authentication.Authentication.EmailSender;
import authentication.Authentication.MFAAuthentication;
import authentication.Authentication.MFAInputPopup;

public class TestEmailVerification {
    public static void main(String[] args) {

        // Replace with the recipient's email address
        String toEmail = "hzlol999@gmail.com"; // Replace with your email or the recipient's email
        String verificationCode = "53491"; // Example verification code

        // Step 1: Send the verification code via email
        System.out.println("Sending verification code to: " + toEmail);
        EmailSender.sendEmail(toEmail, verificationCode);

        // Step 2: Display the pop-up for the user to enter the code
        MFAInputPopup popup = new MFAInputPopup();
        String userInput = popup.showPopup(verificationCode);

        // Step 3: Verify the code entered by the user
        if (userInput.equals(verificationCode)) {
            System.out.println("Code verified successfully!");
        } else {
            System.err.println("Verification failed. The code entered is incorrect.");
        }
    }
}