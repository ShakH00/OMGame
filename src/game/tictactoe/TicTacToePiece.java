package game.tictactoe;

import game.Player;
import game.pieces.PieceType;
import game.pieces.StationaryPiece;
import javafx.scene.paint.Color;

public class TicTacToePiece extends StationaryPiece {
    public TicTacToePiece(Color color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy);
    }
}
