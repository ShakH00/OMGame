package account.statistics;

import account.LoggedInAccount;
import game.GameType;
import account.Account;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Contains functions for updating player statistics with the results of a match
 */

public class MatchOutcomeHandler {
    public static int opponentID = -1;
    public static int opponentElo = -1;
    public static String opponentUsername = "Not Found";
    public static boolean affectElo = false;

    /**
     * Given an account and their opponent's details, update the account's statistics.
     * @param game                  GameType game that was played
     * @param thisAccountResults    HashMap with results for active player
     */
    public static void RecordMatchOutcome(GameType game, HashMap<StatisticType, Integer> thisAccountResults)
    {
        // Get this account
        Account thisAccount = LoggedInAccount.getAccount();

        // Update statistics for account
        thisAccount.updateStatistics(game, thisAccountResults);
        thisAccount.logMatch(composeMatchLog(game, opponentID, opponentUsername, opponentElo, thisAccountResults));

        // Update both players' Elo (if it wasn't a private/friendly match)
        if (affectElo) {
            int previousElo = thisAccount.getElo(game);
            double score = getMatchScore(thisAccountResults);
            double expectedScore = getExpectedScore(previousElo, opponentElo);
            int kFactor = getKFactor(game);
            int eloChange = (int) Math.round(kFactor * (score - expectedScore));
            int newElo = previousElo + eloChange;
            thisAccount.updateElo(game, newElo);
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

    private static String[] composeMatchLog(GameType game,
                                            int opponentID,
                                            String opponentUsername,
                                            int opponentElo,
                                            HashMap<StatisticType, Integer> thisResults)
    {
        String[] matchLog = new String[6];

        // index 0: game result (win/loss/draw)
        if (thisResults.getOrDefault(StatisticType.WINS, 0) == 1){
            matchLog[0] = "Win";
        }
        else if (thisResults.getOrDefault(StatisticType.LOSSES, 0) == 1){
            matchLog[0] = "Loss";
        }
        else if (thisResults.getOrDefault(StatisticType.DRAWS, 0) == 1){
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
        matchLog[2] = opponentUsername;

        // index 3: opponent ID
        matchLog[3] = String.valueOf(opponentID);

        // index 4: opponent Elo
        matchLog[4] = String.valueOf(opponentElo);

        // index 5: match date
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        matchLog[5] = format.format(new java.util.Date());

        // return
        return matchLog;
    }
}
