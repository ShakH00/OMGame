package authentication;

public class CAPTCHAAuthentication (String mathProblem) throws CAPTCHAAuthenticationFailedException {

    public static int generateMathProblem() {
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
            a = a * b; // ensures clean division
            problem = a + " / " + b;
        }

        System.out.println("Solve this CAPTCHA to continue: " + problem);
        return answer;
    }
}