package networking;

public class CensorshipTest {
    public static void main(String[] args) {
        String[] testMessages = {
                "you are stupid",
                "hello friend",
                "StUpId",
                "stupidly done",
                "you are stupid, dumb, and an idiot!",
                "what a stupid stupid person",
                "",
                "this is fine"
        };

        for (String msg : testMessages) {
            System.out.println("Before: " + msg);
            System.out.println("After:  " + censorChat(msg));
            System.out.println();
        }
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
        String[] badWords = { "stupid", "idiot", "dumb" };

        for (String badWord : badWords) {
            message = message.replaceAll("(?i)\\b" + badWord + "\\b", "****");
        }

        return message;
    }
}
