/**
 * A CheckersPiece class for all checker pieces (objects), inherits MovingPiece which inherits Piece.
 * Most of the piece's attributes are superclass-ed up to Piece
 * The CheckersPiece ovverrides two key methods present within MovingPiece: move, isValidMove.
 * This is done due to how a checkers piece moves differently than say a chess piece
 *
 * @author Abdu, Adam
 */
public class CheckersPiece extends MovingPiece {
    private boolean isKing; //boolean to track if the piece is a king or not

    /**
     * Constructor to create a Checkers Piece
     * This object inherits a MovingPiece which is an abstract object that inherits Piece
     *
     * @param x: the x value location, row, of the piece initially
     * @param y: the y value location, column, of the piece initially
     * @param colour: the colour of the piece, important for visual player distinction
     * @param ownedBy: the player who owns the piece
     * @param score: score of the piece once captured
     */
    public CheckersPiece(int x, int y, String colour, Player ownedBy, int score){
        super(x, y, colour, ownedBy, score);
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
     * @param newX: new x (row) location
     * @param newY: new y (column) location
     * @param board: board being played on
     */
    @Override
    public void move(int newX, int newY, Board board){
        //if this move is valid, perform it
        if(isValidMove(newX, newY, board)){
            setX(newX);
            setY(newY);
        }
    }

    /**
     * Check for a move's validity
     *
     * @param newX: X location to check
     * @param newY: Y location to check
     * @param board: board to check on
     * @return true if move is valid, false if invalid
     */
    @Override
    public boolean isValidMove(int newX, int newY, Board board){
        return false; //temporary
    }
}
