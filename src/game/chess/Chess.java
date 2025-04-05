package game.chess;

import game.*;
import game.pieces.MovingPiece;
import game.pieces.Piece;

public class Chess extends Game {
    private Player player1;
    private Player player2;
    private GameState gameState;

    public Chess(){
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public void setBoard(Board board){
        this.board = board;
        board.fillBoard(GameType.CHESS);
    }

    public void start(){
        gameState = GameState.P1_TURN;
    }



    public void switchTurn() {
        if (gameState == GameState.P1_TURN){
            gameState = GameState.P2_TURN;
        }
        else if (gameState == GameState.P2_TURN){
            gameState = GameState.P1_TURN;
        }
    }

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

    /**
     * author: YOUSIF BEDAIR
     * checks if there is a checkmate, otherwise it goes backing to calling code
     */
    public void checkWinCondition() {
        if (isCheckmate(player1) || isCheckmate(player2)) {
            matchOutcome();
        }
    }

    /**
     * author YOUSIF BEDAIR
     * Checks if there is a checkmate for a given player by simulating all possible moves.
     * @param player
     * @return true/false
     */
    private boolean isCheckmate(Player player) {
        if (!isKingInCheck(player)) return false;

        Piece[][] state = board.getBoardState();

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Piece piece = state[i][j];

                if (piece == null || piece.getOwnedBy() != player || !(piece instanceof MovingPiece)) continue;

                MovingPiece mp = (MovingPiece) piece;

                for (int x = 0; x < board.getRows(); x++) {
                    for (int y = 0; y < board.getCols(); y++) {
                        if (!mp.canMoveTo(x, y, board)) continue;

                        // save board state
                        Piece captured = state[x][y];
                        int originalX = mp.getX();
                        int originalY = mp.getY();

                        // simulate move
                        state[originalX][originalY] = null;
                        state[x][y] = mp;
                        mp.setX(x);
                        mp.setY(y);

                        // check if king is still in check
                        boolean stillInCheck = isKingInCheck(player);

                        // undo move
                        state[originalX][originalY] = mp;
                        state[x][y] = captured;
                        mp.setX(originalX);
                        mp.setY(originalY);

                        if (!stillInCheck) {
                            return false; // player can get out of check
                        }
                    }
                }
            }
        }

        return true; // no legal move to escape check
    }

    public void surrender() {

    }

    /**
     * updates gameState based on the match outcome
     */
    public void matchOutcome() {
        // Incomplete, still needs to check for stalemate and surrender.
        if (isCheckmate(player1)){
            gameState = GameState.P2_WIN;
        } else if (isCheckmate(player2)){
            gameState = GameState.P1_WIN;
        }
    }
}
