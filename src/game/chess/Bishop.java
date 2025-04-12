/**
 * A Bishop object class for bishop pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Bishop automatically has a score of three (3) pawns, 3x1 = 3.
 *
 * @author Abdulrahman Negmeldin
 */

package game.chess;

import game.Board;
import game.GameState;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

public class Bishop extends MovingPiece {

    /**
     * Constructor to create a bishop object piece
     * @param x: initial x coordinate
     * @param y: initial y coordinate
     * @param color: Color of bishop
     * @param pieceType: PieceType enum (LIGHT or DARK)
     * @param ownedBy: Player who owns the piece
     */
    public Bishop(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, 3);
    }

    /**
     * Method to move the bishop
     * @param newX: new x coordinate being moved to
     * @param newY: new y coordinate being moved to
     * @param gameBoard: board being played on
     * @return true if piece was moved
     */
    @Override
    public boolean move(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        //if valid move then move there
        if(isValidMove(newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            this.setX(newX);
            this.setY(newY);
            board[newX][newY] = this;
            return true;
        }
        return false; //if reached end of code then invalid move, did not occur
    }

    /**
     * A method to check if a move can be performed by the Bishop
     * A few notes on Bishop movement in Chess:
     * 1. A bishop moves diagonally, so long as nothing is obstructing its path, a similar attribute to that of the Rook
     * 2. This means a Bishop can only move on tiles that are the same color as its home tile
     * There are no special moves for the Bishop
     * @param newX: new x coordinate it might move to
     * @param newY: new y coordinate it might move to
     * @param gameBoard: board being played on
     * @return true if this is a valid move to perform
     */
    @Override
    public boolean isValidMove(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();
        Piece isPieceOnTile = board[newX][newY];

        //out of bound check, prevent illegal start position
        if (currentX < 0 || currentX >= 8 || currentY < 0 || currentY >= 8) {
            return false;
        }

        //out of bound check, prevent illegal move
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }

        //diagonal check (like bishop)
        if (Math.abs(newX - currentX) == Math.abs(newY - currentY)) {
            // Ensure we move in the correct direction
            int xDir = (newX > currentX) ? 1 : -1; //if newX > currentX then 1, else -1
            int yDir = (newY > currentY) ? 1 : -1; //same as x but for y direction

            int x = currentX + xDir;
            int y = currentY + yDir;

            //check that every tile in between is empty
            while (x != newX) {
                if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                    return false; //check bounds for each step
                }
                if (board[x][y] != null) {
                    return false; //tile has piece, so invalid move
                }

                //move onto next tile
                x += xDir;
                y += yDir;
            }

            //final destination check (either empty or an enemy piece)
            return isPieceOnTile == null || isPieceOnTile.getPieceType() != type;
        }

        return false; //reach end of method meaning invalid move
    }


}
