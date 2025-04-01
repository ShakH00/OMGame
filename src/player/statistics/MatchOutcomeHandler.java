package player.statistics;

import game.GamesEnum;
import player.Player;

import java.util.HashMap;

/**
 * Contains functions for updating player statistics with the results of a match
 */

public class MatchOutcomeHandler {
    /**
     * Add the results of a Match to the participating players' statistics and match history.
     * @param game                      GamesEnum for the game that is being played
     * @param matchID                   int unique ID for this match to store in database
     * @param player1                   Player 1 of this match
     * @param player1Results            HashMap containing statistics for Player 1
     * @param player2                   Player 2 of this match
     * @param player2Results            HashMap containing statistics for Player 2
     * @throws MatchOutcomeInvalidError Error thrown if the Match Outcome is malformed and cannot be processed
     */
    public static void RecordMatchOutcome(GamesEnum game, int matchID,
                                          Player player1, HashMap<StatisticsEnum, Integer> player1Results,
                                          Player player2, HashMap<StatisticsEnum, Integer> player2Results)
                        throws MatchOutcomeInvalidError
    {
        // if the Player Statistics fields are not malformed, update PlayerStatistics and log match in Player match histories
        if (matchOutcomeIsValid(game, player1, player1Results, player2, player2Results)){
            // Update statistics for both Players
            player1.getAccount().updateStatistics(game, player1Results);
            player2.getAccount().updateStatistics(game, player2Results);

            // Add a String[] representation of the match outcome to both players' accounts
            player1.getAccount().logMatch(composeMatchLog(game, player1Results, player2, matchID));
            player2.getAccount().logMatch(composeMatchLog(game, player2Results, player1, matchID));

            // Update both players' Elo
            int player1OldElo = player1.getAccount().getElo(game);
            int player2OldElo = player2.getAccount().getElo(game);
            Integer player1NewElo = -1;  // TODO: Handle elo calculation. Use player1Results to figure out if it was a win or loss.
            Integer player2NewElo = -1;  // TODO: Handle elo calculation. Use player2Results to figure out if it was a win or loss.
            player1.getAccount().updateElo(game, player1NewElo);
            player2.getAccount().updateElo(game, player2NewElo);
        }
    }

    /**
     * Check if the MatchOutcomeHandler is initialized correctly
     * @return                          true if the Players exist and the results are well-formed
     * @throws MatchOutcomeInvalidError if the match outcome is not created properly
     */
    private static boolean matchOutcomeIsValid( GamesEnum game,
                                                Player player1,
                                                HashMap<StatisticsEnum, Integer> player1Results,
                                                Player player2,
                                                HashMap<StatisticsEnum, Integer> player2Results)
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

    private static String[] composeMatchLog(GamesEnum game,
                                            HashMap<StatisticsEnum, Integer> playerResults,
                                            Player opponent,
                                            int matchID)
    {
        String[] matchLog = new String[6];

        // index 0: game result (win/loss/draw)
        if (playerResults.get(StatisticsEnum.WINS) == 1){
            matchLog[0] = "Win";
        }
        else if (playerResults.get(StatisticsEnum.LOSSES) == 1){
            matchLog[0] = "Loss";
        }
        else if (playerResults.get(StatisticsEnum.DRAWS) == 1){
            matchLog[0] = "Draw";
        }

        // index 1: game name (chess/checkers/connect 4/tic-tac-toe)
        switch(game){
            case GamesEnum.CHESS -> matchLog[1] = "Chess";
            case GamesEnum.CONNECT4 -> matchLog[1] = "Connect 4";
            case GamesEnum.TICTACTOE -> matchLog[1] = "Tic-Tac-Toe";
            case GamesEnum.CHECKERS -> matchLog[1] = "Checkers";
        }

        // index 2: opponent name
        matchLog[2] = opponent.getAccount().getUsername();

        // index 3: opponent ID
        matchLog[3] = String.valueOf(opponent.getAccount().getID());

        // index 4: opponent Elo
        matchLog[4] = String.valueOf(opponent.getAccount().getElo(game));

        // index 5: match ID
        matchLog[5] = String.valueOf(matchID);

        // return
        return matchLog;
    }
}
