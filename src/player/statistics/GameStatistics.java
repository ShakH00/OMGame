package player.statistics;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Object that tracks a set of statistics relating to a specific Game for an Account.
 */

public class GameStatistics {
    // Dynamically updated in response to changes to other stats
    private int elo;

    // Properties corresponding to statistics in StatisticsEnum for this game that should be updated after each match
    private int wins;
    private int losses;
    private int draws;

    /**
     * Set of accepted statistics for this game
     */
    private final HashSet<StatisticsEnum> acceptedStatistics = new HashSet<>(List.of(StatisticsEnum.values()));

    /**
     * Set of statistics that MUST be logged for each match of this game
     */
    private final HashSet<HashSet<StatisticsEnum>> requirements = new HashSet<>(List.of(
            new HashSet<>(List.of(StatisticsEnum.WINS, StatisticsEnum.LOSSES, StatisticsEnum.DRAWS)),
            new HashSet<>(List.of(StatisticsEnum.NUMBER_OF_TURNS))
    ));

    public GameStatistics(){
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.elo = 100;       // TODO: Set this to the correct default value
    }

    public updateStatistics(HashMap<>){

    }

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }

    public int getDraws(){
        return draws;
    }

    public int getElo(){
        return elo;
    }

    public void combine(Collection<GameStatistics> allStatistics){
        return new GameStatistics()
    }
}
