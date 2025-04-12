package statistics;

import account.statistics.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StatisticsChessTest {

    private StatisticsChess stats;

    @Before
    public void setUp() {
        stats = new StatisticsChess();
    }

    @Test
    public void testInitialValues_DefaultConstructor() {
        assertEquals(1000, stats.getStatistic(StatisticType.ELO));
        assertEquals(0.0, stats.getStatistic(StatisticType.WIN_RATE));
        assertEquals(0, stats.getStatistic(StatisticType.CHECKMATES));
        assertEquals(0, stats.getStatistic(StatisticType.KINGS_CAPTURED));
    }

    @Test
    public void testCustomConstructor_OverridesValues() {
        HashMap<StatisticType, Number> customStats = new HashMap<>();
        customStats.put(StatisticType.WINS, 10);
        customStats.put(StatisticType.CHECKS, 4);
        customStats.put(StatisticType.ELO, 1600);

        StatisticsChess customStatsObj = new StatisticsChess(customStats);

        assertEquals(10, customStatsObj.getStatistic(StatisticType.WINS));
        assertEquals(4, customStatsObj.getStatistic(StatisticType.CHECKS));
        assertEquals(1600, customStatsObj.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testAddStatistics_UpdatesCorrectly() {
        HashMap<StatisticType, Integer> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);
        toAdd.put(StatisticType.CHECKMATES, 2);

        stats.addStatistics(toAdd);

        assertEquals(1, stats.getStatistic(StatisticType.WINS));
        assertEquals(1, stats.getStatistic(StatisticType.MATCHES_PLAYED));
        assertEquals(2, stats.getStatistic(StatisticType.CHECKMATES));
    }

    @Test
    public void testCanAddStatistics_Valid() {
        HashMap<StatisticType, Number> valid = new HashMap<>();
        valid.put(StatisticType.WINS, 1);
        valid.put(StatisticType.LOSSES, 0);
        valid.put(StatisticType.DRAWS, 0);
        valid.put(StatisticType.MATCHES_PLAYED, 1);

        assertTrue(stats.canAddStatistics(valid));
    }

    @Test
    public void testCanAddStatistics_Invalid_MultipleResults() {
        HashMap<StatisticType, Number> invalid = new HashMap<>();
        invalid.put(StatisticType.WINS, 1);
        invalid.put(StatisticType.LOSSES, 1);
        invalid.put(StatisticType.DRAWS, 0);
        invalid.put(StatisticType.MATCHES_PLAYED, 1);

        assertFalse(stats.canAddStatistics(invalid));
    }

    @Test
    public void testUpdateElo_SetsCorrectValue() {
        stats.updateElo(1500);
        assertEquals(1500, stats.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testUpdateWinRate_ComputesCorrectly() {
        stats.addStatistic(StatisticType.WINS, 6);
        stats.addStatistic(StatisticType.LOSSES, 2);
        assertEquals(0.75, stats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testGetStatisticsOrdered() {
        stats.updateElo(1337);
        stats.addStatistic(StatisticType.ROOKS_CAPTURED, 3);

        StatisticType[] order = new StatisticType[]{
                StatisticType.ELO,
                StatisticType.ROOKS_CAPTURED
        };

        Number[] result = stats.getStatistics(order);

        assertEquals(1337, result[0]);
        assertEquals(3, result[1]);
    }
}
