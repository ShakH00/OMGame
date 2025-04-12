import account.Account;
import account.LoggedInAccount;
import authentication.Authentication.MFAAuthentication;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import authentication.ExceptionsAuthentication.EncryptionFailedException;
import authentication.ExceptionsAuthentication.MFAAuthenticationFailedException;
import database.DatabaseManager;
import database.DecryptionAuthentication;
import database.EncryptionAuthentication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Text guestText;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text notificationText;


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Login.fxml"));
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

    public void initialize() {
        UtilityManager.createScaleTransition(backButtonLogin);
        UtilityManager.createScaleTransition(toSignUp);
        UtilityManager.createScaleTransition(submitButton);
        UtilityManager.colourTransition(guestText, Color.color(0.6235, 0.5961, 0.549, 1.0));
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
    private void login(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        String username = usernameField.getText();
        String password = passwordField.getText();


        if(username.equals("admin") && password.equals("admin")){
            SceneManager.switchScene(stage, "screens/AdminScreen.fxml");
            return;
        }
        Account user = null;
        try {
            user = DatabaseManager.queryAccountByEmail(EncryptionAuthentication.encryptionDriver(username));
        } catch (EncryptionFailedException e) {

        }
        if(user == null){
            // If the account wasn't found via email, try via username
            user = DatabaseManager.queryAccountByUsername(username);
        }
        boolean accountExists = false;
        if(user != null) {
            accountExists = true;
            user.setIsGuest(false);
            LoggedInAccount.setAccount(user);
        }
        if(accountExists){
            try {
                if ((DecryptionAuthentication.decryptionDriver(user.getPassword())).equals(password)) {
                    // If the password matches the username/email, log them in
                    openMFAPopup(user.getEmail());
                    // TODO: remove to use mfa properly
                    SceneManager.switchScene(stage, "screens/MatchType.fxml");
                    return;
                }else{
                    notificationText.setText("Incorrect username or password!");
                }
            } catch (DecryptionFailedException e) {
                System.out.println(e);
            }
        } else if (username.isEmpty()){
            notificationText.setText("Please enter a username!");
            return;
        } else if (password.isEmpty()) {
            notificationText.setText("Please enter a password!");
            return;
        } else {
            notificationText.setText("Incorrect username or password!");
            return;
        }
    }

    @FXML
    private void continueAsGuest(javafx.scene.input.MouseEvent mouseEvent) {
        LoggedInAccount.setAccount(new Account());
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/MatchType.fxml");
    }


    @FXML
    private void openMFAPopup(String email) {
        // TODO: turn errors into messages in gui
        // TODO: make sure it doesnt grant access to games if code is wrong or cancelled
        try {
            // Generate and send the verification code via email
            String verificationCode = MFAAuthentication.emailAuthenticatorDriver(email);

            // Load the FXML file for the MFA pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MFAPopup.fxml"));
            Parent root = loader.load();

            // Get the controller and set the verification code
            MFAPopupController controller = loader.getController();
            controller.setVerificationCode(verificationCode); // Pass the generated code to the controller


        } catch (IOException e) {
            e.printStackTrace();
        } catch (MFAAuthenticationFailedException e) {
            e.printStackTrace();
            System.err.println("Failed to send the verification code: " + e.getMessage());
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}
