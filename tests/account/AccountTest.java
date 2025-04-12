package account;


import account.statistics.StatisticType;
import game.GameType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class AccountTest {

    private Account guestAccount;
    private Account userAccount1;
    private Account userAccount2;

    @Before
    public void setUp() {
        guestAccount = new Account();
        userAccount1 = new Account("Alice", "alice@example.com", "password123");
        userAccount2 = new Account("Bob", "bob@example.com", "securePass");
    }

    @Test
    public void testGuestAccountInitialization() {
        assertTrue(guestAccount.getIsGuest());
        assertEquals(-1, guestAccount.getID());
    }

    @Test
    public void testUserAccountInitialization() {
        assertFalse(userAccount1.getIsGuest());
        assertEquals("Alice", userAccount1.getUsername());
        assertEquals(1, userAccount1.getID());
    }

    @Test
    public void testConvertGuestToUser() {
        guestAccount.convertToNonGuestAccount(3, "Charlie", "charlie@example.com", "newPass");
        assertFalse(guestAccount.getIsGuest());
        assertEquals(3, guestAccount.getID());
        assertEquals("Charlie", guestAccount.getUsername());
    }

    @Test
    public void testUpdateAccountInfoValid() {
        userAccount1.updateAccountInfo("AliceNew", "newalice@example.com", "newPass123");
        assertEquals("AliceNew", userAccount1.getUsername());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateAccountInfoInvalidEmail() {
        userAccount1.updateAccountInfo("AliceNew", "invalidEmail", "newPass123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateAccountInfoInvalidPassword() {
        userAccount1.updateAccountInfo("AliceNew", "alice@example.com", "123");
    }

    @Test
    public void testAddAndRemoveFriend() {
        assertEquals(1, userAccount1.getFriends().size());
        assertTrue(userAccount1.getFriends().contains(userAccount2));

        boolean removed = userAccount1.removeFriend(userAccount2.getID());
        assertTrue(removed);
        assertFalse(userAccount1.getFriends().contains(userAccount2));
    }

    @Test
    public void testRemoveNonexistentFriend() {
        boolean removed = userAccount1.removeFriend(userAccount2.getID());
        assertFalse(removed);
    }

//    @Test
//    public void testUpdateStatistics() {
//        HashMap<StatisticType, Integer> statUpdates = new HashMap<>();
//        statUpdates.put(StatisticType.WINS, 2);
//        statUpdates.put(StatisticType.LOSSES, 1);
//
//        userAccount1.updateStatistics(GameType.CHESS, statUpdates);
//        assertEquals(2, userAccount1.getStatistic(GameType.CHESS, StatisticType.WINS));
//        assertEquals(1, userAccount1.getStatistic(GameType.CHESS, StatisticType.LOSSES));
//    }

    @Test
    public void testUpdateElo() {
        userAccount1.updateElo(GameType.CHESS, 1600);
        assertEquals(1600, userAccount1.getElo(GameType.CHESS));
    }

    @Test
    public void testMatchHistoryLogging() {
        String[] match1 = {"Win", "Chess", "Bob", "1400", "2", "12345"};
        userAccount1.logMatch(match1);

        String[][] history = userAccount1.getMatchHistory();
        assertEquals("Win", history[1][0]);
        assertEquals("Chess", history[1][1]);
    }

//    @Test
//    public void testGetGameStatistics() {
//        HashMap<StatisticType, Integer> statUpdates = new HashMap<>();
//        statUpdates.put(StatisticType.WINS, 5);
//        statUpdates.put(StatisticType.LOSSES, 2);
//        userAccount1.updateStatistics(GameType.CHESS, statUpdates);
//
//        String[] stats = userAccount1.getGameStatistics(GameType.CHESS);
//        assertEquals("5", stats[0]); // Assuming order: WINS first
//        assertEquals("2", stats[1]); // Assuming order: LOSSES second
//    }

//    @Test
//    public void testGetCombinedStatistics() {
//        HashSet<GameType> games = new HashSet<>();
//        games.add(GameType.CHESS);
//        games.add(GameType.CHECKERS);
//
//        String[] stats = userAccount1.getCombinedStatistics(games);
//        assertNotNull(stats);
//    }

    @Test
    public void testQueueingSystem() {
        assertNull(userAccount1.getQueuedFor());

        userAccount1.setQueuedFor(GameType.CONNECT4);
        assertEquals(GameType.CONNECT4, userAccount1.getQueuedFor());

        userAccount1.clearQueuedFor();
        assertNull(userAccount1.getQueuedFor());
    }

    @Test
    public void testLoginPlaceholder() {
        assertFalse(userAccount1.TryLoginWithUsernameAndPassword("Alice", "password123"));
    }

}
