package authentication.Authentication;

import authentication.ExceptionsAuthentication.CAPTCHAAuthenticationFailedException;

import java.io.File;
import java.util.Random;

public class CAPTCHAAuthentication {

    public static class captcha {
        private final String prompt;
        private final String answer;
        private final String instructions;


        /**
         *
         * @param prompt - The question present to the user
         * @param answer - The correct answer in order to pass CAPTCHA
         */
        public captcha(String prompt, String answer, String instructions) {
            this.prompt = prompt;
            this.answer = answer;
            this.instructions = instructions;
        }

        /**
         * Returns the prompt which is displayed to the user
         * @return - The CAPTCHA prompt string
         */
        public String getPrompt() {
            return prompt;
        }

        /**
         * Returns the instructions which is displayed to the user
         * @return - The CAPTCHA prompt string
         */
        public String getInstructions() {
            return instructions;
        }

        /**
         * Returns the correct answer associated to the CAPTCHA
         * @return - The expected answer as string
         */
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

        return new captcha(problem, String.valueOf(answer), "Solve this CAPTCHA to continue: ");
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
        String inst;

        if (reverseText) {
            prompt = new StringBuilder(word).reverse().toString();
            inst = "CAPTCHA: \nType this word reversed: ";
            expectedAnswer = word;
        } else {
            prompt = word.toLowerCase();
            inst = "CAPTCHA: \nType this word in uppercase: ";
            expectedAnswer = word.toUpperCase();
        }
        return new captcha(prompt, expectedAnswer, inst);
    }

    /**
     * Stimulating a CAPTCHA authentication
     *
     * @param userInput     - Takes in the user input for the answer to the math, text, and image based CAPTCHA
     * @param mode          - Takes in the user input for if they want to do a math, text, or image based CAPTCHA
     * @param correctAnswer - Checking if the correct answer is inputted for the math, text, and image based CAPTCHA
     * @return
     * @throws CAPTCHAAuthenticationFailedException - Exception thrown if user's input for math equation was incorrect
     */
    public static String captchaAuthenticatorDriver(String userInput, String mode, String correctAnswer) throws CAPTCHAAuthenticationFailedException {

        /** if user chooses "math" then this if condition will happen */
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

        /** if user chooses "text" then this else if condition will happen */
        } else if (mode.equalsIgnoreCase("text")) {
            if (userInput.equals(correctAnswer)) {
                return "CAPTCHA verified";
            } else {
                throw new CAPTCHAAuthenticationFailedException("Incorrect Text CAPTCHA!");
            }

        /** if user chooses "image" then this else if condition will happen */
        } else if(mode.equalsIgnoreCase("image")) {
            String input = userInput.toLowerCase().trim();
            String expectedOutput = correctAnswer.toLowerCase().trim();

            if(input.equals(expectedOutput)) {
                    return "CAPTCHA verified";
            } else {
                throw new CAPTCHAAuthenticationFailedException("Incorrect Image CAPTCHA!");
            }

        /** else statement will throw that user has entered the incorrect CAPTCHA mode if none is chosen between math, text, or image*/
        } else {
            throw new CAPTCHAAuthenticationFailedException("Invalid CAPTCHA mode.");
        }
    }

    /**
     * The CAPTCHA will choose a random image from the files if user enters the mode as "image"
     * @return - Image from one of the files
     */
    public static String chooseImage(){
        Random rand = new Random();
        String[] files = {"4f8yp.png", "6t9bcds.png", "381057.png", "cdfen.png", "data.png", "dsjcbka.png",
                "eridati.png", "fche6.png", "finding.png", "following.png", "m8m4x.png"};
        Integer index = rand.nextInt() % files.length;
        if(index < 0){
            index = -index;
        }
        String image = files[index];
        return image;
    }

    /**
     * Verifying the users input with the expected answer from the image based CAPTCHA
     * @param input - The users input for the image CAPTCHA
     * @param file - The file which was chosen for CAPTCHA
     * @return - True if the users input matches the CAPTCHA image verification, otherwise false
     */
    public static boolean verifyCAPTCHA(String input, File file){
        input = input.toLowerCase().trim();
        String expected = file.getName();
        expected = expected.substring(0, expected.length()-4);
        return expected.equals(input);
    }

}