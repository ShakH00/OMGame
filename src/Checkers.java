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

    public boolean isValidMove(Piece piece, int newX, int newY) {
        int currentX = piece.getX();
        int currentY = piece.getY();
    
        // Check if the destination is within bounds
        if (!board.isWithinBounds(newX, newY)) {
            return false;
        }
    
        // Check if the destination square is empty
        if (board.getPieceAt(newX, newY) != null) {
            return false;
        }
    
        // Check if the move is diagonal
        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);
        if (deltaX != deltaY || deltaX > 2) {
            return false;
        }
    
        // Check for a simple move (1 square diagonal)
        if (deltaX == 1) {
            return true;
        }
    
        // Check for a jump move (2 squares diagonal)
        if (deltaX == 2) {
            int midX = (currentX + newX) / 2;
            int midY = (currentY + newY) / 2;
            Piece midPiece = board.getPieceAt(midX, midY);
    
            // Ensure there's an opponent's piece to jump over
            if (midPiece != null && midPiece.getPlayer() != piece.getPlayer()) {
                return true;
            }
        }
    
        return false;
    }

    public void jumpMove(Piece piece, Board board){

    }

    public void checkWinCondition(GameState gameState){

    }

    public void surrender(){

    }

    public void matchOutcome(){

    }
}