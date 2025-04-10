//package matchmaking;
//
//import account.Account;
//import game.GameType;
//import matchmaking.local.LocalMatchHandler;
//import matchmaking.local.PrivateMatch;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//
//
//public class PrivateMatchTest {
//    private LocalMatchHandler handler;
//    private Account testAccount1;
//    private Account testAccount2;
//    private GameType testGame;
//
//    @Before
//    public void setUp() {
//        handler = new LocalMatchHandler();
//        testAccount1 = new Account(1234, "Alice", "Alice.email@gmail.com", "Password");
//        testAccount2 = new Account(2345, "Bob", "Bob.email@gmail.com", "12345");
//        testGame = GameType.CHESS;
//    }
//
//    @Test
//    public void createPrivateMatchTest() {
//        handler.createPrivateMatch(testAccount1);
//        ArrayList<PrivateMatch> matches =  handler.getPrivateMatches();
//        assertEquals(matches.size(), 1);
//    }
//
//    @Test
//    public void createHostForMatchTest() {
//        PrivateMatch match = handler.createPrivateMatch(testAccount1);
//        assertEquals(match.getHost(), testAccount1);
//    }
//
//    @Test
//    public void joinPrivateMatchWithIDTest() {
//        PrivateMatch match = handler.createPrivateMatch(testAccount1);
//        String id = match.getRoomID();
//        handler.joinUsingID(testAccount2, id);
//        assertEquals(match.getAccounts().size(), 2);
//    }
//
//    @Test
//    public void hostLeavePrivateMatch() {
//        PrivateMatch match = handler.createPrivateMatch(testAccount1);
//        String id = match.getRoomID();
//        handler.joinUsingID(testAccount2, id);
//        handler.leavePrivateMatch(testAccount1, match);
//        ArrayList<PrivateMatch> matches =  handler.getPrivateMatches();
//        assertEquals(matches.size(), 0);
//    }
//
//    @Test
//    public void nonHostLeavePrivateMatch() {
//        PrivateMatch match = handler.createPrivateMatch(testAccount1);
//        String id = match.getRoomID();
//        handler.joinUsingID(testAccount2, id);
//        handler.leavePrivateMatch(testAccount2, match);
//        assertEquals(match.getAccounts().size(), 1);
//    }
//
//}
