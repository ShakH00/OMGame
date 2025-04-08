/**
 * A chess game which controls and puts everything together.
 * This is the true controller of the game. The only other class that interacts with this is ChessController
 * ChessController simply initializes this class and starts the game. This class is what does the heavy lifting
 * It initializes the board and takes care of all movement and checks
 *
 * @author Abdulrahman Negmeldin, Yousif Bedair
 */
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
    private int score1;
    private int score2;
    // Leaderboard Statistics
    private int p1Turns = 0;
    private int p2Turns = 0;
    private int p1Captures = 0;
    private int p2Captures = 0;

    /**
     * Constructor to initiate a chess game
     * @author Abdulrahman Negmeldin
     */
    public Chess(){
        this.player1 = new Player();
        this.player2 = new Player();
        this.score1 = 0;
        this.score2 = 0;
    }

    /**
     * initiate the board, fill it in with pieces
     * @param board: the board being initialized
     * @author Abdulrahman Negmeldin
     */
    public void setBoard(Board board){
        this.board = board;
        board.fillChessBoard(GameType.CHESS, player1, player2);
    }

    /**
     * Start the game, sets gameState to P1_TURN
     * @author Abdulrahman Negmeldin
     */
    public void start(){
        gameState = GameState.P1_TURN;
    }

    /**
     *
     * @return gameState
     */
    public GameState getState(){
        return gameState;
    }

    /**
     * Switch the turn of who is meant to play
     * @author Abdulrahman Negmeldin
     */
    public void switchTurn() {
        if (gameState == GameState.P1_TURN){
            gameState = GameState.P2_TURN;
        }
        else if (gameState == GameState.P2_TURN){
            gameState = GameState.P1_TURN;
        }
    }

    /**
     * Move a piece to certain x and y coordinates given from the ChessController
     * @param piece: Piece being moved
     * @param x: new x coordinate
     * @param y: new y coordinate
     * @author Abdulrahman Negmeldin
     */
    public void move(MovingPiece piece, int x, int y) {
        PieceType type = piece.getPieceType();
        boolean isCorrectTurn = (type.equals(PieceType.LIGHT) && getState() == GameState.P1_TURN) ||
                (type.equals(PieceType.DARK) && getState() == GameState.P2_TURN);

        if (!isCorrectTurn) return;

        Player player = piece.getOwnedBy();
        Piece[][] state = board.getBoardState();
        int oldX = piece.getX();
        int oldY = piece.getY();

        Piece captured = state[x][y];

        // Simulate the move
        state[oldX][oldY] = null;
        state[x][y] = piece;
        piece.setX(x);
        piece.setY(y);

        boolean kingStillInCheck = isKingInCheck(player);

        // Undo the move
        state[oldX][oldY] = piece;
        state[x][y] = captured;
        piece.setX(oldX);
        piece.setY(oldY);

        // If the move doesn't help escape check, deny the move
        if (kingStillInCheck) return;

        // Actually perform the move

        Piece potentialEnemy = board.getBoardState()[x][y];
        //need to account for en passant somehow
        if (piece.move(x, y, board)) {
            if(piece.getOwnedBy() == this.player1){
                addp1Turn();
                if(potentialEnemy != null && potentialEnemy instanceof MovingPiece) addp1Score(potentialEnemy.getScore());
            } else if(piece.getOwnedBy() == this.player2){
                addp2Turn();
                if(potentialEnemy != null && potentialEnemy instanceof MovingPiece) addp1Score(potentialEnemy.getScore());
            }
            switchTurn();
            checkWinCondition();
        }
    }

    private void getStats(){
        System.out.println("Game Statistics: ");
        System.out.println("Player 1 Turns: " + getP1Turns() + ", Captures: " + getP1Captures() + ", Capture Score: " + getScore1());
        System.out.println("Player 2 Turns: " + getP2Turns() + ", Captures: " + getP2Captures() + ", Capture Score: " + getScore2());
    }

    private int getP1Captures(){
        return this.p1Captures;
    }

    private int getP2Captures(){
        return this.p2Captures;
    }

    private int getP1Turns(){
        return this.p1Turns;
    }

    private int getP2Turns(){
        return this.p2Turns;
    }

    private void addp1Score(int score){
        this.score1 += score;
        this.p1Captures++;
    }

    private void addp2Score(int score){
        this.score2 += score;
        this.p2Captures++;
    }

    private void addp1Turn() {
        this.p1Turns++;
    }

    private void addp2Turn() {
        this.p2Turns++;
    }


    /**
     * Method to check if the king moving to a spot is safe
     * @param king: King being moved
     * @param x: new x coordinate
     * @param y: new y coordinate
     * @return true if movement is safe
     */
    private boolean isMoveSafeForKing(King king, int x, int y) {
        Piece[][] state = board.getBoardState();

        int oldX = king.getX();
        int oldY = king.getY();
        Piece captured = state[x][y];

        //simulate movement
        state[oldX][oldY] = null;
        state[x][y] = king;
        king.setX(x);
        king.setY(y);

        boolean inCheck = isKingInCheck(king.getOwnedBy()); //check if this puts king in check

        //undo simulation because movement never actually happened
        state[oldX][oldY] = king;
        state[x][y] = captured;
        king.setX(oldX);
        king.setY(oldY);

        return !inCheck; //return
    }

    /**
     * Method to check if a piece is pinned in place due to possible discovered check
     * Does this by removing the piece from the board and checking if king would be in check during piece absence
     * @param piece: Piece being checked for being pinned
     * @return true if piece is pinned
     * @author Yousif Bedair, modified by Abdulrahman Negmeldin
     */
    public boolean isPiecePinned(MovingPiece piece) {
        Player owner = piece.getOwnedBy();
        int originalX = piece.getX();
        int originalY = piece.getY();
        Piece[][] state = board.getBoardState();

        //find the king
        King king = wheresKing(owner);

        //loop through moves for piece
        for (int x = 0; x < board.getRows(); x++) {
            for (int y = 0; y < board.getCols(); y++) {
                //skip the current position and check if the move is valid
                if ((x == originalX && y == originalY) || !piece.isValidMove(x, y, board)) continue;

                Piece captured = state[x][y];

                //simulate the move, undone later obviously
                state[originalX][originalY] = null;
                state[x][y] = piece;
                piece.setX(x);
                piece.setY(y);

                // Check if the king is in check after the move
                boolean stillInCheck = isKingInCheck(owner);

                // Undo the move
                state[originalX][originalY] = piece;
                state[x][y] = captured;
                piece.setX(originalX);
                piece.setY(originalY);

                // If moving this piece would not expose the king to check, it's not pinned
                if (stillInCheck) {
                    return true;
                }
            }
        }

        // If no valid move would prevent the king from being in check, the piece is pinned
        return false;
    }




    /**
     * Find the king owned by the player
     * Loops through board to find the king
     * @param player: Player whose king is being looked for
     * @return the king owned by player
     * @author Yousif Bedair, modified by Abdulrahman Negmeldin
     */
    public King wheresKing(Player player){
        Piece[][] state = board.getBoardState();
        //simple for loop to find king
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                MovingPiece p = (MovingPiece) state[i][j];
                if (p instanceof King && p.getOwnedBy().equals(player)) {
                    return (King) p;
                }
            }
        }
        System.out.println("KING NULL!");
        return null;
    }

    /**
     * Method to return a list of all possible moves for a piece
     * Used with GUI to show tiles you can move to
     * @param piece: Piece that legal moves are being checked for
     * @return int[] list of tile coordinates
     * @author Yousif Bedair
     */
    public List<int[]> getLegalMoves(MovingPiece piece) {
        List<int[]> legalMoves = new ArrayList<>();
        if (piece == null) return legalMoves;

        int originalX = piece.getX();
        int originalY= piece.getY();
        for (int x = 0; x < board.getRows(); x++) {
            for (int y = 0; y < board.getCols(); y++) {
               if (piece.isValidMove(x,y,board) && !isPiecePinned(piece)){
                   legalMoves.add(new int[]{x,y});
                   piece.move(originalX,originalY,board);
               }
            }
        }

        return legalMoves;
    }

    /**
     * search the board to check if the king is placed in check or not
     * @param player: Player who owns the king
     * @return true if king is in check
     * @author Yousif Bedair, modified by Abdulrahman Negmeldin
     */
    public boolean isKingInCheck(Player player) {
        Piece[][] state = board.getBoardState();
        King king = wheresKing(player);

        //should never be null but just in case
        if (king == null) {
            return false;  // King not found, no check possible
        }

        int kingX = king.getX();
        int kingY = king.getY();

        //check all pieces of the opponent
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Piece p = state[row][col];

                if (p == null || p.getOwnedBy() == player) continue;  // Skip own pieces

                if (p instanceof MovingPiece) {
                    MovingPiece mp = (MovingPiece) p;
                    if (mp.isValidMove(kingX, kingY, board)) {
                        // Check if the piece can attack the king directly
                        return true;
                    }
                }
            }
        }
        return false;  // No opponent can attack the king
    }


    /**
     * Method to check if a player has won by checkmate
     * @author Yousif Bedair
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
     * Check if there is a checkmate
     * This method works by checking for all reasons why a checkmate is NOT there
     * This means it checks using proof by contradiction
     * Hence it returns true at the end because if it reaches end then it means there is a checkmate
     * @param player: Player who could've been checkmated and lost
     * @return true if player has been checkmated by enemy
     * @author Yousif Bedair, modified by Abdulrahman Negmeldin
     */
    public boolean isCheckmate(Player player) {
        //if the king is not in check, it's not checkmate
        if (!isKingInCheck(player)) return false;

        Piece[][] state = board.getBoardState();

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Piece piece = state[i][j];

                if (piece == null || piece.getOwnedBy() != player || !(piece instanceof MovingPiece)) continue;

                MovingPiece mp = (MovingPiece) piece;

                // Check all possible moves for this piece
                for (int x = 0; x < board.getRows(); x++) {
                    for (int y = 0; y < board.getCols(); y++) {
                        if (!mp.isValidMove(x, y, board)) continue;

                        // Save the current state of the board
                        Piece captured = state[x][y];
                        int originalX = mp.getX();
                        int originalY = mp.getY();

                        // Simulate the move
                        state[originalX][originalY] = null;
                        state[x][y] = mp;
                        mp.setX(x);
                        mp.setY(y);

                        // Check if this move would get the king out of check
                        boolean stillInCheck = isKingInCheck(player);

                        // Undo the move
                        state[originalX][originalY] = mp;
                        state[x][y] = captured;
                        mp.setX(originalX);
                        mp.setY(originalY);

                        // If the king is not in check after the move, it's not checkmate
                        if (!stillInCheck) {
                            return false;  // The player has a move that gets them out of check
                        }
                    }
                }
            }
        }

        // If no move prevents check, it's checkmate
        return true;
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
            getStats();
        } else if (gameState.equals(GameState.P1_WIN)){
            System.out.println("Player 1 wins!");
            getStats();
        }
    }

    public void drawGame()
    {
        gameState = GameState.DRAW;
    }

    public Player getPlayer1(){
        return this.player1;
    }
    public Player getPlayer2(){
        return this.player2;
    }


    public Board getBoard() {
        return board;
    }


    public int getScore1(){
        return this.score1;
    }

    public int getScore2(){
        return this.score2;
    }

    public void setScore1(int score1){
        this.score1 = score1;
    }

    public void setGameState(int score2){
        this.score2 = score2;
    }
}
