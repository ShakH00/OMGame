package game.tictactoe;

import game.*;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

import java.util.Random;

public class TicTacToe extends Game {
    public TicTacToePiece piece1;
    public TicTacToePiece piece2;

    public TicTacToe() {
        super.player1 = new Player();
        super.player2 = new Player();
        super.gameType = GameType.TICTACTOE;
        super.board = new Board(gameType);
        super.score1 = 0;
        super.score2 = 0;
        super.gameState = GameState.SETUP;
        super.gameRules = new GameRules();
        Random random = new Random();
        int randomNumber = random.nextInt(2);

        if(randomNumber == 0)
        {
            piece1 = new TicTacToePiece(Color.RED, PieceType.LIGHT, super.player1);
            piece2 = new TicTacToePiece(Color.GOLD, PieceType.DARK, super.player2);
        }
        else
        {
            piece1 = new TicTacToePiece(Color.RED, PieceType.DARK, super.player1);
            piece2 = new TicTacToePiece(Color.GOLD, PieceType.LIGHT, super.player2);
        }
    }

    public void move(Piece piece, int row, int col) {
        if(row >= 0 && row < board.getRows())
        {
            if (col >= 0 && col < board.getCols()) {
                if (board.getBoardState()[row][col] == null) {
                    board.place(piece, row, col);
                    checkWinCondition();
                }
            }
        }
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
        if(gameState != GameState.P1_WIN && gameState != GameState.P2_WIN && gameState != GameState.DRAW)
        {
            if(board.isFull())
            {
                gameState = GameState.DRAW;
            }
        }
    }

    // Check for win in given row
    public boolean winInRow(int row, Piece piece)
    {
        if(board.getBoardState()[row][0] == null || board.getBoardState()[row][1] == null || board.getBoardState()[row][2] == null)
            return false;
        return board.getBoardState()[row][0].equals(piece) && board.getBoardState()[row][1].equals(piece) && board.getBoardState()[row][2].equals(piece);
    }

    // Check for win in given column
    public boolean winInCol(int col, Piece piece)
    {
        if(board.getBoardState()[0][col] == null || board.getBoardState()[1][col] == null || board.getBoardState()[2][col] == null)
            return false;
        return board.getBoardState()[0][col].equals(piece) && board.getBoardState()[1][col].equals(piece) && board.getBoardState()[2][col].equals(piece);
    }

    // Check for win in a backslash diagonal
    public boolean winInDiagonalBackslash(Piece piece)
    {
        if(board.getBoardState()[0][0] == null || board.getBoardState()[1][1] == null || board.getBoardState()[2][2] == null)
            return false;
        return board.getBoardState()[0][0].equals(piece) && board.getBoardState()[1][1].equals(piece) && board.getBoardState()[2][2].equals(piece);
    }

    // Check for win in a frontslash diagonal
    public boolean winInDiagonalFrontslash(Piece piece)
    {
        if(board.getBoardState()[0][2] == null || board.getBoardState()[1][1] == null || board.getBoardState()[2][0] == null)
            return false;
        return board.getBoardState()[0][2].equals(piece) && board.getBoardState()[1][1].equals(piece) && board.getBoardState()[2][0].equals(piece);
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

    public GameState getGameState()
    {
        return gameState;
    }

    public Board getBoard()
    {
        return board;
    }

    public void start()
    {
        gameState = GameState.P1_TURN;
    }

    public void nextTurn()
    {
        if(gameState.equals(GameState.P1_TURN))
        {
            gameState = GameState.P2_TURN;
        }

        else if(gameState.equals(GameState.P2_TURN))
        {
            gameState = GameState.P1_TURN;
        }
    }
}
