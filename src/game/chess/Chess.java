package game.chess;

import game.*;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chess extends Game {
    private final Player player1;
    private final Player player2;
    private GameState gameState;
    private King whiteKing;
    private King blackKing;

    /**
     * Constructor to initiate a chess game
     * @author Abdulrahman Negmeldin
     */
    public Chess(){
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public void setBoard(Board board){
        this.board = board;
        board.fillChessBoard(GameType.CHESS, player1, player2);
    }

    public void start(){
        gameState = GameState.P1_TURN;
    }

    public GameState getState(){
        return gameState;
    }

    public void switchTurn() {
        if (gameState == GameState.P1_TURN){
            gameState = GameState.P2_TURN;
        }
        else if (gameState == GameState.P2_TURN){
            gameState = GameState.P1_TURN;
        }
    }

    public void move(MovingPiece piece, int x, int y) {
        PieceType type = piece.getPieceType();
        boolean isCorrectTurn = (type.equals(PieceType.LIGHT) && getState() == GameState.P1_TURN) ||
                (type.equals(PieceType.DARK) && getState() == GameState.P2_TURN);

        if (!isCorrectTurn) return;

        if (piece instanceof King) {
            if (!isMoveSafeForKing((King) piece, x, y)) return;
        } else {
            if (isPiecePinned(piece)) return;
        }

        boolean result = piece.move(x, y, board);
        if (result) switchTurn();
    }

    private boolean isMoveSafeForKing(King king, int x, int y) {
        Piece[][] state = board.getBoardState();

        int oldX = king.getX();
        int oldY = king.getY();
        Piece captured = state[x][y];

        // simulate
        state[oldX][oldY] = null;
        state[x][y] = king;
        king.setX(x);
        king.setY(y);

        boolean inCheck = isKingInCheck(king.getOwnedBy());

        // undo
        state[oldX][oldY] = king;
        state[x][y] = captured;
        king.setX(oldX);
        king.setY(oldY);

        return !inCheck;
    }

    /**
     * @author YOUSIF BEDAIR
     * Removes the given piece and checks if its absence would result in a check
     * @return true or false
     */
    public boolean isPiecePinned(MovingPiece piece){
        //check if moving the piece off its current tile puts that piece's king in check
        Player owner = piece.getOwnedBy();
        int x = piece.getX();
        int y = piece.getY();
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
                MovingPiece p = (MovingPiece) state[i][j];
                if (p instanceof King && p.getOwnedBy().equals(player)) {
                    System.out.println("Found king at " + i + "," + j + " owned by " + p.getOwnedBy());
                    System.out.println("Equal to player? " + p.getOwnedBy().equals(player));
                    return (King) p;
                }
            }
        }
        System.out.println("KING NULL!");
        return null;
    }
    public List<int[]> getLegalMoves(MovingPiece piece) {
        List<int[]> legalMoves = new ArrayList<>();
        if (piece == null) return legalMoves;

        Player player = piece.getOwnedBy();
        int originalX = piece.getX();
        int originalY = piece.getY();
        Piece[][] state = board.getBoardState();

        for (int x = 0; x < board.getRows(); x++) {
            for (int y = 0; y < board.getCols(); y++) {
                if (!piece.canMoveTo(x, y, board)) continue;
                // Save the state
                Piece captured = state[x][y];
                // Simulate the move
                state[originalX][originalY] = null;
                state[x][y] = piece;
                piece.setX(x);
                piece.setY(y);
                // Check if it causes a check
                boolean causesCheck = isKingInCheck(player);
                // Undo the move
                state[originalX][originalY] = piece;
                state[x][y] = captured;
                piece.setX(originalX);
                piece.setY(originalY);
                if (!causesCheck) {
                    legalMoves.add(new int[]{x, y});
                }
            }
        }

        return legalMoves;
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
        if(king == null){
            System.out.println("Issue");
            return false;
        }
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
        if(isCheckmate(player1)){
            gameState = GameState.P2_WIN;
            matchOutcome();
        } else if(isCheckmate(player2)){
            gameState = GameState.P1_WIN;
            matchOutcome();
        }
    }

    /**
     * author YOUSIF BEDAIR
     * Checks if there is a checkmate for a given player by simulating all possible moves.
     * @param player
     * @return true/false
     */
    public boolean isCheckmate(Player player) {
        System.out.println(player == player1);
        System.out.println(player == player2);
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
        if(gameState == GameState.P1_TURN)
        {
            gameState = GameState.P2_WIN;
        }
        else if(gameState == GameState.P2_TURN)
        {
            gameState = GameState.P1_WIN;
        }
    }

    /**
     * updates gameState based on the match outcome
     */
    public void matchOutcome() {
        // Incomplete, still needs to check for stalemate and surrender.
        if (gameState.equals(GameState.P2_WIN)){
            System.out.println("Player 2 wins!");
        } else if (gameState.equals(GameState.P1_WIN)){
            System.out.println("Player 1 wins!");
        }
    }

    public void drawGame()
    {
        gameState = GameState.DRAW;
    }
}
