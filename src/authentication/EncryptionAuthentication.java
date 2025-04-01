package authentication;

import ExceptionsAuthentication.EncryptionFailedException;

public class EncryptionAuthentication {

    private static String encryptString(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                char shifted = (char) (((character - base + 3) % 26) + base);
                encrypted.append(shifted);
            } else {
                throw new IllegalArgumentException("Non alphabetic characters are not supported.");
            }
        }
        return encrypted.toString();
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
