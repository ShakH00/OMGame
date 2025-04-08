import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController extends Application {

    @FXML
    AnchorPane rootPane;
    @FXML
    private StackPane backButtonLogin;
    @FXML
    private StackPane toSignUp;
    @FXML
    private StackPane submitButton;
    @FXML
    private StackPane guestButton;
    @FXML
    private Text guestText;

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

    public void initialize() {
        StartController.createScaleTransition(backButtonLogin);
        StartController.createScaleTransition(toSignUp);
        StartController.createScaleTransition(submitButton);
        StartController.createScaleTransition(guestButton);
    }

    @FXML
    private void switchToSignup(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Signup.fxml");
    }

    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }

    @FXML
    private void switchToGameSelect(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/gameSelect.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
