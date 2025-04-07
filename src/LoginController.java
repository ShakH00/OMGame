import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Application {

    @FXML
    private Label closeButton;
    @FXML
    AnchorPane rootPane;
    @FXML
    private Button toSignUp;


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Login.fxml"));
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

    private void switchToSignup(ActionEvent event) {

        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Signup.fxml");
    }

    @FXML
    private void handleCloseButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Login.fxml"));
        Parent helpRoot = loader.load();
        helpRoot.setOnMouseClicked(event -> {
            helpRoot.setVisible(false);  // Hide the popup
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
