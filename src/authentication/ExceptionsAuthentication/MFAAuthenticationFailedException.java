package authentication.ExceptionsAuthentication;

/**
 * Exception thrown when MFA Authentication fails
 */
public class MFAAuthenticationFailedException extends Exception {

    /**
     * Message outputted when an error occurs
     * @param message - Message is displayed if an exception occurs
     */
    public MFAAuthenticationFailedException(String message) {
        super(message);
    }
}
