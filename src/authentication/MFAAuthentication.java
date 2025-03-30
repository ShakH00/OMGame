package authentication;

public static void MFAAuthentication {

    public static void emailAuthenticatorDriver(String email) throws MFAAuthenticationFailedException {

    Scanner s = new Scanner(System.in);
    String code;
    if (testMode == false) {
        code = generateRandomCode();
    } else {
        code = "123456";
    }

    System.out.printf("Verification code: %s", code, '\n');
    System.out.println("Please enter verification code: ");
    String userInput = s.nextLine();

    if (userInput.equals(code)) {
        System.out.println("Code verified");
    } else {
        throw new EmailAuthenticationFailedException("Invalid code entered!");
        }
    }

    public static String generateRandomCode () {
        int randomCode = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(randomCode);  // Return as a string
    }
}