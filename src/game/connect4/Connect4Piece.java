package game.connect4;

import game.Player;
import game.pieces.PieceType;
import game.pieces.StationaryPiece;

public class Connect4Piece extends StationaryPiece {
    public Connect4Piece(String color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy);
    }
}
