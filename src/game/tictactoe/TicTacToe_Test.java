package game.tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToe_Test {

    /*
     * Creates a game.Board and Creates Another Using Code
     */
    @Test
    public void testInitializeBoard() {
        TicTacToe game = new TicTacToe();
        char[][] expectedBoard = {
            {'-', '-', '-'},
            {'-', '-', '-'},
            {'-', '-', '-'}
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expectedBoard[i][j], game.initializeBoard());
            }
        }
    }

    /*
     * Create a game.Board
     * Check if the game.Board is Empty
     */
    @Test
    public void testIsBoardFull_EmptyBoard() {
        TicTacToe game = new TicTacToe();
        assertFalse(game.isBoardFull());
    }

    /*
     * Create a game.Board
     * Fill the game.Board (Draw)
     * Check if the game.Board is Full
     */
    @Test
    public void testIsBoardFull_FullBoard() {
        game.tictactoe.TicTacToe game = new TicTacToe();
        char[][] fullBoard = {
            {'O', 'X', 'O'},
            {'O', 'X', 'X'},
            {'X', 'O', 'X'}
        };
        game.printBoard(fullBoard);
        assertTrue(game.isBoardFull());
    }

    /*
     * Create a game.Board
     * Fill the game.Board (Row Win)
     * Call isGameOver()
     */
    @Test
    public void testIsGameOver_RowWin() {
        TicTacToe game = new TicTacToe();
        char[][] boardWithRowWin = {
            {'X', 'X', 'X'},
            {'-', '-', '-'},
            {'-', '-', '-'}
        };
        game.printBoard(boardWithRowWin);
        assertTrue(game.isGameOver());
    }

    /*
     * Create a game.Board
     * Fill the game.Board (Column Win)
     * Call isGameOver()
     */
    @Test
    public void testIsGameOver_ColumnWin() {
        TicTacToe game = new TicTacToe();
        char[][] boardWithColumnWin = {
            {'X', '-', '-'},
            {'X', '-', '-'},
            {'X', '-', '-'}
        };
        game.printBoard(boardWithColumnWin);
        assertTrue(game.isGameOver());
    }

    /*
     * Create a game.Board
     * Fill the game.Board (Diagonal Win)
     * Call isGameOver()
     */
    @Test
    public void testIsGameOver_DiagonalWin() {
        TicTacToe game = new TicTacToe();
        char[][] boardWithDiagonalWin = {
            {'X', '-', '-'},
            {'-', 'X', '-'},
            {'-', '-', 'X'}
        };
        game.printBoard(boardWithDiagonalWin);
        assertTrue(game.isGameOver());
    }

    /*
     * Create a game.Board
     * Place a Piece at (0, 0)
     * makeMove() = isValidMove()
     */
    @Test
    public void testMakeMove_ValidMove00() {
        TicTacToe game = new TicTacToe();
        game.makeMove(0, 0);
        assertEquals('X', game.printBoard()[0][0]);
    }

    /*
     * Not Completely Implemented Yet, Waiting for GIU Team
     * Create a game.Board
     * Place a Piece at (1, 2)
     * makeMove() = isValidMove()
     */
    @Test
    public void testMakeMove_ValidMove12() {
        TicTacToe game = new TicTacToe();
        game.makeMove(0, 0);
        assertEquals('X', game.printBoard()[0][0]);
    }

    /*
     * Not Completely Implemented Yet, Waiting for GIU Team
     * Create a game.Board
     * Both Players Place Piece in the Same Location at (0, 0)
     * makeMove() = isValidMove()
     */
    @Test
    public void testMakeMove_InvalidMove() {
        TicTacToe game = new TicTacToe();
        game.makeMove(0, 0);
        game.makeMove(0, 0);
        assertEquals('X', game.printBoard()[0][0]); 
    }

    //Haven't Implemented Yet, Waiting for GIU Team
    @Test
    public void testMakeMove_SwitchPlayer() {
        TicTacToe game = new game.tictactoe.TicTacToe();
        game.makeMove(0, 0);
        assertEquals('O', game.getCurrentPlayer());
    }

    /*
     * Simulate a Game Where game.Player X Wins with a Row
     */
    @Test
    public void testPlay_PlayerXRowWin() {
        TicTacToe game = new TicTacToe();
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X - Row Win
        assertTrue(game.isGameOver());
        assertEquals('X', game.getCurrentPlayer());
    }

    /*
     * Simulate a Game That Ends in a Draw
     */
    @Test
    public void testPlay_DrawGame() {
        TicTacToe game = new game.tictactoe.TicTacToe();
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(0, 2); // X
        game.makeMove(1, 1); // O
        game.makeMove(1, 0); // X
        game.makeMove(1, 2); // O
        game.makeMove(2, 1); // X
        game.makeMove(2, 0); // O
        game.makeMove(2, 2); // X - Full game.Board, Draw
        assertTrue(game.isBoardFull());
        assertFalse(game.isGameOver());
    }
}