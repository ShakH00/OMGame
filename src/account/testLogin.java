package account;

import authentication.ExceptionsAuthentication.EncryptionFailedException;

public class testLogin {
    public static void main(String[] args) {
        // Make sure there is an account in the database before testing
        // Change the username, password to the correct account
        // Create an account object
        Account account = new Account();

        // Attempt to login using valid credentials
        boolean loginSuccess = false;
        try {
            loginSuccess = account.TryLoginWithUsernameAndPassword("test", "test@123");
        } catch (EncryptionFailedException e) {
            throw new RuntimeException(e);
        }

        // Check if login was successful
        if (loginSuccess) {
            System.out.println("Login successful!");
            System.out.println("Account Details:");
            System.out.println("Username: " + account.getUsername());
            System.out.println("Email: " + account.getEmail());
            System.out.println("ID: " + account.getID());
            System.out.println("Friends: " + account.getFriends());
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }
}
