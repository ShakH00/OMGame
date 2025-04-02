package game;

import account.Account;

public class Player {
    private final Account account;
    private String team;

    public Player(Account account) {
        this.account = account;
        System.out.println("game.Player Constructor");
    }

    private int getWinCount() {

        return 0;
    }

    private int getLoseCount() {
        
        return 0;
    }

    private String getTeam() {
    
        return team;
    }
    
    private boolean isturn() {

        return false;
    }

    private void surrender() {
        
    }

    public Account getAccount() {
        return account;
    }
}
