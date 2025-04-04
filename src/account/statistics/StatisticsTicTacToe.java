package account.statistics;

public class StatisticsTicTacToe extends AStatistics implements IStatistics {
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
}