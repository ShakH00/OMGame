package networking;
import game.Game;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Main class for all the client-side networking. Initially used for Stubs.
 */
public class Networking {
    private Game cachedGame; // Internal cached instance of game that is sent/received between players
    public static final String timeFormatting = "yy.MM.dd HH:mm:ss.SSS"; // Template used for time stamp formatting

    private boolean isConnected = false; // Connection status, initialize without connection
    private String IP = "0.0.0.0";
    private int port = 9999;

    /**
     * Connects to the opponent player or server using a fixed IP address, returns boolean of connection status.
     *
     * @param IP IP Address of opponent in the format: 192.168.0.1
     * @param port Port of the server/client for connection
     * @return Status of if the connection was successful
     */
    public boolean connectToPlayer(String IP, int port){
        // Connection code
        isConnected = false;
        return false; // Return false on error
    }

    /**
     * Receives the entire game state (using Game object) from opponent and returns it for use locally. Prints debug information to console.
     *
     * @return Game object from opponent containing their latest turn
     * @author Nova Driscoll
     */
    public Game recieveGame() {
        System.out.printf("[Net: %s] Receiving Game from: %s:%d\n", getTime(),IP,port);
        return cachedGame;
    }

    /**
     * Sends the entire game state (using Game object) to opponent and caches it locally. Then puts the player into listening mode, thus preventing further input.  Prints debug information to console.
     *
     * @param game Game object to be sent to the opponent player, including just completed turn
     * @author Nova Driscoll
     */
    public void sendGame(Game game) {
        System.out.printf("[Net: %s] Sending Game to: %s:%d\n", getTime(),IP,port);
        cachedGame = game;
        listenMode();
    }

    /**
     * Puts client into listening mode, where it waits for incoming signal from opponent a
     *
     * @author Nova Driscoll
     */
    public void listenMode() {
        System.out.printf("[Net: %s] Listening Mode, not accepting player input.\n", getTime());
    }

    /**
     * Returns connection status
     *
     * @return Boolean of connection status
     * @author Uzair Ansari
     *
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Internal class to create debug time string
     * Used as reference: https://stackoverflow.com/questions/2942857/how-to-convert-current-date-into-string-in-java
     *
     * @return Returns current time in the format: yy.mm.dd hh:mm:ss.ms
     * @author Nova Driscoll
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(timeFormatting);
        return format.format(cal.getTime());
    }

}
