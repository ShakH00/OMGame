package game.tictactoe;

import account.statistics.StatisticType;
import game.*;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Random;

public class TicTacToe extends Game {
    public TicTacToePiece piece1;
    public TicTacToePiece piece2;
    private int p1Turns;
    private int p2Turns;

    private networking.Networking networking = new networking.Networking();

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
                    if(piece.getOwnedBy() == player1) {
                        p1Turns++;
                    }
                    else if(piece.getOwnedBy() == player2) {
                        p2Turns++;
                    }
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
    public void surrender()
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
            networking.sendGame(this);
            netUpdateGame();
        }

        else if(gameState.equals(GameState.P2_TURN))
        {
            gameState = GameState.P1_TURN;
            networking.sendGame(this);
            netUpdateGame();
        }
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

    public void drawGame()
    {
        gameState = GameState.DRAW;
    }
    private void netUpdateGame(){
        TicTacToe temp = (TicTacToe) networking.recieveGame();
        this.board = temp.board;
        this.score1 = temp.score1;
        this.score2 = temp.score2;
        this.gameState = temp.getGameState();
        this.player1 = temp.getPlayer1();
        this.player2 = temp.getPlayer2();
    }
}
