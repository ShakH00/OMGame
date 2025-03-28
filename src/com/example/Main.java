package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load the win screen
        //Parent root = FXMLLoader.load(getClass().getResource("WinScreen.fxml"));
        //primaryStage.setTitle("You Win!");

        Parent root = FXMLLoader.load(getClass().getResource("DrawScreen.fxml"));
        primaryStage.setTitle("You Have Drawn");


        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}