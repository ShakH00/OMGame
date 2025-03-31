
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class StartController extends Application {

    public AnchorPane rootPane;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            ImageView gifView = new ImageView(new Image(getClass().getResource("/images/twinklingstars.gif").toExternalForm()));

            StackPane root = new StackPane();
            root.getChildren().add(gifView);

            primaryStage.setResizable(false);

            // Set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private AnchorPane helpPopup;

    // open the help popup overlay
    @FXML
    private void openHelpPopup(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            // load help.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
            Parent helpRoot = loader.load();

            // get help controller
            HelpController helpController = loader.getController();

            rootPane.getChildren().add(helpRoot);  // rootPane is the main container in Start.fxml

            // set the helpRoot visible (it will be hidden initially)
            helpRoot.setVisible(true);

            // to close the popup, click anywhere
            helpRoot.setOnMouseClicked(event -> {
                helpRoot.setVisible(false);  // Hide the popup
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}