package game;

import account.Account;

public class Player {
    private final Account account;
    private String team;

    public Player(Account account) {
        this.account = account;
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

    public Account getAccount() {
        return account;
    }
}
