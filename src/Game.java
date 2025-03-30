abstract class Game {
    public Player player1;
    Player player2;
    int score1, score2;
    GameType gameType;
    GameState gameState;
    Board board;
    //Piece selectedPiece;
    GameRules gameRules;

    abstract void move(Piece piece);

    abstract void checkWinCondition();

    abstract void surrender();

    abstract void matchOutcome();
}
