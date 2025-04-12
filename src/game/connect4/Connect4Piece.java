package game.connect4;

import game.Player;
import game.pieces.PieceType;
import game.pieces.StationaryPiece;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class Connect4Piece extends StationaryPiece implements Serializable {
    public Connect4Piece(Color color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy);
    }
}
