package game.pieces;

import game.Player;

public abstract class StationaryPiece extends Piece {
    public StationaryPiece(String color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy, 1);
    }
}
