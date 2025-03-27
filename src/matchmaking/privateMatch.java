package matchmaking;

import player.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class privateMatch {
    private int roomID;
    private List<Player> players = new ArrayList<>();
    //FUNCTIONS TO ADD:
    //Host select game
    //Host start match

    /**
     * @author Logan Olszak
     * privateMatch is an initilizing function that assigns the new match with a unique room ID
     */
    public privateMatch() {
        //establish a unique room ID
        //ensure no duplicates by comparing ID to all other matches stored in database
    }

    /**
     * @author Logan Olszak
     * @param game      game ENUM to set the rooms match game as
     * hostSelectGame is a function that changes the type of game that is being played in a private match
     */
    public void hostSelectGame(GamesEnum game) {
        //set match game to game from
    }

    /**
     * @author Logan Olszak
     * hostStartGame is a function that starts the private matches selected game
     */
    public void hostStartGame() {
        //checks that there are two players in the private match
        //start the match when this function is called
    }
}