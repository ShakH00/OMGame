import account.Account;
import account.LoggedInAccount;
import account.statistics.AStatistics;
import account.statistics.StatisticType;
import game.GameType;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
//import player.Account;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
    private Label chessELO;

    @FXML
    private Label checkersELO;

    @FXML
    private Label connect4ELO;

    @FXML
    private Label ticTacToeELO;

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

    @FXML
    private ScrollPane friendsSPane;


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

        Account currentAccount = LoggedInAccount.getAccount();

        if (currentAccount != null) { // make sure the account exits

            // set Username to account username or guest if guest
            username.setText(currentAccount.getUsername());

            // set elo's for each game according to account data
            checkersELO.setText(String.valueOf(currentAccount.getElo(GameType.CHECKERS)));
            chessELO.setText(String.valueOf(currentAccount.getElo(GameType.CHESS)));
            connect4ELO.setText(String.valueOf(currentAccount.getElo(GameType.CONNECT4)));
            ticTacToeELO.setText(String.valueOf(currentAccount.getElo(GameType.TICTACTOE)));

            //stats list needed for Game info panes
            StatisticType[] order = {StatisticType.MATCHES_PLAYED, StatisticType.WIN_RATE};

            HashSet<GameType> games = new HashSet<>(); // hashset of the games (used for getCombinedStatistics)
            games.add(GameType.TICTACTOE);
            games.add(GameType.CHESS);
            games.add(GameType.CHECKERS);
            games.add(GameType.CONNECT4);

            float winRatePrev = -1;
            String bestGame = "n/a"; // which game has the highest winrate
            for (GameType game: games){ // for each game
                String[] statistics = currentAccount.getGameStatistics(game, order); // get stats
                float winRate = (Float.parseFloat(statistics[1]) * 100); // turn winrate to percent
                if (winRate > winRatePrev){
                    bestGame = String.valueOf(game); // best game has higher winrate
                }
                winRatePrev = winRate; // previous winrate becomes current
                switch(game) {
                    case GameType.TICTACTOE:
                        gamesPlayedTicTacToe.setText(statistics[0]);
                        gamesWonTicTacToe.setText((winRate) + "%");
                        break;

                    case GameType.CHECKERS:
                        gamesPlayedCheckers.setText(statistics[0]);
                        gamesWonCheckers.setText(winRate + "%");
                        break;

                    case GameType.CHESS:
                        gamesPlayedChess.setText(statistics[0]);
                        gamesWonChess.setText(winRate + "%");
                        break;

                    case GameType.CONNECT4:
                        gamesPlayedConnect4.setText(statistics[0]);
                        gamesWonConnect4.setText(winRate + "%");
                        break;

                }
            }
            // getting overall stats
            String[] statistics = currentAccount.getCombinedStatistics(games, order);
            gamesPlayedOverall.setText(statistics[0]); // set total games played
            gamesWonOverall.setText(statistics[1]); // set total games won
            this.bestGame.setText(bestGame); // set best game

            try {
                ArrayList<Account> friends = currentAccount.getFriends();
                if (friends != null) {
                    VBox vbox = new VBox();
                    for (Account friend : friends) {
                        Text text = new Text(friend.getUsername() + "\n");
                        System.out.println(text);
                        text.setFill(Color.WHITE);
                        vbox.getChildren().add(text);
                    }
                    friendsSPane.setContent(vbox);
                } else {
                    System.out.println("Friends list is null.");
                }
            } catch (Exception e) {
            e.printStackTrace(); // Better than throwing a new RuntimeException for debugging
            }
        }
    }

    @FXML
    private void goToSettings() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/UserSettings.fxml");
    }

    @FXML
    public void openPopup(MouseEvent event) {
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