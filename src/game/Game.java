package game;

import game.pieces.Piece;

public abstract class Game {
    public Player player1;
    protected Player player2;
    protected int score1;
    protected int score2;
    protected GameType gameType;
    protected GameState gameState;
    protected Board board;
    //game.pieces.Piece selectedPiece;
    protected GameRules gameRules;

    protected abstract void move(Piece piece);

    protected abstract void checkWinCondition();

    protected abstract void surrender();

    protected abstract void matchOutcome();
}
