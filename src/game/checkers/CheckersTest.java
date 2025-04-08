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

    /**
     * Test to ensure that a players must make multiple captures if presented with the opportunity.
     */
    @Test
    public void testMultiCaptures(){
        // When another capture is available the player should and must capture again.
        clearBoard();

        Piece[][] piece = board.getBoardState();
        // Create the players
        Player player1 = new Player();
        Player player2 = new Player();
        // Create Pieces
        CheckersPiece blackPiece1 = new CheckersPiece(2,5, Color.BLACK, PieceType.DARK, player1, 0);
        CheckersPiece blackPiece2 = new CheckersPiece(1,2, Color.BLACK, PieceType.DARK, player1, 0);
        CheckersPiece blackPiece3 = new CheckersPiece(1,4, Color.BLACK, PieceType.DARK, player1, 0);
        CheckersPiece whitePiece = new CheckersPiece(4,7, Color.WHITE, PieceType.LIGHT, player2, 0);

        // Place Pieces
        piece[1][4] = blackPiece1;
        piece[1][2] = blackPiece2;
        piece[2][5] = blackPiece3;
        piece[4][7] = whitePiece;

        // P1 to move blackPiece1 to 3,6 which forces a capture from P2
        assertTrue(checkersGame.isValidMove(2,5,3,6,board));
        checkersGame.move(blackPiece1, 3, 6);
        // P2's turn, moving to do a forced capture.
        assertTrue(checkersGame.isValidMove(4,7, 2,5, board));
        checkersGame.move(whitePiece, 2,5);
        // P2's turn remains as he can perform multi captures to blackPiece3
        assertEquals(GameState.P2_TURN, checkersGame.getGameState());
        // P2 to capture again
        checkersGame.move(whitePiece, 0,3);
        // P2 whitePiece promoted to king which allow another capture of blackPiece2
        assertEquals(GameState.P2_TURN, checkersGame.getGameState());
        assertTrue(checkersGame.isValidMove(0,3,2,1,board));
        checkersGame.move(whitePiece, 2,1);
    }


    /**
     * Test for Forced captures. If a piece is capturable then all other moves are locked.
     */
    @Test
    public void testForcedCaptures(){
        clearBoard();

        Piece[][] piece = board.getBoardState();
        // Create the players
        Player player1 = new Player();
        Player player2 = new Player();
        // Create Pieces
        CheckersPiece blackPiece = new CheckersPiece(1,4, Color.BLACK, PieceType.DARK, player1, 0);
        CheckersPiece whitePiece = new CheckersPiece(3,6, Color.WHITE, PieceType.LIGHT, player2, 0);

        // Place Pieces
        piece[1][4] = blackPiece;
        piece[3][6] = whitePiece;

        // P1 to move from 4,7 to 3,6
        assertTrue(checkersGame.isValidMove(1,4,2,5,board));
        checkersGame.move(blackPiece, 2,5);

        // Ensure it is P2's turn
        assertEquals(GameState.P2_TURN, checkersGame.getGameState());

        // P2 is forced to capture and any attempt otherwise the move is prevented and P2 is to make the capture before turn switch.
        assertTrue(checkersGame.forcedCapture(player2));

        // P2 captures
        checkersGame.move(whitePiece, 1,4);

        // Turn is switched
        assertEquals(GameState.P1_TURN, checkersGame.getGameState());


    }

    /**
     * Test for illegal captures. Checks if a piece is in bounds, on a playable square, and if the adjacent diagonal is free.
     */
    @Test
    public void testIllegalCaptures(){
        // out of bounds
        // out of turns
        // blocked capture
        clearBoard();
        Piece[][] piece = board.getBoardState();
        // Create the players
        Player player1 = new Player();
        Player player2 = new Player();
        // Create Pieces
        CheckersPiece blackPiece = new CheckersPiece(6,7, Color.BLACK, PieceType.DARK, player1, 0);
        CheckersPiece whitePiece1 = new CheckersPiece(1,4, Color.WHITE, PieceType.LIGHT, player2, 0);
        CheckersPiece whitePiece2 = new CheckersPiece(2,5, Color.WHITE, PieceType.LIGHT, player2, 0);
        CheckersPiece whitePiece3 = new CheckersPiece(2,7, Color.WHITE, PieceType.LIGHT, player2, 0);
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
