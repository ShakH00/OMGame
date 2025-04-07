package Chess;
import game.*;
import game.chess.*;
import game.pieces.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
public class ChessTesting {
    @Test
    public void testIsCheckmate_Player1Checkmated() {
        Player p1 = new Player();
        Player p2 = new Player();
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // p1 king at (0,0)
        King king1 = new King(0, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king1, 0, 0);
        // p2 queen at (1,1), king at (7,7)
        Queen queen = new Queen(1, 1, Color.BLACK, PieceType.DARK, p2);
        board.place(queen,1,1);
        King king2 = new King(7, 7, Color.BLACK, PieceType.DARK, p2);
        board.place(king2,7,7);

        assertTrue(game.isKingInCheck(p1));
        assertTrue(game.isCheckmate(p1));
        assertFalse(game.isCheckmate(p2));
    }
    @Test
    public void testIsPiecePinned_BishopBlockingDiagonalCheck() {
        Player p1 = new Player();
        Player p2 = new Player();
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // Place p1 king at (0, 0)
        King king1 = new King(0, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king1, 0, 0);

        // place p1 bishop at (1, 1) — directly between king and attacking queen
        Bishop bishop = new Bishop(1, 1, Color.WHITE, PieceType.LIGHT, p1);
        board.place(bishop, 1, 1);

        // place p2 queen at (2, 2) — diagonal threat
        Queen queen = new Queen(2, 2, Color.BLACK, PieceType.DARK, p2);
        board.place(queen, 2, 2);

        assertTrue(game.isPiecePinned(bishop));
    }
    @Test
    public void testWheresKingFindsCorrectKing() {
        Player p1 = new Player();
        Player p2 = new Player();
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // place p1 king at (4, 4)
        King king1 = new King(4, 4, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king1, 4, 4);

        King foundKing = game.wheresKing(p1);
        assertNotNull(foundKing);
        assertEquals(4, foundKing.getX());
        assertEquals(4, foundKing.getY());
    }

    @Test
    public void testMatchOutcomePrintsWinner() {
        Player p1 = new Player();
        Player p2 = new Player();
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // set up checkmate against player 1
        King king1 = new King(0, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king1, 0, 0);
        Queen queen = new Queen(1, 1, Color.BLACK, PieceType.DARK, p2);
        board.place(queen, 1, 1);
        King king2 = new King(7, 7, Color.BLACK, PieceType.DARK, p2);
        board.place(king2, 7, 7);

        // capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.checkWinCondition();

        // reset System.out
        System.setOut(System.out);

        assertTrue(outContent.toString().contains("Player 2 wins!"));
    }
    @Test
    public void testKingAttemptCastleKingside() {
        Player p1 = new Player();
        Player p2 = new Player();
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // Place king and rook in initial positions
        King king = new King(7, 4, Color.WHITE, PieceType.LIGHT, p1);
        Rook rook = new Rook(7, 7, Color.WHITE, PieceType.LIGHT, p1);

        board.place(king, 7, 4);
        board.place(rook, 7, 7);

        // Empty spaces between king and rook
        board.place(null, 7, 5);
        board.place(null, 7, 6);

        // Act: Try to castle kingside to (7,6)
        boolean result = king.move(7, 6, board);

        // Assert
        assertTrue(result, "King should be able to castle kingside");
        assertEquals(7, king.getX());
        assertEquals(6, king.getY());

        Piece rookAfter = board.getBoardState()[7][5];
        assertTrue(rookAfter instanceof Rook, "Rook should have moved to f1 after castling");
    }
    @Test
    public void testKingAttemptCastleQueenside() {
        Player p1 = new Player();
        Player p2 = new Player();
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        King king = new King(7, 4, Color.WHITE, PieceType.LIGHT, p1); // e1
        Rook rook = new Rook(7, 0, Color.WHITE, PieceType.LIGHT, p1); // a1

        board.place(king, 7, 4);
        board.place(rook, 7, 0);

        // Clear spaces between them: b1, c1, d1
        board.place(null, 7, 1);
        board.place(null, 7, 2);
        board.place(null, 7, 3);

        // Try to castle queenside to c1 (7,2)
        boolean result = king.move(7, 2, board); // c1

        assertTrue(result, "King should be able to castle queenside");
        assertEquals(7, king.getX());
        assertEquals(2, king.getY());

        Piece rookAfter = board.getBoardState()[7][3]; // d1
        assertTrue(rookAfter instanceof Rook, "Rook should have moved to d1 after castling");
    }
    @Test
    public void testKingCastlesKingside() {
        Player player = new Player();
        Board board = new Board(GameType.CHESS);
        King king = new King(7, 4, Color.WHITE, PieceType.LIGHT, player); // e1
        Rook rook = new Rook(7, 7, Color.WHITE, PieceType.LIGHT, player); // h1

        board.place(king, 7, 4);
        board.place(rook, 7, 7);

        // Clear space between king and rook
        board.place(null, 7, 5);
        board.place(null, 7, 6);

        // Attempt castling to (7,6)
        boolean moved = king.move(7, 6, board);

        assertTrue(moved, "King should be able to castle kingside");
        assertEquals(7, king.getX());
        assertEquals(6, king.getY());
        assertEquals(king, board.getBoardState()[7][6]);
        assertNotNull(board.getBoardState()[7][5], "Rook should have moved to f1 (7,5)");
        assertTrue(board.getBoardState()[7][5] instanceof Rook);
    }
    @Test
    public void testIsCheckmate_KingInCheckButBlockable() {
        Player p1 = new Player(); // player with king and rook
        Player p2 = new Player(); // attacker
        Chess game = new Chess();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // Clear the board
        Piece[][] state = board.getBoardState();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                state[i][j] = null;
            }
        }

        // King for p1 at (7, 4)
        King king = new King(7, 4, Color.WHITE, PieceType.LIGHT, p1);
        state[7][4] = king;

        // Rook for p1 at (7, 0) — it can move to block the check
        Rook rook = new Rook(7, 0, Color.WHITE, PieceType.LIGHT, p1);
        state[7][0] = rook;

        // Queen for p2 at (3, 4) — giving vertical check
        Queen queen = new Queen(3, 4, Color.BLACK, PieceType.DARK, p2);
        state[3][4] = queen;

        // Now king is in check, but rook can move to (4, 4), (5, 4), or (6, 4)
        assertTrue(game.isKingInCheck(p1), "King should be in check");
        assertFalse(game.isCheckmate(p1), "Should NOT be checkmate since rook can block");
    }

}
