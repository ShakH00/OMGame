package player.statistics;

import java.util.HashSet;
import java.util.List;

public class StatisticsChess extends Statistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final HashSet<StatisticsEnum> includedStatistics = new HashSet<>(List.of(
            StatisticsEnum.ELO,
            StatisticsEnum.WIN_RATE,
            StatisticsEnum.WINS,
            StatisticsEnum.LOSSES,
            StatisticsEnum.DRAWS,
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
    ));
}
