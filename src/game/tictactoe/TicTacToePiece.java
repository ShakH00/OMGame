package game.tictactoe;

import game.Player;
import game.pieces.PieceType;
import game.pieces.StationaryPiece;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class TicTacToePiece extends StationaryPiece implements Serializable {
    public TicTacToePiece(Color color, PieceType pieceType, Player ownedBy) {
        super(color, pieceType, ownedBy);
    }
}
