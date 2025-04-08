package TicTacToe;

import game.tictactoe.TicTacToe;
import game.pieces.PieceType;
import game.tictactoe.TicTacToePiece;
import game.GameState;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicTacToeTestingTest {

    private TicTacToe tictactoe;

    @Before
    public void setUp() {
        tictactoe = new TicTacToe();
        tictactoe.start();
    }

    // Valid Placement
    @Test
    public void testMoveValidPlacement() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[0][0]);
    }

    // Invalid Placement
    @Test
    public void testMoveSamePlace() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 0); // Attempt to place in the same spot
        assertEquals(piece, tictactoe.getBoard().getBoardState()[0][0]);
    }

    @Test
    public void testMoveOutOfBounds() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, -1, -1); // Out of bounds
        assertNull(tictactoe.getBoard().getBoardState()[0][0]);
    }

    @Test
    public void testWinInRow() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 0, 2);
        assertTrue(tictactoe.winInRow(0, piece));
    }

    @Test
    public void testWinInCol() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInCol(0, piece));
    }

    @Test
    public void testWinInDiagonalBackslash() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInDiagonalBackslash(piece));
    }

    @Test
    public void testWinInDiagonalFrontslash() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInDiagonalFrontslash(piece));
    }

    @Test
    public void testDrawGame() {
        tictactoe.drawGame();
        assertEquals(GameState.DRAW, tictactoe.getGameState());
    }

    @Test
    public void testSurrender() {
        tictactoe.surrender();
        assertEquals(GameState.P2_WIN, tictactoe.getGameState());
    }

    @Test
    public void testNextTurn() {
        tictactoe.nextTurn();
        assertEquals(GameState.P2_TURN, tictactoe.getGameState());
    }
}