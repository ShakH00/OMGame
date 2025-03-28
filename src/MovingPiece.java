abstract class MovingPiece extends Piece{

    public MovingPiece(int x, int y, String colour, Player ownedBy, int score){
        super(x, y, colour, ownedBy, score);
    }

    abstract void move(int currentX, int currentY, int newX, int newY, Board board);
    abstract boolean isValidMove(int newX, int newY, Board board);

}
