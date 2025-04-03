/**
 * A Bishop object class for bishop pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Bishop automatically has a score of three (3) pawns, 3x1 = 3.
 *
 * @author Abdulrahman
 */

package game.chess;

import game.Board;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

public class Bishop extends MovingPiece {

    /**
     *
     * @param x
     * @param y
     * @param color
     * @param pieceType
     * @param ownedBy
     */
    public Bishop(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, 3);
    }

    /**
     *
     * @param currentX
     * @param currentY
     * @param newX
     * @param newY
     * @param gameBoard
     */
    @Override
    protected void move(int currentX, int currentY, int newX, int newY, Board gameBoard) {

    }

    /**
     *
     * @param currentX
     * @param currentY
     * @param newX
     * @param newY
     * @param gameBoard
     * @return
     */
    @Override
    protected boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        return false;
    }
}
