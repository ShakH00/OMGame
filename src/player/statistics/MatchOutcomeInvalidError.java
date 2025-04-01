package player.statistics;

public class MatchOutcomeInvalidError extends Exception {
    public MatchOutcomeInvalidError(){}
    public MatchOutcomeInvalidError(String message){
        super(message);
    }
}
