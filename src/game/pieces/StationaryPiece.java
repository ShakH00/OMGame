package game.pieces;

import game.Player;
import javafx.scene.paint.Color;
import java.io.Serializable;

public abstract class StationaryPiece extends Piece implements Serializable {
    public StationaryPiece(Color color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy, 1);
    }
}
