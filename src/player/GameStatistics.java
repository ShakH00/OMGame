package player;

import game.MatchOutcome;

import java.util.Collection;

/**
 * Object that tracks a set of statistics relating to a specific Game for an Account.
 */

public class GameStatistics {
    private int wins;
    private int losses;
    private int draws;
    private int elo;

    public GameStatistics(){
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.elo = 100;       // TODO: Set this to the correct default value
    }

    public updateWithMatchOutcome(MatchOutcome matchOutcome){

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
