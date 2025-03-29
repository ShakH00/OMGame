package player.statistics;

import java.util.ArrayList;
import java.util.List;

public class StatisticsCheckers extends Statistics implements IStatistics {
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
            StatisticsEnum.MULTI_CAPTURES
    };
}
