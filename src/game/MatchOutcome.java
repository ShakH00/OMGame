package game;

import player.Player;
import player.StatisticsEnum;

import java.util.HashMap;

/**
 * Class for storing the outcomes of matches.
 * Contains fields for parts of the MatchOutcome that will need to be kept for Account match history
 * Automatically updates included Players' Accounts upon initialization, if they are not guests
 */

public class MatchOutcome {
    /**
     * Private variables for parts of the MatchOutcome that will need to be kept for Account match history
     */
    private final GamesEnum game;

    /**
     * Unique integer ID for the match
     */
    private final int matchID;

    /**
     * Player 1 name
     */
    private final String player1Name;

    /**
     * Player 1 ID
     */
    private final int player1ID;

    /**
     * Player 2 name
     */
    private final String player2Name;

    /**
     * Player 2 ID
     */
    private final int player2ID;


    /**
     * Initialize a MatchOutcome object using data from a Match, and send the information to the Players
     * @param game                      GamesEnum for the game that is being played
     * @param matchID                   int unique ID for this match to store in database
     * @param player1                   Player 1 of this match
     * @param player1Results            HashMap containing statistics for Player 1
     * @param player2                   Player 2 of this match
     * @param player2Results            HashMap containing statistics for Player 2
     * @throws MatchOutcomeInvalidError Error thrown if the Match Outcome is malformed and cannot be processed
     */
    public MatchOutcome(GamesEnum game, int matchID,
                        Player player1, HashMap<StatisticsEnum, Integer> player1Results,
                        Player player2, HashMap<StatisticsEnum, Integer> player2Results)
                        throws MatchOutcomeInvalidError
    {
        // update MatchHistory fields
        this.game = game;
        this.matchID = matchID;
        this.player1Name = player1.getAccount().getUsername();
        this.player1ID = player2.getAccount().getID();
        this.player2Name = player2.getAccount().getUsername();
        this.player2ID = player2.getAccount().getID();

        // if the Player Statistics fields are not malformed, update PlayerStatistics
        if (matchOutcomeIsValid(player1, player1Results, player2, player2Results)){
            player1.logMatch(this);
            player1.updateStatistics(player1Results);
            player2.logMatch(this);
            player2.updateStatistics(player2Results);
        }
    }

    /**
     * Check if the MatchOutcome is initialized correctly
     * @return                          true if the MatchOutcome is initialized correctly
     * @throws MatchOutcomeInvalidError if the MatchOutcome is invalid, discard the MatchOutcome
     */
    private boolean matchOutcomeIsValid(Player player1, HashMap<StatisticsEnum, Integer> player1Results,
                                        Player player2, HashMap<StatisticsEnum, Integer> player2Results)
                                        throws MatchOutcomeInvalidError
    {
        if (player1 == null || player2 == null){
            throw new MatchOutcomeInvalidError("A match must have two players."); }
        else if (player1 == player2){
            throw new MatchOutcomeInvalidError("A match must have two unique players.");
        }

        // TODO: Additional checks to ensure results hashmaps are valid

        return true;
    }
}
