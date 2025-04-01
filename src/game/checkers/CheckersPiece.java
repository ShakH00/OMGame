package game.checkers;
import game.Board;
import game.Player;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;

/**
 * A game.checkers.CheckersPiece class for all checker game.pieces (objects), inherits MovingPiece which inherits Piece.
 * Most of the piece's attributes are superclass-ed up to Piece
 * The game.checkers.CheckersPiece ovverrides two key methods present within MovingPiece: move, isValidMove.
 * This is done due to how a game.checkers piece moves differently than say a game.chess piece
 *
 * @author Abdulrahman, Adam
 */
public class CheckersPiece extends MovingPiece {
    private boolean isKing; //boolean to track if the piece is a king or not

    /**
     * Constructor to create a game.checkers.Checkers Piece
     * This object inherits a MovingPiece which is an abstract object that inherits Piece
     *
     * @param x: the x value location, row, of the piece initially
     * @param y: the y value location, column, of the piece initially
     * @param colour: the colour of the piece, important for visual player distinction
     * @param ownedBy: the player who owns the piece
     * @param score: score of the piece once captured
     */
    public CheckersPiece(int x, int y, String colour, PieceType pieceType, Player ownedBy, int score){
        super(x, y, colour, pieceType, ownedBy, score);
        this.isKing = false;
    }


    /**
     * isKing: check if the piece is a king
     * @return true if it is a king, false if not
     */
    public boolean isKing() {
        return this.isKing;
    }

    /**
     * Promotion method to turn piece into a king, allowing it to move backwards
     */
    public void promote() {
        this.isKing = true;
    }

    /**
     * Move a piece on the board
     *
     * @param currentX: current X location of piece
     * @param currentY: current Y location of piece
     * @param newX: new x (row) location
     * @param newY: new y (column) location
     * @param gameBoard: board being played on
     */
    @Override
    protected void move(int currentX, int currentY, int newX, int newY, Board gameBoard){
        //if this move is valid, perform it
        if(isValidMove(currentX, currentY, newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            setX(newX);
            setY(newY);
            board[newX][newY] = this;
        }
    }

    /**
     * Check for a move's validity
     *
     * @param currentX: current X location of piece
     * @param currentY: current Y location of piece
     * @param newX: X location to check
     * @param newY: Y location to check
     * @param gameBoard: board to check on
     * @return true if move is valid, false if invalid
     */
    @Override
    protected boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard){
        return false; //temporary
    }
}
