import authentication.ExceptionsAuthentication.EncryptionFailedException;
import database.EncryptionAuthentication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static account.CreateAccount.createAccount;

public class SignUpController extends Application {
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    AnchorPane rootPane;
    @FXML
    private StackPane backButtonSignUp;
    @FXML
    private StackPane toLogin;
    @FXML
    private StackPane submitButton;
    @FXML
    private Text guestText;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Signup.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();

            Font pressStartFont = Font.loadFont(fontPath, 40);
            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
            Font pixeliteFont = Font.loadFont(pixelitePath, 40);

            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            primaryStage.setResizable(false);

            // Set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSubmitButton() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {

            return;
        }

        // Call the create account method
        boolean success;
        try {
            success = createAccount(username, email, password);
        }catch(EncryptionFailedException e){
            success = false;
        }

        if (success){
            // Switch to game menu?
        } else {
            // Show error
        }
    }

    public void initialize() {
        UtilityManager.createScaleTransition(backButtonSignUp);
        UtilityManager.createScaleTransition(toLogin);
        UtilityManager.createScaleTransition(submitButton);
        UtilityManager.colourTransition(guestText, Color.color(0.6235, 0.5961, 0.549, 1.0));
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
    private void switchToGameSelect(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/MatchType.fxml");
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
