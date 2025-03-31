package authentication;

import ExceptionsAuthentication.MFAAuthenticationFailedException;

import java.util.Scanner;

public class AuthenticationTestMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("**** MFA Authentication Test ****");
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        try {
            MFAAuthentication.emailAuthenticatorDriver(email);
        } catch (MFAAuthenticationFailedException e) {
            System.out.println("MFA Authentication failed: " + e.getMessage());
        }

    }
}
