package authentication.ExceptionsAuthentication;

/**
 * Exception thrown when CAPTCHA Authentication fails
 */
public class CAPTCHAAuthenticationFailedException extends Exception {

    /**
     * Message outputted when an error occurs
     * @param message - Message is displayed if an exception occurs
     */
    public CAPTCHAAuthenticationFailedException(String message) {
        super(message);
    }
}
