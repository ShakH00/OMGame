package game.chess;
import game.*;
import game.pieces.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        // Create a new Chess game
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // Clear the board
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board.place(null, x, y);
            }
        }

        // Set up: King at (0,0), Bishop at (1,1), Queen at (2,2)
        King king = new King(7, 0, Color.WHITE, PieceType.LIGHT, p1);
        Bishop bishop = new Bishop(6, 1, Color.WHITE, PieceType.LIGHT, p1);
        Queen queen = new Queen(5, 2, Color.BLACK, PieceType.DARK, p2);

        board.place(king, 7, 0);
        board.place(bishop, 6, 1);
        board.place(queen, 5, 2);

        // Sanity check — with bishop removed, king is in check
        board.place(null, 6, 1); // Remove bishop
        boolean kingInCheck = game.isKingInCheck(p1);
        board.place(bishop, 6, 1); // Restore bishop
        System.out.println("King in check with bishop removed: " + kingInCheck);

        // Actual test
        assertTrue(kingInCheck, "King should be in check without bishop.");
        assertTrue(game.isPiecePinned(bishop), "Bishop should be pinned blocking queen's diagonal attack.");
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
    public void testMatchOutcome_GameStatePlayer2Wins() {
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // Clear board
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board.place(null, x, y);
            }
        }

        // Set up checkmate on player 1
        King king1 = new King(7, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king1, 7, 0);
        Queen queen = new Queen(6, 1, Color.BLACK, PieceType.DARK, p2);
        board.place(queen, 6, 1);
        King king2 = new King(0, 0, Color.BLACK, PieceType.DARK, p2);
        Bishop bishop = new Bishop(5, 2, Color.BLACK, PieceType.DARK, p2);
        board.place(bishop, 5, 2);
        board.place(king2, 0, 0);
        System.out.print("checkmate: " + game.isCheckmate(p1));
        game.checkWinCondition();

        assertEquals(GameState.P2_WIN, game.getState(), "Game state should reflect Player 2 victory");
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

    @Test
    public void getLegalMovesTest(){
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);
        board.place(null, 7,0);
        board.place(null, 6,2); // setBoard fills the board with pieces
        Color white = Color.WHITE;
        PieceType pieceType = PieceType.LIGHT;

        Knight knight = new Knight(7,0, white, pieceType, p1);
        board.place(knight, 7, 0);
        List<int[]> expected = new ArrayList<>();
        expected.add(new int[]{5,1});
        expected.add(new int[]{6,2});
        List<int[]> actual = game.getLegalMoves(knight);
        assertEquals(expected.size(), actual.size(), "List sizes differ");
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), actual.get(i), "Mismatch at index " + i);
        }
    }
    @Test
    public void testCheckButNotCheckmate_BishopCanBlock() {
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        // Clear board
        for (int x = 0; x < 8; x++) for (int y = 0; y < 8; y++) board.place(null, x, y);

        // King under diagonal check
        King king = new King(0, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king, 0, 0);

        // Queen threatening king
        Queen queen = new Queen(3, 3, Color.BLACK, PieceType.DARK, p2);
        board.place(queen, 3, 3);

        // Bishop can block at (2, 2)
        Bishop bishop = new Bishop(4, 1, Color.WHITE, PieceType.LIGHT, p1);
        board.place(bishop, 4, 1);

        assertFalse(game.isCheckmate(p1));
    }
    @Test
    public void testCheckButNotCheckmate_KnightCanCaptureAttacker() {
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        for (int x = 0; x < 8; x++) for (int y = 0; y < 8; y++) board.place(null, x, y);

        King king = new King(0, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king, 0, 0);

        Queen queen = new Queen(2, 1, Color.BLACK, PieceType.DARK, p2);
        board.place(queen, 2, 1);

        Knight knight = new Knight(1, 3, Color.WHITE, PieceType.LIGHT, p1); // Can move to (2,1)
        board.place(knight, 1, 3);

        assertFalse(game.isCheckmate(p1));
    }
    @Test
    public void testCheckButNotCheckmate_PawnCanBlock() {
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        for (int x = 0; x < 8; x++) for (int y = 0; y < 8; y++) board.place(null, x, y);

        King king = new King(7, 4, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king, 7, 4);

        Queen queen = new Queen(4, 4, Color.BLACK, PieceType.DARK, p2); // vertical check
        board.place(queen, 4, 4);

        Pawn pawn = new Pawn(6, 3, Color.WHITE, PieceType.LIGHT, p1); // Can move to 5,4 to block
        board.place(pawn, 6, 3);

        assertFalse(game.isCheckmate(p1));
    }
    @Test
    public void testCheckButNotCheckmate_RookCapturesAttacker() {
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        for (int x = 0; x < 8; x++) for (int y = 0; y < 8; y++) board.place(null, x, y);

        King king = new King(7, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king, 7, 0);

        Rook enemyRook = new Rook(7, 3, Color.BLACK, PieceType.DARK, p2); // horizontal check
        board.place(enemyRook, 7, 3);

        Rook myRook = new Rook(7, 5, Color.WHITE, PieceType.LIGHT, p1); // Can capture at (7,3)
        board.place(myRook, 7, 5);

        assertFalse(game.isCheckmate(p1));
    }
    @Test
    public void testCheckButNotCheckmate_KingCanEscape() {
        Chess game = new Chess();
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Board board = new Board(GameType.CHESS);
        game.setBoard(board);

        for (int x = 0; x < 8; x++) for (int y = 0; y < 8; y++) board.place(null, x, y);

        King king = new King(7, 0, Color.WHITE, PieceType.LIGHT, p1);
        board.place(king, 7, 0);

        Rook rook = new Rook(7, 2, Color.BLACK, PieceType.DARK, p2);
        board.place(rook, 7, 2);

        // Ensure escape square (6,0) is free
        assertFalse(game.isCheckmate(p1));
    }

}
