import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class GameSelectController extends Application {

    @FXML
    AnchorPane rootPane;
    @FXML
    StackPane greenCartridge;
    @FXML
    StackPane orangeCartridge;
    @FXML
    StackPane pinkCartridge;
    @FXML
    StackPane purpleCartridge;
    @FXML
    StackPane backButton;
    @FXML
    Text errorMessage;
    @FXML
    ImageView profile;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/GameSelect.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            ImageView gifView = new ImageView(new Image(getClass().getResource("/images/twinklingstars.gif").toExternalForm()));

            StackPane root = new StackPane();
            root.getChildren().add(gifView);
            // TODO; make this variable
            //profile.setImage(new Image("/images/pink_circlebutton.png"));

            primaryStage.setResizable(true);

            // Set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        UtilityManager.createScaleTransition(backButton);
        UtilityManager.createtranslationTransition(greenCartridge);
        UtilityManager.createtranslationTransition(pinkCartridge);
        UtilityManager.createtranslationTransition(purpleCartridge);
        UtilityManager.createtranslationTransition(orangeCartridge);
    }

    @FXML
    private void switchToHome(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Start.fxml");
    }
    @FXML
    private void switchToProfile(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/UserProfile.fxml");
    }
    @FXML
    private void switchToConnect4(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/Connect4.fxml");
    }
    // TODO; connect when screens done
//    @FXML
//    private void switchToTicTacToe(javafx.scene.input.MouseEvent mouseEvent) {
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        SceneManager.switchScene(stage, "screens/TicTacToe.fxml");
//    }


//    @FXML
//    private void switchToChess(javafx.scene.input.MouseEvent mouseEvent) {
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        SceneManager.switchScene(stage, "screens/Chess.fxml");
//    }

//    @FXML
//    private void switchToCheckers(javafx.scene.input.MouseEvent mouseEvent) {
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        SceneManager.switchScene(stage, "screens/Checkers.fxml");
//    }


    public static void main(String[] args) {
        launch(args);
    }
}
