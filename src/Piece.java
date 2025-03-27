public class Piece {
    private int x;
    private int y;
    private String colour;
    private Player ownedBy;
    private int score;

}

public class CheckersPiece {
    private String colour;
    private boolean isKing;
    private int row;
    private int col;

    public checkersPiece(string colour, boolean isKing, int x, int y) {
        this.colour = colour;
        this.isKing = false;
        this.row = x;
        this.col = y;
    }

    public String getColour() {
        return colour;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isKing(){
        return isKing;
    }

    public void promote(){
        isKing = True;
    }

}
