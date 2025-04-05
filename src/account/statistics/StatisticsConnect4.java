package account.statistics;

import java.util.HashMap;

public class StatisticsConnect4 extends AStatistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final StatisticType[] acceptedStatistics = new StatisticType[]{
            StatisticType.ELO,
            StatisticType.WIN_RATE,
            StatisticType.WINS,
            StatisticType.LOSSES,
            StatisticType.DRAWS,
            StatisticType.MATCHES_PLAYED,
            StatisticType.NUMBER_OF_TURNS,
            StatisticType.WINS_BLOCKED
    };

    public StatisticsConnect4(){
        this.statistics = new HashMap<>();
        initializeHashMap();
    }

    public StatisticsConnect4(HashMap<StatisticType, Number> statisticsHashMap){
        this.statistics = statisticsHashMap;
    }
}
