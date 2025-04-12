package game;

import java.io.Serializable;

public class Player implements Serializable {
    private String team;

    public Player() {
        System.out.println("game.Player Constructor");
    }

    public int getWinCount() {
        return 0;
    }

    public int getLoseCount() {
        return 0;
    }

    public String getTeam() {
        return team;
    }

    public boolean isTurn() {
        return false;
    }

    private void surrender() {

    }

}
