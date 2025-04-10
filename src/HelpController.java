import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HelpController extends Application {

    @FXML
    private Label closeButton;

    @Override
        public void start(Stage primaryStage) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Help.fxml"));
                Scene scene = new Scene(loader.load(), 800, 570);

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
        private void handleCloseButton() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Help.fxml"));
            Parent helpRoot = loader.load();

            helpRoot.setOnMouseClicked(event -> {
                helpRoot.setVisible(false);  // hide the popup
            });
    }

        public static void main(String[] args) {
            launch(args);
        }
    }