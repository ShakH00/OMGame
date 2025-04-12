package game;

import game.pieces.Piece;
import java.io.Serializable;

public abstract class Game implements Serializable {
    public Player player1;
    protected Player player2;
    protected int score1;
    protected int score2;
    protected GameType gameType;
    protected GameState gameState;
    public Board board;
    //game.pieces.Piece selectedPiece;
    protected GameRules gameRules;

    protected abstract void checkWinCondition();

    protected abstract void surrender();

    protected abstract void matchOutcome();
}
