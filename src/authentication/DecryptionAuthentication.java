package authentication;

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

}
