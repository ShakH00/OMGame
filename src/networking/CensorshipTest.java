package networking;

public class CensorshipTest {
    public static void main(String[] args) {
        String msg1 = "you are stupid";
        String msg2 = "hello friend";

        System.out.println("Before: " + msg1);
        System.out.println("After: " + censorChat(msg1));

        System.out.println("Before: " + msg2);
        System.out.println("After: " + censorChat(msg2));
    }

    /**
     * Censors predefined bad words in the input message by replacing them with asterisks.
     *
     * This method is inspired by the approach discussed in the Stack Overflow post:
     * https://stackoverflow.com/questions/2966172/censoring-selected-words-replacing-them-with-using-a-single-replaceall
     *
     * @param message The input string potentially containing offensive words.
     * @return The sanitized string with bad words replaced by asterisks.
     */
    public static String censorChat(String message) {
        // Example list of bad words (consider expanding this list as needed)
        String[] badWords = { "stupid", "idiot", "dumb" };

        for (String badWord : badWords) {
            // Using regex to perform case-insensitive replacement
            message = message.replaceAll("(?i)\\b" + badWord + "\\b", "****");
        }

        return message;
    }
}
