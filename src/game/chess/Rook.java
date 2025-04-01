/**
 * A Rook object class for rook pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Rook automatically has a score of five (5) pawns, 5x1 = 5.
 *
 * @author Abdulrahman
 */

package game.chess;

import game.Board;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.PieceType;

public class Rook extends MovingPiece {
    private boolean doneFirstMove;

    /**
     *
     * @param x
     * @param y
     * @param colour
     * @param pieceType
     * @param ownedBy
     */
    public Rook(int x, int y, String colour, PieceType pieceType, Player ownedBy){
        super(x, y, colour, pieceType, ownedBy, -1);
        this.doneFirstMove = false;
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
