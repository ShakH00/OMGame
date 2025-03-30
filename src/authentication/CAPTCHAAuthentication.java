package authentication;

import java.util.Random;

public class CAPTCHAAuthentication {

    public static int generateMathProblem () {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        int operation = rand.nextInt(4);

        String problem = "";
        int answer = 0;

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

    public static void captchaAuthenticatorDriver(String userInput) throws CAPTCHAAuthenticationFailedException {
        int correctAnswer = CAPTCHAAuthentication.generateProblem();

        try {
            int userAnswer = Integer.parseInt(userInput);

            if (userAnswer == correctAnswer) {
                System.out.println("CAPTCHA verified");
            } else {
                throw new CAPTCHAException("Invalid answer entered!");
            }

        } catch (NumberFormatException e) {
            throw new CAPTCHAException("Invalid input format! Please enter a number.");
        }
    }
}