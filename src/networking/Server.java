package networking;

import game.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Server class, used for both game and chat communication.
 *
 * @author Nova Driscoll, Sultan Amrani, Saqib Mazhar
 */
public class Server {
    private final List<ClientHandler> clients = new ArrayList<>();
    private final HashMap<Integer, String> chatLogs = new HashMap<>();
    private ServerSocket serverSocket;

    /**
     * Constructor for the Server class.
     * Initializes the server socket on the specified port.
     *
     * @param port The port number to bind the server socket to.
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.printf("[Server: %s] Server started on port %d", Networking.getTime(), port);
        } catch (IOException e) {
            System.err.printf("[Server: %s] Error creating server: %d", Networking.getTime(), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main method to start the server program.
     */
    public static void main(String[] args) {
        Server server = new Server(30001);
        server.start();
        System.out.printf("[Server: %s] Game/Chat server is running", Networking.getTime());
    }

    /**
     * Initializes the server to accept incoming connections.
     */
    public void start() {
        if (serverSocket == null) return;
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.printf("[Server: %s] New client connected: %s", Networking.getTime(), clientSocket.getInetAddress());
                    ClientHandler handler = new ClientHandler(clientSocket);
                    synchronized (clients) {
                        clients.add(handler);
                    }
                    handler.start();
                } catch (IOException e) {
                    System.err.printf("[Server: %s] Error accepting client: %s", Networking.getTime(), e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Broadasts a chat message to all clients.
     *
     * @param message The chat message to broadcast.
     * @param sender  The client that sent the message.
     */
    private void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && client.isActive()) {
                    client.sendMessage(message);
                }
            }
        }
    }

    /**
     * Broadcasts a game object to all clients.
     *
     * @param game   The game object to broadcast.
     * @param sender The client that sent the game object.
     */
    private void broadcast(Game game, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && client.isActive()) {
                    client.sendGame(game);
                }
            }
        }
    }

    /**
     * ClientHandler class, responsible for handling individual client connections.
     */
    private class ClientHandler {
        private static int nextId = 1;
        private final Socket socket;
        private final ObjectOutputStream out;
        private final ObjectInputStream in;
        private final int clientId;
        private boolean active = true;

        /**
         * Constructor for the ClientHandler class.
         *
         * @param socket The socket connected to the client.
         * @throws IOException If an I/O error occurs while creating the input/output streams.
         */
        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.clientId = nextId++;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush(); // DO NOT REMOVE THIS WHATSOEVER THIS RUINED MY LIFE
            this.in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client #" + clientId + " handler created");
        }

        /**
         * Checks if the client is active.
         *
         * @return true if the client is active, false otherwise.
         */
        public boolean isActive() {
            return active && socket != null && !socket.isClosed();
        }

        /**
         * Starts the client handler thread to listen for incoming messages.
         */
        public void start() {
            new Thread(() -> {
                try {
                    sendMessage("OMGAME: Welcome to the chat");
                    broadcast("OMGAME: Client #" + clientId + " has joined the chat", this);
                    while (isActive()) {
                        try {
                            Object objectIn = in.readObject();
                            if (objectIn instanceof String message) {
                                processChatMessage(message);
                            } else if (objectIn instanceof Game game) {
                                broadcast(game, this);
                            } else {
                                System.err.printf("[Server: %s] Received non-string/game object: %s", Networking.getTime(), objectIn.getClass().getName());
                            }
                        } catch (ClassNotFoundException e) {
                            System.err.printf("[Server: %s] Unknown object type received", Networking.getTime());
                        } catch (IOException e) {
                            System.err.printf("[Server: %s] Client #%d disconnected: %s", Networking.getTime(), clientId, e.getMessage());
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.err.printf("[Server: %s] Error handling client #%d: ", Networking.getTime(), clientId, e.getMessage());
                } finally {
                    cleanupClient();
                }
            }).start();
        }

        /**
         * Processes a chat message received from the client, and adds chat censoring.
         *
         * @param message The chat message to process.
         */
        private void processChatMessage(String message) {
            TextCensorship.CensorResult censored = TextCensorship.censorChat(message);
            String filteredContent = censored.filteredMessage();

            logChat(clientId, filteredContent);
            System.out.printf("[Server: %s] Client #%d: ", Networking.getTime(), clientId, filteredContent);

            sendMessage("Me: " + filteredContent);
            broadcast("Client #" + clientId + ": " + filteredContent, this);
        }

        /**
         * Logs the chat message.
         *
         * @param playerId The ID of the player sending the message.
         * @param message  The content of the chat message.
         */
        private void logChat(int playerId, String message) {
            String currentLog = chatLogs.getOrDefault(playerId, "");
            chatLogs.put(playerId, currentLog + message + "\n");
        }

        /**
         * Cleans up the client handler when the client disconnects.
         */
        private void cleanupClient() {
            active = false;
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.printf("[Server: %s] Error sending to client #%d: ", Networking.getTime(), clientId, e.getMessage());
            }

            synchronized (clients) {
                clients.remove(this);
            }
            broadcast("SYSTEM: Client #" + clientId + " has left the chat", null);
        }

        /**
         * Sends a message to the client.
         *
         * @param message The message to send.
         */
        public void sendMessage(String message) {
            try {
                if (isActive()) {
                    out.writeObject(message);
                    out.flush();
                }
            } catch (IOException e) {
                System.err.printf("[Server: %s] Error sending to client #%d: ", Networking.getTime(), clientId, e.getMessage());
                active = false;
            }
        }

        /**
         * Sends a game object to the client.
         *
         * @param game The game object to send.
         */
        public void sendGame(Game game) {
            try {
                if (isActive()) {
                    out.writeObject(game);
                    out.flush();
                }
            } catch (IOException e) {
                System.err.printf("[Server: %s] Error sending to client #%d: ", Networking.getTime(), clientId, e.getMessage());
                active = false;
            }
        }
    }
}