package leaderboard;

import account.Account;
import game.GameType;
import account.statistics.StatisticType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderboardTest {

    private Leaderboard leaderboard;
    private GameType testGame;

    @Before
    public void setUp() {
        leaderboard = new Leaderboard();
        testGame = GameType.CHESS;
    }

    private Account createMockAccount(String username, double elo, double winRate, double wins, double customStat) {
        return new Account() {
            @Override
            public Number getStatistic(GameType game, StatisticType stat) {
                switch (stat) {
                    case ELO: return elo;
                    case WIN_RATE: return winRate;
                    case WINS: return wins;
                    default: return customStat;
                }
            }
        };
    }

    @Test
    public void testGetGlobalLeaderboard_SortsDescendingAndPaginatesCorrectly() {
        ArrayList<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(createMockAccount("Alice", 1500, 0.6, 30, 5));
        mockAccounts.add(createMockAccount("Bob", 1400, 0.5, 25, 10));
        mockAccounts.add(createMockAccount("Carol", 1600, 0.7, 35, 8));


        // add the database to complete the  test cases

        String[][] result = leaderboard.getGlobalLeaderboard(
                testGame,
                StatisticType.ELO,
                StatisticType.WINS,
                false, // Descending
                2,
                1
        );

        assertArrayEquals(new String[]{"RANK", "USERNAME", "ELO", "WIN_RATE", "WINS", "WINS"}, result[0]);
        assertEquals("1", result[1][0]);
        assertEquals("Carol", result[1][1]);
        assertEquals("1600.0", result[1][2]);

        assertEquals("2", result[2][0]);
        assertEquals("Alice", result[2][1]);
        assertEquals("1500.0", result[2][2]);

        assertArrayEquals(new String[]{"", "", "", "", "", ""}, result[3]); // Only 3 accounts exist, but 2 per page
    }

    @Test
    public void testGetGlobalLeaderboard_SortsAscending() {
        ArrayList<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(createMockAccount("X", 1300, 0.3, 20, 2));
        mockAccounts.add(createMockAccount("Y", 1700, 0.8, 40, 15));

        // NEED THE DATABASE TO BE HERE

        String[][] result = leaderboard.getGlobalLeaderboard(
                testGame,
                StatisticType.ELO,
                StatisticType.WIN_RATE,
                true, // Ascending
                1,
                1
        );

        assertEquals("X", result[1][1]); // X has lower ELO
        assertEquals("0.3", result[1][3]); // WIN_RATE shown in last column
    }
}

