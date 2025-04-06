/**
 * A game.chess.King object class for the game of Chess
 * As with other pieces, it tracks x, y coordinates, colour, player who owns it, and score
 * The score is set to -1 as a King is the most valuable piece on the board
 * Hence, the -1 simply represents that it is valued at infinity. The king will never be eaten anyway
 * but rather would only ever get checkmated
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

public class King extends MovingPiece {
    private boolean doneFirstMove; //used to check if can castle

    /**
     * Constructor class to make a King, uses the super method call as this inherits MovingPiece
     * @param x: x coordinate of king
     * @param y: y coordinate of king
     * @param color: Color of king
     * @param pieceType: PieceType, LIGHT or DARK
     * @param ownedBy; Player who owns the piece
     */
    public King(int x, int y, Color color, PieceType pieceType, Player ownedBy){
        super(x, y, color, pieceType, ownedBy, -1);
        this.doneFirstMove = false;
    }


    /**
     * Method to move the king
     * @param newX: new x coordinate that the king might move to
     * @param newY: new y coordinate that the king might move to
     * @param gameBoard: board being played on
     * @return true if piece was moved
     */
    @Override
    public boolean move(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        if(isValidMove(newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();


            if(isTryingToCastle(currentX, currentY, newX, newY, gameBoard)){
                Object[] rookInfo = getCastlingRook(currentX, currentY, newX, newY, gameBoard);
                if (rookInfo != null) {
                    Rook rook = (Rook) rookInfo[0];
                    int oldRookX = (int) rookInfo[1];
                    int oldRookY = (int) rookInfo[2];
                    int newRookX = (int) rookInfo[3];
                    int newRookY = (int) rookInfo[4];

                    board[oldRookX][oldRookY] = null;
                    board[newRookX][newRookY] = rook;
                    rook.setX(newRookX);
                    rook.setY(newRookY);
                    rook.setDoneFirstMove(true); // if you track rook move state
                    board[currentX][currentY] = null;
                    this.setX(newX);
                    this.setY(newY);
                    board[newX][newY] = this;
                }
            } else{
                board[currentX][currentY] = null;
                this.setX(newX);
                this.setY(newY);
                board[newX][newY] = this;
            }
            if(!doneFirstMove){
                this.doneFirstMove = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the King is attempting to castle, used to move the rook if true
     * Assumes king has not moved, and castling logic is not blocked by check conditions here.
     * @param currentX: x coordinate initially
     * @param currentY: y coordinate initially
     * @param newX: The x coordinate king is moving to
     * @param newY: The y coordinate king is moving to
     * @param gameBoard: board being played on
     * @return true if the king is trying to castle
     */
    protected boolean isTryingToCastle(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        Piece[][] board = gameBoard.getBoardState();

        //king must not have done first move yet
        if (doneFirstMove || currentX != newX) {
            return false;
        }

        // check for kingside castle (rook on the right)
        if (newY - currentY == 2) {
            Piece potentialRook = board[currentX][7];
            if (potentialRook instanceof Rook) {
                Rook rook = (Rook) potentialRook;
                if (!rook.isDoneFirstMove()
                        && board[currentX][5] == null
                        && board[currentX][6] == null) {
                    return true;
                }
            }
        }

        //Queenside castle (rook on the left)
        if (currentY - newY == 2) {
            Piece potentialRook = board[currentX][0];
            if (potentialRook instanceof Rook) {
                Rook rook = (Rook) potentialRook;
                if (!rook.isDoneFirstMove()
                        && board[currentX][1] == null
                        && board[currentX][2] == null
                        && board[currentX][3] == null) {
                    return true;
                }
            }
        }

        return false; //not trying to castle if reached end of method
    }

    /**
     * Finds the rook involved in castling (either kingside or queenside)
     * @param newX: The x coordinate the king moved to
     * @param newY: The y coordinate the king moved to
     * @param gameBoard: board being played on
     * @return an array: [rook, oldX, oldY, newX, newY] for moving the rook
     */
    protected Object[] getCastlingRook(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        Piece[][] board = gameBoard.getBoardState();
        // Kingside
        if (newY - currentY == 2) {
            Rook rook = (Rook) board[currentX][7];
            return new Object[]{rook, currentX, 7, currentX, 5};
        }
        // Queenside
        else if (currentY - newY == 2) {
            Rook rook = (Rook) board[currentX][0];
            return new Object[]{rook, currentX, 0, currentX, 3};
        }
        return null;
    }


    /**
     * A method to check if the tile the game.chess.King is being moved to is a valid move
     * Some notes on how the game.chess.King can be moved in game.chess.Chess:
     * 1. A king can only move one tile, in any direction.
     * 2. There is a special move however, castling, where, if the king hasn't moved once yet, and neither
     *      was one of the rooks, with absolutely no piece in between them to stop it, then the rook and king
     *      will effectively alternate positions ending up side by side, clearing the edge
     * A king cannot move into a tile if it would cause him to end up in check, or checkmate
     * @param newX: x coordinate of tile that the king might be moved to
     * @param newY: y coordinate of tile that the king might be moved to
     * @param gameBoard: board being played on
     * @return true if this is a valid move to perform for the king
     */
    @Override
    protected boolean isValidMove(int newX, int newY, Board gameBoard) {
        int currentX = this.getX();
        int currentY = this.getY();
        //checking if any piece's movement may cause the king to be put in check will be done within the Chess file instead
        Piece[][] board = gameBoard.getBoardState();
        PieceType type = this.getPieceType();
        Piece isPieceOnTile = board[newX][newY];
        //might be trying to castle
        if(!doneFirstMove || board[newX][newY] == null){
                if(newY-currentY == 2 && board[currentX][7] instanceof Rook){
                    Rook rook = (Rook) board[currentX][7];
                    if(!rook.isDoneFirstMove() && board[currentX][6] == null && board[currentX][5] == null){
                        System.out.println("castle a");
                        return true;
                    }
                }
                if(currentY-newY == 2 && board[currentX][0] instanceof Rook){
                    Rook rook = (Rook) board[currentX][0];
                    if(!rook.isDoneFirstMove() && board[currentX][1] == null && board[currentX][2] == null && board[currentX][3] == null){
                        System.out.println("castle b");
                        return true;
                    }
                }

        } else{
            if(isPieceOnTile == null || isPieceOnTile.getPieceType() != type){
                return true;
            }
        }
        return false;
    }


}
