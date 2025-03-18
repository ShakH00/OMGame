interface Game {
    Player player1 = new Player();
    Player player2 = new Player();
    int score1 = 0, score2 = 0;
    Board board = new Board();
    Piece selectedPiece = new Piece();
    GameState gameState = new GameState();
    GameRules gameRules = new GameRules();

    public void move(Piece piece);

    public void checkWinCondition();

    public void surrender();

    public void matchOutcome();
}
