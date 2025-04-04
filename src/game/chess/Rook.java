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
     * @param currentX: current x coordinate of the rook
     * @param currentY: current y coordinate of the rook
     * @param newX: new x coordinate being moved to
     * @param newY: new y coordinate being moved to
     * @param gameBoard: board being played on
     */
    @Override
    protected void move(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        if(isValidMove(currentX, currentY, newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            this.setX(newX);
            this.setY(newY);
            board[newX][newY] = this;
            if(!doneFirstMove){
                this.doneFirstMove = true;
            }
        }
    }

    /**
     * Method to check if a move is valid to perform
     * A few notes on Rook movement in chess:
     * 1. A rook moves in a straight line, so long as nothing is obstructing its path
     *      this means a rook can move from one edge of the board to the opposite one,
     *      as long as it is either on the same row or column
     * 2. The only special move performed using a Rook is castling, where it alternates with a king, moving closer
     *      This special move is taken care of on the King's side as it can only be done through the King piece
     * @param currentX: current x coordinate of the rook
     * @param currentY: current y coordinate of the rook
     * @param newX: new x coordinate that the rook may be moved to
     * @param newY: new y coordinate that the rook may be moved to
     * @param gameBoard: board being played on
     * @return true if this is a valid move to amke
     */
    @Override
    protected boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();
        Piece isPieceOnTile = board[newX][newY];
        //must move in a straight line
        if(isPieceOnTile.getPieceType() != type){
            if(currentX == newX && currentY != newY){
                if(newY-currentY>0){ //moving right
                    //have to check every tile in between is empty
                    for(int i = currentY; i < newY; i++){
                        if(board[newX][i] != null){
                            return false; //not empty so return false
                        }
                    }
                    return true; //empty so return true
                } else { //moving left
                    //check every piece between current y and new y as empty
                    for(int i = newY; i < currentY; i++){
                        if(board[newX][i] != null){
                            return false; //not empty = return false
                        }
                    }
                    return true; //empty, return true
                }
            } else if(currentY == newY && currentX != newX){
                if(newX-currentX > 0){ //moving down
                    //check every piece between current x and new x as empty
                    for(int i = currentX; i < newX; i++){
                        if(board[i][newY] != null){
                            return false; //not empty = return false
                        }
                    }
                    return true; //empty so return true
                } else{ //moving up
                    //check every piece between current and new x as empty
                    for(int i = newX; i < currentX; i++){
                        if(board[i][newY] != null){
                            return false; //not empty so return false
                        }
                    }
                    return true; //empty so return true
                }
            }
        }

        return false; //if reach end then move is invalid
    }
}
