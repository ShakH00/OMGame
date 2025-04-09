package database;

import account.Account;
import authentication.ExceptionsAuthentication.EncryptionFailedException;

public class EncryptionAuthentication {

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
     * Applies a character shift to the input string by the specified amount.
     *
     * @param input the original string
     * @param shift the number of positions to shift each character
     * @return the shifted string
     */
    private static String encryptString(String input) {
        return shiftCharacters(input, SHIFT);
    }

    /**
     * Entry point for encryption that handles null checks and exceptions.
     *
     * @param input the string to encrypt
     * @return the encrypted string
     * @throws EncryptionFailedException if input is null or encryption fails
     */
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
    /**
     * Encrypts an Account object's email and password fields.
     *
     * @param account the Account to encrypt
     * @return the encrypted Account
     * @throws EncryptionFailedException if encryption fails
     */
    public static Account encryptAccount(Account account) throws EncryptionFailedException {
        String encryptedEmail = encryptionDriver(account.getEmail());
        String encryptedPassword = encryptionDriver(account.getPassword());

        account.setEmail(encryptedEmail);
        account.setPassword(encryptedPassword);

        return account;
    }

}
