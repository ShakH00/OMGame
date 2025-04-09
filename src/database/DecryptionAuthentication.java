package database;

import account.Account;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import authentication.ExceptionsAuthentication.EncryptionFailedException;

public class DecryptionAuthentication {

    private static final int SHIFT = 3;  // Fixed shift constant

    /**
     * Applies a character shift to the input string by the specified amount.
     *
     * @param input the original string
     * @param shift the number of positions to shift each character
     * @return the shifted string
     */
    private static String shiftCharacters(String input, int shift) {
        StringBuilder output = new StringBuilder();
        for (char character : input.toCharArray()) {
            char shifted = (char) (character + shift);
            output.append(shifted);
        }
        return output.toString();
    }

    /**
     * Decrypts a string by applying a reverse shift.
     *
     * @param input the encrypted string
     * @return the decrypted string
     */
    private static String decryptString(String input) {
        return shiftCharacters(input, -SHIFT);
    }

    /**
     * Entry point for decryption that handles null checks and exceptions.
     *
     * @param input the string to decrypt
     * @return the decrypted string
     * @throws DecryptionFailedException if input is null or decryption fails
     */
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

    /**
     * Decrypts an Account object's email and password fields.
     *
     * @param account the Account to decrypt
     * @return the decrypted Account
     * @throws DecryptionFailedException if decryption fails
     */
    public static Account decryptAccount(Account account) throws DecryptionFailedException {
        String originalEmail = decryptionDriver(account.getEmail());
        String originalPassword = decryptionDriver(account.getPassword());

        account.setEmail(originalEmail);
        account.setPassword(originalPassword);

        return account;
    }

}
