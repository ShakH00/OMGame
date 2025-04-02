package matchmaking;

import matchmaking.Matchmaking;
public class ChessMatchMaking extends Matchmaking {
    private long startingTime;
    private int findElo;
    private int increment;

    public ChessMatchMaking() {
        this.kFactor = 32;
    }

    @Override
    public void changeElo(int elo) {

    }

    public void increaseRange(int elo) {

    }

    public void findTwoPlayers() {

    }
}
