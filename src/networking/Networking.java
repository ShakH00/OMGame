package networking;

import game.Game;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Main class for all the client-side networking. Initially used for Stubs.
 *
 * @author
 */
public class Networking {
    private static final String timeFormatting = "yy.MM.dd HH:mm:ss.SSS"; // Template used for time stamp formatting
    private final int MAX_RECONNECT_ATTEMPTS = 3;
    private final String IP = "localhost";
    private final int port = 30001;
    private Game cachedGame; // Internal cached instance of game that is sent/received between players
    private boolean isConnected = false; // Connection status, initialize without connection
    private Socket socket;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    private int reconnectAttempts = 0;
    public boolean gameRecieved = false; // Flag to indicate if game has been received
    


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

    /**
     * Receives the entire game state (using Game object) from opponent and returns it for use locally. Prints debug information to console.
     *
     * @return Game object from opponent containing their latest turn
     * @author Nova Driscoll
     */
    public Game recieveGame() {
        System.out.printf("[Net: %s] Receiving Game from: %s:%d\n", getTime(), IP, port);
        gameRecieved = false;
        return cachedGame;
    }

    /**
     * Sends the entire game state (using Game object) to opponent and caches it locally. Then puts the player into listening mode, thus preventing further input.  Prints debug information to console.
     *
     * @param game Game object to be sent to the opponent player, including just completed turn
     * @author Nova Driscoll
     */
    public void sendGame(Game game) {
        System.out.printf("[Net: %s] Sending Game to: %s:%d\n", getTime(), IP, port);
        if (isConnected) {
            try {
                outObj.writeObject(game);
                outObj.flush(); 
                System.out.println("Message sent successfully");
            } catch (IOException e) {
                System.err.println("Send failed: " + e.getMessage());
                handleDisconnection();
            }
        }
    }

    /**
     * Puts client into listening mode, where it waits for incoming game from server
     *
     * @author Nova Driscoll
     */
    private void listenMode() {
        new Thread(() -> {
            try {
                while (isConnected && !Thread.currentThread().isInterrupted()) {
                    Object receivedObj = inObj.readObject();
                    Game receivedGame;
                    if (receivedObj instanceof Game) {
                        receivedGame = (Game) receivedObj;
                        if (!receivedGame.equals(cachedGame)) {
                            gameRecieved = true;
                            updateGame(receivedGame);
                        }
                    }
                }
                System.out.println("Chat listener thread ending");
            } catch (Exception e) {
                System.err.println("Listener thread exception: " + e.getMessage());
            }
        }).start();
    }

    private void updateGame(Game receivedGame) {
        cachedGame = receivedGame;
        System.out.println("Game updated with new state");
    }

    public void initialize(int playerID, String playerName) {
        System.out.println("Status: Connected to server.");

        try {
            connectToServer();
        } catch (Exception e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
    }


    private void connectToServer() {
        try {
            System.out.println("Attempting to connect to chat server...");

            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.err.println("Error closing previous socket: " + ex.getMessage());
                }
            }


            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 5000);
            System.out.println("Socket connected!");


            outObj = new ObjectOutputStream(socket.getOutputStream());
            outObj.flush();
            System.out.println("Output stream created");

            inObj = new ObjectInputStream(socket.getInputStream());
            System.out.println("Input stream created");

            isConnected = true;
            reconnectAttempts = 0;
            System.out.println("Connection established successfully");


            System.out.println("Connected to chat server");


            listenMode();

        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();

            isConnected = false;
            System.err.println("Failed to connect: " + e.getMessage());


            Platform.runLater(() -> {
                System.out.println("SYSTEM: Chat server is offline.\n");
            });
        } catch (Exception e) {
            System.out.println(" error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleDisconnection() {
        isConnected = false;
        System.out.println("Disconnected. Attempting to reconnect...");


        if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
            reconnectAttempts++;


            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    connectToServer();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            System.out.println("Failed to reconnect after multiple attempts.");
        }
    }

    public void handleCloseChat() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            isConnected = false;
        } catch (IOException e) {
            System.out.println("Error closing chat: " + e.getMessage());
        }


    }

}
