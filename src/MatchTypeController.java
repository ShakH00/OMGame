import account.Account;
import database.DatabaseManager;
import game.GameType;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import matchmaking.MatchmakingHandler;



import javax.swing.text.html.ImageView;
import java.util.concurrent.TimeUnit;

public class MatchTypeController extends Application {

    @FXML
    static AnchorPane rootPane;
    @FXML
    private Pane hostGamePane;
    @FXML
    private Pane standardGamePane;
    @FXML
    private Pane joinGamePane;
    @FXML
    private Pane joinCodePopup;
    @FXML
    private Label matchIDLabel;
    @FXML
    private Label gameSelectedLabel;
    @FXML
    private TextField roomCodeInput;

    @FXML
    private Label waitingLabel;

    Account activeAccount;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MatchType.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();

            Font pressStartFont = Font.loadFont(fontPath, 40);
            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
            Font pixeliteFont = Font.loadFont(pixelitePath, 40);

            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            primaryStage.setResizable(false);

            MatchTypeController controller = loader.getController();
            Account testAccount = DatabaseManager.queryAccountByID(4);
            controller.setAccount(testAccount);

            // Set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initialize() {
        UtilityManager.addHoverEffectRectangle(hostGamePane);
        UtilityManager.addHoverEffectRectangle(joinGamePane);
        UtilityManager.addHoverEffectRectangle(standardGamePane);
        System.out.println("Chess: " + chess);
        System.out.println("Checkers: " + checkers);
        System.out.println("TicTacToe: " + tictactoe);
        System.out.println("Connect4: " + connect4);
        gameFrames = new Pane[] {chess, checkers, tictactoe, connect4};

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(gamesPane.widthProperty());  // Bind the clip width to the StackPane width
        clip.heightProperty().bind(gamesPane.heightProperty()); // Bind the clip height to the StackPane height
        gamesPane.setClip(clip);  // Apply the clip to the StackPane

        startDotAnimation();


    }


    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }

    @FXML
    private void switchToGameSelect(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/GameSelect.fxml");
    }

    @FXML
    private StackPane gamesPane;

    @FXML
    private Pane connect4;
    @FXML
    private Pane tictactoe;
    @FXML
    private Pane checkers;
    @FXML
    private Pane chess;
    @FXML
    private Pane hostPopup;
    @FXML
    private Pane codePopup;


    private Pane[] gameFrames; // array to hold frames
    private int currentFrameIndex = 0; // current frame tracker

    // to swipe right
    public void swipeToNext() {
        Pane currentFrame = gameFrames[currentFrameIndex];
        currentFrame.setVisible(true);
        Pane nextFrame = gameFrames[(currentFrameIndex + 1) % gameFrames.length];
        nextFrame.setVisible(true);
        nextFrame.setTranslateX(nextFrame.getWidth()); // position off screen (formatting - looks crazy otherwise)

        // slide current frame to left
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), currentFrame);
        slideOut.setToX(-currentFrame.getWidth());

        // slide next frame in from right
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), nextFrame);
        slideIn.setToX(0);

        // play transitions
        slideOut.play();
        slideIn.play();

        // update frame index
        currentFrameIndex = (currentFrameIndex + 1) % gameFrames.length; // Move to the next frame
    }

    // to swipe left
    public void swipeBack() {
        Pane currentFrame = gameFrames[currentFrameIndex];
        currentFrame.setVisible(true);
        Pane prevFrame = gameFrames[(currentFrameIndex - 1 + gameFrames.length) % gameFrames.length];
        prevFrame.setVisible(true);

        prevFrame.setTranslateX(-prevFrame.getWidth()); // position off screen (formatting - looks crazy otherwise)

        // slide current frame to right
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), currentFrame);
        slideOut.setToX(currentFrame.getWidth());

        // slide previous frame out of view to left
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), prevFrame);
        slideIn.setToX(0);

        // play transitions
        slideOut.play();
        slideIn.play();

        // update frame index
        currentFrameIndex = (currentFrameIndex - 1 + gameFrames.length) % gameFrames.length; // Move to the previous frame
    }

    public void setAccount(Account account) {
        this.activeAccount = account;
    }

    @FXML
    private void handleSelectButton() {
        GameType selectedGame = GameType.values()[currentFrameIndex]; // get gametype based on frame index

        // Get hosting details
        MatchmakingHandler handler = activeAccount.getMatchmakingHandler();
        int accountID = activeAccount.getID() != -1 ? activeAccount.getID() : DatabaseManager.getTempID(); // if guest, use temp ID
        String roomCode = handler.getUniqueRoomCode();
        String networkingInformation = "";      // TODO: Integrate w/ networking

        // go to next screen
        hostPopup.setVisible(false);
        codePopup.setVisible(true);
        gameSelectedLabel.setText("You selected:  " + selectedGame);
        matchIDLabel.setText(roomCode);

        // Wait for someone to join
        try {
            TimeUnit.SECONDS.sleep(1);
            // Start hosting.
            handler.startHosting(accountID, selectedGame, roomCode, networkingInformation);
        } catch (InterruptedException e) {
            System.out.println("Hosting interrupted");
        }
    }

    @FXML
    private void onSwipeNextButtonClicked() {
        swipeToNext();
    }

    @FXML
    private void onSwipeBackButtonClicked() {
        swipeBack();
    }

    @FXML
    private void onCancelButtonClicked() {
        hostPopup.setVisible(false);
        joinCodePopup.setVisible(false);
    }

    @FXML
    private void onBackButtonClicked() {
        codePopup.setVisible(false);
        hostPopup.setVisible(true);
        activeAccount.getMatchmakingHandler().stopHosting();
        activeAccount.getMatchmakingHandler().stopMatchmaking();
    }


    @FXML
    private void openHostGame(javafx.scene.input.MouseEvent mouseEvent) {
        hostPopup.setVisible(true);
    }


    // methods to add dots one by one to waitingLabel, which shows that player is waiting for a match
    private String baseText = "Waiting for a match";
    private int dotCount = 0;

    private void startDotAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> updateLabelText())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateLabelText() {
        // add dots one by one to the waitingLabel
        if (dotCount < 3) {
            waitingLabel.setText(baseText + ".".repeat(dotCount + 1));
            dotCount++;
        } else {
            waitingLabel.setText(baseText);  // reset back to base
            dotCount = 0;
        }
    }

    /**
     * Called from outside of this class by the MatchmakingHandler to load the game GUI
     * @param selectedGame  Game GUI to load
     */
    public static void startGame(GameType selectedGame){
        Stage stage = (Stage) rootPane.getScene().getWindow();

        // switch to that game screen
        String gameScreenFXML = getGameScreenFXML(selectedGame);
            SceneManager.switchScene(stage, gameScreenFXML);
        }



    // Helper method to map selected game type to corresponding FXML screen
    private static String getGameScreenFXML(GameType selectedGame) {
        return switch (selectedGame) {
            case CHESS -> "screens/Chess.fxml";         // Replace with actual screen path for Chess
            case CHECKERS -> "screens/Checkers.fxml";   // Replace with actual screen path for Checkers
            case TICTACTOE -> "screens/TicTacToe.fxml"; // Replace with actual screen path for TicTacToe
            case CONNECT4 -> "screens/Connect4.fxml";   // Replace with actual screen path for Connect4
        };
    }

    @FXML
    private void onSubmitButtonClicked() {
        int accountID = activeAccount.getID() != -1 ? activeAccount.getID() : DatabaseManager.getTempID(); // if guest, use temp ID
        String roomCode = roomCodeInput.getText();                // TODO: Get input from text field
        String networkingInformation = "";      // TODO: Integrate w/ networking

        // Try to join the host with the provided room code.
        activeAccount.getMatchmakingHandler().tryJoinHost(accountID, roomCode, networkingInformation);
    }

    @FXML
    private void onJoinGameClicked() {
        joinCodePopup.setVisible(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
