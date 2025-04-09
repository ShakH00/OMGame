package matchmaking.table;

import account.*;
import account.statistics.MatchOutcomeHandler;
import account.statistics.MatchOutcomeInvalidError;
import account.statistics.StatisticType;
import game.GameType;

import java.util.HashMap;

public class MatchmakingHandlerTest {
    public static void main(String[] args){
        // Initialize test account 1
        int id1 = 123;
        String username1 = "Alice";
        String email1 = "Alice@gmail.com";
        String pass1 = "alice123";
        Account testAccount1 = new Account(id1, username1, email1, pass1);

        // Initialize test account 2
        int id2 = 234;
        String username2 = "Bob";
        String email2 = "Bob@gmail.com";
        String pass2 = "bob123";
        Account testAccount2 = new Account(id2, username2, email2, pass2);

        // Add match outcome for both accounts
        GameType game = GameType.CHESS;

        HashMap<StatisticType, Integer> account1Results = new HashMap<>();
        account1Results.put(StatisticType.WINS, 1);

        HashMap<StatisticType, Integer> account2Results = new HashMap<>();
        account2Results.put(StatisticType.LOSSES, 1);

        try {
            MatchOutcomeHandler.RecordMatchOutcome(game, true, testAccount1, account1Results,testAccount2, account2Results);
        } catch (MatchOutcomeInvalidError e){
            System.out.println(e);
        }


        // NOTE: Since I haven't implemented threading, you will have to run two instances of this class to get ...
        // the test to work. This is because the current thread will not halt on the call to .startMatchmaking until
        // the matchmaking process is complete or cancelled. Please ask me (Elijah) if you have any questions.

//        // Add matchmaking table handlers for account 1
//        MatchmakingTableHandler account1MM = new MatchmakingTableHandler(testAccount1, "");
//
//        try {
//            account1MM.startMatchmaking(game, testAccount1.getElo(game));
//            System.out.println("Finished matchmaking for account 1");
//        } catch (Exception e){
//            System.out.println(e);
//        }

        // Add matchmaking table handlers for account 2
        MatchmakingTableHandler account2MM = new MatchmakingTableHandler(testAccount2, "");

        try {
            account2MM.startMatchmaking(game, testAccount2.getElo(game));
            System.out.println("Finished matchmaking for account 2");
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
