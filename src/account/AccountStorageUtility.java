package account;

import account.statistics.*;
import game.Game;
import game.GameType;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountStorageUtility {

    // MATCH HISTORY

    private static final String matchHistoryEntryDivider = ", ";
    private static final String matchHistoryLineDivider = "\n";

    public static String matchHistoryToString(String[][] matchHistory){
        // Create an ArrayList containing entries in a MatchHistory row, joined by the match history entry divider.
        ArrayList<String> matchHistoryLines = new ArrayList<>();
        for (String[] row : matchHistory){
            String line = String.join(matchHistoryEntryDivider, row);
            matchHistoryLines.add(line);
        }
        // Return match history lines, joined by the match history line divider.
        return String.join(matchHistoryLineDivider, matchHistoryLines);
    }

    public static String[][] matchHistoryFromString(String string){
        // Output: String[][] for match history
        String[][] output = new String[10][6];

        // Get input string, and split it into lines using the line divider constant
        String[] lines = string.split(matchHistoryLineDivider);

        // Split each line into its entries and add them to the output
        for (int i = 0; i < lines.length; i++){
            output[i] = lines[i].split(matchHistoryEntryDivider);
        }

        return output;
    }


    // STATISTICS

    // String form example: "game#<statistic=value>, <statistic=value>, ... %game#<statistic=value>, ..."
    private static final String gameDelimiter = "%";                // Separate game statistics objects
    private static final String gameStatisticsDelimiter = "#";      // Separate game name from statistics list
    private static final String statisticsDelimiter = ", ";         // Separate statistics in statistics list
    private static final String statisticTupleDelimiter = "=";      // Separate type and value in statistic

    public static String statisticsToString(HashMap<GameType, AStatistics> statistics){
        // Initialize list of game strings, which will be combined after the loop over all game statistics objects
        ArrayList<String> gameStrings = new ArrayList<>();

        // Loop over all game statistics objects in the statistics input
        for (GameType gameType : statistics.keySet()){
            // Get statistics object reference
            AStatistics thisStatistics = statistics.get(gameType);

            // Get accepted statistics
            StatisticType[] statisticsTypes = thisStatistics.getAcceptedStatistics();

            // For each StatisticType in the statistics for this game, add a tuple of (Statistics Type, Value)
            ArrayList<String> statisticsTuples = new ArrayList<>();
            for (StatisticType statisticType : statisticsTypes){
                // Ignore statistics with default/missing values
                if (thisStatistics.getStatistic(statisticType) != null){
                    // Get the string for the statistic type
                    String statisticTypeString = statisticType.toString();

                    // Get the string for the value, with prefix "i" if Integer or "d" if Double.
                    Number value = thisStatistics.getStatistic(statisticType);
                    String valueString;
                    if      (value instanceof Integer)  { valueString = String.format("i%d", value); }
                    else if (value instanceof Double)   { valueString = String.format("d%f", value); }
                    else                                { valueString = String.format("i%d", (int) value); }

                    // Add tuple of form (<statistics type string>=<value string>)
                    statisticsTuples.add(String.format("%s%s%s", statisticTypeString, statisticTupleDelimiter, valueString));
                }
            }

            // Combine statistics tuples
            String joinedStatistics = String.join(statisticsDelimiter, statisticsTuples);

            // Get <game name>#<statistics tuples>
            String joinedGameAndStatistics = String.format("%s%s%s", gameType.toString(), gameStatisticsDelimiter, joinedStatistics);

            // Add it to gameStrings
            gameStrings.add(joinedGameAndStatistics);
        }

        return String.join(gameDelimiter, gameStrings);
    }

    public static HashMap<GameType, AStatistics> statisticsFromString(String string){
        // Split the String by game
        String[] gameStatistics = string.split(gameDelimiter);

        // Initialize HashMap that assigns a Statistics object to each GameType
        HashMap<GameType, AStatistics> statisticsPerGame = new HashMap<>();

        // Iterate through each game in the String, adding a corresponding Statistic object to the initialized HashMap
        for (String gameStatistic : gameStatistics) {
            // Get GameType from the part of the string before the delimiter "#"
            String gameTypeString = gameStatistic.split(gameStatisticsDelimiter)[0];
            GameType gameType = GameType.fromString(gameTypeString);

            // Form a HashMap of statistics from the statistics list string after the delimiter "#"
            String statisticsListString = gameStatistic.split(gameStatisticsDelimiter)[1];
            String[] statisticsList = statisticsListString.split(statisticsDelimiter);

            // Initialize a HashMap that relates each StatisticType enum to a value (Integer or Double)
            HashMap<StatisticType, Number> statisticsHashMap = new HashMap<>();

            // Populate previously created HashMap using each statistic tuple in StatisticsList
            for (String statisticTuple : statisticsList) {
                // Get StatisticType from the part of the string before the delimiter "="
                String statisticTypeString = statisticTuple.split(statisticTupleDelimiter)[0];
                StatisticType statisticType = StatisticType.fromString(statisticTypeString);

                // Get corresponding value from the part of the string after the delimiter "="
                String valueString = statisticTuple.split(statisticTupleDelimiter)[1];

                // Convert value to appropriate type then add an entry in statisticsHashMap
                if (valueString.startsWith("i")){
                    Integer value = Integer.valueOf(valueString.replace("i", ""));
                    statisticsHashMap.put(statisticType, value);
                }
                else if (valueString.startsWith("d")){
                    Double value = Double.valueOf(valueString.replace("d", ""));
                    statisticsHashMap.put(statisticType, value);
                }
            }

            // Initialize an appropriate Statistics object using the statisticsHashMap
            AStatistics statistics = null;
            switch (gameType){
                case GameType.CHECKERS -> statistics = new StatisticsCheckers(statisticsHashMap);
                case GameType.CHESS -> statistics = new StatisticsChess(statisticsHashMap);
                case GameType.CONNECT4 -> statistics = new StatisticsConnect4(statisticsHashMap);
                case GameType.TICTACTOE -> statistics = new StatisticsTicTacToe(statisticsHashMap);
                case null -> {}     // not possible, this just hides the warning
            }

            statisticsPerGame.put(gameType, statistics);
        }

        return statisticsPerGame;
    }


    // FRIENDS LIST
    private static final String friendsDelimiter = ", ";

    /**
     *
     * @param friendIDs
     * @return
     */
    public static String friendIDsToString(ArrayList<Integer> friendIDs){
        ArrayList<String> friendIDStrings = new ArrayList<>();
        for (Integer friendID : friendIDs){
            friendIDStrings.add(String.valueOf(friendID));
        }
        return String.join(friendsDelimiter, friendIDStrings);
    }

    /**
     *
     * @param string
     * @return
     */
    public static ArrayList<Integer> friendIDsFromString(String string){
        ArrayList<Integer> friendIDs = new ArrayList<>();
        String[] friendIDStrings = string.split(friendsDelimiter);
        for (String friendIDString : friendIDStrings){
            friendIDString = friendIDString.trim();
            if (!friendIDString.isEmpty()){
                try {
                    friendIDs.add(Integer.parseInt(friendIDString));
                } catch (NumberFormatException e) {
                    System.out.println("Error in parsing friendID: " + friendIDString);
                }
            }
        }
        return friendIDs;
    }
}
