package player.statistics;

public class StatisticsChess extends AStatistics implements IStatistics {
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
            StatisticsType.PIECES_CAPTURED,
            StatisticsType.PAWNS_CAPTURED,
            StatisticsType.KNIGHTS_CAPTURED,
            StatisticsType.BISHOPS_CAPTURED,
            StatisticsType.ROOKS_CAPTURED,
            StatisticsType.QUEENS_CAPTURED,
            StatisticsType.KINGS_CAPTURED,
            StatisticsType.CHECKS,
            StatisticsType.CHECKMATES,
            StatisticsType.PIECES_PROMOTED
    };
}
