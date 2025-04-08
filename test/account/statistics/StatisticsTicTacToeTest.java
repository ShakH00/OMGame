package account.statistics;

import account.statistics.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StatisticsTicTacToeTest {

    private StatisticsTicTacToe stats;

    @Before
    public void setUp() {
        stats = new StatisticsTicTacToe();
    }

    @Test
    public void testInitialValues_DefaultConstructor() {
        assertEquals(1000, stats.getStatistic(StatisticType.ELO));
        assertEquals(0, stats.getStatistic(StatisticType.WINS));
        assertEquals(0, stats.getStatistic(StatisticType.DRAWS));
        assertEquals(0.0, stats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testCustomConstructor_OverridesValues() {
        HashMap<StatisticType, Number> initialStats = new HashMap<>();
        initialStats.put(StatisticType.ELO, 1250);
        initialStats.put(StatisticType.WINS, 3);
        initialStats.put(StatisticType.LOSSES, 2);
        StatisticsTicTacToe customStats = new StatisticsTicTacToe(initialStats);

        assertEquals(1250, customStats.getStatistic(StatisticType.ELO));
        assertEquals(3, customStats.getStatistic(StatisticType.WINS));
        assertEquals(2, customStats.getStatistic(StatisticType.LOSSES));
    }

    @Test
    public void testAddStatisticAndWinRateUpdate() {
        stats.addStatistic(StatisticType.WINS, 1);
        stats.addStatistic(StatisticType.MATCHES_PLAYED, 1);

        assertEquals(1, stats.getStatistic(StatisticType.WINS));
        assertEquals(1.0, stats.getStatistic(StatisticType.WIN_RATE));
    }

    @Test
    public void testUpdateElo() {
        stats.updateElo(1400);
        assertEquals(1400, stats.getStatistic(StatisticType.ELO));
    }

    @Test
    public void testGetStatisticsWithOrder() {
        stats.addStatistic(StatisticType.WINS, 2);
        stats.addStatistic(StatisticType.LOSSES, 1);
        stats.updateElo(1100);

        StatisticType[] order = new StatisticType[]{
                StatisticType.LOSSES,
                StatisticType.ELO,
                StatisticType.WINS
        };

        Number[] values = stats.getStatistics(order);
        assertEquals(1, values[0]);
        assertEquals(1100, values[1]);
        assertEquals(2, values[2]);
    }
}
