/**
 * A Rook object class for rook pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Rook automatically has a score of five (5) pawns, 5x1 = 5.
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

public class Rook extends MovingPiece {
    private boolean doneFirstMove; //used to check if can castle

    /**
     * A constructor to create a Rook object
     * @param x: initial x coordinate
     * @param y: initial y coordinate
     * @param color: Color of rook
     * @param pieceType: PieceType (LIGHT or DARK)
     * @param ownedBy: Player who owns the rook
     */
    public Rook(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, -1);
        this.doneFirstMove = false;
    }

    /**
     * Method used to check if the rook has performed its first move yet
     * @return true if first move has been performed
     */
    public boolean isDoneFirstMove(){
        return doneFirstMove;
    }

    /**
     * Method to modify the doneFirstMove boolean attribute
     * Used for when you perform the Castle special move
     * @param doneFirstMove: boolean to set doneFirstMove as
     */
    public void setDoneFirstMove(boolean doneFirstMove){
        this.doneFirstMove = doneFirstMove;
    }

    /**
     * Method to move the rook
     * @param newX: new x coordinate being moved to
     * @param newY: new y coordinate being moved to
     * @param gameBoard: board being played on
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
            if(!doneFirstMove){
                this.doneFirstMove = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Method to check if a move is valid to perform
     * A few notes on Rook movement in chess:
     * 1. A rook moves in a straight line, so long as nothing is obstructing its path
     *      this means a rook can move from one edge of the board to the opposite one,
     *      as long as it is either on the same row or column
     * 2. The only special move performed using a Rook is castling, where it alternates with a king, moving closer
     *      This special move is taken care of on the King's side as it can only be done through the King piece
     * @param newX: new x coordinate that the rook may be moved to
     * @param newY: new y coordinate that the rook may be moved to
     * @param gameBoard: board being played on
     * @return true if this is a valid move to amke
     */
    @Override
    public boolean isValidMove(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();

        // Prevent illegal current position
        if (currentX < 0 || currentX >= 8 || currentY < 0 || currentY >= 8) {
            return false;
        }

        // Prevent illegal destination
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }

        Piece isPieceOnTile = board[newX][newY];

        //must move in a straight line
        if(currentX != newX && currentY != newY){ //not straight line
            return false; //return false, end here
        }
        if(currentX == newX){ //move horizontal because x stays the same
            int yStep = (newY > currentY) ? 1 : -1; //if move right = +1, move left = -1
            for (int y = currentY + yStep; y != newY; y += yStep) {

                if (y < 0 || y >= board[0].length) return false; // Extra check - Yousif

                if (board[newX][y] != null) {
                    return false; //tile has piece so invalid move
                }
            }
        } else { //move vertical because y stays the same
            int xStep = (newX > currentX) ? 1 : -1; // move down = +1, move up = -1
            for (int x = currentX + xStep; x != newX; x += xStep) {

                if (x < 0 || x >= board.length) return false; // extra check - Yousif

                if (board[x][newY] != null) {
                    return false; //tile has piece so invalid move
                }
            }
        }
        return isPieceOnTile == null || isPieceOnTile.getPieceType() != type; //true if destination tile is empty or has enemy piece to be eaten

    }
}
