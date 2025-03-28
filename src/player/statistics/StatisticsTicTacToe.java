package player.statistics;

import java.util.HashSet;
import java.util.List;

public class StatisticsTicTacToe extends Statistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final HashSet<StatisticsEnum> includedStatistics = new HashSet<>(List.of(
            StatisticsEnum.ELO,
            StatisticsEnum.WIN_RATE,
            StatisticsEnum.WINS,
            StatisticsEnum.LOSSES,
            StatisticsEnum.DRAWS,
            StatisticsEnum.MATCHES_PLAYED,
            StatisticsEnum.NUMBER_OF_TURNS,
            StatisticsEnum.WINS_BLOCKED
    ));}
