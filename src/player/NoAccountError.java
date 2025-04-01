package player;

/**
 * Error indicating that an attempt was made to access Account details that do not exist for a guest Account
 */

public class NoAccountError extends Exception {
    public NoAccountError(){}
    public NoAccountError(String message){
        super(message);
    }
}
