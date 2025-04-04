package account.statistics;

/**
 * Enum for statistics that must be tracked by each game.
 * When a match is complete, it should be logged using MatchOutcomeHandler with results HashMaps that contain all statistics
 * that are related to the specific game, as described in the comments of this class.
 */
public enum StatisticType {
    ELO,                // Complex; cannot be updated by simple addition/subtraction
    WIN_RATE,           // Complex; cannot be updated by simple addition/subtraction

    WINS,
    LOSSES,
    DRAWS,
    MATCHES_PLAYED,
    NUMBER_OF_TURNS,
    PIECES_CAPTURED,    // Chess, Checkers      Includes pawns
    PAWNS_CAPTURED,     // Chess
    KNIGHTS_CAPTURED,   // Chess
    BISHOPS_CAPTURED,   // Chess
    ROOKS_CAPTURED,     // Chess
    QUEENS_CAPTURED,    // Chess
    KINGS_CAPTURED,     // Chess
    CHECKS,             // Chess
    CHECKMATES,         // Chess
    PIECES_PROMOTED,    // Chess
    MULTI_CAPTURES,     // Checkers             When a player captures multiple pieces in one turn
    WINS_BLOCKED;       // Tic-Tac-Toe, Connect 4


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
     * @param string    String given by a call to some StatisticType enum's toString() function.
     * @return          StatisticType corresponding to that string.
     */
    public StatisticType fromString(String string){
        for (StatisticType statisticType : StatisticType.values()){
            if (statisticType.toString().equals(string)) {
                return statisticType;
            }
        }
        return null;
    }
}
