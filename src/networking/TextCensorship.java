package networking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TextCensorship class to censor words in a message.
 *
 * @author Hatem Chehade
 */
public class TextCensorship {

    /**
     * Censors words from a list in CensoredWordList.txt, including spaced/dotted variations.
     * Based on Stack Overflow file reading: <a href="https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java">...</a>
     * Bypass regex based on: <a href="https://stackoverflow.com/questions/19605150/regex-to-match-characters-separated-by-non-characters">...</a>
     * Censored Wordlist: <a href="https://github.com/coffee-and-fun/google-profanity-words">...</a>
     *
     * @author Hatem Chehade
     */
    public static CensorResult censorChat(String message) {
        List<String> badWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/networking/CensoredWordList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                badWords.add(line.trim());
            }
        } catch (IOException e) {
            // Comment out error message as it will constantly show up when hosted on Docker
            // System.err.printf("[TextCensor: %s] Error loading bad words: %s\n", getTime(), e.getMessage());
        }

        String filteredMessage = message;

        for (String badWord : badWords) {
            String censor = generateCensor(badWord);

            // Censor normal word
            Pattern exactPattern = Pattern.compile("(?i)\\b" + Pattern.quote(badWord) + "\\b");
            Matcher exactMatcher = exactPattern.matcher(filteredMessage);
            while (exactMatcher.find()) {
                filteredMessage = exactMatcher.replaceFirst(censor);
                exactMatcher = exactPattern.matcher(filteredMessage); // reset matcher
            }

            // Censor spaced/dotted/etc. bypass versions
            Pattern bypassPattern = Pattern.compile(buildBypassRegex(badWord), Pattern.CASE_INSENSITIVE);
            Matcher bypassMatcher = bypassPattern.matcher(filteredMessage);
            while (bypassMatcher.find()) {
                filteredMessage = bypassMatcher.replaceFirst(censor);
                bypassMatcher = bypassPattern.matcher(filteredMessage); // reset matcher
            }
        }

        return new CensorResult(filteredMessage);
    }

    /**
     * Censors everything but the first letter: "stupid" → "s*****"
     *
     * @author Hatem Chehade
     */
    public static String generateCensor(String word) {
        if (word.length() <= 1) return "*";
        return word.charAt(0) +
                "*".repeat(word.length() - 1);
    }

    /**
     * Builds regex like: s[^a-zA-Z0-9]{0,3}t[^a-zA-Z0-9]{0,3}...
     * to detect spacing, punctuation, or symbol bypasses
     *
     * @author Hatem Chehade
     */
    public static String buildBypassRegex(String word) {
        StringBuilder regex = new StringBuilder();
        for (char c : word.toCharArray()) {
            regex.append(Pattern.quote(String.valueOf(c)));
            regex.append("[^a-zA-Z0-9]{0,3}");
        }
        return regex.toString();
    }

    /**
     * CensorResult record to hold the filtered message.
     *
     * @author Hatem Chehade
     */
    public record CensorResult(String filteredMessage) {
        /**
         * Gets the filtered message.
         *
         * @return filteredMessage message after censorship
         * @author Hatem Chehade
         */
        @Override
        public String filteredMessage() {
            return filteredMessage;
        }
    }
}