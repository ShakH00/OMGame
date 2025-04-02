package account.statistics;

public class StatisticsConnect4 extends AStatistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final StatisticsType[] acceptedStatistics = new StatisticsType[]{
            StatisticsType.ELO,
            StatisticsType.WIN_RATE,
            StatisticsType.WINS,
            StatisticsType.LOSSES,
            StatisticsType.DRAWS,
            StatisticsType.MATCHES_PLAYED,
            StatisticsType.NUMBER_OF_TURNS,
            StatisticsType.WINS_BLOCKED
    };
}
