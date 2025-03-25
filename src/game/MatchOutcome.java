package game;

import player.Player;

/**
 * Class for storing the outcomes of matches.
 * Automatically updates included Players' Accounts upon initialization, if they are not guests
 */

public class MatchOutcome {
    /**
     * Enumerator for game type
     */
    private final GamesEnum game;

    /**
     * Unique integer ID for the match
     */
    private final int matchID;

    /**
     * Any Player who played in the match
     */
    private final Player player1;

    /**
     * The other Player who played in the match
     */
    private final Player player2;

    /**
     * The Player that won the match. Set to Null if the match was a tie/draw
     */
    private final Player winner;

    public MatchOutcome(GamesEnum game, int matchID, Player player1, Player player2, Player winner) throws MatchOutcomeInvalidError {
        this.game = game;
        this.matchID = matchID;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;

        if (matchOutcomeIsValid()){
            updatePlayerStatistics();
        }
    }

    /**
     * Check if the MatchOutcome is initialized correctly
     * @return                          true if the MatchOutcome is initialized correctly
     * @throws MatchOutcomeInvalidError if the MatchOutcome is invalid, discard the MatchOutcome
     */
    private boolean matchOutcomeIsValid() throws MatchOutcomeInvalidError {
        if (player1 == null || player2 == null){
            throw new MatchOutcomeInvalidError("A match must have two players.");
        }
        else if (player1 == player2){
            throw new MatchOutcomeInvalidError("A match must have two unique players.");
        }
        else if (winner != player1 && winner != player2){
            throw new MatchOutcomeInvalidError("The winner of a match must be one of its players.");
        }
        else {
            return true;
        }
    }

    /**
     * Get the two Players who participated in the match and update their stats (for non-guest Accounts)
     */
    private void updatePlayerStatistics(){

    }
}
