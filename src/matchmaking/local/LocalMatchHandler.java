package matchmaking.local;

import account.Account;

import java.util.ArrayList;

public class LocalMatchHandler {
    private ArrayList<PrivateMatch> privateMatches;

    public LocalMatchHandler() {
        this.privateMatches = new ArrayList<PrivateMatch>();
    }
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
        //need to work with GUI to open private match GUI, speaking of, need to get GUI team to make a private match GUI
        return match;
    }

    /**
     * @author Logan Olszak
     * @param clickedBy player who clicked to end the private match, match is disbanded if this player is the host
     * @param match     private match object that this is being closed for
     * closePrivateMatch is a method that closes a privateMatch object if the action is initialized by the host of the match
     */
    public void leavePrivateMatch(Account clickedBy, PrivateMatch match) {
        if (clickedBy == match.getHost()) {
            //If the host leaves the match, fully close down the match
            privateMatches.remove(match);
            //need to work with GUI to close private match GUI, speaking of, need to get GUI team to make a private match GUI
        }
        else {
            //If another player leaves the match, remove them from the match
            match.disconnectFromPrivateMatch(clickedBy);
        }
    }

    /**
     * @author Logan Olszak
     * @param account   account object trying to join the privateMatch
     * @param inputID   input ID of the match the player is trying to join
     * joinUsingID is a method that adds a player to an existing private match, found by its room ID
     */
    public void joinUsingID(Account account, String inputID) {
        for (PrivateMatch current : privateMatches) {
            if (current.getRoomID() == inputID) {
                current.connectToPrivateMatch(account);
                break;
            }
        }
    }

    /**
     * @author Logan Olszak
     * @param account   account object trying to join the privateMatch
     * @param friend    account object on player's friend list, this is the player hosting the private match
     * joinFromFriends is a method that adds a player to an existing private match, found by its host
     */
    public void joinFromFriends(Account account, Account friend) {
        for (PrivateMatch current : privateMatches) {
            if (current.getHost() == friend) {
                current.connectToPrivateMatch(account);
                break;
            }
        }
    }

    /**
     * getter for the private matches arraylist
     */
    public ArrayList<PrivateMatch> getPrivateMatches() {
        return privateMatches;
    }

}