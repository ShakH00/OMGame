package matchmaking;

import account.Account;

import java.util.ArrayList;

public class MatchHandler {
    private ArrayList<PrivateMatch> privateMatches;

    /**
     * @author Logan Olszak
     * @param host      player who initialized the private match, match is disbanded if this player leaves
     * @return          returns the new privateMatch object
     * createPrivateMatch is a method that initializes a new privateMatch object
     */
    public PrivateMatch createPrivateMatch(Account host) {
        PrivateMatch match = new PrivateMatch();
        match.setHost(host);
        privateMatches.add(match);
        return match;
    }

    /**
     * @author Logan Olszak
     * @param account   account object trying to join the privateMatch
     * @param inputID   input ID of the match the player is trying to join
     * joinUsingID is a method that adds a player to an existing private match, found by its room ID
     */
    public void joinUsingID(Account account, int inputID) {
        //Find match using the inputID
        //If match exists and has space for player, add the player
        //match.players.add(player);
    }

    /**
     * @author Logan Olszak
     * @param account   account object trying to join the privateMatch
     * @param friend    account object on player's friend list, this is the player hosting the private match
     * joinFromFriends is a method that adds a player to an existing private match, found by its host
     */
    public void joinFromFriends(Account account, Account friend) {
        //Find match using the player friend
        //If match exists and has space for player, add the player
        //match.players.add(player);
    }

    /**
     * getter for the private matches arraylist
     */
    public ArrayList<PrivateMatch> getPrivateMatches() {
        return privateMatches;
    }

}