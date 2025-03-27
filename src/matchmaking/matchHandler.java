package matchmaking;

import matchmaking.privateMatch;

public class matchHandler {


    /**
     * @author Logan Olszak
     * @param host      player who initialized the private match, match is disbanded if this player leaves
     * @return          returns the new privateMatch object
     * createPrivateMatch is a method that initializes a new privateMatch object
     */
    public privateMatch createPrivateMatch(Player host) {
        return null;
    }

    /**
     * @author Logan Olszak
     * @param player    player object trying to join the privateMatch
     * @param inputID   input ID of the match the player is trying to join
     * joinUsingID is a method that adds a player to an existing private match, found by its room ID
     */
    public void joinUsingID(Player player, int inputID) {
        //Find match using the inputID
        //If match exists and has space for player, add the player
        //match.players.add(player);
    }

    /**
     * @author Logan Olszak
     * @param player    player object trying to join the privateMatch
     * @param friend    player object on player's friend list, this is the player hosting the private match
     * joinFromFriends is a method that adds a player to an existing private match, found by its host
     */
    public void joinFromFriends(Player player, Player friend) {
        //Find match using the player friend
        //If match exists and has space for player, add the player
        //match.players.add(player);
    }

}