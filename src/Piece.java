/**
 * An Abstract Piece class which is inherited by ALL pieces whether they are moving or stationary pieces
 * Each piece, regardless of its kind, requires a x (row) and y (column) attribute to determine its location
 * Each piece also requires a colour which is a visual indicator of which player it belongs to
 * Each piece holds an ownedBy attribute which is the Player that it belongs to
 * Each piece has a score value when it comes to its worth
 *
 * @author Abdulrahman
 */
abstract class Piece {
    private String colour;
    private Player ownedBy;
    private int score;
    private PieceType pieceType;

    /**
     * Constructor
     * @param colour: String, colour
     * @param ownedBy: Player, player who owns the piece
     * @param score: int
     */
    public Piece(String colour, PieceType pieceType, Player ownedBy, int score){
        this.colour = colour;
        this.pieceType = pieceType;
        this.ownedBy = ownedBy;
        this.score = score;
    }

    /**
     * Get score value
     * @return score: int
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Get piece colour
     * @return colour: String
     */
    public String getColour(){
        return this.colour;
    }

    /**
     * Get the player who owns the piece
     * @return ownedBy: Player
     */
    public Player getOwnedBy(){
        return this.ownedBy;
    }

    /**
     * Get the piece type (LIGHT or DARK)
     * @return pieceType: PieceType
     */
    public PieceType getPieceType(){
        return this.pieceType;
    }

}

