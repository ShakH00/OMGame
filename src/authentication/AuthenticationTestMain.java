package authentication;

import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;

import java.util.Scanner;

import static authentication.MFAAuthentication.generateRandomCode;
import static authentication.MFAAuthentication.testMode;

public class AuthenticationTestMain {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("**** MFA Authentication Test ****");
//        System.out.print("Enter your email: ");
//        String email = sc.nextLine();
//
//        try {
//            MFAAuthentication.emailAuthenticatorDriver(email);
//        } catch (MFAAuthenticationFailedException e) {
//            System.out.println("MFA Authentication failed: " + e.getMessage());
//        }
//        System.out.println("\n**** CAPTCHA Authentication Test ****");
//        System.out.print("Enter your answer to the CAPTCHA equation: ");
//        String captchaInput = sc.nextLine();
//
//        try {
//            CAPTCHAAuthentication.captchaAuthenticatorDriver(captchaInput);
//        } catch (CAPTCHAAuthenticationFailedException e) {
//            System.out.println("CAPTCHA Authentication failed: " + e.getMessage());
//        }
//    }

    public static void main(String[] args) {
        try {
            String result = MFAAuthenticationV2.emailAuthenticatorDriver("nebilawako@gmail.com");
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
        }
    }


}
