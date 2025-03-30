/**
 * Abstract MovingPiece class which is inherited by CheckersPiece and every Chess piece object
 * This class inherits Piece by default but addons two methods that should be overridden within CheckersPiece,
 * and within any Chess piece object class. These methods are 'move' and 'isValidMove' which are used to do
 * exactly what it sounds like, move the piece and check if a certain move is valid to perform.
 *
 * The reason these two methods are present in every Chess piece object and CheckersPiece yet overridden are due
 * to the variety of movement depending on the piece (i.e. a pawn moves differently than a queen)
 *
 * @author Abdulrahman
 */
abstract class MovingPiece extends Piece{

    public MovingPiece(int x, int y, String colour, Player ownedBy, int score){
        super(x, y, colour, ownedBy, score);
    }

    abstract void move(int currentX, int currentY, int newX, int newY, Board gameBoard);
    abstract boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard);

}
