/**
 * A Queen object class for Queen pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Queen automatically has a score of nine (9) pawns, 9x1 = 9.
 *
 * @author Abdulrahman
 */

package game.chess;

import game.Board;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

public class Queen extends MovingPiece {

    /**
     *
     * @param x
     * @param y
     * @param color
     * @param pieceType
     * @param ownedBy
     */
    public Queen(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, 9);
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
