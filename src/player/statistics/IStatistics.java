package player.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Object that tracks a set of statistics relating to a specific Game for an Account.
 */

public interface IStatistics {
    /**
     * Set of statistics represented by this object
     */
    HashSet<StatisticsEnum> includedStatistics = new HashSet<>();

    /**
     * Set of statistics that cannot be updated by simple addition/subtraction
     */
    HashSet<StatisticsEnum> complexStatistics = new HashSet<>();

    /**
     * Maps each StatisticsEnum in includedStatistics to some integer value
     */
    HashMap<StatisticsEnum, Integer> statistics = new HashMap<>();

    /**
     * Return an object that contains the combination of some set of other GameStatistics objects
     * @param setOfGameStatistics   Which GameStatistics objects to include in the combination
     * @return                      new immutable CombinedStatistics object that contains combined stats
     */
    StatisticsCombined combineStatistics(HashSet<Statistics> setOfGameStatistics);

    /**
     * Check if the statistics HashMap can be added to the current statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     * @return          True if it is possible to add results to the statistics HashMap
     */
    boolean canAddStatistics(HashMap<StatisticsEnum, Integer> statistics);

    /**
     * Add the statistics HashMap to existing statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     */
    void addStatistics(HashMap<StatisticsEnum, Integer> statistics);

    /**
     * Get the values of all statistics tracked for this game
     * @return          ArrayList containing the values of all statistics tracked for this game
     */
    ArrayList<Integer> getStatistics();

    /**
     * Get the values of all stats requested in the order parameter, or null if that statistic is not tracked
     * @param order     ArrayList that is the ordered list of statistics to retrieve for this game
     * @return          ArrayList containing the values of the requested statistics in the same order
     */
    ArrayList<Integer> getStatistics(ArrayList<StatisticsEnum> order);

    /**
     * Get a specific Statistic from this object
     * @param statistic statistic to get
     * @return          Integer value for the statistic
     */
    Integer getStatistic(StatisticsEnum statistic);
}
