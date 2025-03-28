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

    public boolean isValidMove(Piece piece){

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