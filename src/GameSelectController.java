//import account.Account;
//import database.DatabaseManager;
//import game.GameType;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.scene.layout.StackPane;
//import javafx.util.Duration;
//
//import java.io.IOException;
//
//
//public class GameSelectController extends Application {
//
//    @FXML
//    AnchorPane rootPane;
//    @FXML
//    StackPane greenCartridge;
//    @FXML
//    StackPane orangeCartridge;
//    @FXML
//    StackPane pinkCartridge;
//    @FXML
//    StackPane purpleCartridge;
//    @FXML
//    StackPane backButton;
//    @FXML
//    Text errorMessage;
//
//    @FXML
//    Pane joinPopup;
//    @FXML
//    Label waitingLabel;
//    @FXML
//    Label gameSelectedLabel;
//    @FXML
//    private StackPane backButton1;
//
//
//    private GameType selectedGame;
//
//    public  MatchTypeController controller;
//    public  MatchTypeController MatchTypeController;
//   Account activeAccount;
//
//    private Parent matchTypeRoot;
//    private MatchTypeController matchTypeController;
//
//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/GameSelect.fxml"));
//            Scene scene = new Scene(loader.load(), 800, 570);
//
//            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
//            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
//            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();
//
//            Font pressStartFont = Font.loadFont(fontPath, 40);
//            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
//            Font pixeliteFont = Font.loadFont(pixelitePath, 40);
//
//            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
//
//            ImageView gifView = new ImageView(new Image(getClass().getResource("/images/twinklingstars.gif").toExternalForm()));
//
//            StackPane root = new StackPane();
//            root.getChildren().add(gifView);
//            // TODO; make this variable
//            //profile.setImage(new Image("/images/pink_circlebutton.png"));
//
//
//            GameSelectController meow = loader.getController();
//            Account guestAccount = new Account();
//            Account player1Account = new Account("Arwa", "arwa@gmail.com", "arwa123");
//            Account player2Account = new Account("Elijah", "elijah@gmail.com", "elijah123");
//            DatabaseManager.saveAccount(player1Account);
//            DatabaseManager.saveAccount(player2Account);
//            player1Account = DatabaseManager.queryAccountByUsername("Arwa");
//            player2Account = DatabaseManager.queryAccountByUsername("Elijah");
//            meow.setAccount(player2Account);
//
//
//            MatchTypeController controller = loader.getController();
//            controller.setGameSelectController(this); // pass this reference
//            System.out.println("controller = " + controller);
//            primaryStage.setResizable(true);
//
//            // Set up the primary stage
//            primaryStage.setTitle("OMG!");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setAccount(Account account) {
//        this.activeAccount = account;
//    }
//
//    public void initialize() throws IOException {
//        UtilityManager.createScaleTransition(backButton);
//        UtilityManager.createTranslationTransition(greenCartridge);
//        UtilityManager.createTranslationTransition(pinkCartridge);
//        UtilityManager.createTranslationTransition(purpleCartridge);
//        UtilityManager.createTranslationTransition(orangeCartridge);
//        UtilityManager.createScaleTransition(backButton1);
//
//        startDotAnimation();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MatchType.fxml"));
//        matchTypeRoot = loader.load();
//        matchTypeController = loader.getController();
//
//
//    }
//
//    @FXML
//    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        SceneManager.switchScene(stage, "screens/MatchType.fxml");
//    }
//
//
//    // TODO: all four of these games should open joinPopup when a game is clicked, wait for a match to be found, and then switch screens to the appropriate game
//    // right now, it opens the popup and then switches screens w/o waiting for a match to be found
//
//
//    public void setMatchTypeController(MatchTypeController controller) {
//        this.MatchTypeController = controller;
//    }
//
//    @FXML
//    private void switchToConnect4() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MatchType.fxml"));
//        Parent root = loader.load();
//        MatchTypeController controller = loader.getController();
//
//
//        setMatchTypeController(controller);
//        gameSelectedLabel.setText("You selected: Connect 4");
//        joinPopup.setVisible(true);
//        MatchTypeController.handleMatchmakingButton(GameType.CONNECT4);
//
////
////        Stage stage = (Stage) rootPane.getScene().getWindow();
////        SceneManager.switchScene(stage, "screens/TicTacToe.fxml");
//    }
//
//    @FXML
//    private void switchToTicTacToe(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MatchType.fxml"));
//        Parent root = loader.load();
//        MatchTypeController controller = loader.getController();
//
//
//        setMatchTypeController(controller);
//        gameSelectedLabel.setText("You selected: TicTacToe");
//        joinPopup.setVisible(true);
//        MatchTypeController.handleMatchmakingButton(GameType.TICTACTOE);
//    }
//
//    @FXML
//    private void switchToChess(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MatchType.fxml"));
//        Parent root = loader.load();
//        MatchTypeController controller = loader.getController();
//
//
//        setMatchTypeController(controller);
//        gameSelectedLabel.setText("You selected: Chess");
//        joinPopup.setVisible(true);
//        MatchTypeController.handleMatchmakingButton(GameType.CHESS);
//    }
//
//    @FXML
//    private void switchToCheckers(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MatchType.fxml"));
//        Parent root = loader.load();
//        MatchTypeController controller = loader.getController();
//
//
//        setMatchTypeController(controller);
//        gameSelectedLabel.setText("You selected: Checkers");
//        joinPopup.setVisible(true);
//        MatchTypeController.handleMatchmakingButton(GameType.CHECKERS);
//    }
//
//    @FXML
//    private void onCancelButtonClicked() throws IOException {
//        MatchTypeController.handleStopMatchmakingButton();
//        joinPopup.setVisible(false);
//    }
//
//    // methods to add dots one by one to waitingLabel, which shows that player is waiting for a match
//    private String baseText = "Waiting for a match";
//    private int dotCount = 0;
//
//    private void startDotAnimation() {
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.seconds(0.5), event -> updateLabelText())
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
//    }
//
//    private void updateLabelText() {
//        // add dots one by one to the waitingLabel
//        if (dotCount < 3) {
//            waitingLabel.setText(baseText + ".".repeat(dotCount + 1));
//            dotCount++;
//        } else {
//            waitingLabel.setText(baseText);  // reset back to base
//            dotCount = 0;
//        }
//    }
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
