package authentication;

import ExceptionsAuthentication.CAPTCHAAuthenticationFailedException;

import java.util.Random;

/**
 * This class is for CAPTCHA Authentication
 * It is generating a random math based equation
 */
public class CAPTCHAAuthentication {

    /** Generating a random math equation either using addition, subtraction, multiplication, or division
     * @return - The correct answer for the CAPTCHA equation
     */
    public static int generateProblem () {
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

        System.out.println("Solve this CAPTCHA to continue: " + problem);
        return answer;
    }

    public static String generateTextProblem() {
        String[] words = {"abcd", "efg", "hijk", "lmno"};
        Random random = new Random();
        String word = words[random.nextInt(words.length)];

        boolean reverseText = random.nextBoolean();

        if (reverseText) {
            String reversed = new StringBuilder(word).reverse().toString();
            System.out.println("CAPTCHA: Type this word reversed: " + reversed);
            return word;
        }
    }

    /**
     * Stimulating a CAPTCHA authentication
     * @param userInput - Takes in the user input for the answer to the math equation
     * @throws CAPTCHAAuthenticationFailedException - Exception thrown if user's input for math equation was incorrect
     */
    public static void captchaAuthenticatorDriver(String userInput) throws CAPTCHAAuthenticationFailedException {
        int correctAnswer = CAPTCHAAuthentication.generateProblem();

        try {
            int userAnswer = Integer.parseInt(userInput);

            if (userAnswer == correctAnswer) {
                System.out.println("CAPTCHA verified");
            } else {
                throw new CAPTCHAAuthenticationFailedException("Invalid answer entered!");
            }

        } catch (NumberFormatException e) {
            throw new CAPTCHAAuthenticationFailedException("Invalid input format! Please enter a number.");
        }
    }
}