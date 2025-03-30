package authentication;

public static void MFA(String email) throws EmailAuthenticationFailedException {
    Scanner sc = new Scanner(System.in);
    String code;
    if (testMode == false) {
        code = generateRandomCode();
    } else {
        code = "123456";
    }
}