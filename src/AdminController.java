//import authentication.Authentication.Admin;
import account.Account;
import account.statistics.StatisticsCheckers;
import account.statistics.StatisticsChess;
import account.statistics.StatisticsConnect4;
import account.statistics.StatisticsTicTacToe;
import authentication.Authentication.Admin;
import authentication.ExceptionsAuthentication.DecryptionFailedException;
import database.DatabaseManager;
import database.DecryptionAuthentication;
import database.EncryptionAuthentication;
import game.GameType;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
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

    //private Admin adminUser = new Admin();


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

    public void initialize() {
        UtilityManager.createScaleTransition(backButton);
        UtilityManager.createScaleTransition(findUserButton);
        UtilityManager.createScaleTransition(clearStatsButton);
        UtilityManager.createScaleTransition(deleteUserButton);
        UtilityManager.createScaleTransition(submitButton);
    }

    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }

    @FXML
    private void findUserButton() {
        String userIDStr = IDField.getText();
        Integer userID = Integer.parseInt(userIDStr);
        Account player = DatabaseManager.queryAccountByID(userID);
        if(player != null) {
            userNameField.setText(player.getUsername());

            try {
                String password = DecryptionAuthentication.decryptionDriver(player.getPassword());
                passwordField.setText(password);
                String email = DecryptionAuthentication.decryptionDriver(player.getEmail());
                emailField.setText(email);
            } catch (DecryptionFailedException e) {
                System.out.println(e);
            }
        }else{
            userNameField.setText("");
            passwordField.setText("");
            emailField.setText("");
        }
    }

    @FXML
    private void clearStatsButton() {
        HashMap statistics = new HashMap<>();
        statistics.put(GameType.CHESS, new StatisticsChess());
        statistics.put(GameType.CHECKERS, new StatisticsCheckers());
        statistics.put(GameType.CONNECT4, new StatisticsConnect4());
        statistics.put(GameType.TICTACTOE, new StatisticsTicTacToe());

        String userIDStr = IDField.getText();
        Integer userID = Integer.parseInt(userIDStr);
        DatabaseManager.updateAccountStats(userID, statistics);
    }

    @FXML
    private void deleteUserButton() {
        String userIDStr = IDField.getText();
        Integer userID = Integer.parseInt(userIDStr);
        Admin.deleteUser(userID);
    }

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


    public static void main(String[] args) {
        launch(args);
    }
}
