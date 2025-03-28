public class Checkers{
    private Player player1;
    private Player player2;
    private int score;
    private Piece[] capturedP1;
    private Piece[] captruedP2;
    private Piece selectedPiece;
    private Board board;
    private GameState gameState;
    private GameRules gameRules;
    
    public void move(CheckersPiece piece, int newX, int newY){
        int currentX = piece.getX();
        int currentY = piece.getY();

        piece.setX(newX);
        piece.setY(newY);
    }

    /**
     * [!] Should we consider a draw occurring when players repeat move? (TBD later)
     * 
     *
     */
    public boolean isValidMove(int x, int y, int newX, int newY, Board board){
        // Check if move is within bounds
        if(newX < 0 || newY > 7){
        return false;
        }

        // Check destination is empty
        if(board[newX][newY] != null) {
            reurn false;
        }

        // using col and row diff lets us do captures later. 
        int rowDiff = Math.abs(x-newX);
        int colDiff = Math.abs(y-newY);    
        /** 
        * Check row validity. 
        * To move diagonal you must move same # of rows & cols.
        * Can max move 2 spaces if you are capturing.
        */  
        if(rowDiff > 2 || rowDiff != colDiff) {
            return false; 
        }

        // Due to conditions above we know that colomn movement is good.
        if(rowDiff == 1){
            return true;
        }

    }

    public boolean isOccupied(int newX, int newY){
        return false;
    }

    /**
     * Check diagonal of the piece, check if enemy piece, check if the piece diagonal is empty,
     *
     */
    public void jumpMove(Piece piece, Board board){

    }

    public void checkDiagonal(){

    }

    /**
     * Check pieces on the board.
     * Piece 1 = Player 1, BLACK
     * Piece 2 = Player 2, RED
     * If !Piece1Exists then P2 Wins
     * If !Piece2Exists then P1 Wins.
     * [!!!] How do we decide colours? Let colour be red/black and GUI do the rest?
     * board.getCell(x,y) was personal changes since Board.java didnt have a way to fetch any cells.
     */
    public void checkWinCondition(){
        boolean Piece1Exists = false;
        boolean Piece2Exists = false;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = board.getCell(i, j);
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
     *
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
     *
     *
     */
    public void matchOutcome(){
        if(gameState == GameState.P1_WIN){
            System.out.println("Player 1 has won the game!");
        }
        else if(gameState == GameState.P2_WIN) {
            System.out.println("Player 2 has won the game!");
        }
        else{
            System.out.println("The game is ongoing");
        }
    }
}