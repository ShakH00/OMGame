package game;

import game.chess.*;
import game.pieces.Piece;
import game.checkers.CheckersPiece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class Board implements Serializable {
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

    /**
     * Method specific for filling chess board as Player attribute matters across the board within our methods
     * @param gameType: There as a check, in case called by other game accidentally
     * @param player1: Player 1 who will own all the LIGHT pieces
     * @param player2: Player 2 who will own all the DARK pieces
     */
    public void fillChessBoard(GameType gameType, Player player1, Player player2){
        if(gameType.equals(GameType.CHESS)){
            //DARK pieces fill in
            board[0][0] = new Rook(0,0, Color.BLACK, PieceType.DARK, player2);
            board[0][1] = new Knight(0, 1, Color.BLACK, PieceType.DARK, player2);
            board[0][2] = new Bishop(0, 2, Color.BLACK, PieceType.DARK, player2);
            board[0][3] = new Queen(0, 3, Color.BLACK, PieceType.DARK, player2);
            board[0][4] = new King(0, 4, Color.BLACK, PieceType.DARK, player2);
            board[0][5] = new Bishop(0, 5, Color.BLACK, PieceType.DARK, player2);
            board[0][6] = new Knight(0, 6, Color.BLACK, PieceType.DARK, player2);
            board[0][7] = new Rook(0, 7, Color.BLACK, PieceType.DARK, player2);
            for (int col = 0; col < 8; col++) {
                board[1][col] = new Pawn(1, col, Color.BLACK, PieceType.DARK, player2);
            }

            //LIGHT pieces, bottom two rows
            String white = "white";
            board[7][0] = new Rook(7, 0, Color.WHITE, PieceType.LIGHT, player1);
            board[7][1] = new Knight(7, 1, Color.WHITE, PieceType.LIGHT, player1);
            board[7][2] = new Bishop(7, 2, Color.WHITE, PieceType.LIGHT, player1);
            board[7][3] = new Queen(7, 3, Color.WHITE, PieceType.LIGHT, player1);
            board[7][4] = new King(7, 4, Color.WHITE, PieceType.LIGHT, player1);
            board[7][5] = new Bishop(7, 5, Color.WHITE, PieceType.LIGHT, player1);
            board[7][6] = new Knight(7, 6, Color.WHITE, PieceType.LIGHT, player1);
            board[7][7] = new Rook(7, 7, Color.WHITE, PieceType.LIGHT, player1);
            for (int col = 0; col < 8; col++) {
                board[6][col] = new Pawn(6, col, Color.WHITE, PieceType.LIGHT, player1);
            }
        }
    }

    public void fillBoard(GameType gameType)
    {
        Player player1 = new Player();
        Player player2 = new Player();


        if(gameType == GameType.CHECKERS)
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
