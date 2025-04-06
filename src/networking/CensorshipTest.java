package networking;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

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
     * This version reads the list of bad words from an external file for easier updates.
     * Inspired by file-reading patterns from Stack Overflow:
     * https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
     *
     * @param message The input chat message
     * @return Censored message with offensive words replaced by ****
     */
    public static String censorChat(String message) {
        List<String> badWords = new ArrayList<>();

        // Adjusted path for when the program is run from the src directory
        try (BufferedReader br = new BufferedReader(new FileReader("networking/badwords.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                badWords.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error loading bad words: " + e.getMessage());
        }

        for (String badWord : badWords) {
            message = message.replaceAll("(?i)\\b" + Pattern.quote(badWord) + "\\b", "****");
        }

        return message;
    }
