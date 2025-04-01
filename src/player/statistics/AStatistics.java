package player.statistics;

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
    final HashSet<StatisticsEnum> complexStatistics = new HashSet<>(List.of(
        StatisticsEnum.ELO,
        StatisticsEnum.WIN_RATE
    ));

    /**
     * Maps each StatisticsEnum in includedStatistics to some value (Integer or Double)
     */
    final HashMap<StatisticsEnum, Number> statistics = new HashMap<>();

    /**
     * Check if a set of statistics is well-formed. Automatically checked before adding a new set of statistics.
     * @param statistics    HashMap that assigns a value to some set of StatisticsEnums
     * @return              True if it is possible to add statistics to the statistics HashMap
     */
    public boolean canAddStatistics(HashMap<StatisticsEnum, Number> statistics) {
        // For each statistic...
        for (StatisticsEnum statistic : statistics.keySet()){
            // Statistic must be possible for this game
            if (!isAccepted(statistic)){
                return false;
            }

            // Statistic must not be a complex statistic (these are updated through other methods)
            if (isComplex(statistic)){
                return false;
            }

            // Statistic must not be negative
            else if ((int) statistics.get(statistic) < 0){
                return false;
            }
        }

        // Exactly one of WIN, LOSS, or DRAW is true.
        if ((Integer) statistics.get(StatisticsEnum.WINS)
                + (Integer) statistics.get(StatisticsEnum.LOSSES)
                + (Integer) statistics.get(StatisticsEnum.DRAWS)
                != 1)
        {
            return false;
        }

        // MATCHES_PLAYED should be 1
        if ((Integer) statistics.get(StatisticsEnum.MATCHES_PLAYED) != 1){
            return false;
        }

        return true;
    }

    /**
     * Add the statistics HashMap to existing statistics
     * @param statistics   HashMap that assigns an integer value to some set of StatisticsEnums
     */
    public void addStatistics(HashMap<StatisticsEnum, Integer> statistics) {
        for (StatisticsEnum key : statistics.keySet()){
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
    public void addStatistic(StatisticsEnum statistic, Integer value){
        Integer currentValue = (Integer) this.statistics.get(statistic);
        this.statistics.put(statistic, currentValue + value);

        // update win rate
        updateWinRate();
    }

    /**
     * Return a list of statistics in StatisticsEnum that this game can have
     * @return  StatisticsEnum[] of possible statistics for this game
     */
    public StatisticsEnum[] getAcceptedStatistics(){
        return acceptedStatistics;
    }

    /**
     * Get the values of all statistics tracked for this game
     * @return          ArrayList containing the values of all statistics tracked for this game
     */
    public Number[] getStatistics() {
        ArrayList<Number> statistics = new ArrayList<>();
        for (StatisticsEnum statistic : acceptedStatistics) {
            statistics.add(getStatistic(statistic));
        }
        return statistics.toArray(new Number[0]);  // Java should automatically resize the array.
    }

    /**
     * Get the values of all stats requested in the order parameter, or null if that statistic is not tracked
     * @param order     ArrayList that is the ordered list of statistics to retrieve for this game
     * @return          ArrayList containing the values of the requested statistics in the same order
     */
    public Number[] getStatistics(StatisticsEnum[] order) {
        Number[] statistics = new Number[order.length];
        for (int i = 0; i < order.length; i++){
            StatisticsEnum statistic = order[i];
            statistics[i] = getStatistic(statistic);
        }
        return statistics;
    }

    /**
     * Get a statistic
     * @param statistic StatisticsEnum for which statistic to retrieve
     * @return          int value associated with the StatisticsEnum input, or null if that statistic doesn't exist for
     *                  this game.
     */
    public Number getStatistic(StatisticsEnum statistic) {
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
        statistics.put(StatisticsEnum.ELO, newElo);
    }

    /**
     * Recalculate the win rate statistic
     */
    void updateWinRate() {
        Double wins = (Double) statistics.get(StatisticsEnum.WINS);
        Double losses = (Double) statistics.get(StatisticsEnum.LOSSES);
        statistics.put(StatisticsEnum.WIN_RATE, wins/(wins + losses));
    }

    /**
     * Return true if the statistic is complex, i.e. cannot be updated by simple addition
     * @param statistic Statistic to query
     * @return          True if the statistic is complex; false otherwise
     */
    boolean isComplex(StatisticsEnum statistic){
        return complexStatistics.contains(statistic);
    }

    /**
     * Return true if the statistic applies to the game related to this Statistics object
     * @param statistic Statistic to query
     * @return          True if the statistic applies to the related game
     */
    private boolean isAccepted(StatisticsEnum statistic){
        for (StatisticsEnum acceptedStatistic : acceptedStatistics){
            if (statistic == acceptedStatistic){
                return true;
            }
        }
        return false;
    }
}
