/**
 * A Queen object class for Queen pieces in the game of Chess
 * Like other piece objects, it tracks x, y coordinates, colour, player who owns it, and score
 * Each Queen automatically has a score of nine (9) pawns, 9x1 = 9.
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

public class Queen extends MovingPiece {

    /**
     * A constructor to create a queen piece object
     * @param x: initial x coordinate
     * @param y: initial y coordinate
     * @param color: Color of queen
     * @param pieceType: PieceType (LIGHT or DARK)
     * @param ownedBy: Player who owns the queen
     */
    public Queen(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, 9);
    }

    /**
     * A move method to move the queen
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
            return true;
        }
        return false;
    }

    /**
     * A method to check if a potential move is valid to be performed
     * A few notes on queen movement in chess
     * 1. A queen can move in any given direction so long as the tile it is being moved to is empty
     *      or has an enemy piece which would be eaten by the queen
     * 2. The only other condition is that every tile in between the current one and destination tile is empty
     * This means a queen can move diagonally like a bishop or straight like a rook, but NOT like a knight
     * However, it cannot move like a knight
     * @param newX: new x coordinate that the queen might move to
     * @param newY: new y coordinate that the queen might move to
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

        // Prevent illegal current position
        if (currentX < 0 || currentX >= 8 || currentY < 0 || currentY >= 8) {
            return false;
        }

        // Prevent illegal destination
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }

        //if move diagonal like bishop
        if(Math.abs(newX-currentX) == Math.abs(newY-currentY)){
            //ensure we move in right direction
            int xDir = (newX > currentX) ? 1 : -1; //if newX > currentX then one (1), else then negative one (-1)
            int yDir = (newY > currentY) ? 1 : -1; //same deal as above

            int x = currentX + xDir;
            int y = currentY + yDir;

            //check if all tiles are empty in between current and destination tiles
            while(x != newX){
                if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                    return false; // Check bounds for each step
                }
                if(board[x][y] != null){
                    return false;
                }
                //update x and y to check next tile!
                x += xDir;
                y += yDir;
            }
            return isPieceOnTile == null || isPieceOnTile.getPieceType() != type; //make sure destination tile is either empty or enemy piece
        }
        //rook-like movement check
        if(currentX != newX && currentY != newY){ //not straight line
            return false; //return false, end here
        } else if(currentX == newX){ //move horizontal because x stays the same
            int yStep = (newY > currentY) ? 1 : -1; //if move right = +1, move left = -1
            for (int y = currentY + yStep; y != newY; y += yStep) {
                if (y < 0 || y >= 8) return false;
                if (board[newX][y] != null) {
                    return false; //tile has piece so invalid move
                }
            }
        } else { //move vertical because y stays the same
            int xStep = (newX > currentX) ? 1 : -1; // move down = +1, move up = -1
            for (int x = currentX + xStep; x != newX; x += xStep) {
                if (x < 0 || x >= 8) return false;
                if (board[x][newY] != null) {
                    return false; //tile has piece so invalid move
                }
            }
        }


        return isPieceOnTile == null || isPieceOnTile.getPieceType() != type; //true if destination tile is empty or has enemy piece to be eaten
    }
}
