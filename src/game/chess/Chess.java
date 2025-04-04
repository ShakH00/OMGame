package game.chess;

import game.Board;
import game.Game;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;

public class Chess extends Game {

    public void move(Piece piece) {

    }

    /**
     * @author YOUSIF BEDAIR
     * Removes the given piece and checks if its absence would result in a check
     * @return true or false
     */
    public boolean isPiecePinned(Piece piece){
        //check if moving the piece off its current tile puts that piece's king in check
        Player owner = piece.getOwnedBy();
        piece = (MovingPiece) piece;
        int x = ((MovingPiece) piece).getX();
        int y = ((MovingPiece) piece).getY();
        board.place(null, x, y);
        if (isKingInCheck(owner)){
            board.place(piece, x, y);
            return true;
        }
        else {
            board.place(piece, x, y);
            return false; //temporary
        }
    }

    /**
     * @author YOUSIF BEDAIR
     * @param player
     * @return King of provided player.
     */
    public King wheresKing(Player player){
        Piece[][] state = board.getBoardState();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                Piece p = state[i][j];
                if (p != null && p instanceof King && p.getOwnedBy() == player) {
                    return (King) p;
                }
            }
        }
        return null;
    }

    /**
     * @author YOUSIF BEDAIR
     * @param player
     * searches the whole board to check whether the king is in check
     * @return
     */
    public boolean isKingInCheck(Player player) {
        Piece[][] state = board.getBoardState();
        King king = wheresKing(player);

        int kingX = king.getX();
        int kingY = king.getY();
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Piece p = state[row][col];

                if (p == null || p.getOwnedBy() == player) continue;

                if (p instanceof MovingPiece) {
                    MovingPiece mp = (MovingPiece) p;
                    if (mp.canMoveTo(kingX, kingY, board)) {
                        return true; // enemy piece can attack the king
                    }
                }

            }
        }
        return false;
    }

    public void checkWinCondition() {

    }


    public void surrender() {

    }

    public void matchOutcome() {

    }
}
