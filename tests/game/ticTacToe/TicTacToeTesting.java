package game.ticTacToe;

import game.tictactoe.TicTacToe;
import game.pieces.PieceType;
import game.tictactoe.TicTacToePiece;
import game.GameState;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTesting {

    private TicTacToe tictactoe;

    @BeforeEach
    public void setUp() {
        tictactoe = new TicTacToe();
        tictactoe.start();
    }

// ------------------------------------------------- ALL TESTING FOR P1 -------------------------------------------------

    /**
     * Test placements of tic-tac-toe piece. P1
     */
    @Test
    public void testMoveValidPlacementP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[0][0]);
    }

    /**
     * Test invalid placements of tic-tac-toe piece.
     */
    @Test
    public void testMoveSamePlaceP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 0); // Attempt to place in the same spot
        assertEquals(piece, tictactoe.getBoard().getBoardState()[0][0]);
    }

    /**
     * Test out-of-bounds placements of tic-tac-toe piece.
     */
    @Test
    public void testMoveOutOfBoundsP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, -1, -1); // Out of bounds
        assertNull(tictactoe.getBoard().getBoardState()[0][0]);
    }

    /**
     * Test win conditions in rows 1 of tic-tac-toe piece.
     */
    @Test
    public void testWinInRow1P1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 0, 2);
        assertTrue(tictactoe.winInRow(0, piece));
    }

    /**
     * Test win conditions in rows 2 of tic-tac-toe piece.
     */
    @Test
    public void testWinInRow2P1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 1, 2);
        assertTrue(tictactoe.winInRow(1, piece));
    }

    /**
     * Test win conditions in rows 3 of tic-tac-toe piece.
     */
    @Test
    public void testWinInRow3P1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 2, 0);
        tictactoe.move(piece, 2, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInRow(2, piece));
    }

    /**
     * Test win conditions in col 1 of tic-tac-toe piece.
     */
    @Test
    public void testWinInCol1P1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInCol(0, piece));
    }

    /**
     * Test win conditions in col 2 of tic-tac-toe piece.
     */
    @Test
    public void testWinInCol2P1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 1);
        assertTrue(tictactoe.winInCol(1, piece));
    }

    /**
     * Test win conditions in col 3 of tic-tac-toe piece.
     */
    @Test
    public void testWinInCol3P1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 2);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInCol(2, piece));
    }

    /**
     * Test win conditions of diagonal backslash tic-tac-toe piece.
     */
    @Test
    public void testWinInDiagonalBackslashP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInDiagonalBackslash(piece));
    }

    /**
     * Test win conditions of diagonal forward slash tic-tac-toe piece.
     */
    @Test
    public void testWinInDiagonalFrontslashP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInDiagonalFrontslash(piece));
    }

// ------------------------------------------------- ALL TESTING FOR P2 -------------------------------------------------
    /**
     * Testing Alters With Light and Dark PieceType.
     */

    /**
     * Test placements of tic-tac-toe piece. P2
     */
    @Test
    public void testMoveValidPlacementP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 2, 1);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[2][1]);
    }

    /**
     * Test invalid placements of tic-tac-toe piece.
     */
    @Test
    public void testMoveSamePlaceP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 0); // Attempt to place in the same spot
        assertEquals(piece, tictactoe.getBoard().getBoardState()[0][0]);
    }

    /**
     * Test out-of-bounds placements of tic-tac-toe piece.
     */
    @Test
    public void testMoveOutOfBoundsP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, -1, -1); // Out of bounds
        assertNull(tictactoe.getBoard().getBoardState()[0][0]);
    }

    /**
     * Test win conditions in rows 1 of tic-tac-toe piece.
     */
    @Test
    public void testWinInRow1P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 0, 2);
        assertTrue(tictactoe.winInRow(0, piece));
    }

    /**
     * Test win conditions in rows 2 of tic-tac-toe piece.
     */
    @Test
    public void testWinInRow2P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 1, 2);
        assertTrue(tictactoe.winInRow(1, piece));
    }

    /**
     * Test win conditions in rows 3 of tic-tac-toe piece.
     */
    @Test
    public void testWinInRow3P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 2, 0);
        tictactoe.move(piece, 2, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInRow(2, piece));
    }

    /**
     * Test win conditions in col 1 of tic-tac-toe piece.
     */
    @Test
    public void testWinInCol1P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInCol(0, piece));
    }

    /**
     * Test win conditions in col 2 of tic-tac-toe piece.
     */
    @Test
    public void testWinInCol2P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 1);
        assertTrue(tictactoe.winInCol(1, piece));
    }

    /**
     * Test win conditions in col 3 of tic-tac-toe piece.
     */
    @Test
    public void testWinInCol3P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 2);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInCol(2, piece));
    }

    /**
     * Test win conditions of diagonal backslash tic-tac-toe piece.
     */
    @Test
    public void testWinInDiagonalBackslashP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInDiagonalBackslash(piece));
    }

    /**
     * Test win conditions of diagonal forward slash tic-tac-toe piece.
     */
    @Test
    public void testWinInDiagonalFrontslashP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player2);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInDiagonalFrontslash(piece));
    }

// ------------------------------------------------- TESTING FOR GAME STATES -------------------------------------------------
    /**
     * Test drawGame method to set GameState to draw.
     */
    @Test
    public void testDrawGame() {
        tictactoe.drawGame();
        assertEquals(GameState.DRAW, tictactoe.getGameState());
    }

    /**
     * Test surrender method to ensure that the opposing player wins when the current player calls for it.
     */
    @Test
    public void testSurrender() {
        // P1 to move
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());
        // P1 calls surrender during their turn
        tictactoe.surrender();
        // P2 wins as P1 has surrendered.
        assertEquals(GameState.P2_WIN, tictactoe.getGameState());

        // Reset turn to P1
        tictactoe.start();
        // Call nextTurn to test P2 surrenders
        tictactoe.nextTurn();
        // P2 calls surrender during their turn
        tictactoe.surrender();
        // P1 wins as P2 has surrendered.
        assertEquals(GameState.P1_WIN, tictactoe.getGameState());
    }

    @Test
    public void testNextTurn() {
        // P1 to move
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());
        // nextTurn method called
        tictactoe.nextTurn();
        // P2 to move.
        assertEquals(GameState.P2_TURN, tictactoe.getGameState());
        // nextTurn method called
        tictactoe.nextTurn();
        // P1 to move.
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());
    }
}