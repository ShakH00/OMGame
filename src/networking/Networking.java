package networking;
import game.Game;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 *
 */
public class Networking {
    private Game cachedGame; // Internal cached instance of game that is sent/received between players
    public static final String timeFormatting = "yy.MM.dd HH:mm:ss.SSS"; // Template used for time stamp formatting

    /**
     * Receives the entire game state (using Game object) from opponent and returns it for use locally. Prints debug information to console.
     *
     * @return Game object from opponent containing their latest turn
     * @author Nova Driscoll
     */
    public Game recieveGame() {
        System.out.printf("[Net: %s] Receiving Game from: [TO ADD PLAYER IP]", getTime());
        return cachedGame;
    }

    /**
     * Sends the entire game state (using Game object) to opponent and caches it locally. Then puts the player into listening mode, thus preventing further input.  Prints debug information to console.
     *
     * @param game
     * @author Nova Driscoll
     */
    public void sendGame(Game game) {
        System.out.printf("[Net: %s] Sending Game from: [TO ADD PLAYER IP]", getTime());
        cachedGame = game;
        listenMode();
    }

    /**
     * Puts client into listening mode, where it waits for incoming signal from opponent a
     * @author Nova Driscoll
     */
    public void listenMode() {
        System.out.printf("[Net: %s] Listening Mode, not accepting player input.", getTime());
    }

    /**
     * Internal class to create debug time string
     *
     * @return Returns current time in the format: yy.mm.dd hh:mm:ss.ms
     * @author Nova Driscoll
     */
    private static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(timeFormatting);
        return format.format(cal.getTime());
    }

}
