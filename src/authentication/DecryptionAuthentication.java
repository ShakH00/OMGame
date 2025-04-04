package authentication;

import authentication.ExceptionsAuthentication.DecryptionFailedException;

public class DecryptionAuthentication {

    private static final int SHIFT = 3;  // Fixed shift constant

    private static String shiftCharacters(String input, int shift) {
        StringBuilder output = new StringBuilder();
        for (char character : input.toCharArray()) {
            char shifted = (char) (character + shift);
            output.append(shifted);
        }
        return output.toString();
    }

    private static String decryptString(String input) {
        return shiftCharacters(input, -SHIFT);
    }

    public static String decryptionDriver(String input) throws DecryptionFailedException {
        if (input == null) {
            throw new DecryptionFailedException("Input cannot be null!");
        }
        try {
            return decryptString(input);
        } catch (Exception e) {
            throw new DecryptionFailedException("Decryption failed!");
        }
    }

}
