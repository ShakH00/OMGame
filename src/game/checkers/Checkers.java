/**
 * Class that envelops the checkers game. 
 * Utilizes CheckersPiece.java, Board.Java primarily 
 * @author Adam, Abdu, Charls
 * */
package game.checkers;

import game.*;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

import java.util.concurrent.LinkedTransferQueue;

public class Checkers extends Game {
    private Player player1;
    private Player player2;
    public Piece selectedPiece;
    private Board board;
    private GameState gameState;

    public Checkers() { //Constructor to initialize player 1 and 2
        this.player1 = new Player();
        this.player2 = new Player();
    }

    // Leaderboard Statistics
    private int p1Turns = 0;
    private int p2Turns = 0;
    private int p1Captures = 0;
    private int p2Captures = 0;
    private int p1MultiCaptures = 0;
    private int p2MultiCaptures = 0;

    // Set Board for checkers game and fill it
    public void setBoard(Board board){
        this.board = board;
        board.fillBoard(GameType.CHECKERS);
    }

    /**
     * Method to move a piece to a new x and y coordinate
     * @param piece: piece being moved.
     * @param newX: new x coordinate
     * @param newY: new y coordinate
     */
    public void move(CheckersPiece piece, int newX, int newY){
        int currentX = piece.getX();
        int currentY = piece.getY();
        boolean captureMove = Math.abs(currentX-newX) == 2;

        // If the move is not a capture move but a forced capture must be made then print string and return nothing.
        if (!captureMove && forcedCapture(piece.getOwnedBy())) {
            System.out.println("A capture is available and must be performed.");
            return;
        }

        // If valid moves, make the move.
        if (isValidMove(currentX, currentY, newX, newY, board)){
            Piece[][] boardState = board.getBoardState();
            // Place piece in new location.
            boardState[newX][newY] = piece;
            // remove prev piece location
            boardState[currentX][currentY] = null;
            // update the piece's location.
            piece.setX(newX);
            piece.setY(newY);

            // Take count of turns made by players.
            // If player1 made the turn increase p1Turns by 1.
            if (piece.getColor().equals(Color.BLACK)){
                p1Turns++;
            }
            else {
                // Else player2 made the turn so increase p2Turns by 1.
                p2Turns++;
            }

            // Capture move.
            // If the move made is 2 cells it is a capture move.
            if (captureMove){
                // Get the location of the captured piece.
                int capturedX = (currentX + newX) / 2;
                int capturedY = (currentY + newY) / 2;
                // remove the captured piece.
                boardState[capturedX][capturedY] = null;

                // Take count of captures made by players.
                // If player1 made the capture increase p1Captures by 1.
                if (piece.getColor().equals(Color.BLACK)){
                    p1Captures++;
                }
                else {
                // Else player2 made the capture so increase p2Captures by 1.
                    p2Captures++;
                }
            }

            // game.chess.King promotion, assuming black is p1 and red is p2
            if ((piece.getColor().equals(Color.BLACK) && newX == 7) || (piece.getColor().equals(Color.WHITE) && newX == 0)){
                // Promote IF the piece has reached the other end.
                piece.promote();
            }

            // Multiple captures available
            if (captureMove && canCapture(piece)){
                // Take count of multi captures made by players.
                // If player1 is the one making the multi capture then increase p1MultiCaptures by 1.
                if (piece.getColor().equals(Color.BLACK)){
                    p1MultiCaptures++;
                }
                else {
                // Else player2 is the one making the multi capture so increase p2MultiCaptures by 1.
                    p2MultiCaptures++;
                }

                System.out.println("Continuous jumps are available, continue capturing.");
            }
            else {
                switchTurn();

            }

      }
    }

    /**
     * Method to check the board and the player's piece to see if any capture is available.
     * Captures are forced in checkers.
     * @param currentPlayer: To check the piece owner
     * @return true if player has to capture.
     */
    public boolean forcedCapture (Player currentPlayer) {
        // Get the board
        Piece[][] boardState = board.getBoardState();
        // Iterate through the board
        for ( int i = 0; i < boardState.length; i++){
            for (int j = 0; j < boardState[i].length; j++){
                // Check for each cell
                Piece piece = boardState[i][j];
                // Check If not empty, or it's not the current player's piece
                if (piece != null && piece.getOwnedBy().equals(currentPlayer)){
                    if (canCapture((CheckersPiece) piece)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Helper method for forcedCapture method, checks if the piece owned by the player has any possible captures.
     * @param piece: The piece being checked
     * @return true if piece is able to capture.
     */
    private boolean canCapture(CheckersPiece piece) {
        int x = piece.getX();
        int y = piece.getY();
        // coordinates to calculate top left, top right, bottom left, bottom right captures.
        int[][] coordinates = { {-2, -2}, {2, -2}, {-2, 2}, {2, 2} };
        for (int[] diagonals : coordinates){
            // get new coordinate
            int newX = x + diagonals[0];
            int newY = y + diagonals[1];

            // if out of bounds, continue to check the other corners.
            if (newX < 0 || newX > 7 || newY < 0 || newY > 7) {
                continue;
            }
            // If the move is valid then can capture is true.
            if (isValidMove(x, y, newX, newY, board)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method validate movement of a piece to a new location.
     * @param currentX: current x coordinate of the piece
     * @param currentY: current y coordinate of the piece
     * @param newX: new x coordinate being moved to
     * @param newY: new y coordinate being moved to
     * @param gameBoard: board being played on
     * @return true if the selected piece can move to its new location.
     */
    public boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard){
        Piece[][] board = gameBoard.getBoardState();
        selectedPiece = board[currentX][currentY];

        // Player1 is trying to move a piece when not their turn
        if(selectedPiece.getPieceType().equals(PieceType.DARK) && gameState != GameState.P1_TURN){
            return false;
        }

        // Player2 is trying to move a piece when not their turn
        if(selectedPiece.getPieceType().equals(PieceType.LIGHT) && gameState != GameState.P2_TURN){
            return false;
        }

        // Check if move is within bounds
        if (newX < 0 || newX > 7 || newY < 0 || newY > 7) {
        return false;           // Move out of bounds
        }

        // Check destination is empty
        if(board[newX][newY] != null) {
            return false;       // There is a Piece in the new position
        }

        // using col and row diff to calculate directions.
        int rowDiff = Math.abs(currentX-newX);
        int colDiff = Math.abs(currentY-newY);

        /*
        * Check row validity. 
        * To move diagonal you must move same # of rows & cols.
        * Can max move 2 spaces if you are capturing.
        */  
        if(rowDiff > 2 || rowDiff != colDiff) {
            return false;       /*
                                * Piece is trying to jump more than 1 space
                                * Piece is not moving diagonally
                                */
        }

        /*
         * Check if King and king movement for Player 1
         * Assuming Player 1 is bottom of the board (7,0)
         */

        if (!selectedPiece.isKing()) {
            if (selectedPiece.getColor().equals(Color.BLACK) && newX <= currentX) {
                return false;
            }
            /*
             * Check if King and king movement for Player 2
             * Assuming Player 2 is top of board (0,0)
             */
            if (selectedPiece.getColor().equals(Color.WHITE) && newX >= currentX) {
                return false;
            }
        }



        // Since check above is constraints we know that if rowDiff == 1 the piece is only moving 1 tile.
        if(rowDiff == 1){
            return true;        // Piece is moving 1 space diagonally
        }

        // index of piece in between starting position and end of jump
        int intermediateRow = (currentX + newX)/2;
        int intermediateCol = (currentY + newY)/2;
        Piece intermediatePiece = board[intermediateRow][intermediateCol];

        /*
         * Verify that the selected piece can capture.
         * Check if a Piece exists in the intermediate square.
         * If there is an intermediate Piece and if the colour matches the selected piece fail.
         * */
        if(rowDiff == 2) {
            return (intermediatePiece != null) &&
                    !intermediatePiece.getColor().equals(selectedPiece.getColor());   // No Piece to capture
                                                                                        // Piece to Capture
        }
        return false;
    }

    /**
     * Method to initialize the GameState for P1 to make the first move.
     */
    public void start(){
        gameState = GameState.P1_TURN;
    }

    /**
     * Method for switching turn.
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
     * Method to checkWinCondition and update the GameState depending on which player has missing pieces.
     */
    public void checkWinCondition(){
        boolean Piece1Exists = false;
        boolean Piece2Exists = false;
        Piece[][] gameBoard = board.getBoardState();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = gameBoard[i][j];
                if (piece != null){
                    if (piece.getColor().equals(Color.BLACK)){
                        Piece1Exists = true;
                    }
                    if (piece.getColor().equals(Color.WHITE)){
                        Piece2Exists = true;
                    }
                }
            }
        }
        if (!Piece1Exists){
            gameState = GameState.P2_WIN;
        }
        else if (!Piece2Exists){
            gameState = GameState.P1_WIN;
        }
    }


    /**
     * surrender method that changes the gameState to the opposing player winning if a player calls it during their turn.
     * Called with a button in controller.
     */
    public void surrender(){
        if (gameState == GameState.P1_TURN){
            gameState = GameState.P2_WIN;
        } else if (gameState == GameState.P2_TURN) {
            gameState = GameState.P1_WIN;
        }
    }

    /**
     * draw method that changes Game State to draw.
     */
    public void draw(){
        gameState = GameState.DRAW;
    }

    /**
     * matchOutcome method checks the state of the game and sends out a string to inform the status of the game.
     */
    public void matchOutcome(){
        if(gameState == GameState.P1_WIN){
            System.out.println("Player 1 has won the game!");
            displayMatchStatistics();
        }
        else if(gameState == GameState.P2_WIN) {
            System.out.println("Player 2 has won the game!");
            displayMatchStatistics();
        }
        else if (gameState == GameState.DRAW) {
            System.out.println("Both players have agreed to a draw!");
            displayMatchStatistics();
        }
        else{
            System.out.println("The game is ongoing");
        }
    }

    public Board getBoard() {
        return board;
    }


    /**
     * getGameState getter method.
     * @return current gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * getter method to get turns made by P1
     * @return Turns made by player 1 in int.
     */
    public int getP1Turns() {
        return p1Turns;
    }

    /**
     * getter method to get turns made by P2
     * @return Turns made by player 2 in int.
     */
    public int getP2Turns() {
        return p2Turns;
    }

    /**
     * getter method to get captures made by P1
     * @return captures made by player 1 in int.
     */
    public int getP1Captures() {
        return p1Captures;
    }


    /**
     * getter method to get captures made by P2
     * @return captures made by player 2 in int.
     */
    public int getP2Captures() {
        return p2Captures;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    /**
     * getter method to get multi captures made by P1
     * @return multi captures made by player 1 in int.
     */
    public int getP1MultiCaptures() {
        return p1MultiCaptures;
    }

    /**
     * getter method to get multi captures made by P2
     * @return multi captures made by player 2 in int.
     */
    public int getP2MultiCaptures() {
        return p2MultiCaptures;
    }

    public void displayMatchStatistics(){
        System.out.println("Game Statistics: ");
        System.out.println("Player 1 Turns: " + getP1Turns() + ", Captures: " + getP1Captures() + ", Multi-Captures: " + getP1MultiCaptures());
        System.out.println("Player 2 Turns: " + getP2Turns() + ", Captures: " + getP2Captures() + ", Multi-Captures: " + getP2MultiCaptures());
    }


}