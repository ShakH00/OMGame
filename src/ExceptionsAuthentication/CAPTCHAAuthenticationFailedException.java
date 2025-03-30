package ExceptionsAuthentication;

public class CAPTCHAAuthenticationFailedException extends Exception {
    public CAPTCHAAuthenticationFailedException(String message) {
        super(message);
    }
}
