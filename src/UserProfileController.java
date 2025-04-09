import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
//import player.Account;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.awt.*;

public class UserProfileController extends Application {


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


    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Label statsLabel;

    @FXML
    private Rectangle bannerRegion;

    @FXML
    private Tab overview;
    @FXML
    private Tab matches;


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
    }

    @FXML
    private void goToSettings() {
        System.out.println("To user settings");
    }

    public static void main (String[]args){
        launch(args);
    }
}