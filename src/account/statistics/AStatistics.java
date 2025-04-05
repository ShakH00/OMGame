package account.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Abstract class for storing, updating, and retrieving statistics related to a certain Game for some Account.
 */

public abstract class AStatistics implements IStatistics {
    /**
     * Set of statistics that are updated through special methods, and cannot be added to like other statistics
     */
    final HashSet<StatisticType> complexStatistics = new HashSet<>(List.of(
        StatisticType.ELO,
        StatisticType.WIN_RATE
    ));

    /**
     * Maps each StatisticType in includedStatistics to some value (Integer or Double)
     */
    HashMap<StatisticType, Number> statistics = new HashMap<>();

    /**
     * Initialize all values in the statistics hashmap to 0, except for Elo which starts at 1000
     */
    void initializeHashMap(){
        for (StatisticType statisticType : acceptedStatistics){
            if (statisticType == StatisticType.ELO){
                statistics.put(statisticType, (Integer) 1000);
            }
            else if (isComplex(statisticType)){
                statistics.put(statisticType, (Double) 0.0);
            }
            else {
                statistics.put(statisticType, (Integer) 0);
            }
        }
    }

    /**
     * Check if a set of statistics is well-formed. Automatically checked before adding a new set of statistics.
     * @param statistics    HashMap that assigns a value to some set of StatisticsEnums
     * @return              True if it is possible to add statistics to the statistics HashMap
     */
    public boolean canAddStatistics(HashMap<StatisticType, Number> statistics) {
        // For each statistic...
        for (StatisticType statistic : statistics.keySet()){
            // Statistic must be possible for this game
            if (!isAccepted(statistic)){
                return false;
            }

            // Statistic must not be a complex statistic (these are updated through other methods)
            if (isComplex(statistic)){
                return false;
            }

            // Statistic must not be null (i.e. uninitialized
            else if (statistics.get(statistic) == null){
                return false;
            }

            // Statistic must not be negative
            else if ((int) statistics.get(statistic) < 0){
                return false;
            }
        }

        // Exactly one of WIN, LOSS, or DRAW is true.
        if ((Integer) statistics.get(StatisticType.WINS)
                + (Integer) statistics.get(StatisticType.LOSSES)
                + (Integer) statistics.get(StatisticType.DRAWS)
                != 1)
        {
            return false;
        }

        // MATCHES_PLAYED should be 1
        if ((Integer) statistics.get(StatisticType.MATCHES_PLAYED) != 1){
            return false;
        }

        return true;
    }

    /**
     * Add the statistics HashMap to existing statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     */
    public void addStatistics(HashMap<StatisticType, Integer> statistics) {
        for (StatisticType key : statistics.keySet()){
            if (!isComplex(key)) {
                Integer value = statistics.get(key);
                addStatistic(key, value);
            }
        }
    }

    /**
     * Increase a statistic by some value
     * @param statistic StatisticEnum statistic to increase
     * @param value     Integer value to increase statistic by
     */
    public void addStatistic(StatisticType statistic, Integer value){
        Integer currentValue = (Integer) this.statistics.getOrDefault(statistic, 0);
        this.statistics.put(statistic, currentValue + value);

        // update win rate
        updateWinRate();
    }

    /**
     * Return a list of statistics in StatisticType that this game can have
     * @return  StatisticType[] of possible statistics for this game
     */
    public StatisticType[] getAcceptedStatistics(){
        return acceptedStatistics;
    }

    /**
     * Get the values of all statistics tracked for this game
     * @return          ArrayList containing the values of all statistics tracked for this game
     */
    public Number[] getStatistics() {
        ArrayList<Number> statistics = new ArrayList<>();
        for (StatisticType statistic : acceptedStatistics) {
            statistics.add(getStatistic(statistic));
        }
        return statistics.toArray(new Number[0]);  // Java should automatically resize the array.
    }

    /**
     * Get the values of all stats requested in the order parameter, or null if that statistic is not tracked
     * @param order     ArrayList that is the ordered list of statistics to retrieve for this game
     * @return          ArrayList containing the values of the requested statistics in the same order
     */
    public Number[] getStatistics(StatisticType[] order) {
        Number[] statistics = new Number[order.length];
        for (int i = 0; i < order.length; i++){
            StatisticType statistic = order[i];
            statistics[i] = getStatistic(statistic);
        }
        return statistics;
    }

    /**
     * Get a statistic
     * @param statistic StatisticType for which statistic to retrieve
     * @return          int value associated with the StatisticType input, or null if that statistic doesn't exist for
     *                  this game.
     */
    public Number getStatistic(StatisticType statistic) {
        if (isAccepted(statistic)){
            return statistics.getOrDefault(statistic, 0);
        }
        else {
            return 0;
        }
    }

    /**
     * Update the Elo of the account for this game
     * @param newElo    int for new Elo to use. Overwrites previous Elo.
     */
    public void updateElo(int newElo){
        statistics.put(StatisticType.ELO, newElo);
    }

    /**
     * Recalculate the win rate statistic
     */
    void updateWinRate() {
        double wins = (int) statistics.getOrDefault(StatisticType.WINS, 0);
        double losses = (int) statistics.getOrDefault(StatisticType.LOSSES, 0);
        statistics.put(StatisticType.WIN_RATE, (Double) wins/(wins + losses));
    }

    /**
     * Return true if the statistic is complex, i.e. cannot be updated by simple addition
     * @param statistic Statistic to query
     * @return          True if the statistic is complex; false otherwise
     */
    public boolean isComplex(StatisticType statistic){
        return complexStatistics.contains(statistic);
    }

    /**
     * Return true if the statistic applies to the game related to this Statistics object
     * @param statistic Statistic to query
     * @return          True if the statistic applies to the related game
     */
    private boolean isAccepted(StatisticType statistic){
        for (StatisticType acceptedStatistic : acceptedStatistics){
            if (statistic == acceptedStatistic){
                return true;
            }
        }
        return false;
    }
}
