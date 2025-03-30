public class Connect4 extends Game {

    public Connect4() {
        super.player1 = new Player();
        super.player2 = new Player();
        super.gameType = GameType.CONNECT4;
        super.board = new Board(gameType);
        super.score1 = 0;
        super.score2 = 0;
        super.gameState = GameState.SETUP;
        super.gameRules = new GameRules();
    }
    void move(Piece piece) {

    }

    void checkWinCondition() {

    }

    void surrender() {

    }

    void matchOutcome() {

    }
}
