package game.pieces;

import game.Player;
import javafx.scene.paint.Color;

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
    private Color color;
    private Player ownedBy;
    private int score;
    private PieceType pieceType;

    /**
     * Constructor
     * @param colour: String, colour
     * @param ownedBy: game.Player, player who owns the piece
     * @param score: int
     */
    public Piece(Color color, PieceType pieceType, Player ownedBy, int score){
        this.color = color;
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
    public Color getColor(){
        return this.color;
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

    public boolean isKing() {
        return false;
    }
}

