/**
 * A King object class for the game of Chess
 * As with other pieces, it tracks x, y coordinates, colour, player who owns it, and score
 * The score is set to -1 as a King is the most valuable piece on the board
 * Hence, the -1 simply represents that it is valued at infinity. The king will never be eaten anyway
 * but rather would only ever get checkmated
 *
 * @author Abdulrahman
 */
public class King extends MovingPiece{
    private boolean firstMove;

    public King(int x, int y, String colour, Player ownedBy, int score){
        super(x, y, colour, ownedBy, -1);
        this.firstMove = false;
    }

    public void setFirstMove(boolean firstMove){
        this.firstMove = firstMove;
    }

    public boolean getFirstMoveStatus(){
        return this.firstMove;
    }

    @Override
    void move(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        if(isValidMove(currentX, currentY, newX, newY, gameBoard)){
            Piece[][] board = gameBoard.getBoardState();
            board[currentX][currentY] = null;
            setX(newX);
            setY(newY);
            board[newX][newY] = this;
        }
    }

    @Override
    boolean isValidMove(int currentX, int currentY, int newX, int newY, Board gameBoard) {
        return false;
    }


}
