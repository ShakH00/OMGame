package game.checkers;
import game.*;
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

    // Method to clear board in some custom test cases.
    private void clearBoard(){
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
        // set players
        checkersGame = new Checkers();
        // fill the board with pieces
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
        Piece[][] piece = board.getBoardState();
        int cols = board.getCols();

        // Test all supposed locations of P1 pieces on rows 0,1,2.
        // The rest should return null as they are empty.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    assertNotNull(piece[i][j]);
                    assertEquals(Color.BLACK, piece[i][j].getColor());
                }
                else{
                    assertNull(piece[i][j]);
                }
            }
        }

        // Test all supposed locations of P2 pieces on rows 5,6,7.
        // The rest should return null as they are empty.
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    assertNotNull(piece[i][j]);
                    assertEquals(Color.WHITE, piece[i][j].getColor());
                }
                else{
                    assertNull(piece[i][j]);
                }
            }
        }

        // Middle rows should have no pieces
        for (int i = 3; i < 5; i++) {
            for (int j = 0; j < cols; j++) {
                assertNull(piece[i][j]);
            }
        }
    }

    /**
     * Test to ensure that pieces are able to make a normal move.
     */
    @Test
    public void testNormalMovement(){
        // check move validity of piece at 2,1 to 3,2
        assertTrue(checkersGame.isValidMove(2,1,3,2, board));
        // Move the piece
        Piece[][] piece = board.getBoardState();
        Piece blackPiece = piece[2][1];
        checkersGame.move((CheckersPiece) blackPiece, 3, 2);
        // Check piece's original location == null
        assertNull(piece[2][1]);
        // Check piece is at new location at 3,2
        assertEquals(blackPiece, piece[3][2]);
        // Test Out of bound movement, invalid.
        Piece piece2 = piece[2][7];
        assertFalse(checkersGame.isValidMove(2, 7, 3, 8, board));
        checkersGame.move((CheckersPiece) piece2, 3, 8);
        // Piece should remain in current location 2,1
        assertEquals(piece2, piece[2][7]);
    }

    /**
     * Test to ensure that pieces are not to make an illegal move.
     */
    @Test
    public void testIllegalMovement(){
        Piece[][] piece = board.getBoardState();
        Piece blackPiece = piece[0][1];
        // Move piece located at 0,1 horizontally to 0,2
        assertFalse(checkersGame.isValidMove(0,1, 0, 2, board));
        checkersGame.move((CheckersPiece) blackPiece, 0, 2);
        // piece remains in its location
        assertEquals(blackPiece, piece[0][1]);

        // Move piece located at 0,1 on to another existing piece at 1,2
        assertFalse(checkersGame.isValidMove(0,1, 1, 2, board));
        checkersGame.move((CheckersPiece) blackPiece, 1,2);
        // piece remains in its location
        assertEquals(blackPiece, piece[0][1]);

        Piece whitePiece = piece[5][0];
        // Move piece located at 5,0 to 4,1, moving a piece not player 1's
        assertFalse(checkersGame.isValidMove(5,0, 4, 1, board));
        checkersGame.move((CheckersPiece) whitePiece, 4,1);
        // and piece remains in its location
        assertEquals(whitePiece, piece[5][0]);
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
        CheckersPiece blackPiece = new CheckersPiece(2,3, Color.BLACK, PieceType.DARK, player1, 0);
        // Place Piece
        piece[2][3] = blackPiece;

        // Create Piece
        CheckersPiece whitePiece = new CheckersPiece(3,2, Color.WHITE, PieceType.LIGHT, player2, 0);
        // Place Piece
        piece[3][2] = whitePiece;

        // Check that the move is valid.
        assertTrue(checkersGame.isValidMove(2,3,4,1, board));

        // Perform the capture move.
        checkersGame.move(blackPiece, 4,1);

        // Check that the whitePiece has been captured (null)
        assertNull(piece[3][2]);
        // Check that previous position of the capturing piece is also empty.
        assertNull(piece[2][3]);
        // Check that it is in new location
        assertEquals(blackPiece, piece[4][1]);

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
