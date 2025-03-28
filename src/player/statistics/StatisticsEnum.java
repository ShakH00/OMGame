package player.statistics;

/**
 * Enum for statistics that must be tracked by each game.
 * When a match is complete, it should be logged using MatchOutcomeHandler with results HashMaps that contain all statistics
 * that are related to the specific game, as described in the comments of this class.
 */
public enum StatisticsEnum {
    // Complex stats (cannot be updated by simple addition/subtraction)
    ELO,
    WIN_RATE,

    // All games
    WINS,
    LOSSES,
    DRAWS,
    NUMBER_OF_TURNS,

    // Chess, Checkers
    PIECES_CAPTURED,    // yes, this includes pawns

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
    MULTI_CAPTURES, // when a player captures multiple pieces in one turn

    // Tic-Tac-Toe, Connect 4
    WINS_BLOCKED    // e.g. P1 has three red pieces in a line, and P2 places a yellow which blocks P1's potential win
}
