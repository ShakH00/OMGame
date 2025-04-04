package game.connect4;

import game.Player;
import game.pieces.PieceType;
import game.pieces.StationaryPiece;
import javafx.scene.paint.Color;

public class Connect4Piece extends StationaryPiece {
    public Connect4Piece(Color color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy);
    }
}
