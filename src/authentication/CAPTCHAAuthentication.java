package authentication;

public class CAPTCHAAuthentication (String mathProblem) throws CAPTCHAAuthenticationFailedException {

    public static int generateMathProblem() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        int op = rand.nextInt(4);

    }
}