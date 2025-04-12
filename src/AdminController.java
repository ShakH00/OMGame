import account.Account;
import account.statistics.StatisticsCheckers;
import account.statistics.StatisticsChess;
import account.statistics.StatisticsConnect4;
import account.statistics.StatisticsTicTacToe;
import authentication.Authentication.Admin;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import database.DatabaseManager;
import database.DecryptionAuthentication;
import game.GameType;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;

public class AdminController extends Application {
    @FXML
    AnchorPane rootPane;
    @FXML
    private StackPane findUserButton;
    @FXML
    private StackPane clearStatsButton;
    @FXML
    private StackPane deleteUserButton;
    @FXML
    private StackPane submitButton;
    @FXML
    private StackPane backButton;
    @FXML
    private TextField IDField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;



    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/AdminScreen.fxml"));
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
            primaryStage.setTitle("OMG! - Admin");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    initialize button effects
     */
    public void initialize() {
        UtilityManager.createScaleTransition(backButton);
        UtilityManager.createScaleTransition(findUserButton);
        UtilityManager.createScaleTransition(clearStatsButton);
        UtilityManager.createScaleTransition(deleteUserButton);
        UtilityManager.createScaleTransition(submitButton);
    }

    /*
    return to home screen when back button is pressed
     */
    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }

    /*
    search for user when find user button is pressed
     */
    @FXML
    private void findUserButton() {
        // Search for user matching the id in the field
        String userIDStr = IDField.getText();
        if(!userIDStr.isEmpty()){
            Integer userID = Integer.parseInt(userIDStr);
            Account player = DatabaseManager.queryAccountByID(userID);
            if (player != null) {
                // If the player is found, populate the fields
                userNameField.setText(player.getUsername());
                try {
                    String password = DecryptionAuthentication.decryptionDriver(player.getPassword());
                    passwordField.setText(password);
                    String email = DecryptionAuthentication.decryptionDriver(player.getEmail());
                    emailField.setText(email);
                } catch (DecryptionFailedException e) {
                    System.out.println(e);
                }
            } else {
                // If the user is not found, clear input fields
                userNameField.setText("");
                passwordField.setText("");
                emailField.setText("");
            }
        }
    }

    /*
    clear the stats of the selected player when clear stats button is pressed
     */
    @FXML
    private void clearStatsButton() {
        // create new empty stats
        HashMap statistics = new HashMap<>();
        statistics.put(GameType.CHESS, new StatisticsChess());
        statistics.put(GameType.CHECKERS, new StatisticsCheckers());
        statistics.put(GameType.CONNECT4, new StatisticsConnect4());
        statistics.put(GameType.TICTACTOE, new StatisticsTicTacToe());

        String userIDStr = IDField.getText();
        Integer userID = Integer.parseInt(userIDStr);
        // replace stats with empty ones
        DatabaseManager.updateAccountStats(userID, statistics);
    }

    /*
    delete selected user when delete user button is pressed
     */
    @FXML
    private void deleteUserButton() {
        String userIDStr = IDField.getText();
        Integer userID = Integer.parseInt(userIDStr);
        Admin.deleteUser(userID);
    }

    /*
    save changes when save changes button is pressed
     */
    @FXML
    private void submitButton() {
        String userIDStr = IDField.getText();
        Integer userID = Integer.parseInt(userIDStr);
        String username = userNameField.getText();
        String userEmail = emailField.getText();
        String userPassword = passwordField.getText();
        Admin.updateUsername(userID, username);
        Admin.updateEmail(userID, userEmail);
        Admin.updatePassword(userID, userPassword);
    }


    public static void main(String[] args) {launch(args);}
}
