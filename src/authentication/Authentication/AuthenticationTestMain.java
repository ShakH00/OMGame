package authentication.Authentication;

import java.util.Scanner;

public class AuthenticationTestMain {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter your email: ");
//        String email = scanner.nextLine();


        String email = "yomansup703@gmail.com";

        try {
            String result = MFAAuthentication.emailAuthenticatorDriver(email);
            System.out.println(result); // "Code verified"
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
