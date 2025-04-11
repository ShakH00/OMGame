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

public class Server {
    private final List<ClientHandler> clients = new ArrayList<>();
    private final HashMap<Integer, String> chatLogs = new HashMap<>();
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.err.println("Error creating server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(30001);
        server.start();
        System.out.println("Game/Chat server is running");
    }

    public void start() {
        if (serverSocket == null) return;

        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());


                    ClientHandler handler = new ClientHandler(clientSocket);
                    synchronized (clients) {
                        clients.add(handler);
                    }
                    handler.start();

                } catch (IOException e) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }
        }).start();
    }

    private void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && client.isActive()) {
                    client.sendMessage(message);
                }
            }
        }
    }

    private void broadcast(Game game, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && client.isActive()) {
                    client.sendGame(game);
                }
            }
        }
    }

    private class ClientHandler {
        private static int nextId = 1;
        private final Socket socket;
        private final ObjectOutputStream out;
        private final ObjectInputStream in;
        private final int clientId;
        private boolean active = true;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.clientId = nextId++;
            this.out = new ObjectOutputStream(socket.getOutputStream());


            //DO NOT REMOVE THIS WHATSOEVER THIS RUINED MY LIFE VV
            this.out.flush();
            // ^^


            this.in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client #" + clientId + " handler created");
        }

        public boolean isActive() {
            return active && socket != null && !socket.isClosed();
        }

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
                            }
                            if (objectIn instanceof Game game) {
                                broadcast(game, this);
                            } else {
                                System.err.println("Received non-string/game object: " + objectIn.getClass().getName());
                            }
                        } catch (ClassNotFoundException e) {
                            System.err.println("Unknown object type received");
                        } catch (IOException e) {
                            System.err.println("Client #" + clientId + " disconnected: " + e.getMessage());
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error handling client #" + clientId + ": " + e.getMessage());
                } finally {
                    cleanupClient();
                }
            }).start();
        }

        private void processChatMessage(String message) {

            TextCensorship.CensorResult censored = TextCensorship.censorChat(message);
            String filteredContent = censored.getFilteredMessage();


            logChat(clientId, filteredContent);

            System.out.println("Client #" + clientId + ": " + filteredContent);


            sendMessage("Me: " + filteredContent);


            broadcast("Client #" + clientId + ": " + filteredContent, this);
        }

        private void logChat(int playerId, String message) {
            String currentLog = chatLogs.getOrDefault(playerId, "");
            chatLogs.put(playerId, currentLog + message + "\n");
        }

        private void cleanupClient() {
            active = false;
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                //gnore
            }

            synchronized (clients) {
                clients.remove(this);
            }

            broadcast("SYSTEM: Client #" + clientId + " has left the chat", null);
        }

        public void sendMessage(String message) {
            try {
                if (isActive()) {
                    out.writeObject(message);
                    out.flush();
                }
            } catch (IOException e) {
                System.err.println("Error sending to client #" + clientId + ": " + e.getMessage());
                active = false;
            }
        }

        public void sendGame(Game game) {
            try {
                if (isActive()) {
                    out.writeObject(game);
                    out.flush();
                }
            } catch (IOException e) {
                System.err.println("Error sending to client #" + clientId + ": " + e.getMessage());
                active = false;
            }
        }
    }
}