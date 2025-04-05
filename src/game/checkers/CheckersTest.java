package game.checkers;
import game.Board;
import game.GameType;
import game.Player;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckersTest {

    /**
     * Things to test:
     * Initial Board setup.
     * Captures
     * Repeated Captures
     * Normal Movement
     * Illegal Movement due to wrong direction
     * Invalid Capture
     * WinConditions
     * Surrender
     * Switch Turn
     * King Promotions
     * Forced Capture that prevents normal moves.
     */

    private Board board;
    private Checkers checkersGame;
    private Piece[][] piece;

    // Method to clear board in some custom test cases.
    public void clearBoard(){
        // get the board.
        Piece[][] piece = board.getBoardState();
        // iterate through the board and clear it.
        for (int i = 0; i < board.getRows(); i++){
            for (int j = 0; j < board.getCols(); j++){
                // clear the cell.
                piece[i][j] = null;
            }
        }
    }

    /**
     * Create a new instance of Checkers board before each test.
     */
    @BeforeEach
    public void setup(){
        // initialize board and checker game before each test
        board = new Board(GameType.CHECKERS);
        checkersGame = new Checkers();
        checkersGame.setBoard(board);
        // Start game by setting game state to P1_Turn
        checkersGame.start();
    }

    /**
     * Test to ensure that the right size of the board is being made for checkers.
     */
    @Test
    public void testBoardDimension(){
        // Checkers board size is 8x8 and so it should be return true.
        assertEquals(8, board.getRows());
        assertEquals(8, board.getCols());
    }

    /**
     * Test to ensure that pieces are placed correctly.
     */
    @Test
    public void testPieceLocation(){
        board.fillBoard(GameType.CHECKERS);
        Piece[][] piece = board.getBoardState();
        // P1 Piece exists on 0,1
        assertEquals(Color.BLACK, piece[0][1].getColor());
        // P2 Piece exists on 5,0
        assertEquals(Color.WHITE, piece[5][0].getColor());
        // No piece exists on 0,0
        assertNull(piece[0][0]);
    }

    /**
     * Test to ensure that pieces are able to make a normal move.
     */
    @Test
    public void testNormalMovement(){
        board.fillBoard(GameType.CHECKERS);
        // check move validity of piece at 2,1 to 3,2
        assertTrue(checkersGame.isValidMove(2,1,3,2, board));
        // Move the piece

        // Check piece's original location == null

        // Check piece new location at 3,2

        // Test Out of bound movement, invalid.

        // Piece should remain in current location 2,1

    }

    /**
     * Test to ensure that pieces are not to make an illegal move.
     */
    @Test
    public void testIllegalMovement(){
        board.fillBoard(GameType.CHECKERS);
        // Move piece located at 0,1 horizontally to 0,2

        // Move piece located at 0,1 on to another existing piece at 1,2

        // Move piece located at 5,0 to 4,1, performing an out of turn move.

    }


    /**
     * Test to ensure captures occurs validly and correctly updates pieces by removing captured piece and moving the capturing piece in a new location.
     */
    @Test
    public void testCapture(){
        // Call method to clear board.
        clearBoard();
        Piece[][] piece = board.getBoardState();
        // Create the players
        Player player1 = new Player();
        Player player2 = new Player();

        // Create Piece

        // Place Piece


        // Create Piece

        // Place Piece


        // Check that the move is valid.


        // Perform the capture move.


        // Check that the whitePiece has been captured (null)

        // Check that previous position of the capturing piece is also empty.

        // Check that it is in new location


    }

    @Test
    public void testMultiCaptures(){
        // when another capture is available the player should and must capture again.
    }

    @Test
    public void testForcedCaptures(){
        // p1 moves a piece that p2 can capture
        // p2 has to capture, any other move is not made until this piece is captured.
    }

    @Test
    public void testIllegalCaptures(){
        // out of bounds
        // out of turns
        // blocked capture
    }

    @Test
    public void testSwitchTurn(){
        // test switching turn to the other player when a player performed a successful move.
        // test when a player does not make a successful move (it should remain their turn)
    }

    @Test
    public void testSurrender(){
        // test game state after surrender method is called.
        // test when either players call for surrender during their turn.
    }

    @Test
    public void testCheckWinCondition(){
        // game state when P1 or P2 has won (either player no longer have any pieces)
    }


}
