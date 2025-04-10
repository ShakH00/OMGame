package statistics;

import account.statistics.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StatisticsConnect4Test {

    private StatisticsConnect4 stats;

    @Before
    public void setUp() {
        stats = new StatisticsConnect4();
    }

    @Test
    public void testInitialValues_DefaultConstructor() {
        assertEquals(1000, stats.getStatistic(StatisticType.ELO));
        assertEquals(0, stats.getStatistic(StatisticType.WINS));
        assertEquals(0, stats.getStatistic(StatisticType.LOSSES));
        assertEquals(0.0, stats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testCustomConstructor_OverridesValues() {
        HashMap<StatisticType, Number> initialStats = new HashMap<>();
        initialStats.put(StatisticType.WINS, 2);
        initialStats.put(StatisticType.LOSSES, 1);
        initialStats.put(StatisticType.ELO, 1150);
        initialStats.put(StatisticType.MATCHES_PLAYED, 1);
        StatisticsConnect4 customStats = new StatisticsConnect4(initialStats);

        assertEquals(2, customStats.getStatistic(StatisticType.WINS));
        assertEquals(1, customStats.getStatistic(StatisticType.LOSSES));
        assertEquals(1150, customStats.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testAddStatisticAndUpdateWinRate() {
        HashMap<StatisticType, Integer> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);

        stats.addStatistics(toAdd);

        assertEquals(1, stats.getStatistic(StatisticType.WINS));
        assertEquals(1, stats.getStatistic(StatisticType.MATCHES_PLAYED));
        assertEquals(1.0, stats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testUpdateElo() {
        stats.updateElo(1350);
        assertEquals(1350, stats.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testGetStatistics_ReturnsCorrectOrder() {
        StatisticType[] order = new StatisticType[] {
                StatisticType.WINS,
                StatisticType.LOSSES,
                StatisticType.ELO
        };

        stats.addStatistic(StatisticType.WINS, 2);
        stats.addStatistic(StatisticType.LOSSES, 3);
        stats.updateElo(1250);

        Number[] values = stats.getStatistics(order);

        assertEquals(2, values[0]);
        assertEquals(3, values[1]);
        assertEquals(1250, values[2]);
    }
}
