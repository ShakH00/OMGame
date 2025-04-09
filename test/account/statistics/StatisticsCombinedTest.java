package account.statistics;

import account.statistics.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class StatisticsCombinedTest {

    private StatisticsCheckers checkersStats;
    private StatisticsChess chessStats;

    @Before
    public void setUp() {
        HashMap<StatisticType, Number> checkersData = new HashMap<>();
        checkersData.put(StatisticType.WINS, 3);
        checkersData.put(StatisticType.LOSSES, 1);
        checkersData.put(StatisticType.DRAWS, 0);
        checkersData.put(StatisticType.MATCHES_PLAYED, 1);
        checkersData.put(StatisticType.ELO, 1200);
        checkersStats = new StatisticsCheckers(checkersData);

        HashMap<StatisticType, Number> chessData = new HashMap<>();
        chessData.put(StatisticType.WINS, 1);
        chessData.put(StatisticType.LOSSES, 1);
        chessData.put(StatisticType.DRAWS, 0);
        chessData.put(StatisticType.MATCHES_PLAYED, 1);
        chessData.put(StatisticType.CHECKMATES, 2);
        chessData.put(StatisticType.ELO, 1400);
        chessStats = new StatisticsChess(chessData);
    }

    @Test
    public void testCombinedStatistics_SumsNonComplexCorrectly() {
        HashSet<AStatistics> statSet = new HashSet<>();
        statSet.add(checkersStats);
        statSet.add(chessStats);

        StatisticsCombined combinedStats = new StatisticsCombined(statSet);

        // Should sum wins, losses, matches played, etc.
        assertEquals(8, combinedStats.getStatistic(StatisticType.WINS));
        assertEquals(4, combinedStats.getStatistic(StatisticType.LOSSES));
        assertEquals(4, combinedStats.getStatistic(StatisticType.MATCHES_PLAYED));
        assertEquals(4, combinedStats.getStatistic(StatisticType.CHECKMATES));
    }

    @Test
    public void testCombinedStatistics_ComputesAverageElo() {
        HashSet<AStatistics> statSet = new HashSet<>();
        statSet.add(checkersStats);
        statSet.add(chessStats);

        StatisticsCombined combinedStats = new StatisticsCombined(statSet);

        assertEquals(1300, combinedStats.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testCombinedStatistics_UpdatesWinRate() {
        HashSet<AStatistics> statSet = new HashSet<>();
        statSet.add(checkersStats);
        statSet.add(chessStats);

        StatisticsCombined combinedStats = new StatisticsCombined(statSet);

        assertEquals(0.6666666666666666, combinedStats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testEmptySet_ThrowsException() {
        HashSet<AStatistics> emptySet = new HashSet<>();
        try {
            new StatisticsCombined(emptySet);
            fail("divide-by-zero in elo average");
        } catch (ArithmeticException e) {
            // catch Exception
        }
    }
}
