package authentication.ExceptionsAuthentication;

public class EncryptionFailedException extends Exception {
    public EncryptionFailedException(String message) {
        super(message);
    }
}
