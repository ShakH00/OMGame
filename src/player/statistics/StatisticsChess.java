package player.statistics;

public class StatisticsChess extends AStatistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final StatisticsEnum[] acceptedStatistics = new StatisticsEnum[]{
            StatisticsEnum.ELO,
            StatisticsEnum.WIN_RATE,
            StatisticsEnum.WINS,
            StatisticsEnum.LOSSES,
            StatisticsEnum.DRAWS,
            StatisticsEnum.MATCHES_PLAYED,
            StatisticsEnum.NUMBER_OF_TURNS,
            StatisticsEnum.PIECES_CAPTURED,
            StatisticsEnum.PAWNS_CAPTURED,
            StatisticsEnum.KNIGHTS_CAPTURED,
            StatisticsEnum.BISHOPS_CAPTURED,
            StatisticsEnum.ROOKS_CAPTURED,
            StatisticsEnum.QUEENS_CAPTURED,
            StatisticsEnum.KINGS_CAPTURED,
            StatisticsEnum.CHECKS,
            StatisticsEnum.CHECKMATES,
            StatisticsEnum.PIECES_PROMOTED
    };
}
