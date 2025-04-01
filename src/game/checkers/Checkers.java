package game.checkers;

import game.Board;
import game.GameRules;
import game.GameState;
import game.Player;
import game.pieces.Piece;

public class Checkers{
    private Player player1;
    private Player player2;
    private int score;
    private Piece[] capturedP1;
    private Piece[] capturedP2;
    private Piece selectedPiece;
    private Board board;
    private GameState gameState;
    private GameRules gameRules;
    
    public void move(CheckersPiece piece, int newX, int newY){
        int currentX = piece.getX();
        int currentY = piece.getY();

        // If valid moves, make the move.
        if (isValidMove(currentX, currentY, newX, newY, board)){
            Piece[][] boardState = board.getBoardState();
            // Place piece in new location.
            boardState[newX][newY] = piece;
            // remove prev piece location
            boardState[currentX][currentY] = null;
            piece.setX(newX);
            piece.setY(newY);

            // Capture move.
            // If the move made is 2 cells it is a capture move.
            if (Math.abs(currentX-newX) == 2){
                // Get the location of the captured piece.
                int capturedX = currentX + newX / 2;
                int capturedY = currentY + newY / 2;
                // remove the captured piece.
                boardState[capturedX][capturedY] = null;
            }

            // game.chess.King promotion, assuming black is p1 and red is p2
            if (piece.getColour().equals("BLACK") && newX == 7 || piece.getColour().equals("RED") && newX == 0){
                // Promote IF the piece has reached the other end.
                piece.promote();
            }
      }
    }

    /**
     * [!] Should we consider a draw occurring when players repeat move? (TBD later)
     * [!] NOT COMPLETE NEED TO CHECK CAPTURE VALUES ~ Adam
     * 
     */
    public boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard){
        Piece[][] board = gameBoard.getBoardState();
        selectedPiece = board[currentX][currentY];
        
        // Check if move is within bounds
        if(newX < 0 || newY > 7){
        return false;           // Move out of bounds
        }

        // Check destination is empty
        if(board[newX][newY] != null) {
            return false;       // There is a Piece in the new position
        }

        // using col and row diff to calculate directions.
        int rowDiff = Math.abs(currentX-newX);
        int colDiff = Math.abs(currentY-newY);

        /** 
        * Check row validity. 
        * To move diagonal you must move same # of rows & cols.
        * Can max move 2 spaces if you are capturing.
        */  
        if(rowDiff > 2 || rowDiff != colDiff) {
            return false;       /**
                                * Piece is trying to jump more than 1 space
                                * Piece is not moving diagonally
                                */
        }

        // Since check above is constraints we know that if rowDiff == 1 the piece is only moving 1 tile.
        if(rowDiff == 1){
            return true;        // Piece is moving 1 space diagonally
        }

        // index of piece inbetween starting position and end of jump
        int intermediateRow = (currentX + newX)/2;
        int intermediateCol = (currentY + newY)/2;
        Piece intermediatePiece = board[intermediateRow][intermediateCol];

        /**
         * Verify that the selected piece can capture.
         * Check if a Piece exists in the intermediate square.
         * If there is an intermediate Piece and if the colour matches the selected piece fail.
         * */ 
        if(rowDiff == 2) {
            if((intermediatePiece == null) || 
                intermediatePiece.getColour() == selectedPiece.getColour()){
                return false;   // No Piece to capture
            }
            return true;        // Piece to Capture
        }
        return false;
    }


    /**
     * Check game.pieces on the board.
     * Piece 1 = game.Player 1, BLACK
     * Piece 2 = game.Player 2, RED
     * If !Piece1Exists then P2 Wins
     * If !Piece2Exists then P1 Wins.
     * [!!!] How do we decide colours? Let colour be red/black and GUI do the rest?
     *
     */
    public void checkWinCondition(){
        boolean Piece1Exists = false;
        boolean Piece2Exists = false;
        Piece[][] gameBoard = board.getBoardState();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = gameBoard[i][j];
                if (piece != null){
                    if (piece.getColour().equals("BLACK")){
                        Piece1Exists = true;
                    }
                    if (piece.getColour().equals("RED")){
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
     *
     */
    public void surrender(){
        if (gameState == GameState.P1_TURN){
            gameState = GameState.P2_WIN;
        } else if (gameState == GameState.P2_TURN) {
            gameState = GameState.P1_WIN;
        }
    }

    /**
     * Match Outcome, checks the state of the game and depending on it,
     * will set out a line stating which player won or if the game is still ongoing otherwise.
     *
     */
    public void matchOutcome(){
        if(gameState == GameState.P1_WIN){
            System.out.println("game.Player 1 has won the game!");
        }
        else if(gameState == GameState.P2_WIN) {
            System.out.println("game.Player 2 has won the game!");
        }
        else{
            System.out.println("The game is ongoing");
        }
    }
}