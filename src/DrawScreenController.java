import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class DrawScreenController extends Application {

    @FXML
    AnchorPane rootPane;

    @FXML
    private void handleStartOver(javafx.event.ActionEvent event) {
        System.out.println("Started over");
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

        // TODO: Add logic to restart the game
    }

    @FXML
    private void handleMainMenu(javafx.event.ActionEvent event) {
        System.out.println("Main menu");
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        stage.close();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //
        SceneManager.registerScenes("/screens/Start.fxml");



        SceneManager.switchScene(stage, "/screens/Start.fxml");

//
//        // TODO: Add logic to go to the main menu
    }


    @Override
    public void start(Stage primaryStage) {
//        SceneManager.registerScenes("/screens/Start.fxml");  // e
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/Start.fxml"));
//            Parent root = loader.load();
//            primaryStage.setTitle("Main Menu");
//            primaryStage.setScene(new Scene(root, 800, 570));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}