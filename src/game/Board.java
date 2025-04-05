package game;

import game.pieces.Piece;
import game.checkers.CheckersPiece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;

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

    public void fillBoard(GameType gameType)
    {
        Player player1 = new Player();
        Player player2 = new Player();

        if(gameType == GameType.CHESS)
        {
            // Method to fill board with game.chess game.pieces
        }

        else if(gameType == GameType.CHECKERS)
        {
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    board[i][j] = null; // Fill with empty cells
                }
            }
            // Method to fill board with game.checkers game.pieces
            // Place white pieces in rows 0, 1, 2
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < cols; j++) {
                    if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                        board[i][j] = new CheckersPiece(i, j, Color.BLACK, PieceType.DARK, player1, 1);
                    }
                }
            }

            // Place white pieces in rows 5, 6, 7
            for (int i = 5; i < 8; i++) {
                for (int j = 0; j < cols; j++) {
                    if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                        board[i][j] = new CheckersPiece(i, j, Color.WHITE, PieceType.LIGHT, player2, 1);
                    }
                }
            }
        }


        else if(gameType == GameType.TICTACTOE)
        {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j] = null; // Fill with empty cells
                }
            }
        }

        else if(gameType == GameType.CONNECT4)
        {
            // Method to fill empty board
        }
    }

    public void displayBoard(){

    }

    public void place(Piece piece, int row, int col)
    {
        board[row][col] = piece;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public boolean isFull()
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(board[i][j] == null)
                {
                    return false;
                }
            }
        }
        return true;
    }


    public Piece[][] getBoardState(){
        return board;
    }

    /*
     * Will this just do a full wipe and set all values to null?
     * Or does this return the board to the original state of any game? ~ Adam
     */
    public void resetBoard(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = null; // Fill with empty cells
            }
        }
        fillBoard(gameType);
    }
}
