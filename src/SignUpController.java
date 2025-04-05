import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController extends Application {


    @FXML
    AnchorPane rootPane;
    @FXML
    private StackPane backButtonSignUp;
    @FXML
    private StackPane toLogin;
    @FXML
    private StackPane submitButton;
    @FXML
    private StackPane guestButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Signup.fxml"));
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
        StartController.createScaleTransition(backButtonSignUp);
        StartController.createScaleTransition(toLogin);
        StartController.createScaleTransition(submitButton);
        StartController.createScaleTransition(guestButton);
    }

    @FXML
    private void switchToLogin(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Login.fxml");
    }

    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }

    @FXML
    private void handleCloseButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Signup.fxml"));
        Parent helpRoot = loader.load();
        helpRoot.setOnMouseClicked(event -> {
            helpRoot.setVisible(false);  // Hide the popup
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
