package player;

/**
 * Enum for statistics that must be tracked by each game
 * It is expected that the results hashmaps used to initialize a MatchOutcome object contain all relevant statistics
 */
public enum StatisticsEnum {
    // All games
    WINS,
    LOSSES,
    DRAWS,
    ELO,
    NUMBER_OF_TURNS,

    // Chess, Checkers only
    PIECES_CAPTURED,    // yes, this includes pawns

    // Chess only
    PAWNS_CAPTURED,
    KNIGHTS_CAPTURED,
    BISHOPS_CAPTURED,
    ROOKS_CAPTURED,
    QUEENS_CAPTURED,
    KINGS_CAPTURED,
    CHECKS,
    CHECKMATES,
    PIECES_PROMOTED,

    // Tic-Tac-Toe, Connect 4 only
    WINS_BLOCKED    // e.g. P1 has three red pieces in a line, and P2 places a yellow which blocks P1's potential win

}
