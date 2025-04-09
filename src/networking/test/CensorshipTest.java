package networking.test;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CensorshipTest {

    public static void main(String[] args) {
        String[] testMessages = {
                "you are stupid",
                "you are s t u p i d",
                "you are s.t.u.p.i.d",
                "you are s_t_u_p_i_d",
                "you are stupid, dumb, and an idiot!",
                "StUpId",
                "what a stupid stupid person",
                "",
                "this is fine"
        };

        for (String msg : testMessages) {
            CensorResult result = censorChat(msg);
            System.out.println("Before: " + msg);
            System.out.println("After:  " + result.getFilteredMessage());
            System.out.println("Censored Words: " + result.getCensorCount());
            System.out.println();
        }
    }

    /**
     * Censors words from a list in badwords.txt, including spaced/dotted variations.
     * Based on Stack Overflow file reading: https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
     * Bypass regex based on: https://stackoverflow.com/questions/19605150/regex-to-match-characters-separated-by-non-characters
     */
    public static CensorResult censorChat(String message) {
        List<String> badWords = new ArrayList<>();
        int censorCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/networking/badwords.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                badWords.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error loading bad words: " + e.getMessage());
        }

        String filteredMessage = message;

        for (String badWord : badWords) {
            String censor = generateCensor(badWord);

            // Censor normal word
            Pattern exactPattern = Pattern.compile("(?i)\\b" + Pattern.quote(badWord) + "\\b");
            Matcher exactMatcher = exactPattern.matcher(filteredMessage);
            while (exactMatcher.find()) {
                filteredMessage = exactMatcher.replaceFirst(censor);
                censorCount++;
                exactMatcher = exactPattern.matcher(filteredMessage); // reset matcher
            }

            // Censor spaced/dotted/etc. bypass versions
            Pattern bypassPattern = Pattern.compile(buildBypassRegex(badWord), Pattern.CASE_INSENSITIVE);
            Matcher bypassMatcher = bypassPattern.matcher(filteredMessage);
            while (bypassMatcher.find()) {
                filteredMessage = bypassMatcher.replaceFirst(censor);
                censorCount++;
                bypassMatcher = bypassPattern.matcher(filteredMessage); // reset matcher
            }
        }

        return new CensorResult(filteredMessage, censorCount);
    }

    /**
     * Censors everything but the first letter: "stupid" → "s*****"
     */
    public static String generateCensor(String word) {
        if (word.length() <= 1) return "*";
        StringBuilder censored = new StringBuilder();
        censored.append(word.charAt(0));
        for (int i = 1; i < word.length(); i++) {
            censored.append("*");
        }
        return censored.toString();
    }

    /**
     * Builds regex like: s[^a-zA-Z0-9]{0,3}t[^a-zA-Z0-9]{0,3}...
     * to detect spacing, punctuation, or symbol bypasses
     */
    public static String buildBypassRegex(String word) {
        StringBuilder regex = new StringBuilder();
        for (char c : word.toCharArray()) {
            regex.append(Pattern.quote(String.valueOf(c)));
            regex.append("[^a-zA-Z0-9]{0,3}");
        }
        return regex.toString();
    }

    // Helper class to return both the result and word count
    public static class CensorResult {
        private final String filteredMessage;
        private final int censorCount;

        public CensorResult(String filteredMessage, int censorCount) {
            this.filteredMessage = filteredMessage;
            this.censorCount = censorCount;
        }

        public String getFilteredMessage() {
            return filteredMessage;
        }

        public int getCensorCount() {
            return censorCount;
        }
    }
}