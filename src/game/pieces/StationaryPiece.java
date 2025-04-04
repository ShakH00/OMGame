package game.pieces;

import game.Player;
import javafx.scene.paint.Color;

public abstract class StationaryPiece extends Piece {
    public StationaryPiece(Color color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy, 1);
    }
}
