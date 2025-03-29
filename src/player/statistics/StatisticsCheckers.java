package player.statistics;

import java.util.HashSet;
import java.util.List;

public class StatisticsCheckers extends Statistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final HashSet<StatisticsEnum> acceptedStatistics = new HashSet<>(List.of(
            StatisticsEnum.ELO,
            StatisticsEnum.WIN_RATE,
            StatisticsEnum.WINS,
            StatisticsEnum.LOSSES,
            StatisticsEnum.DRAWS,
            StatisticsEnum.MATCHES_PLAYED,
            StatisticsEnum.NUMBER_OF_TURNS,
            StatisticsEnum.PIECES_CAPTURED,
            StatisticsEnum.MULTI_CAPTURES
    ));
}
