public class Board {
    private Piece[][] board;
    private int rows;
    private int cols;
    private String gameType;
    public Board(String gameType)
    {
        this.gameType = gameType;

        if(gameType.equalsIgnoreCase("Chess") || gameType.equalsIgnoreCase("Checkers"))
        {
            rows = 8;
            cols = 8;
        }
        else if(gameType.equalsIgnoreCase("TicTacToe"))
        {
            rows = 3;
            cols = 3;
        }
        else if(gameType.equalsIgnoreCase("Connect4"))
        {
            rows = 6;
            cols = 7;
        }

        board = new Piece[rows][cols];
    }
}
