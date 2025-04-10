package authentication.Authentication;

import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;

import java.util.Scanner;

import static authentication.Authentication.MFAAuthentication.generateRandomCode;
import static authentication.Authentication.MFAAuthentication.testMode;

public class MFAAuthenticationV2 {
    public static String emailAuthenticatorDriver(String email) throws MFAAuthenticationFailedException {
        Scanner sc = new Scanner(System.in);
        String code;

        if (!testMode) {
            code = generateRandomCode();
            EmailSender.sendEmail(email, code);
        } else {
            code = "123456";
            System.out.println("Test mode. Your code is: " + code);
        }

        System.out.println("Please enter verification code:");
        String userInput = sc.nextLine();

        if (userInput.equals(code)) {
            return "Code verified";
        } else {
            throw new MFAAuthenticationFailedException("Code Entered is Invalid!");
        }
    }
}
