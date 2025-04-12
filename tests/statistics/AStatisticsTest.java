package statistics;

import account.statistics.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class AStatisticsTest {

    private TestStatistics stats;

    private static class TestStatistics extends AStatistics {
        public TestStatistics() {
            StatisticType[] acceptedStatistics = new StatisticType[]{
                    StatisticType.WINS,
                    StatisticType.LOSSES,
                    StatisticType.DRAWS,
                    StatisticType.MATCHES_PLAYED,
                    StatisticType.ELO,
                    StatisticType.WIN_RATE
            };
        }
    }

    @Before
    public void setup() {
        stats = new TestStatistics();
    }

    @Test
    public void testCanAddStatistics_ValidInput_ReturnsTrue() {
        HashMap<StatisticType, Number> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.LOSSES, 0);
        toAdd.put(StatisticType.DRAWS, 0);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);

        boolean result = stats.canAddStatistics(toAdd);
        assertTrue(result);
    }

    @Test
    public void testCanAddStatistics_InvalidWinLossDrawCombination_ReturnsFalse() {
        HashMap<StatisticType, Number> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.LOSSES, 1);
        toAdd.put(StatisticType.DRAWS, 0);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);

        assertFalse(stats.canAddStatistics(toAdd));
    }

    @Test
    public void testCanAddStatistics_UsesUnacceptedStatistic_ReturnsFalse() {
        HashMap<StatisticType, Number> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);
        toAdd.put(StatisticType.ELO, 1000);
        toAdd.put(StatisticType.LOSSES, 0);
        toAdd.put(StatisticType.DRAWS, 0);

        assertFalse(stats.canAddStatistics(toAdd));
    }

    @Test
    public void testCanAddStatistics_ContainsNegativeValue_ReturnsFalse() {
        HashMap<StatisticType, Number> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, -1);
        toAdd.put(StatisticType.LOSSES, 0);
        toAdd.put(StatisticType.DRAWS, 0);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);

        assertFalse(stats.canAddStatistics(toAdd));
    }

    @Test
    public void testAddStatistics_ValidInput_UpdatesValuesCorrectly() {

        HashMap<StatisticType, Integer> toAdd = new HashMap<>();
        toAdd.put(StatisticType.WINS, 1);
        toAdd.put(StatisticType.MATCHES_PLAYED, 1);

        stats.addStatistics(toAdd);

        assertEquals(1, stats.getStatistic(StatisticType.WINS));
        assertEquals(1, stats.getStatistic(StatisticType.MATCHES_PLAYED));

        assertEquals(1000, stats.getStatistic(StatisticType.ELO));
    }

}
