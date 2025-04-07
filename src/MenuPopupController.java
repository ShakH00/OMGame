import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPopupController extends Application {
    @FXML
    private StackPane closeButton;
    @FXML
    private StackPane drawButton;
    @FXML
    private StackPane resignButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MenuPopup.fxml"));
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
    public void initialize() {
        UtilityManager.createScaleTransition(closeButton);
        UtilityManager.createScaleTransition(drawButton);
        UtilityManager.createScaleTransition(resignButton);
    }

    @FXML
    private void closeButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MenuPopup.fxml"));
        Parent helpRoot = loader.load();
        helpRoot.setOnMouseClicked(event -> {
            helpRoot.setVisible(false);  // hide the popup
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

