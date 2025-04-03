package matchmaking;

import game.GameType;
import account.*;

import java.util.ArrayList;
import java.util.Random;

public class PrivateMatch {
    private String roomID;

    private Account host;

    private GameType gameType;

    //letting accounts be greater than 2 in case future games have more than 2 players or spectator system implemented
    private ArrayList<Account> accounts = new ArrayList<>();

    String possibleIDCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    //FUNCTIONS TO ADD:
    //Host select game
    //Host start match

    /**
     * @author Logan Olszak
     * privateMatch is an initilizing function that assigns the new match with a unique room ID
     */
    public PrivateMatch() {
        //establish a unique room ID
        this.roomID = findUniqueID();
    }

    /**
     * @author Logan Olszak
     * @param game      game ENUM to set the rooms match game as
     * hostSelectGame is a function that changes the type of game that is being played in a private match
     */
    public void hostSelectGame(GameType game) {
        this.gameType = game;
    }

    /**
     * @author Logan Olszak
     * hostStartGame is a function that starts the private matches selected game
     */
    public void hostStartGame() {
        if (accounts.size() >= 2) {
            //start the match when this function is called
        }
    }

    /**
     * @param account account object that initialized the creation of the private match
     *
     * Setter method for the private matches host
     */
    public void setHost(Account account) {
        this.host = account;
        accounts.add(host);
    }

    /**
     * @author Logan Olszak
     * @return Unique ID string of length 6
     * findUniqueID is a function that generates random 6 character room IDs until a unique ID is found
     */
    public String findUniqueID() {
        String potentialID = "";
        boolean loop = true;
        while (loop) {
            potentialID = generateRandomID();
            //NEED TO CREATE a MatchHandler object to call getPrivateMatches() on in main program once that exists
            ArrayList<PrivateMatch> matches = null; //Should eventualy be getPrivateMatches() instead of null
            boolean matching = false;
            for (PrivateMatch current : matches) {
                if (current.roomID == potentialID) {
                    matching = true;
                }
            }
            if (matching == false) {
                loop = false;
            }
        }
        return potentialID;
    }

    /**
     * @author Logan Olszak
     * @return String random string of length 6
     *
     * generateRandomID is a function that generates a random 6 character room ID using characters A-Z and 0-9
     */
    public String generateRandomID() {
        StringBuilder idString = new StringBuilder ();
        Random rand =new Random();
        for (int i = 0; i < 6; i++) {
            int randIndex=rand.nextInt(possibleIDCharacters.length());
            idString.append(possibleIDCharacters.charAt(randIndex));
        }
        return idString.toString();
    }
}