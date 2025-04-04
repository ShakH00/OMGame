import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.io.IOException;
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
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tetrisCatIcon.png"))));

            SceneManager.preloadScenes("screens/Start.fxml", "screens/Signup.fxml", "screens/Login.fxml", "screens/Help.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        createScaleTransition(startButton);
        createScaleTransition(rankingButton);
        createScaleTransition(helpButton);
    }

    public static void createScaleTransition(StackPane button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        button.setOnMouseEntered(event -> {
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
            button.setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
            button.setCursor(Cursor.DEFAULT);
        });
    }

    @FXML
    private AnchorPane helpPopup;

    // open the help popup overlay
    @FXML
    private void openHelpPopup(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            // load help.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Help.fxml"));
            Parent helpRoot = loader.load();

            // get help controller
            HelpController helpController = loader.getController();

            rootPane.getChildren().add(helpRoot);  // rootPane is the main container in Start.fxml

            // set the helpRoot visible (it will be hidden initially)
            helpRoot.setVisible(true);

            // to close the popup, click anywhere
            helpRoot.setOnMouseClicked(event -> {
                helpRoot.setVisible(false);  // Hide the popup
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSignUp(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Signup.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}