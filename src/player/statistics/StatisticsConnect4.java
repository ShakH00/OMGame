package player.statistics;

public class StatisticsConnect4 extends Statistics implements IStatistics {
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
            StatisticsEnum.WINS_BLOCKED
    };
}
