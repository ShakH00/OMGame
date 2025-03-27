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
    
    public void move(Piece piece, int newX, int newY){
        int currentX = Piece.x;
        int currentY = Piece.y;

        Piece.x = newX;
        Piece.y = newY;
    
    }

    public bool isValidMove(Piece piece){

        return false;
    }

    public void jumpMove(Piece piece, Array[][] board){

    }

    public void checkWinCondition(GameState gameState){

    }

    public void surrender(){

    }

    public void matchOutcome(){

    }
}