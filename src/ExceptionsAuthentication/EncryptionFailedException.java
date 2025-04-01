package ExceptionsAuthentication;

public class EncryptionFailedException extends RuntimeException {
    public EncryptionFailedException(String message) {
        super(message);
    }
}
