package player.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class Statistics implements IStatistics {
    /**
     * Set of statistics that are updated through special methods
     */
    final HashSet<StatisticsEnum> complexStatistics = new HashSet<>(List.of(
        StatisticsEnum.ELO,
        StatisticsEnum.WIN_RATE
    ));

    /**
     * Maps each StatisticsEnum in includedStatistics to some integer value
     */
    final HashMap<StatisticsEnum, Integer> statistics = new HashMap<>();

    /**
     * Return an object that contains the combination of some set of other GameStatistics objects
     * @param setOfGameStatistics   Which GameStatistics objects to include in the combination
     * @return                      new immutable CombinedStatistics object that contains combined stats
     */
    public StatisticsCombined combineStatistics(HashSet<Statistics> setOfGameStatistics) {
        StatisticsCombined combinedStatistics = new StatisticsCombined();
        for (Statistics gameStatistics : setOfGameStatistics){
            combinedStatistics.addStatistics(gameStatistics.statistics);
        }
        return combinedStatistics;
    }

    /**
     * Check if the statistics in the statistics HashMap can be added to the current statistics
     * @param statistics    HashMap that assigns an integer value to some set of StatisticsEnums
     * @return              True if it is possible to add statistics to the statistics HashMap
     */
    public boolean canAddStatistics(HashMap<StatisticsEnum, Integer> statistics) {
        for (StatisticsEnum statistic : statistics.keySet()){
            if (!this.complexStatistics.contains(statistic)){
                return false;
            }
        }
        return true;
    }

    /**
     * Add the statistics HashMap to existing statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     */
    public void addStatistics(HashMap<StatisticsEnum, Integer> statistics) {
        if (canAddStatistics(statistics)){
            for (StatisticsEnum key : statistics.keySet()){
                int value = statistics.get(key);
                addStatistic(key, value);
            }
        }
    }

    private void addStatistic(StatisticsEnum statistic, int add){
        Integer currentValue = this.statistics.get(statistic);
        this.statistics.put(statistic, currentValue + add);
    }

    /**
     * Get the values of all statistics tracked for this game
     * @return          ArrayList containing the values of all statistics tracked for this game
     */
    public ArrayList<Integer> getStatistics() {
        ArrayList<Integer> statistics = new ArrayList<>();
        for (StatisticsEnum statistic : includedStatistics) {
            statistics.add(getStatistic(statistic));
        }
        return statistics;
    }

    /**
     * Get the values of all stats requested in the order parameter, or null if that statistic is not tracked
     * @param order     ArrayList that is the ordered list of statistics to retrieve for this game
     * @return          ArrayList containing the values of the requested statistics in the same order
     */
    public ArrayList<Integer> getStatistics(ArrayList<StatisticsEnum> order) {
        ArrayList<Integer> statistics = new ArrayList<>();
        for (StatisticsEnum statistic : order) {
            statistics.add(getStatistic(statistic));
        }
        return statistics;
    }

    /**
     * Get a statistic
     * @param statistic StatisticsEnum for which statistic to retrieve
     * @return          int value associated with the StatisticsEnum input, or null if that statistic doesn't exist for
     *                  this game.
     */
    public Integer getStatistic(StatisticsEnum statistic) {
        if (includedStatistics.contains(statistic)){
            return statistics.getOrDefault(statistic, 0);
        }
        else {
            return null;
        }
    }
}
