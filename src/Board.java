public class Board {
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
}
