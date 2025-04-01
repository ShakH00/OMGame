package game.pieces;

import game.Player;

/**
 * An Abstract game.pieces.Piece class which is inherited by ALL game.pieces whether they are moving or stationary game.pieces
 * Each piece, regardless of its kind, requires a x (row) and y (column) attribute to determine its location
 * Each piece also requires a colour which is a visual indicator of which player it belongs to
 * Each piece holds an ownedBy attribute which is the game.Player that it belongs to
 * Each piece has a score value when it comes to its worth
 *
 * @author Abdulrahman
 */
public abstract class Piece {
    private String colour;
    private Player ownedBy;
    private int score;
    private PieceType pieceType;

    /**
     * Constructor
     * @param colour: String, colour
     * @param ownedBy: game.Player, player who owns the piece
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
     * @return ownedBy: game.Player
     */
    public Player getOwnedBy(){
        return this.ownedBy;
    }

    /**
     * Get the piece type (LIGHT or DARK)
     * @return pieceType: game.pieces.PieceType
     */
    public PieceType getPieceType(){
        return this.pieceType;
    }

}

