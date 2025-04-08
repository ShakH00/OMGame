package authentication.Authentication;

public class AuthenticationTestMain {

    public static void main(String[] args) {
        String email = "alia.s@gmail.com";

        try {
            String result = MFAAuthentication.emailAuthenticatorDriver(email);
            System.out.println(result); // "Code verified"
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


}
