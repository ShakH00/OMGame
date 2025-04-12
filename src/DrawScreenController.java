import javafx.application.Application;
import javafx.event.ActionEvent;
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
    private void handleStartOver(ActionEvent event) {
        System.out.println("Started  over");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //
        SceneManager.registerScenes("/screens/MatchType.fxml");
        SceneManager.switchScene(stage, "/screens/MatchType.fxml");

    }

    @FXML
    private void handleMainMenu(ActionEvent event) {
        System.out.println("Main menu");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //
        SceneManager.registerScenes("/screens/MatchType.fxml");
        SceneManager.switchScene(stage, "/screens/MatchType.fxml");

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