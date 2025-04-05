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
        Chess game = new Chess(p1, p2);
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
        Chess game = new Chess(p1, p2);
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
        Chess game = new Chess(p1, p2);
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
        Chess game = new Chess(p1, p2);
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
}
