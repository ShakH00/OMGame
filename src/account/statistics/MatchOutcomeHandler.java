package account.statistics;

import game.GameType;
import account.Account;

import java.util.HashMap;

/**
 * Contains functions for updating player statistics with the results of a match
 */

public class MatchOutcomeHandler {
    /**
     * Add the results of a Match to the participating players' statistics and match history.
     * @param game                          GameType for the game that is being played
     * @param matchID                       int unique ID for this match to store in database
     * @param account1                      Account for player 1 of this match
     * @param account1Results               HashMap containing statistics for player 1
     * @param account2                      Account for player 2 of this match
     * @param account2Results               HashMap containing statistics for player 2
     * @throws MatchOutcomeInvalidError     Error thrown if the Match Outcome is malformed and cannot be processed
     */
    public static void RecordMatchOutcome(GameType game, int matchID,
                                          Account account1, HashMap<StatisticType, Integer> account1Results,
                                          Account account2, HashMap<StatisticType, Integer> account2Results)
                        throws MatchOutcomeInvalidError
    {
        // if the Player Statistics fields are not malformed, update PlayerStatistics and log match in Player match histories
        if (matchOutcomeIsValid(game, account1, account1Results, account2, account2Results)){
            // Update statistics for both Players
            account1.updateStatistics(game, account1Results);
            account2.updateStatistics(game, account2Results);

            // Add a String[] representation of the match outcome to both players' accounts
            account1.logMatch(composeMatchLog(game, account1Results, account2, matchID));
            account2.logMatch(composeMatchLog(game, account2Results, account1, matchID));

            // Update both players' Elo
            int player1OldElo = account1.getElo(game);
            int player2OldElo = account2.getElo(game);

            double player1Score = getMatchScore(account1Results);
            double player2Score = getMatchScore(account2Results);

            double player1Expected = getExpectedScore(player1OldElo, player2OldElo);
            double player2Expected = getExpectedScore(player2OldElo, player1OldElo);

            int kFactor = getKFactor(game);

            int player1EloChange = (int) Math.round(kFactor * (player1Score - player1Expected));
            int player2EloChange = (int) Math.round(kFactor * (player2Score - player2Expected));

            int player1NewElo = player1OldElo + player1EloChange;
            int player2NewElo = player2OldElo + player2EloChange;

            account1.updateElo(game, player1NewElo);
            account2.updateElo(game, player2NewElo);
        }
    }


    private static double getMatchScore(HashMap<StatisticType, Integer> playerResults) {
        if (playerResults.getOrDefault(StatisticType.WINS, 0) == 1) return 1.0;
        if (playerResults.getOrDefault(StatisticType.DRAWS, 0) == 1) return 0.5;
        return 0.0; // loss
    }

    private static double getExpectedScore(int ratingA, int ratingB) {
        return 1 / (1 + Math.pow(10, (ratingB - ratingA) / 400.0));
    }

    private static int getKFactor(GameType game) {
        return switch (game) {
            case CHESS -> 32;
            case CHECKERS -> 20;
            case CONNECT4 -> 15;
            case TICTACTOE -> 10;
        };
    }

    /**
     * Check if the MatchOutcomeHandler is initialized correctly
     * @return                          true if the Players exist and the results are well-formed
     * @throws MatchOutcomeInvalidError if the match outcome is not created properly
     */
    private static boolean matchOutcomeIsValid( GameType game,
                                                Account account1,
                                                HashMap<StatisticType, Integer> player1Results,
                                                Account account2,
                                                HashMap<StatisticType, Integer> player2Results)
                                                throws MatchOutcomeInvalidError
    {
        if (account1 == null || account2 == null){
            throw new MatchOutcomeInvalidError("A match must have two players."); }
        else if (account1 == account2){
            throw new MatchOutcomeInvalidError("A match must have two unique players.");
        }

        // TODO: Additional checks to ensure results hashmaps are valid

        return true;
    }

    private static String[] composeMatchLog(GameType game,
                                            HashMap<StatisticType, Integer> playerResults,
                                            Account opponent,
                                            int matchID)
    {
        String[] matchLog = new String[6];

        // index 0: game result (win/loss/draw)
        if (playerResults.getOrDefault(StatisticType.WINS, 0) == 1){
            matchLog[0] = "Win";
        }
        else if (playerResults.getOrDefault(StatisticType.LOSSES, 0) == 1){
            matchLog[0] = "Loss";
        }
        else if (playerResults.getOrDefault(StatisticType.DRAWS, 0) == 1){
            matchLog[0] = "Draw";
        }

        // index 1: game name (chess/checkers/connect 4/tic-tac-toe)
        switch(game){
            case GameType.CHESS -> matchLog[1] = "Chess";
            case GameType.CONNECT4 -> matchLog[1] = "Connect 4";
            case GameType.TICTACTOE -> matchLog[1] = "Tic-Tac-Toe";
            case GameType.CHECKERS -> matchLog[1] = "Checkers";
        }

        // index 2: opponent name
        matchLog[2] = opponent.getUsername();

        // index 3: opponent ID
        matchLog[3] = String.valueOf(opponent.getID());

        // index 4: opponent Elo
        matchLog[4] = String.valueOf(opponent.getElo(game));

        // index 5: match ID
        matchLog[5] = String.valueOf(matchID);

        // return
        return matchLog;
    }
}
