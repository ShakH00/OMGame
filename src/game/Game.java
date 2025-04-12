package game;

import account.statistics.StatisticType;
import game.pieces.Piece;

import java.util.HashMap;

public abstract class Game {
    public Player player1;
    public Player player2;
    protected int score1;
    protected int score2;
    protected GameType gameType;
    protected GameState gameState;
    public Board board;
    //game.pieces.Piece selectedPiece;
    protected GameRules gameRules;

    protected abstract void checkWinCondition();

    protected abstract void surrender();

    protected abstract HashMap<StatisticType, Integer> matchOutcomeP1();

    protected abstract HashMap<StatisticType, Integer> matchOutcomeP2();
}
