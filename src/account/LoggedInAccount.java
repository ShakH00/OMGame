package account;

public class LoggedInAccount {
    public static Account user;
    public static Account getAccount(){return user;}
    public static void setAccount(Account newUser){user = newUser;}
}
