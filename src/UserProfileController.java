import account.Account;
import account.statistics.AStatistics;
import game.GameType;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
//import player.Account;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class UserProfileController extends Application {
    @FXML
    private Pane rankPane;

    @FXML
    private Pane chessPane;

    @FXML
    private Pane tictactoePane;

    @FXML
    private Pane checkersPane;

    @FXML
    private Pane connect4Pane;

    @FXML
    private Pane overallPane;

    @FXML
    private Pane friendsPane;

    @FXML
    private StackPane settingsButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label username;

    @FXML
    private Label ELO;

    @FXML
    private Label gamesPlayedOverall;

    @FXML
    private Label gamesPlayedChess;

    @FXML
    private Label gamesPlayedCheckers;

    @FXML
    private Label gamesPlayedConnect4;

    @FXML
    private Label gamesPlayedTicTacToe;

    @FXML
    private Label gamesWonOverall;

    @FXML
    private Label gamesWonChess;

    @FXML
    private Label gamesWonCheckers;

    @FXML
    private Label gamesWonConnect4;

    @FXML
    private Label gamesWonTicTacToe;

    @FXML
    private Label bestGame;

    @FXML
    private Tab overview;

    @FXML
    private Tab matches;

    @FXML
    private StackPane backButton;

    private Account currentAccount;



    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/UserProfile.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();

            Font pressStartFont = Font.loadFont(fontPath, 40);
            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
            Font pixeliteFont = Font.loadFont(pixelitePath, 40);

            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

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


//    public void setAccount(Account account) {
//
//        // bind text to get username
//        usernameLabel.setText(account.getUsername());
//
//        // do we even have avatars???
//        //avatarImageView.setImage(new Image(currentAccount.getAvatarImageUrl()));  // Assuming a method like this in Account
//
//        // they do not have banner colors either
//        // banner region color binding
//        //bannerRegion.setStyle("-fx-background-color: " + currentAccount.getBannerColor() + ";");
//    }


    @FXML
    public void initialize() {
        // Apply hover effect to each pane
        UtilityManager.addHoverEffect(rankPane);
        UtilityManager.addHoverEffect(chessPane);
        UtilityManager.addHoverEffect(tictactoePane);
        UtilityManager.addHoverEffect(checkersPane);
        UtilityManager.addHoverEffect(connect4Pane);
        UtilityManager.addHoverEffect(overallPane);
        UtilityManager.addHoverEffect(friendsPane);
        UtilityManager.createScaleTransition(settingsButton);
        UtilityManager.createScaleTransition(backButton);

        currentAccount = LoginController.getAccount();

        if (currentAccount != null) {
            username.setText(currentAccount.getUsername());
            System.out.println(Arrays.toString(currentAccount.getGameStatistics(GameType.TICTACTOE)));
        }

    }

    @FXML
    private void goToSettings() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/UserSettings.fxml");
    }

    @FXML
    public void openPopup(javafx.scene.input.MouseEvent event) {
        UtilityManager.popupOpen(event,"screens/UserPopup.fxml", rootPane);
    }

    @FXML
    public void setBackButton() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/MatchType.fxml");
    }

    public static void main (String[]args){
        launch(args);
    }
}