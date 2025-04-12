package game.pieces;

import game.Player;
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.io.IOException;

/**
 * An Abstract game.pieces.Piece class which is inherited by ALL game.pieces whether they are moving or stationary game.pieces
 * Each piece, regardless of its kind, requires a x (row) and y (column) attribute to determine its location
 * Each piece also requires a colour which is a visual indicator of which player it belongs to
 * Each piece holds an ownedBy attribute which is the game.Player that it belongs to
 * Each piece has a score value when it comes to its worth
 *
 * @author Abdulrahman
 */
public abstract class Piece implements Serializable {
    // Make Piece class serializable, so it can be used for networking
    // Used: Cursor AI IDE
    private transient Color color; // Mark as transient to prevent serialization
    private double red;
    private double green;
    private double blue;
    private double opacity;
    private Player ownedBy;
    private int score;
    private PieceType pieceType;

    /**
     * Constructor
     * @param color: String, colour
     * @param ownedBy: game.Player, player who owns the piece
     * @param score: int
     */
    public Piece(Color color, PieceType pieceType, Player ownedBy, int score){
        this.color = color;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
        this.pieceType = pieceType;
        this.ownedBy = ownedBy;
        this.score = score;
    }

    /**
     * Custom serialization method
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Custom deserialization method
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.color = new Color(red, green, blue, opacity);
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
        if (color == null) {
            color = new Color(red, green, blue, opacity);
        }
        return color;
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

