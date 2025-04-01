package authentication;

public class DecryptionAuthentication {

    private static String decryptString(String input) {
        StringBuilder decrypted = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                char shifted = (char) (((character - base - 3 + 26) % 26) + base);
                decrypted.append(shifted);
            } else {
                throw new IllegalArgumentException("Non-alphabetic characters are not supported.");
            }
        }
        return decrypted.toString();
    }
}
