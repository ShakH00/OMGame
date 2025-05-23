package game.connect4;

import game.Player;
import game.Board;
import game.pieces.PieceType;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Connect4Testing {
    @Test
    public void testWinInCol() {
        Connect4 game = new Connect4();
        Player player = game.player1;
        Board board = game.board;
        Color red = Color.RED;
        Connect4Piece piece = new Connect4Piece(red, PieceType.DARK, player);

        for (int row = 5; row >= 2; row--) {
            board.getBoardState()[row][2] = piece;
        }

        assertTrue(game.winInCol(2, piece));
    }
    @Test
    public void testWinInRow() {
        Connect4 game = new Connect4();
        Player player = game.player1;
        Board board = game.board;

        Color red = Color.RED;
        Connect4Piece piece = new Connect4Piece(red, PieceType.DARK, player);

        // Place 4 red pieces in row 5 (bottom row), columns 1 to 4
        for (int col = 1; col <= 4; col++) {
            board.getBoardState()[5][col] = piece;
        }

        assertTrue(game.winInRow(5, piece));
    }
    @Test
    public void testWinInDiagonalBackslash() {
        Connect4 game = new Connect4();
        Player player = game.player1;
        Board board = game.board;
        Color red = Color.RED;
        Connect4Piece piece = new Connect4Piece(red, PieceType.DARK, player);

    /*
     Diagonal like:
     (2,0)
        (3,1)
           (4,2)
              (5,3)
    */
        for (int i = 0; i < 4; i++) {
            int row = 2 + i;
            int col = i;
            board.getBoardState()[row][col] = piece;
        }

        assertTrue(game.winInDiagonalBackslash(piece));
    }
    @Test
    public void testWinInDiagonalFrontslash() {
        Connect4 game = new Connect4();
        Player player = game.player1;
        Board board = game.board;
        Color red = Color.RED;
        Connect4Piece piece = new Connect4Piece(red, PieceType.DARK, player);

        for (int i = 0; i < 4; i++) {
            int row = 5 - i;
            int col = i;
            board.getBoardState()[row][col] = piece;
        }

        assertTrue(game.winInDiagonalFrontslash(piece));
    }



}
