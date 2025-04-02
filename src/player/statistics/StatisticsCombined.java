package player.statistics;

import java.util.HashMap;
import java.util.HashSet;

public class StatisticsCombined extends AStatistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final StatisticsType[] acceptedStatistics = StatisticsType.values();

    /**
     * Return an object that contains the combination of some set of other GameStatistics objects
     * @param setOfGameStatistics   Which GameStatistics objects to include in the combination
     */
    public StatisticsCombined(HashSet<AStatistics> setOfGameStatistics) {
        // Used to calculate ELO mean.
        int eloCount = 0;
        int eloSum = 0;

        for (AStatistics gameStatistics : setOfGameStatistics){
            for (StatisticsType statistic : gameStatistics.statistics.keySet()){
                // If the statistic is not complex, add it.
                if (!isComplex(statistic)){
                    Integer value = (Integer) gameStatistics.statistics.get(statistic);
                    addStatistic(statistic, value);
                }

                // If the statistic is ELO, add it to the sum so the mean can be taken later.
                else if (statistic == StatisticsType.ELO){
                    eloCount += 1;
                    eloSum += (int) gameStatistics.getStatistic(StatisticsType.ELO);
                }
            }

            // Get all non-complex (i.e. integer statistics that can be incremented) and add them to the combined stats
            HashMap<StatisticsType, Integer> nonComplexGameStatistics = new HashMap<>();
            for (StatisticsType statistic : gameStatistics.statistics.keySet()){
                if (!isComplex(statistic)){
                    Integer value = (Integer) gameStatistics.statistics.get(statistic);
                    nonComplexGameStatistics.put(statistic, value);
                }
            }
            addStatistics(nonComplexGameStatistics);
        }
        // set combined statistics Elo to average Elo for all included games
        updateElo(eloSum/eloCount);

        // update win rate
        updateWinRate();
    }
}
