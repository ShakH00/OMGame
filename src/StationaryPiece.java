public abstract class StationaryPiece extends Piece {
    public StationaryPiece(int x, int y, String color, PieceType pieceType,  Player ownedBy) {
        super(x, y, color, pieceType, ownedBy, 1);
    }
}
