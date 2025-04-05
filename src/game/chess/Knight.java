/**
 * A knight object class for knight pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Knight automatically has a score of three (3) pawns, 3x1 = 3.
 *
 * @author Abdulrahman Negmeldin
 */
package game.chess;

import game.Board;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

public class Knight extends MovingPiece {

    /**
     * A constructor method for creating a Knight piece
     * @param x: x coordinate of piece
     * @param y: y coordinate of piece
     * @param color: colour of the knight
     * @param pieceType: Type of piece (LIGHT or DARK)
     * @param ownedBy: Player who owns the knight
     */
    public Knight(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, 3);
    }

    /**
     * A move method to move the knight
     * @param newX: new x coordinate being moved to
     * @param newY: new y coordinate being moved to
     * @param gameBoard: board playing on
     * @return true if piece was moved
     */
    @Override
    public boolean move(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        if(isValidMove(newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            this.setX(newX);
            this.setY(newY);
            board[newX][newY] = this;
            return true;
        }
        return false;
    }

    /**
     * A method to check if a potential move is valid for the knight to make
     * Some notes on how a knight can move in chess:
     * 1. A Knight moves in an L shape, two tiles in one direction and one tile to the right or left (ex: two tiles up, one right)
     * 2. A piece being in its way does not matter, as it is a Knight, think of it like having the ability to jump over pieces
     * 3. The only thing stopping it is if moving out of its current tile places your king in check, a rule that is across the board for all pieces
     *      Also a rule that is NOT being checked within this method but rather a different one in Chess.java
     * @param newX: new x coordinate it might move to
     * @param newY: new y coordinate it might move to
     * @param gameBoard: board being played on
     * @return true if this is a valid move to make
     */
    @Override
    protected boolean isValidMove(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();
        Piece isPieceOnTile = board[newX][newY];
        //have to check for L shape movement!
        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        //if not L shape then return false
        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
            return false;
        }

        //if tile is empty or enemy piece then move is allowed
        return (isPieceOnTile == null || isPieceOnTile.getPieceType() != type);
    }
}
