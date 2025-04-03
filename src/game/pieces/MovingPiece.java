package game.pieces;

import game.Board;
import game.Player;
import javafx.scene.paint.Color;

/**
 * Abstract game.pieces.MovingPiece class which is inherited by game.checkers.CheckersPiece and every game.chess.Chess piece object
 * This class inherits game.pieces.Piece by default but addons two methods that should be overridden within game.checkers.CheckersPiece,
 * and within any game.chess.Chess piece object class. These methods are 'move' and 'isValidMove' which are used to do
 * exactly what it sounds like, move the piece and check if a certain move is valid to perform.
 *
 * The reason these two methods are present in every game.chess.Chess piece object and game.checkers.CheckersPiece yet overridden are due
 * to the variety of movement depending on the piece (i.e. a pawn moves differently than a queen)
 *
 * @author Abdulrahman
 */
public abstract class MovingPiece extends Piece{
    private int x;
    private int y;
    public MovingPiece(int x, int y, Color color, PieceType pieceType, Player ownedBy, int score){
        super(color, pieceType, ownedBy, score);
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    protected abstract void move(int currentX, int currentY, int newX, int newY, Board gameBoard);
    protected abstract boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard);

    // temp until we figure it out, added to access all chess pieces isValidMove() methods
    public boolean canMoveTo(int newX, int newY, Board board) {
        return isValidMove(this.getX(), this.getY(), newX, newY, board);
    }

}
