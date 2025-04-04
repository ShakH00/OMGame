package game.chess;

import game.Board;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;

/**
 * A game.chess.King object class for the game of game.chess.Chess
 * As with other game.pieces, it tracks x, y coordinates, colour, player who owns it, and score
 * The score is set to -1 as a game.chess.King is the most valuable piece on the board
 * Hence, the -1 simply represents that it is valued at infinity. The king will never be eaten anyway
 * but rather would only ever get checkmated
 *
 * @author Abdulrahman
 */
public class King extends MovingPiece {
    private boolean firstMove;

    public King(int x, int y, String colour, PieceType pieceType, Player ownedBy){
        super(x, y, colour, pieceType, ownedBy, -1);
        this.firstMove = false;
    }

    public void setFirstMove(boolean firstMove){
        this.firstMove = firstMove;
    }

    public boolean getFirstMoveStatus(){
        return this.firstMove;
    }

    @Override
    protected void move(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        if(isValidMove(currentX, currentY, newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            this.setX(newX);
            this.setY(newY);
            board[newX][newY] = this;
            if(!firstMove){
                firstMove = true;
            }
        }
    }

    /**
     * A method to check if the tile the game.chess.King is being moved to is a valid move
     * Some notes on how the game.chess.King can be moved in game.chess.Chess:
     * 1. A king can only move one tile, in any direction.
     * 2. There is a special move however, castling, where, if the king hasn't moved once yet, and neither
     *      was one of the rooks, with absolutely no piece in between them to stop it, then the rook and king
     *      will effectively alternate positions ending up side by side, clearing the edge
     * A king cannot move into a tile if it would cause him to end up in check, or checkmate
     * @param currentX: current x coordinate of king's location
     * @param currentY: current y coordinate of king's location
     * @param newX: x coordinate of tile that the king might be moved to
     * @param newY: y coordinate of tile that the king might be moved to
     * @param gameBoard: board being played on
     * @return true if this is a valid move to perform for the king
     */
    @Override
    protected boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        boolean out = false;
        Piece[][] board = gameBoard.getBoardState();
        PieceType pieceType = this.getPieceType();
        if(board[newX][newY] == null){
            //need to check for if moving here might put king in check
            //need to check if trying to castle
            out = false;
        }
        return out;
    }


}
