import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class StartController extends Application {

    public AnchorPane rootPane;

    @FXML
    private StackPane helpButton;
    @FXML
    private StackPane startButton;
    @FXML
    private StackPane rankingButton;
    ImageView start;
    ImageView ranking;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Start.fxml"));
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
            primaryStage.setTitle("Online Multiplayer Games - OMG");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sprites/tetrisCatIcon.png"))));

            SceneManager.registerScenes("screens/Start.fxml", "screens/Signup.fxml", "screens/Login.fxml",
                    "screens/Help.fxml", "screens/UserProfile.fxml", "screens/TicTacToe.fxml", "screens/Connect4.fxml",
                    "screens/GameSelect.fxml", "screens/MenuPopup.fxml", "screens/Chess.fxml", "screens/P1Checkers.fxml",
                    "screens/TicTacToe.fxml", "screens/AdminScreen.fxml", "screens/LeaderboardScreen.fxml","screens/UserPopup.fxml", "screens/MatchType.fxml"); // , "screens/<Screen>.fxml>"

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        UtilityManager.createScaleTransition(startButton);
        UtilityManager.createScaleTransition(rankingButton);
        UtilityManager.createScaleTransition(helpButton);
    }


    @FXML
    private AnchorPane helpPopup;

    // open the help popup overlay
    @FXML
    private void openHelpPopup(javafx.scene.input.MouseEvent mouseEvent) {
        UtilityManager.popupOpen(mouseEvent, "screens/Help.fxml", rootPane);
    }

    @FXML
    private void switchToSignUp(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Signup.fxml");
    }

    @FXML
    private void switchToLB(javafx.scene.input.MouseEvent mouseEvent) {
        //TODO: uncomment when merged
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        SceneManager.switchScene(stage, "screens/LeaderboardScreen.fxml");
        System.out.println("LEADERBOARD");
    }

    public static void main(String[] args) {
        launch(args);
    }
}