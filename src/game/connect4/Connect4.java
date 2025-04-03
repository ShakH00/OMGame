package game.connect4;

import game.*;
import game.pieces.Piece;
import game.pieces.PieceType;

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

    @Override
    protected void move(Piece piece) {

    }

    @Override
    protected void checkWinCondition()
    {
        // Check for wins in rows
        for(int row = 0; row < board.getRows(); row++)
        {
            if(gameState == GameState.P1_TURN)
            {
                if(winInRow(row, piece1))
                {
                    gameState = GameState.P1_WIN;
                }
            }
            else if(gameState == GameState.P2_TURN)
            {
                if(winInRow(row, piece2))
                {
                    gameState = GameState.P2_WIN;
                }
            }
        }

        // Check for wins in columns
        for(int col = 0; col < board.getCols(); col++)
        {
            if(gameState == GameState.P1_TURN)
            {
                if(winInCol(col, piece1))
                {
                    gameState = GameState.P1_WIN;
                }
            }
            else if(gameState == GameState.P2_TURN)
            {
                if(winInCol(col, piece2))
                {
                    gameState = GameState.P2_WIN;
                }
            }
        }

        // Check for wins in diagonals
        if(gameState == GameState.P1_TURN)
        {
            if(winInDiagonalBackslash(piece1))
            {
                gameState = GameState.P1_WIN;
            }
        }
        else if(gameState == GameState.P2_TURN)
        {
            if(winInDiagonalBackslash(piece2))
            {
                gameState = GameState.P2_WIN;
            }
        }

        if(gameState == GameState.P1_TURN)
        {
            if(winInDiagonalFrontslash(piece1))
            {
                gameState = GameState.P1_WIN;
            }
        }
        else if(gameState == GameState.P2_TURN)
        {
            if(winInDiagonalFrontslash(piece2))
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
    public boolean winInRow(int row, Piece piece)
    {
        int counter = 0;

        for(int col = 0; col < board.getCols(); col++)
        {
            if(board.getBoardState()[row][col] == piece)
            {
                counter++;
            }
            else if(counter < 4)
            {
                counter = 0;
            }
        }

        if(counter >= 4)
        {
            return true;
        }

        return false;
    }

    // Check for win in given column
    public boolean winInCol(int col, Piece piece)
    {
        int counter = 0;
        for (int row = 0; row < board.getRows(); row++)
        {
            Piece current = board.getBoardState()[row][col];
            if (current != null && current.getColour().equals(piece.getColour()))
            {
                counter++;
                if (counter >= 4) return true;
            } else
            {
                counter = 0;
            }
        }
        return false;
    }

    // Check for win in a backslash diagonal
    public boolean winInDiagonalBackslash(Piece piece)
    {
        Piece[][] state = board.getBoardState();
        int rows = board.getRows();
        int cols = board.getCols();
        for (int row = 0; row <= rows - 4; row++)   // Loop through the rows except the last 4 since we're checking diagonal
        {
            for (int col = 0; col <= cols - 4; col++) // Loop through columns except the last 4
            {
                boolean match = true;
                for (int i = 0; i < 4; i++)
                {
                    Piece current = state[row + i][col + i];
                    if (current == null || !current.getColour().equals(piece.getColour()))
                    {
                        match = false;
                        break;
                    }
                }
                if (match) return true;
            }
        }
        return false;
    }

    // Check for win in a frontslash diagonal
    public boolean winInDiagonalFrontslash(Piece piece)
    {
        Piece[][] state = board.getBoardState();
        int rows = board.getRows();
        int cols = board.getCols();

        for (int row = 3; row < rows; row++)
        {
            for (int col = 0; col <= cols - 4; col++)
            {
                boolean match = true;
                for (int i = 0; i < 4; i++)
                {
                    Piece current = state[row - i][col + i];
                    if (current == null || !current.getColour().equals(piece.getColour()))
                    {
                        match = false;
                        break;
                    }
                }
                if (match) return true;
            }
        }
        return false;
    }

    // game.Player surrenders and gives other player the win
    @Override
    protected void surrender()
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
    @Override
    protected void matchOutcome() {

    }
}
