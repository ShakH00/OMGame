package TicTacToe;

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

    /**
     * Set up and Start TicTacToe Game
     */
    @BeforeEach
    public void setUp() {
        tictactoe = new TicTacToe();
        tictactoe.start();
    }

    /**
     * Testing With Light and Dark PieceType.
     */

// ------------------------------------------------- ALL TESTING FOR P1 -------------------------------------------------

    /**
     * Test move Piece and Check If on Board.
     */
    @Test
    public void testMovePlacedP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        tictactoe.move(piece, 1, 1);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[1][1]);
    }

    /**
     * Test When Player Puts Piece in the Same Spot.
     *     Do Nothing
     */
    @Test
    public void testMoveSamePlaceP1() {
        TicTacToePiece piece = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        
        // Place at (0, 0)
        tictactoe.move(piece, 0, 0);

        // Attempt to Place in the Same Spot
        tictactoe.move(piece, 0, 0);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[0][0]);
    }

    /**
     * Test Row1 Win Condition.
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
     * Test Row2 Win Condition.
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
     * Test Row3 Win Condition.
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
     * Test Column1 Win Condition
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
     * Test Column2 Win Condition
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
     * Test Column3 Win Condition
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
     * Test Diagonal BackSlash Win Condition,
     *     Top Right to Bottom Left.
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
     * Test Diagonal ForwardSlash Win Condition
     *     Top Left to Bottom Right.
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
     * Test move Piece and Check If on Board.
     */
    @Test
    public void testMovePlacedP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 2, 1);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[2][1]);
    }

    /**
     * Test When Player Puts Piece in the Same Spot.
     *     Do Nothing
     */
    @Test
    public void testMoveSamePlaceP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        
        // Place at (0, 0)
        tictactoe.move(piece, 1, 2);

        // Attempt to Place in the Same Spot
        tictactoe.move(piece, 1, 2);
        assertEquals(piece, tictactoe.getBoard().getBoardState()[1][2]);
    }

    /**
     * Test Row1 Win Condition.
     */
    @Test
    public void testWinInRow1P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 0, 2);
        assertTrue(tictactoe.winInRow(0, piece));
    }

    /**
     * Test Row2 Win Condition.
     */
    @Test
    public void testWinInRow2P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 1, 2);
        assertTrue(tictactoe.winInRow(1, piece));
    }

    /**
     * Test Row3 Win Condition.
     */
    @Test
    public void testWinInRow3P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 2, 0);
        tictactoe.move(piece, 2, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInRow(2, piece));
    }

    /**
     * Test Column1 Win Condition
     */
    @Test
    public void testWinInCol1P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 0);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInCol(0, piece));
    }

    /**
     * Test Column2 Win Condition
     */
    @Test
    public void testWinInCol2P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 0, 1);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 1);
        assertTrue(tictactoe.winInCol(1, piece));
    }

    /**
     * Test Column3 Win Condition
     */
    @Test
    public void testWinInCol3P2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 2);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInCol(2, piece));
    }

    /**
     * Test Diagonal BackSlash Win Condition,
     *     Top Right to Bottom Left.
     */
    @Test
    public void testWinInDiagonalBackslashP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 0, 0);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 2);
        assertTrue(tictactoe.winInDiagonalBackslash(piece));
    }

    /**
     * Test Diagonal ForwardSlash Win Condition
     *     Top Left to Bottom Right.
     */
    @Test
    public void testWinInDiagonalFrontslashP2() {
        TicTacToePiece piece = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);
        tictactoe.move(piece, 0, 2);
        tictactoe.move(piece, 1, 1);
        tictactoe.move(piece, 2, 0);
        assertTrue(tictactoe.winInDiagonalFrontslash(piece));
    }

// ------------------------------------------------- TESTING FOR GAME STATES -------------------------------------------------
    /**
     * Test drawGame Method to set GameState to Draw.
     */
    @Test
    public void testDrawGame() {
        tictactoe.drawGame();
        assertEquals(GameState.DRAW, tictactoe.getGameState());
    }

    /**
     * Test Full Board, Draw.
     */
    @Test
    public void testFullBoardDraw() {
        TicTacToePiece piece1 = new TicTacToePiece(Color.RED, PieceType.LIGHT, tictactoe.player1);
        TicTacToePiece piece2 = new TicTacToePiece(Color.GOLD, PieceType.DARK, tictactoe.player2);

        // Both Players Fill the Board
        tictactoe.move(piece1, 0, 1);
        tictactoe.move(piece2, 0, 0);
        tictactoe.move(piece1, 0, 2);
        tictactoe.move(piece2, 1, 1);
        tictactoe.move(piece1, 1, 0);
        tictactoe.move(piece2, 1, 2);
        tictactoe.move(piece1, 2, 1);
        tictactoe.move(piece2, 2, 0);
        tictactoe.move(piece1, 2, 2);

        // Check if GameState is set to Draw
        assertEquals(GameState.DRAW, tictactoe.getGameState());
    }

    /**
     * Test GameState to check if it is P1_TURN or P2_TURN.
     */
    @Test
    public void testNextTurn() {
        // P1 Turn
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());

        // Call nextTurn Method (P1 --> P2)
        tictactoe.nextTurn();

        // P2 Turn
        assertEquals(GameState.P2_TURN, tictactoe.getGameState());

        // Call nextTurn Method (P2 --> P1)
        tictactoe.nextTurn();

        // P1 Turn.
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());
    }

    /**
     * Test surrender Method to set GameState to P1_WIN or P2_WIN.
     *     testSurrenderP1() --> P1 Surrender = P2_WIN
     *     testSurrenderP2() --> P2 Surrender = P1_WIN
     */

    @Test
    public void testSurrenderP1() {
        // P1 Turn
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());

        // P1 Calls Surrender During Their Turn
        tictactoe.surrender();

        // P2 Wins as P1 has Surrendered.
        assertEquals(GameState.P2_WIN, tictactoe.getGameState());
    }

    @Test
    public void testSurrenderP2() {
        // P1 Turn
        assertEquals(GameState.P1_TURN, tictactoe.getGameState());

        // Call nextTurn for P2 Turn
        tictactoe.nextTurn();

        // P2 Calls Surrender During Their Turn
        tictactoe.surrender();

        // P1 Wins as P2 has Surrendered.
        assertEquals(GameState.P1_WIN, tictactoe.getGameState());
    }
}