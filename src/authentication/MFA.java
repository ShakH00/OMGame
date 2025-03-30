package authentication;

public static void MFA(String email) throws EmailAuthenticationFailedException {
    Scanner sc = new Scanner(System.in);
    String code;
    if (testMode == false) {
        code = generateRandomCode();
    } else {
        code = "123456";
    }

    System.out.printf("Verification code: %s", code, '\n');
    System.out.println("Please enter verification code: ");
    String userInput = s.nextLine();

    if(userInput.equals(code)) {
        System.out.println("Code verified");
    }
    else {
        throw new EmailAuthenticationFailedException("Invalid code entered!");
}

}