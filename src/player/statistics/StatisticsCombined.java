package player.statistics;

import java.util.HashSet;
import java.util.List;

public class StatisticsCombined extends Statistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final HashSet<StatisticsEnum> includedStatistics = new HashSet<>(
            List.of(StatisticsEnum.values())    // all possible statistics
    );
}
