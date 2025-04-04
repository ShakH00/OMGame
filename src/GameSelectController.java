import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GameSelectController extends Application {
    @FXML
    ImageView greenCartridge;
    AnchorPane set;
    ImageView orangeCartridge;
    ImageView pinkCartridge;
    ImageView purpleCartridge;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/gameSelect.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            ImageView gifView = new ImageView(new Image(getClass().getResource("/images/twinklingstars.gif").toExternalForm()));

            StackPane root = new StackPane();
            root.getChildren().add(gifView);

            primaryStage.setResizable(true);

            // Set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animation(ImageView cartridge){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setNode(cartridge);
        translateTransition.setByX(175);
        translateTransition.setByY(-136);
        translateTransition.play();

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(cartridge);
        scaleTransition.setByY(5.5);
        scaleTransition.setByX(5.5);
        scaleTransition.play();

//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
//        fadeTransition.setNode(cartridge);
//        fadeTransition.setFromValue(1.0);
//        fadeTransition.setToValue(0.3);
//        fadeTransition.play();
    }

    @FXML
     void clicked(){
        //animation(greenCartridge);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
