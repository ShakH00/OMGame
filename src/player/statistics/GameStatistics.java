package player.statistics;

import java.util.HashMap;
import java.util.HashSet;

public abstract class GameStatistics {
    /**
     * Return an object that contains the combination of some set of other GameStatistics objects
     * @param setOfGameStatistics   Which GameStatistics objects to include in the combination
     * @return                      new immutable CombinedStatistics object that contains combined stats
     */
    public CombinedStatistics combineStatistics(HashSet<GameStatistics> setOfGameStatistics) {
        CombinedStatistics combinedStatistics = new CombinedStatistics();
        for (GameStatistics gameStatistics : setOfGameStatistics){
            combinedStatistics.addStatistics(gameStatistics.getStatistics());
        }
        return combinedStatistics;
    }

    /**
     * Check if the statistics in the statistics HashMap can be added to the current statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     * @return          True if it is possible to add statistics to the statistics HashMap
     */
    public boolean canAddStatistics(HashMap<StatisticsEnum, Integer> statistics) {
        return this.acceptedStatistics.contains(statistics.keySet());
    }

    /**
     * Add the statistics HashMap to existing statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     */
    public void addStatistics(HashMap<StatisticsEnum, Integer> statistics) {
        if (canAddStatistics(statistics)){
            for (StatisticsEnum key : statistics.keySet()){
                Integer currentValue = this.statistics.get(key);
                Integer addValue = statistics.get(key);
                this.statistics.put(key, currentValue + addValue);
            }
        }
    }

    /**
     * Get all Statistics in this object
     * @return          a new HashMap containing all statistics
     */
    public HashMap<StatisticsEnum, Integer> getStatistics() {

    }

    /**
     * Get a HashMap containing all Statistics in this object that are included in the filter set
     * @param filter    HashSet of StatisticsEnums to get
     * @return          A new HashMap containing the requested statistics
     */
    public HashMap<StatisticsEnum, Integer> getStatistics(HashSet<StatisticsEnum> filter) {

    }
}
