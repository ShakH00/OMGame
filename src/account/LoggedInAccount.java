package account;

/*
This class keeps track of the user who is currently logged in, of the account of the guest playing the games
 */

public class LoggedInAccount {

    // Account currently being used
    public static Account user;

    /*
    getter for the account
     */
    public static Account getAccount(){return user;}

    /*
    setter for the account
     */
    public static void setAccount(Account newUser){user = newUser;}
}
