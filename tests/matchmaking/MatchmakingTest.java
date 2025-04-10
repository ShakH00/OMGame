//package matchmaking;
//
//import account.Account;
//import game.GameType;
//import matchmaking.local.Matchmaking;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//
//public class MatchmakingTest {
//
//    private Matchmaking matchMaking;
//    private Account testAccount1;
//    private Account testAccount2;
//    private GameType testGame;
//
//    @Before
//    public void setUp() {
//        matchMaking = new Matchmaking();
//        testAccount1 = new Account(1234, "Alice", "Alice.email@gmail.com", "Password");
//        testAccount2 = new Account(2345, "Bob", "Bob.email@gmail.com", "12345");
//        testGame = GameType.CHESS;
//    }
//
//    @Test
//    public void joinQueueTest() {
//        matchMaking.joinQueue(testAccount1, testGame);
//        assertEquals(testAccount1.getQueuedFor(), testGame);
//    }
//
//    @Test
//    public void removeFromQueueTest() {
//        matchMaking.joinQueue(testAccount1, testGame);
//        assertEquals(testAccount1.getQueuedFor(), testGame);
//        matchMaking.removeFromQueue(testAccount1);
//        assertNull(testAccount1.getQueuedFor());
//    }
//
//    @Test
//    public void updateWithoutGameTest() {
//        assertFalse(matchMaking.updateMatchmakingRange(testAccount1));
//    }
//
//    @Test
//    public void testCompatible1() {
//        testAccount1.updateElo(testGame, 1000);
//        testAccount2.updateElo(testGame, 1020);
//        matchMaking.joinQueue(testAccount1, testGame);
//        matchMaking.joinQueue(testAccount2, testGame);
//        assertTrue(matchMaking.areCompatible(testAccount1, testAccount2));
//    }
//
//    @Test
//    public void testCompatible2() {
//        testAccount1.updateElo(testGame, 1000);
//        testAccount2.updateElo(testGame, 1150);
//        matchMaking.joinQueue(testAccount1, testGame);
//        matchMaking.joinQueue(testAccount2, testGame);
//        assertFalse(matchMaking.areCompatible(testAccount1, testAccount2));
//    }
//
//}
