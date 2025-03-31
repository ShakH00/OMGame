
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    // Open the help popup overlay
    @FXML
    private void openHelpPopup(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            // Load the Help.fxml content into a Parent (as before)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
            Parent helpRoot = loader.load();

            // Get the controller of the Help popup
            HelpController helpController = loader.getController();

            // Add the helpRoot as a child of the main root (StackPane or your main container)
            rootPane.getChildren().add(helpRoot);  // rootPane is the main container in Start.fxml

            // Set the helpRoot visible (it will be hidden initially)
            helpRoot.setVisible(true);

            // Optionally, you can add an event listener to the root pane to close the popup
            // or trigger the help controller's close method when clicking outside the popup
            // e.g., to close the popup when clicking outside:
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