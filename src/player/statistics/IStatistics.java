package player.statistics;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Object that tracks a set of statistics relating to a specific Game for an Account.
 */

interface IStatistics {
    /**
     * Set of statistics represented by this object
     */
    StatisticsType[] acceptedStatistics = StatisticsType.values();

    /**
     * Set of statistics that cannot be updated by simple addition/subtraction
     */
    HashSet<StatisticsType> complexStatistics = new HashSet<>();

    /**
     * Maps each StatisticsType in includedStatistics to some value
     */
    HashMap<StatisticsType, Number> statistics = new HashMap<>();

    /**
     * Check if the statistics HashMap can be added to the current statistics
     * @param statistics    HashMap that assigns a value to some set of StatisticsEnums
     * @return              True if it is possible to add results to the statistics HashMap
     */
    boolean canAddStatistics(HashMap<StatisticsType, Number> statistics);

    /**
     * Add the statistics HashMap to existing statistics
     * @param statistics    HashMap that assigns a value to some set of StatisticsEnums
     */
    void addStatistics(HashMap<StatisticsType, Integer> statistics);

    /**
     * Get the values of all statistics tracked for this game
     * @return          ArrayList containing the values of all statistics tracked for this game
     */
    Number[] getStatistics();

    /**
     * Get the values of all stats requested in the order parameter, or null if that statistic is not tracked
     * @param order     ArrayList that is the ordered list of statistics to retrieve for this game
     * @return          array containing the values of the requested statistics in the same order
     */
    Number[] getStatistics(StatisticsType[] order);

    /**
     * Get a specific Statistic from this object
     * @param statistic statistic to get
     * @return          value for the statistic
     */
    Number getStatistic(StatisticsType statistic);

    /**
     * Set ELO to a new value
     */
    void updateElo(int newElo);
}
