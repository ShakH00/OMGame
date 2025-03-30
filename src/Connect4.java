public class Connect4 extends Game {
    public Connect4() {
        super.player1 = new Player();
        super.player2 = new Player();
        super.gameType = GameType.CONNECT4;
        super.board = new Board(gameType);
        super.score1 = 0;
        super.score2 = 0;
        super.gameState = GameState.SETUP;
        super.gameRules = new GameRules();
    }

    Connect4Piece piece1 = new Connect4Piece("red", PieceType.LIGHT, super.player1);
    Connect4Piece piece2 = new Connect4Piece("blue", PieceType.DARK, super.player2);

    void move(Piece piece) {

    }

    void checkWinCondition()
    {
        // Check for wins in rows
        for(int row = 0; row < board.getRows(); row++)
        {
            if(winInRow(row))
            {
                if(gameState == GameState.P1_TURN)
                {
                    gameState = GameState.P1_WIN;
                }
                else if(gameState == GameState.P2_TURN)
                {
                    gameState = GameState.P2_WIN;
                }
            }
        }

        // Check for wins in columns
        for(int col = 0; col < board.getCols(); col++)
        {
            if(winInCol(col))
            {
                if(gameState == GameState.P1_TURN)
                {
                    gameState = GameState.P1_WIN;
                }
                else if(gameState == GameState.P2_TURN)
                {
                    gameState = GameState.P2_WIN;
                }
            }
        }

        // Check for wins in diagonals
        if(winInDiagonalBackslash())
        {
            if(gameState == GameState.P1_TURN)
            {
                gameState = GameState.P1_WIN;
            }
            else if(gameState == GameState.P2_TURN)
            {
                gameState = GameState.P2_WIN;
            }
        }

        if(winInDiagonalFrontslash())
        {
            if(gameState == GameState.P1_TURN)
            {
                gameState = GameState.P1_WIN;
            }
            else if(gameState == GameState.P2_TURN)
            {
                gameState = GameState.P2_WIN;
            }
        }

        // Check for draw
        if(board.isFull())
        {
            gameState = GameState.DRAW;
        }
    }

    // Check for win in given row
    boolean winInRow(int row)
    {
        int counter = 0;
        if(gameState == GameState.P1_TURN)
        {
            for(int col = 0; col < board.getCols(); col++)
            {
                if(board.board[row][col] == piece1)
                {
                    counter++;
                }
                else if(counter != 4)
                {
                    counter = 0;
                }
            }

            if(counter >= 4)
            {
                return true;
            }
        }
        else if(gameState == GameState.P2_TURN)
        {
            for(int col = 0; col < board.getCols(); col++)
            {
                if(board.board[row][col] == piece2)
                {
                    counter++;
                }
                else if(counter != 4)
                {
                    counter = 0;
                }
            }

            if(counter >= 4)
            {
                return true;
            }
        }
        return false;
    }

    // Check for win in given column
    boolean winInCol(int col)
    {
        return false;
    }

    // Check for win in a backslash diagonal
    boolean winInDiagonalBackslash()
    {
        return false;
    }

    // Check for win in a frontslash diagonal
    boolean winInDiagonalFrontslash()
    {
        return false;
    }

    // Player surrenders and gives other player the win
    void surrender()
    {
        if(gameState == GameState.P1_TURN)
        {
            gameState = GameState.P2_WIN;
        }
        else if(gameState == GameState.P2_TURN)
        {
            gameState = GameState.P1_WIN;
        }
    }

    void matchOutcome() {

    }
}
