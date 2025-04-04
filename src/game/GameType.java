package game;

import account.statistics.StatisticType;

public enum GameType {
    CHESS,
    CHECKERS,
    CONNECT4,
    TICTACTOE;


    /**
     * Get a String representation of the enum value
     * @return  String representation of enum. Example: WINS_BLOCKED --> "Wins Blocked"
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        // Split enum name into lowercase words
        String[] words = name().toLowerCase().split("_");

        // Convert words to title case and then append them to SB
        for (String word : words){
            sb.append(Character.toUpperCase(word.charAt(0)));
            sb.append(word.substring(1));
            sb.append(" ");
        }

        // Return string for enum
        return sb.toString().trim();
    }

    /**
     * Given a String representation of the enumerator value, return the enumerator value.
     * @param string    String given by a call to some GameType enum's toString() function.
     * @return          GameType corresponding to that string.
     */
    public GameType fromString(String string){
        for (GameType gameType : GameType.values()){
            if (gameType.toString().equals(string)) {
                return gameType;
            }
        }
        return null;
    }
}

