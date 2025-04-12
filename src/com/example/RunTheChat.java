package com.example;

import javafx.application.Application;
import javafx.stage.Stage;


public class RunTheChat extends Application {

    private ChatController chatController;
    private int playerID = 1;

    @Override
    public void start(Stage primaryStage) {

        try {
            chatController = ChatLauncher.launchChatWindow(null, playerID, "Player" + playerID, 225, 300);
            if (chatController == null) {
                System.err.println("Failed to open chat");
            }
        } catch (Exception ex) {
            System.err.println("Error opening chat: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {

        if (chatController != null) {
            chatController.handleCloseChat();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}