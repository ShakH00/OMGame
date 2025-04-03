package game.chess;

import game.Game;
import game.pieces.Piece;

public class Chess extends Game {

    public void move(Piece piece) {

    }

    /**
     * @author YOUSIF BEDAIR
     * @return
     */
    public boolean isPiecePinned(Piece piece){
        //check if moving the piece off its current tile puts that piece's king in check
        return false; //temporary
    }

    public void checkWinCondition() {

    }

    public void surrender() {

    }

    public void matchOutcome() {

    }
}
