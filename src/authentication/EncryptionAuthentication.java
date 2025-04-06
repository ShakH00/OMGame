package authentication;

import authentication.ExceptionsAuthentication.EncryptionFailedException;

public class EncryptionAuthentication {

    private static final int SHIFT = 3;  // Fixed shift constant

    private static String shiftCharacters(String input, int shift) {
        StringBuilder output = new StringBuilder();
        for (char character : input.toCharArray()) {
            char shifted = (char) (character + shift);
            output.append(shifted);
        }
        return output.toString();
    }

    private static String encryptString(String input) {
        return shiftCharacters(input, SHIFT);
    }

    public static String encryptionDriver(String input) throws EncryptionFailedException {
        if (input == null) {
            throw new EncryptionFailedException("Input cannot be null!");
        }
        try {
            return encryptString(input);
        } catch (Exception e) {
            throw new EncryptionFailedException("Encryption failed!");
        }
    }



}
