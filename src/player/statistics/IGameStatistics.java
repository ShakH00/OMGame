package player.statistics;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Object that tracks a set of statistics relating to a specific Game for an Account.
 */

public interface IGameStatistics {
    /**
     * Set of statistics represented by this object
     */
    final HashSet<StatisticsEnum> includedStatistics = new HashSet<>();

    /**
     * Set of statistics that can be updated in this object
     */
    final HashSet<StatisticsEnum> acceptedStatistics = new HashSet<>();

    /**
     * Maps each StatisticsEnum in includedStatistics to some integer value
     */
    final HashMap<StatisticsEnum, Integer> statistics = new HashMap<>();

    /**
     * Return an object that contains the combination of some set of other GameStatistics objects
     * @param setOfGameStatistics   Which GameStatistics objects to include in the combination
     * @return                      new immutable CombinedStatistics object that contains combined stats
     */
    CombinedStatistics combineStatistics(HashSet<GameStatistics> setOfGameStatistics);

    /**
     * Check if the statistics in the results HashMap can be added to the current statistics
     * @param results   HashMap that assigns an integer value to some set of StatisticsEnums
     * @return          True if it is possible to add results to the statistics HashMap
     */
    boolean canAddMatchResults(HashMap<StatisticsEnum, Integer> results);

    /**
     * Add the results HashMap to existing statistics
     * @param results   HashMap that assigns an integer value to some set of StatisticsEnums
     */
    void addMatchResults(HashMap<StatisticsEnum, Integer> results);

    /**
     * Get all Statistics in this object
     * @return          a new HashMap containing all statistics
     */
    HashMap<StatisticsEnum, Integer> getStatistics();

    /**
     * Get a HashMap containing all Statistics in this object that are included in the filter set
     * @param filter    HashSet of StatisticsEnums to get
     * @return          A new HashMap containing the requested statistics
     */
    HashMap<StatisticsEnum, Integer> getStatistics(HashSet<StatisticsEnum> filter);

    /**
     * Get a specific Statistic from this object
     * @param statistic statistic to get
     * @return          Integer value for the statistic
     */
    Integer getStatistic(StatisticsEnum statistic);
}
