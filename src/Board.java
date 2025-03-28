public class Board{
    private Piece[][] board;
    private int rows;
    private int cols;
    private GameType gameType;

    public Board(GameType gameType)
    {
        this.gameType = gameType;

        if(gameType == GameType.CHESS || gameType == GameType.CHECKERS)
        {
            rows = 8;
            cols = 8;
        }
        
        else if(gameType == GameType.TICTACTOE)
        {
            rows = 3;
            cols = 3;
        }

        else if(gameType == GameType.CONNECT4)
        {
            rows = 6;
            cols = 7;
        }

        board = new Piece[rows][cols];
    }

    public void displayBoard(){

    }

    public Piece[][] getBoardState(){
        return board;
    }

    /*
     * Will this just do a full wipe and set all values to null?
     * Or does this return the board to the original state of any game? ~ Adam
     */
    public void resetBoard(){
        
    }

    /* IGNORE THIS, FOR TICTACTOE

    public Piece getCell(int rows, int cols) {
        return board[rows][cols];
    }

    public void setCell(int rows, int cols, Piece value) {
        board[rows][cols] = value;
    }

    */
}
