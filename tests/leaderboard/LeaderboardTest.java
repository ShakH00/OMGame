package leaderboard;

import account.Account;
import account.CreateAccount;
import account.NoAccountError;
import account.statistics.MatchOutcomeHandler;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.DatabaseManager;
import game.GameType;
import account.statistics.StatisticType;
import matchmaking.MatchmakingHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class LeaderboardTest {

    private Leaderboard leaderboard;
    private GameType testGame;

    @Before
    public void setUp() throws EncryptionFailedException {
        leaderboard = new Leaderboard();
        testGame = GameType.CHESS;

        // Clear accounts from database
        DatabaseManager.deleteAllAccounts();

        // Initialize accounts in database
        Account alice = CreateAccount.createAccount("Alice", "alice@alice.alice", "Alice123!!!");
        Account bob = CreateAccount.createAccount("Bob", "bob@bob.bob", "Bob123!!!");
        Account carol = CreateAccount.createAccount("Carol", "carol@carol.carol", "Carol123!!!");

        // Set alice stats
        HashMap<StatisticType, Integer> aliceStats = new HashMap<>();
        aliceStats.put(StatisticType.WINS, 3);
        aliceStats.put(StatisticType.LOSSES, 2);
        aliceStats.put(StatisticType.CHECKS, 3);
        alice.getStatisticsHashMap().get(testGame).addStatistics(aliceStats);

        // Set bob stats
        HashMap<StatisticType, Integer> bobStats = new HashMap<>();
        bobStats.put(StatisticType.WINS, 5);
        bobStats.put(StatisticType.LOSSES, 5);
        bobStats.put(StatisticType.CHECKS, 300);
        bob.getStatisticsHashMap().get(testGame).addStatistics(bobStats);

        // Set carol stats
        HashMap<StatisticType, Integer> carolStats = new HashMap<>();
        carolStats.put(StatisticType.WINS, 7);
        carolStats.put(StatisticType.LOSSES, 3);
        carolStats.put(StatisticType.CHECKS, 1);
        carol.getStatisticsHashMap().get(testGame).addStatistics(carolStats);

        // Set elo for each
        alice.getStatisticsHashMap().get(testGame).updateElo(1500);
        bob.getStatisticsHashMap().get(testGame).updateElo(1400);
        carol.getStatisticsHashMap().get(testGame).updateElo(1600);

        // Add friends
        alice.addFriend(bob.getID());
        bob.addFriend(alice.getID());
        alice.addFriend(carol.getID());
        carol.addFriend(alice.getID());

        // Save each to database
        DatabaseManager.saveAccount(alice);
        DatabaseManager.saveAccount(bob);
        DatabaseManager.saveAccount(carol);
    }

    @Test
    public void testGetGlobalLeaderboard_SortsDescendingAndPaginatesCorrectly() {
        String[][] result = leaderboard.getGlobalLeaderboard(
                testGame,
                StatisticType.ELO,
                StatisticType.WINS,
                false, // Descending
                2,
                1
        );

        assertArrayEquals(new String[]{"RANK", "USERNAME", "Elo", "Win Rate", "Wins", "Wins"}, result[0]);
        assertEquals("1", result[1][0]);
        assertEquals("Carol", result[1][1]);
        assertEquals("1600", result[1][2]);

        assertEquals("2", result[2][0]);
        assertEquals("Alice", result[2][1]);
        assertEquals("1500", result[2][2]);
    }

    @Test
    public void testGetGlobalLeaderboard_SortsAscending() {
        String[][] result = leaderboard.getGlobalLeaderboard(
                testGame,
                StatisticType.ELO,
                StatisticType.WIN_RATE,
                true, // Ascending
                1,
                1
        );

        assertEquals("Bob", result[1][1]); // X has lower ELO
        assertEquals("0.5", result[1][3]); // WIN_RATE shown in last column
    }

    @Test
    public void testGetFriendsLeaderboard() throws NoAccountError {
        Account baseAcc = DatabaseManager.queryAccountByUsername("Alice");
        String[][] leaderboardOutput = leaderboard.getFriendsLeaderboard(
                baseAcc, GameType.CHESS, StatisticType.WINS,
                StatisticType.MATCHES_PLAYED, false, 5, 1);

        assertEquals("Carol", leaderboardOutput[1][1]);
        assertEquals("Bob", leaderboardOutput[2][1]);
        assertEquals("Alice", leaderboardOutput[3][1]);
    }
}

