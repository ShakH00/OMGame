package player;

public class CreateAccount {
    public static Account createAccount() {

        return null;
    }
    public void generateAccountID(){}
    public void validUsername(){

    }
    public void validPassword(){}
    public void validEmail(){

    }
    public static void main(String[] args) {
        Account permanent = createAccount();
        System.out.println("Permanent account created: " + permanent.getUsername());
    }
}
