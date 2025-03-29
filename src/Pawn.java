public class Pawn extends MovingPiece{

    public Pawn(int x, int y, String colour, Player ownedBy, int score){
        super(x, y, colour, ownedBy, 1);
    }

    @Override
    void move(int currentX, int currentY, int newX, int newY, Board board) {

    }

    @Override
    boolean isValidMove(int currentX, int currentY, int newX, int newY, Board board) {
        return false;
    }
}
