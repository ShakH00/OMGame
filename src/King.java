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
