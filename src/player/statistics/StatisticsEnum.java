package player.statistics;

/**
 * Enum for statistics that must be tracked by each game.
 * When a match is complete, it should be logged using MatchOutcomeHandler with results HashMaps that contain all statistics
 * that are related to the specific game, as described in the comments of this class.
 */
public enum StatisticsEnum {
    // All games
    ELO,                // Complex; cannot be updated by simple addition/subtraction
    WIN_RATE,           // Complex; cannot be updated by simple addition/subtraction
    WINS,
    LOSSES,
    DRAWS,
    MATCHES_PLAYED,
    NUMBER_OF_TURNS,

    // Chess, Checkers
    PIECES_CAPTURED,    // Includes pawns

    // Chess
    PAWNS_CAPTURED,
    KNIGHTS_CAPTURED,
    BISHOPS_CAPTURED,
    ROOKS_CAPTURED,
    QUEENS_CAPTURED,
    KINGS_CAPTURED,
    CHECKS,
    CHECKMATES,
    PIECES_PROMOTED,

    // Checkers
    MULTI_CAPTURES,     // When a player captures multiple pieces in one turn

    // Tic-Tac-Toe, Connect 4
    WINS_BLOCKED;       // e.g. P1 has three red pieces in a line, and P2 places a yellow which blocks P1's potential win


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
}
