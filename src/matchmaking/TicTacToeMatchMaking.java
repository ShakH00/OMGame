package matchmaking;

import matchmaking.Matchmaking;
public class TicTacToeMatchMaking extends Matchmaking {
    private long startingTime;
    private int findElo;
    private int increment;

    public TicTacToeMatchMaking() {
        this.kFactor = 10;
    }

    @Override
    public void changeElo(int elo) {

    }

    public void increaseRange(int elo) {

    }

    public void findTwoPlayers() {

    }
}
