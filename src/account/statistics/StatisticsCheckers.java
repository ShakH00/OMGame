package account.statistics;
import java.util.HashMap;

import java.util.HashMap;

public class StatisticsCheckers extends AStatistics implements IStatistics {
    final StatisticType[] acceptedStatistics = new StatisticType[]{
            StatisticType.ELO,
            StatisticType.WIN_RATE,
            StatisticType.WINS,
            StatisticType.LOSSES,
            StatisticType.DRAWS,
            StatisticType.MATCHES_PLAYED,
            StatisticType.NUMBER_OF_TURNS,
            StatisticType.PIECES_CAPTURED,
            StatisticType.MULTI_CAPTURES
    };

    public StatisticsCheckers() {
        initializeHashMap();
    }

    public StatisticsCheckers(HashMap<StatisticType, Number> statisticsHashMap) {
        initializeHashMap(); // init first to set defaults like ELO = 1000
        this.statistics.putAll(statisticsHashMap); // override with passed values
    }
}
