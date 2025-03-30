package authentication;

public class CAPTCHAAuthentication (String mathProblem) throws CAPTCHAAuthenticationFailedException {

    public static int generateMathProblem() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        int op = rand.nextInt(4);

        String problem = "";
        int answer = 0;

        if (op == 0) {
            problem = a + " + " + b;
            answer = a + b;
        } else if (op == 1) {
            problem = a + " - " + b;
            answer = a - b;
        }
    }
}