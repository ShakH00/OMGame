package account.statistics;

import account.statistics.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StatisticsCheckersTest {

    private StatisticsCheckers stats;

    @Before
    public void setUp() {
        stats = new StatisticsCheckers();
    }

    @Test
    public void testInitialValues_DefaultConstructor() {
        assertEquals(1000, stats.getStatistic(StatisticType.ELO));
        assertEquals(0.0, stats.getStatistic(StatisticType.WIN_RATE));
        assertEquals(0, stats.getStatistic(StatisticType.WINS));
        assertEquals(0, stats.getStatistic(StatisticType.LOSSES));
        assertEquals(0, stats.getStatistic(StatisticType.DRAWS));
    }

    @Test
    public void testCustomConstructor_OverridesValues() {
        HashMap<StatisticType, Number> customStats = new HashMap<>();
        customStats.put(StatisticType.WINS, 5);
        customStats.put(StatisticType.ELO, 1234);

        StatisticsCheckers customStatsObj = new StatisticsCheckers(customStats);

        assertEquals(5, customStatsObj.getStatistic(StatisticType.WINS));
        assertEquals(1234, customStatsObj.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testAddStatistics_UpdatesCorrectly() {
        HashMap<StatisticType, Integer> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);

        stats.addStatistics(toAdd);

        assertEquals(1, stats.getStatistic(StatisticType.WINS));
        assertEquals(1, stats.getStatistic(StatisticType.MATCHES_PLAYED));
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
    public void testCanAddStatistics_Invalid_WrongSum() {
        HashMap<StatisticType, Number> invalid = new HashMap<>();
        invalid.put(StatisticType.WINS, 1);
        invalid.put(StatisticType.LOSSES, 1);
        invalid.put(StatisticType.DRAWS, 0);
        invalid.put(StatisticType.MATCHES_PLAYED, 1);

        assertFalse(stats.canAddStatistics(invalid));
    }

    @Test
    public void testUpdateElo_SetsCorrectValue() {
        stats.updateElo(1400);
        assertEquals(1400, stats.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testUpdateWinRate_ComputesCorrectly() {
        // Simulate 3 wins, 1 loss
        stats.addStatistic(StatisticType.WINS, 3);
        stats.addStatistic(StatisticType.LOSSES, 1);
        // WIN_RATE should be 3 / (3+1) = 0.75
        assertEquals(0.75, stats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testGetStatistics_ReturnsInOrder() {
        StatisticType[] order = new StatisticType[]{
                StatisticType.ELO,
                StatisticType.WINS
        };

        stats.updateElo(1100);
        stats.addStatistic(StatisticType.WINS, 2);

        Number[] values = stats.getStatistics(order);

        assertEquals(1100, values[0]);
        assertEquals(2, values[1]);
    }
}

