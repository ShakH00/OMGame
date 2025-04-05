package database;

import account.Account;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static account.AccountStorageUtility.*;

public class DatabaseTest {
    public static void main(String[] args) {
        int accountID=0;
        String username="test";
        String email="nebilawako@gmail.com";
        String password="test@123";

        int accountID1=1;
        String username1="test1";
        String email1="nebilawako@gmail.com1";
        String password1="test@123";

        Account testAccount = new Account(accountID, username, email, password);
        Account testAccount1 = new Account(accountID1, username1, email1, password1);

        testAccount.addFriend(1);
        testAccount.addFriend(2);
        testAccount.addFriend(3);


        Boolean isSaved = DatabaseManager.saveAccount(testAccount);

    }
}
