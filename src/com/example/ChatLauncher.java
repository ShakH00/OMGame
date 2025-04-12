package com.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;


public class ChatLauncher {


    public static ChatController launchChatWindow(Stage parentStage, int playerID, String playerName, int width, int height, int port) {
        try {
            URL cssUrl = ChatLauncher.class.getResource("/com/example/chat-styles.css");
            URL imageUrl = ChatLauncher.class.getResource("/com/example/ChatBG.png");

            if (cssUrl == null) {
                System.err.println("Warning: chat-styles.css not found. Make sure it's in the correct location.");
            }

            if (imageUrl == null) {
                System.err.println("Warning: ChatBG.png not found. Make sure it's in the correct location.");
            }

            FXMLLoader loader = new FXMLLoader(ChatLauncher.class.getResource("/com/example/Chat.fxml"));
            Parent root = loader.load();

            ChatController controller = loader.getController();

            // Pass the port to initializeChat
            controller.initializeChat(playerID, playerName, port);

            Stage chatStage = new Stage(StageStyle.TRANSPARENT);
            chatStage.initModality(Modality.NONE);
            if (parentStage != null) {
                chatStage.initOwner(parentStage);
            }

            Scene scene = new Scene(root, width, height);
            scene.setFill(Color.TRANSPARENT);
            chatStage.setScene(scene);
            chatStage.setResizable(false);

            setupDraggable(root, chatStage);

            chatStage.show();

            return controller;
        } catch (IOException e) {
            System.err.println("Error launching chat window: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Also add this overload method to maintain backward compatibility
    public static ChatController launchChatWindow(Stage parentStage, int playerID, String playerName, int width, int height) {
        // Default to port 30001 for backward compatibility
        return launchChatWindow(parentStage, playerID, playerName, width, height, 30001);
    }


    private static void setupDraggable(Parent root, Stage stage) {
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        root.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });
    }
}