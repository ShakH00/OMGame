package player.statistics;

import java.util.HashSet;

public class StatisticsCombined extends Statistics implements IStatistics {
    /**
     * Set of statistics represented by this object
     */
    final StatisticsEnum[] acceptedStatistics = StatisticsEnum.values();

    /**
     * Return an object that contains the combination of some set of other GameStatistics objects
     * @param setOfGameStatistics   Which GameStatistics objects to include in the combination
     */
    public StatisticsCombined(HashSet<Statistics> setOfGameStatistics) {
        // Used to calculate ELO mean.
        int eloCount = 0;
        int eloSum = 0;

        for (Statistics gameStatistics : setOfGameStatistics){
            for (StatisticsEnum statistic : gameStatistics.statistics.keySet()){
                // If the statistic is not complex, add it.
                if (!isComplex(statistic)){
                    Integer value = (Integer) gameStatistics.statistics.get(statistic);
                    addStatistic(statistic, value);
                }

                // If the statistic is ELO, add it to the sum so the mean can be taken later.
                else if (statistic == StatisticsEnum.ELO){
                    eloCount += 1;
                    eloSum += (int) gameStatistics.getStatistic(StatisticsEnum.ELO);
                }
            }
            addStatistics(gameStatistics.statistics);
        }
        // set combined statistics Elo to average Elo for all included games
        updateElo(eloSum/eloCount);

        // update win rate
        updateWinRate();
    }
}
