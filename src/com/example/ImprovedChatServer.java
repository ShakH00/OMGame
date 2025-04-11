package com.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import account.LoggedInAccount;
import networking.test.CensorshipTest;

public class ImprovedChatServer {
    private ServerSocket chatServerSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private HashMap<Integer, String> chatLogs = new HashMap<>();

    public ImprovedChatServer(int port) {
        try {
            chatServerSocket = new ServerSocket(port);
            System.out.println("Chat server started on port " + port);
        } catch (IOException e) {
            System.out.println("Error creating server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void start() {
        if (chatServerSocket == null) return;

        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = chatServerSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());


                    ClientHandler handler = new ClientHandler(clientSocket);
                    synchronized (clients) {
                        clients.add(handler);
                    }
                    handler.start();

                } catch (IOException e) {
                    System.out.println("Error accepting client: " + e.getMessage());
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

    private class ClientHandler {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private int clientId;
        private String clientUsername;
        private static int nextId = 1;
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
                            Object msg = in.readObject();


                            if (msg instanceof String) {
                                String message = (String) msg;
                                processChatMessage(message);
                            } else {
                                System.out.println("Received non-string object: " + msg.getClass().getName());
                            }
                        } catch (ClassNotFoundException e) {
                            System.out.println("Unknown object type received");
                        } catch (IOException e) {
                            System.out.println("Client #" + clientId + " disconnected: " + e.getMessage());
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error handling client #" + clientId + ": " + e.getMessage());
                } finally {
                    cleanupClient();
                }
            }).start();
        }

        private void processChatMessage(String message) {

            CensorshipTest.CensorResult censored = CensorshipTest.censorChat(message);
            String filteredContent = censored.getFilteredMessage();


            logChat(clientId, filteredContent);

            System.out.println("Client #" + clientId + ": " + filteredContent);


            sendMessage("Me: " + filteredContent);

            String name = "saqib";
            broadcast("Client #" + name + ": " + filteredContent, this);
        }

        private void logChat(int playerId, String message) {
            String name = "saqib";
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
                System.out.println("Error sending to client #" + clientId + ": " + e.getMessage());
                active = false;
            }
        }
    }

    public static void main(String[] args) {
        ImprovedChatServer server = new ImprovedChatServer(30001);
        server.start();
        System.out.println("Chat server is running");
    }
}