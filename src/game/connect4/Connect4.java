package game.connect4;

import account.statistics.StatisticType;
import game.*;
import game.checkers.Checkers;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Connect4 extends Game {
    private int p1Turns = 0;
    private int p2Turns = 0;

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

    public Connect4Piece piece1 = new Connect4Piece(Color.RED, PieceType.LIGHT, super.player1);
    public Connect4Piece piece2 = new Connect4Piece(Color.GOLD, PieceType.DARK, super.player2);

    public void move(Piece piece, int col) {
        if (col >= 0 && col < board.getCols()) {
            // Find first empty row in this column (from bottom up)
            for (int row = board.getRows() - 1; row >= 0; row--) {
                if (board.getBoardState()[row][col] == null) {
                    board.place(piece, row, col);
                    checkWinCondition();
                    if(piece.getOwnedBy() == player1) {
                        p1Turns++;
                    }
                    else if(piece.getOwnedBy() == player2) {
                        p2Turns++;
                    }
                    break;
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
        int counter = 0;

        for(int col = 0; col < board.getCols(); col++)
        {
            if(board.getBoardState()[row][col] != null && board.getBoardState()[row][col].equals(piece))
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
            if (current != null && current.getColor().equals(piece.getColor()))
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
                    if (current == null || !current.getColor().equals(piece.getColor()))
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
                    if (current == null || !current.getColor().equals(piece.getColor()))
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

    protected HashMap<StatisticType, Integer> matchOutcomeP1() {
        HashMap<StatisticType, Integer> matchOutcome = new HashMap<>();
        if(gameState == GameState.P1_WIN)
        {
            matchOutcome.put(StatisticType.WINS, 1);
            matchOutcome.put(StatisticType.LOSSES, 0);
            matchOutcome.put(StatisticType.DRAWS, 0);

        }
        else if(gameState == GameState.P2_WIN)
        {
            matchOutcome.put(StatisticType.WINS, 0);
            matchOutcome.put(StatisticType.LOSSES, 1);
            matchOutcome.put(StatisticType.DRAWS, 0);
        }
        else if (gameState == GameState.DRAW)
        {
            matchOutcome.put(StatisticType.WINS, 0);
            matchOutcome.put(StatisticType.LOSSES, 0);
            matchOutcome.put(StatisticType.DRAWS, 1);
        }
        matchOutcome.put(StatisticType.MATCHES_PLAYED, 1);
        matchOutcome.put(StatisticType.NUMBER_OF_TURNS, getP1Turns());

        return matchOutcome;
    }

    protected HashMap<StatisticType, Integer> matchOutcomeP2() {
        HashMap<StatisticType, Integer> matchOutcome = new HashMap<>();
        if(gameState == GameState.P1_WIN)
        {
            matchOutcome.put(StatisticType.WINS, 0);
            matchOutcome.put(StatisticType.LOSSES, 1);
            matchOutcome.put(StatisticType.DRAWS, 0);

        }
        else if(gameState == GameState.P2_WIN)
        {
            matchOutcome.put(StatisticType.WINS, 1);
            matchOutcome.put(StatisticType.LOSSES, 0);
            matchOutcome.put(StatisticType.DRAWS, 0);
        }
        else if (gameState == GameState.DRAW)
        {
            matchOutcome.put(StatisticType.WINS, 0);
            matchOutcome.put(StatisticType.LOSSES, 0);
            matchOutcome.put(StatisticType.DRAWS, 1);
        }
        matchOutcome.put(StatisticType.MATCHES_PLAYED, 1);
        matchOutcome.put(StatisticType.NUMBER_OF_TURNS, getP2Turns());

        return matchOutcome;
    }

    public int getP1Turns()
    {
        return p1Turns;
    }

    public int getP2Turns()
    {
        return p2Turns;
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

    public void drawGame()
    {
        gameState = GameState.DRAW;
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

}


