package com.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import networking.TextCensorship;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

public class ChatController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageInput;

    @FXML
    private Button sendButton;

    @FXML
    private Button closeButton;

    @FXML
    private Label statusLabel;

    private Socket chatSocket;
    private ObjectOutputStream chatOutObj;
    private ObjectInputStream chatInObj;
    private int playerID;
    private String playerName;
    private HashMap<Integer, String> chatLogs;
    private boolean isConnected = false;
    private int reconnectAttempts = 0;
    private final int MAX_RECONNECT_ATTEMPTS = 3;
    private int port;


    private boolean expectingEchoMessage = false;
    private String lastSentMessage = "";


//    public void initializeChat(int playerID, String playerName, int port) {
//        this.playerID = playerID;
//        this.playerName = playerName;
//        this.port = port; // Add this field to your class
//        statusLabel.setText("Status: Connecting as Player " + playerID);
//
//        try {
//            connectToServer(port); // Modify this method to accept the port
//        } catch (Exception e) {
//            updateStatus("Failed to connect to chat server: " + e.getMessage());
//        }
//    }


    public void initializeChat(int playerID, String playerName, int port) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.chatLogs = new HashMap<>();  // Initialize chatLogs
        statusLabel.setText("Status: Connected as Player " + playerID);

        try {
            connectToServer(port);
        } catch (Exception e) {
            updateStatus("Failed to connect to chat server: " + e.getMessage());
        }
    }

    // Add an overloaded method for backward compatibility



    private void connectToServer(int port) {
        try {
            System.out.println("Attempting to connect to chat server on port " + port + "...");

            if (chatSocket != null && !chatSocket.isClosed()) {
                try {
                    chatSocket.close();
                } catch (IOException ex) {
                    // Silently ignore close exceptions
                }
            }

            chatSocket = new Socket();
            chatSocket.connect(new InetSocketAddress("localhost", port), 5000);
            System.out.println("Socket connected!");

            chatOutObj = new ObjectOutputStream(chatSocket.getOutputStream());
            chatOutObj.flush();
            System.out.println("Output stream created");

            chatInObj = new ObjectInputStream(chatSocket.getInputStream());
            System.out.println("Input stream created");

            isConnected = true;
            reconnectAttempts = 0;
            System.out.println("Connection established successfully");

            updateStatus("Connected to chat server");

            startChatListener();

        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();

            isConnected = false;
            updateStatus("Failed to connect: " + e.getMessage());

            Platform.runLater(() -> {
                chatArea.appendText("SYSTEM: Chat server is offline\n");
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Also add an overloaded method for backward compatibility
    private void connectToServer() {
        // Default to port 30001 for backward compatibility
        connectToServer(30001);
    }


    private void startChatListener() {
        new Thread(() -> {
            try {
                while (isConnected && !Thread.currentThread().isInterrupted()) {
                    try {
                        Object receivedObj = chatInObj.readObject();
                        System.out.println("Received message: " + receivedObj);

                        if (receivedObj instanceof String) {
                            String receivedMsg = (String) receivedObj;


                            if (expectingEchoMessage && receivedMsg.equals("Me: " + lastSentMessage)) {

                                expectingEchoMessage = false;
                            } else {
                                displayMessage(receivedMsg);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Read exception: " + e.getMessage());
                        handleDisconnection();
                        break;
                    } catch (ClassNotFoundException e) {
                        System.out.println("Class not found: " + e.getMessage());
                    }
                }

                System.out.println("Chat listener thread ending");
            } catch (Exception e) {
                System.out.println("Listener thread exception: " + e.getMessage());
            }
        }).start();
    }


    private void displayMessage(String message) {
        Platform.runLater(() -> {
            chatArea.appendText(message + "\n");
        });
    }


    private void handleDisconnection() {
        isConnected = false;
        updateStatus("Disconnected. Attempting to reconnect...");


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
            updateStatus("Failed to reconnect after multiple attempts.");
        }
    }


    @FXML
    public void handleSendMessage() {
        String message = messageInput.getText().trim();

        if (!message.isEmpty()) {
            try {

                TextCensorship.CensorResult censorResult = TextCensorship.censorChat(message);
                String filteredMessage = censorResult.filteredMessage();


                String currentLog = chatLogs.getOrDefault(playerID, "");
                chatLogs.put(playerID, currentLog + "Me: " + filteredMessage + "\n");


                displayMessage("Me: " + filteredMessage);


                expectingEchoMessage = true;
                lastSentMessage = filteredMessage;

                messageInput.clear();


                if (isConnected) {
                    try {
                        System.out.println("Sending message: " + filteredMessage);


                        chatOutObj.writeObject(filteredMessage);
                        chatOutObj.flush();

                        System.out.println("Message sent successfully");
                    } catch (IOException e) {
                        System.out.println("Send failed: " + e.getMessage());
                        handleDisconnection();
                    }
                }
            } catch (Exception e) {
                System.out.println("Send message error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void handleCloseChat() {
        try {
            if (chatSocket != null && !chatSocket.isClosed()) {
                chatSocket.close();
            }
            isConnected = false;
        } catch (IOException e) {
            System.out.println("Error closing chat: " + e.getMessage());
        }

        // Close the window
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    private void updateStatus(String message) {
        Platform.runLater(() -> {
            statusLabel.setText("Status: " + message);
        });
    }


    public HashMap<Integer, String> getChatLogs() {
        return chatLogs;
    }
}