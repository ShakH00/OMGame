package account.statistics;

public class StatisticsChess extends AStatistics implements IStatistics {
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
            StatisticType.PIECES_CAPTURED,
            StatisticType.PAWNS_CAPTURED,
            StatisticType.KNIGHTS_CAPTURED,
            StatisticType.BISHOPS_CAPTURED,
            StatisticType.ROOKS_CAPTURED,
            StatisticType.QUEENS_CAPTURED,
            StatisticType.KINGS_CAPTURED,
            StatisticType.CHECKS,
            StatisticType.CHECKMATES,
            StatisticType.PIECES_PROMOTED
    };
}
