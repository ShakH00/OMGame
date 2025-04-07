package authentication;

import authentication.ExceptionsAuthentication.CAPTCHAAuthenticationFailedException;

import java.io.File;
import java.util.Random;

public class CAPTCHAAuthentication {

    public static class captcha {
        private final String prompt;
        private final String answer;

        public captcha(String prompt, String answer) {
            this.prompt = prompt;
            this.answer = answer;
        }

        public String getPrompt() {
            return prompt;
        }

        public String getAnswer() {
            return answer;
        }
    }

    /**
     * Generating a random math equation either using addition, subtraction, multiplication, or division
     * @return - The correct answer for the CAPTCHA equation
     */
    public static captcha generateProblem () {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        int operation = rand.nextInt(4);

        String problem = "";
        int answer = 0;

        /**
         * conditional statements iterating through +, -, *, and /
         */
        if (operation == 0) {
            problem = a + " + " + b;
            answer = a + b;
        } else if (operation == 1) {
            problem = a + " - " + b;
            answer = a - b;
        } else if (operation == 2) {
            problem = a + " * " + b;
            answer = a * b;
        } else {
            answer = a;
            a = a * b;
            problem = a + " / " + b;
        }

        return new captcha("Solve this CAPTCHA to continue: " + problem, String.valueOf(answer));
    }

    /**
     * Generating a text-based CAPTCHA for user
     * @return - If CAPTCHA is verified or not
     */
    public static captcha generateTextProblem() {
        String[] words = {"laptop", "cpsc", "software", "seng", "computer science", "math", "programming",
                "hello world", "university of calgary", "uofc", "dinos", "science", "computer", "math", "artificial intelligence",
                "terminal", "security", "linux", "git"};

        Random random = new Random();
        String word = words[random.nextInt(words.length)];
        boolean reverseText = random.nextBoolean();

        String prompt;
        String expectedAnswer;

        if (reverseText) {
            String reversed = new StringBuilder(word).reverse().toString();
            prompt = "CAPTCHA: Type this word reversed: " + reversed;
            expectedAnswer = word;
        } else {
            prompt = "CAPTCHA: Type this word in uppercase: " + word.toUpperCase();
            expectedAnswer = word.toUpperCase();
        }
        return new captcha(prompt, expectedAnswer);
    }

    /**
     * Stimulating a CAPTCHA authentication
     *
     * @param userInput     - Takes in the user input for the answer to the math equation and text based CAPTCHA
     * @param mode          - Takes in the user input for if they want to do a math or text based equation
     * @param correctAnswer - Checking if the correct answer is inputted for the math and text based CAPTCHA
     * @return
     * @throws CAPTCHAAuthenticationFailedException - Exception thrown if user's input for math equation was incorrect
     */
    public static String captchaAuthenticatorDriver(String userInput, String mode, String correctAnswer) throws CAPTCHAAuthenticationFailedException {

        if (mode.equalsIgnoreCase("math")) {
            try {
                int userAnswer = Integer.parseInt(userInput);
                int expectedAnswer = Integer.parseInt(correctAnswer);

                if (userAnswer == expectedAnswer) {
                    return "CAPTCHA verified";

                } else {
                    throw new CAPTCHAAuthenticationFailedException("Invalid answer entered for Math CAPTCHA Equation!");
                }
            } catch (NumberFormatException e) {
                throw new CAPTCHAAuthenticationFailedException("Invalid input format! Please enter a number.");
            }
        } else if (mode.equalsIgnoreCase("text")) {
            if (userInput.equals(correctAnswer)) {
                return "CAPTCHA verified";
            } else {
                throw new CAPTCHAAuthenticationFailedException("Incorrect text CAPTCHA!");
            }
        } else {
            throw new CAPTCHAAuthenticationFailedException("Invalid CAPTCHA mode.");
        }
    }

    public static File chooseImage(){
        Random rand = new Random();
        String[] files = {"4f8yp.png", "6t9bcds.png", "381057.png", "cdfen.png", "data.png", "dsjcbka.png" +
                "eridati.png", "fche6.png", "finding.png", "following.png", "m8m4x.png"};
        Integer index = rand.nextInt() % files.length;
        if(index < 0){
            index = -index;
        }
        File image = new File(files[index]);
        return image;
    }

    public static boolean verifyCAPTCHA(String input, File file){
        input = input.toLowerCase().trim();
        String expected = file.getName();
        expected = expected.substring(0, expected.length()-4);
        return expected.equals(input);
    }

}