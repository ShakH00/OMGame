import account.Account;
import account.statistics.MatchOutcomeHandler;
import account.LoggedInAccount;
import database.DatabaseManager;
import game.Game;
import game.GameType;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import matchmaking.MatchData;
import matchmaking.MatchmakingHandler;
import matchmaking.MatchmakingState;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MatchTypeController extends Application {

    @FXML
    AnchorPane rootPane;

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
    private StackPane backButtonLogin;
    @FXML
    private Label backButton1;
    @FXML
    private StackPane backButton2;
    @FXML
    private Label nextButton;
    @FXML
    private StackPane cancelButton1;
    @FXML
    private StackPane cancelButton2;
    @FXML
    private StackPane startButton1;
    @FXML
    private StackPane startButton2;
    @FXML
    private StackPane startButton3;
    @FXML
    private StackPane submitButton1;
    @FXML
    private StackPane submitButton2;
    @FXML
    private StackPane selectButton;

    @FXML
    private TextField roomCodeInput;
    @FXML
    AnchorPane GameSelectPane;
    @FXML
    AnchorPane MatchTypePane;
    @FXML
    StackPane greenCartridge;
    @FXML
    StackPane orangeCartridge;
    @FXML
    StackPane pinkCartridge;
    @FXML
    StackPane purpleCartridge;
    @FXML
    StackPane GameSelectbackButton;
    @FXML
    Text errorMessage;
    @FXML
    Pane joinPopup;
    @FXML
    Label GameSelectwaitingLabel;
    @FXML
    Label GameSelectgameSelectedLabel;
    @FXML
    private StackPane GameSelectbackButton1;

    public  MatchTypeController controller;
    public  MatchTypeController MatchTypeController;
    Account activeAccount;

    @FXML
    private Label waitingLabel;
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

    @FXML
    private StackPane profile;
    @FXML
    private StackPane leaderboard;
    @FXML
    private StackPane help;

    private final GameType[] gameTypes = new GameType[]{
            GameType.CHESS,
            GameType.CHECKERS,
            GameType.TICTACTOE,
            GameType.CONNECT4
    };

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

        UtilityManager.createScaleTransition(backButtonLogin);
        UtilityManager.colourTransition1(backButton1);
        UtilityManager.createScaleTransition(backButton2);
        UtilityManager.colourTransition1(nextButton);

        UtilityManager.createScaleTransition(cancelButton1);
        UtilityManager.createScaleTransition(cancelButton2);

        UtilityManager.createScaleTransition(startButton1);
        UtilityManager.createScaleTransition(startButton2);
        UtilityManager.createScaleTransition(startButton3);

        UtilityManager.createScaleTransition(submitButton1);
        UtilityManager.createScaleTransition(submitButton2);
        UtilityManager.createScaleTransition(selectButton);


        UtilityManager.createScaleTransition(leaderboard);
        UtilityManager.createScaleTransition(profile);
        UtilityManager.createScaleTransition(help);

        UtilityManager.createScaleTransition(GameSelectbackButton);
        UtilityManager.createTranslationTransition(greenCartridge);
        UtilityManager.createTranslationTransition(pinkCartridge);
        UtilityManager.createTranslationTransition(purpleCartridge);
        UtilityManager.createTranslationTransition(orangeCartridge);
        UtilityManager.createScaleTransition(GameSelectbackButton1);

        gameFrames = new Pane[] {chess, checkers, tictactoe, connect4};

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(gamesPane.widthProperty());
        clip.heightProperty().bind(gamesPane.heightProperty());
        gamesPane.setClip(clip); // clip stackpane to stay within bounds

        startDotAnimation();


    }

    @FXML
    private void switchToLB(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/LeaderboardScreen.fxml");
    }

    @FXML
    private void switchToProfile(javafx.scene.input.MouseEvent mouseEvent) {

        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/UserProfile.fxml");
    }

    @FXML
    private void openHelpPopup(javafx.scene.input.MouseEvent mouseEvent) {
        UtilityManager.popupOpen(mouseEvent, "screens/Help.fxml", rootPane);
    }


    @FXML
    private void GameSelectSwitchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/MatchType.fxml");
    }

    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }

    @FXML
    private void switchToGameSelect(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        // transition effect ?
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), MatchTypePane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            MatchTypePane.setVisible(false);

            // Prepare the new pane
            GameSelectPane.setOpacity(0.0);
            GameSelectPane.setVisible(true);

            // Fade in the new pane
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), GameSelectPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }


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
        currentFrameIndex = (currentFrameIndex + 1) % gameFrames.length; // move to the next frame
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
        currentFrameIndex = (currentFrameIndex - 1 + gameFrames.length) % gameFrames.length; // move to the previous frame
    }

    public void setAccount(Account account) {
        this.activeAccount = account;
    }


    // private matchmaking: hosting and joining games
    @FXML
    private void handleSelectButton() throws IOException {
        GameType selectedGame = gameTypes[currentFrameIndex]; // get gametype based on frame index


        // Get hosting details
        activeAccount = LoggedInAccount.getAccount();
        MatchmakingHandler handler = activeAccount.getMatchmakingHandler();
        int accountID = !activeAccount.getIsGuest() ? activeAccount.getID() : DatabaseManager.getTempID(); // if guest, use temp ID
        String roomCode = handler.getUniqueRoomCode();
        String networkingInformation = "";      // TODO: Integrate w/ networking

        // go to next screen
        hostPopup.setVisible(false);
        codePopup.setVisible(true);
        gameSelectedLabel.setText("You selected:  " + selectedGame);
        matchIDLabel.setText(roomCode);

        // Wait for someone to join
        try {
            // Start hosting.
            handler.startHosting(accountID, selectedGame, activeAccount.getElo(selectedGame), roomCode, networkingInformation);

            // Start another thread called "watcher" which watches the matchmaking handler
            // Once the watcher finds a match, it will call startMatch() in this class with match-related parameters.
            MatchmakingHandlerWatcher watcher = new MatchmakingHandlerWatcher(this, handler);
            watcher.start();
        } catch (InterruptedException e) {
            System.out.println("Hosting interrupted");
        }
    }


    @FXML
    private void switchToConnect4() throws IOException {
        GameSelectgameSelectedLabel.setText("You selected: Connect 4");
        joinPopup.setVisible(true);
        handleMatchmakingButton(GameType.CONNECT4);
    }

    @FXML
    private void switchToTicTacToe() throws IOException {
        GameSelectgameSelectedLabel.setText("You selected: TicTacToe");
        joinPopup.setVisible(true);
        handleMatchmakingButton(GameType.TICTACTOE);
    }

    @FXML
    private void switchToChess() throws IOException {
        GameSelectgameSelectedLabel.setText("You selected: Chess");
        joinPopup.setVisible(true);
        handleMatchmakingButton(GameType.CHESS);
    }

    @FXML
    private void switchToCheckers() throws IOException {
        GameSelectgameSelectedLabel.setText("You selected: Checkers");
        joinPopup.setVisible(true);
        handleMatchmakingButton(GameType.CHECKERS);
    }




    // TODO: YOU NEED TO USE THE FUNCTIONS IN HERE FOR MATCHMAKING !!!
    @FXML
    void handleMatchmakingButton(GameType selectedGame) throws IOException {
        System.out.println("Selected game: " + selectedGame);
        // Get matchmaking details
        activeAccount = LoggedInAccount.getAccount();
        MatchmakingHandler handler = activeAccount.getMatchmakingHandler();
        int accountID = !activeAccount.getIsGuest() ? activeAccount.getID() : DatabaseManager.getTempID(); // if guest, use temp ID
        String networkingInformation = "";      // TODO: Integrate w/ networking

        // Wait for someone to join
        try {
            // Start matchmaking
            handler.startMatchmaking(accountID, selectedGame, activeAccount.getElo(selectedGame), networkingInformation);

            // Start another thread called "watcher" which watches the matchmaking handler
            // Once the watcher finds a match, it will call startMatch() in this class with match-related parameters.
            MatchmakingHandlerWatcher watcher = new MatchmakingHandlerWatcher(this, handler);
            watcher.start();
        } catch (InterruptedException e) {
            System.out.println("Hosting interrupted");
        }
    }

    @FXML
    void handleStopMatchmakingButton() throws IOException {
        activeAccount = LoggedInAccount.getAccount();
        activeAccount.getMatchmakingHandler().stopMatchmaking();
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
    private void onCancelButtonClicked() throws IOException {
        hostPopup.setVisible(false);
        joinPopup.setVisible(false);
        joinCodePopup.setVisible(false);
        handleStopMatchmakingButton();
    }

    @FXML
    private void onBackButtonClicked() {
        codePopup.setVisible(false);
        hostPopup.setVisible(true);
        activeAccount.getMatchmakingHandler().stopHosting();
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
            GameSelectwaitingLabel.setText(baseText + ".".repeat(dotCount + 1));
            dotCount++;
        } else {
            waitingLabel.setText(baseText);  // reset back to base
            GameSelectwaitingLabel.setText(baseText + ".".repeat(dotCount + 1));
            dotCount = 0;
        }
    }

    /**
     *
     * @param game
     * @param affectsElo
     * @param selfID
     * @param selfUsername
     * @param selfElo
     * @param selfNetworkingInformation
     * @param opponentID
     * @param opponentUsername
     * @param opponentElo
     * @param opponentNetworkingInformation
     */
    public void startMatch(GameType game,
                           boolean affectsElo,
                           int selfID,
                           String selfUsername,
                           int selfElo,
                           String selfNetworkingInformation,
                           int selfPlayerNo,
                           int opponentID,
                           String opponentUsername,
                           int opponentElo,
                           String opponentNetworkingInformation,
                           int opponentPlayerNo)
    {
        // Prepare the MatchOutcomeHandler variables
        MatchOutcomeHandler.affectElo = affectsElo;
        MatchOutcomeHandler.opponentElo = opponentElo;
        MatchOutcomeHandler.opponentID = opponentID;
        MatchOutcomeHandler.opponentUsername = opponentUsername;

        MatchData matchData = new MatchData(
                game, affectsElo, selfID, selfUsername, selfElo, selfNetworkingInformation, selfPlayerNo,
                opponentID, opponentUsername, opponentElo, opponentNetworkingInformation, opponentPlayerNo
        );

        // TODO: EVIL !!!!!!!
        Stage stage = (Stage) rootPane.getScene().getWindow();
        String gameScreenFXML = getGameScreenFXML(game, selfPlayerNo);
        SceneManager.switchSceneWithData(stage, gameScreenFXML, matchData);
    }



    // Helper method to map selected game type to corresponding FXML screen
    static String getGameScreenFXML(GameType selectedGame, int playerNo) {
        return switch (selectedGame) {
            case CHESS -> playerNo == 1 ? "screens/P1Chess.fxml" : "screens/P2Chess.fxml" ;
            case CHECKERS -> playerNo == 1 ? "screens/P1Checkers.fxml" : "screens/P2Checkers.fxml" ;
            case TICTACTOE -> "screens/TicTacToe.fxml";
            case CONNECT4 -> "screens/Connect4.fxml";
        };
    }

    @FXML
    private void onSubmitButtonClicked() {
        activeAccount = LoggedInAccount.getAccount();
        int accountID = !activeAccount.getIsGuest() ? activeAccount.getID() : DatabaseManager.getTempID(); // if guest, use temp ID
        String roomCode = roomCodeInput.getText();
        String networkingInformation = "";                  // TODO: Networking integration

        // Try to join the host with the provided room code.
        MatchmakingHandler handler = activeAccount.getMatchmakingHandler();
        boolean success = handler.tryJoinHost(accountID, activeAccount, roomCode, networkingInformation);
        if (success){
            // Start the GUI
            MatchmakingHandlerWatcher watcher = new MatchmakingHandlerWatcher(this, handler);
            watcher.start();
        }
    }

    @FXML
    private void onJoinGameClicked() {
        joinCodePopup.setVisible(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}


class MatchmakingHandlerWatcher extends Thread {
    private final MatchTypeController guiController;
    private final MatchmakingHandler handler;
    public MatchmakingHandlerWatcher(MatchTypeController guiController, MatchmakingHandler handler){
        this.guiController = guiController;
        this.handler = handler;
    }


    public void run(){
        while (handler.getState() != MatchmakingState.ONLINE){  // stop early if they stop matchmaking
            // Check if the handler has found a match. If it has, start the game UI
            if (handler.startGame){
                GameType game = handler.m_game;
                boolean affectsElo = handler.m_affectsElo;
                int selfID = handler.m_selfID;
                String selfUsername = handler.m_selfUsername;
                int selfElo = handler.m_selfElo;
                String selfNetworkingInformation = handler.m_selfNetworkingInformation;
                int selfPlayerNo = handler.m_selfPlayerNo;
                int opponentID = handler.m_opponentID;
                String opponentUsername = handler.m_opponentUsername;
                int opponentElo = handler.m_opponentElo;
                String opponentNetworkingInformation = handler.m_opponentNetworkingInformation;
                int opponentPlayerNo = handler.m_opponentPlayerNo;


                String fxmlFile = MatchTypeController.getGameScreenFXML(game, selfPlayerNo);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                try {
                    Parent root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                guiController.startMatch(game, affectsElo, selfID, selfUsername, selfElo, selfNetworkingInformation, selfPlayerNo, opponentID, opponentUsername, opponentElo, opponentNetworkingInformation, opponentPlayerNo);

                break;
            }

            // Sleep 1 sec before repeating
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        handler.startGame = false;
    }


}