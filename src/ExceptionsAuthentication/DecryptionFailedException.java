package ExceptionsAuthentication;

public class DecryptionFailedException extends Exception {
    public DecryptionFailedException(String message) {
        super(message);
    }
}
