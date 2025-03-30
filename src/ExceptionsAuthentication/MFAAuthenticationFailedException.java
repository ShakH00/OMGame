package ExceptionsAuthentication;

public class MFAAuthenticationFailedException extends Exception {
    public MFAAuthenticationFailedException(String message) {
        super(message);
    }
}
