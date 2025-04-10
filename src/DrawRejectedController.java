import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DrawRejectedController extends Application  {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane closeButton;

    // TODO; need to change picture to sad tetris cat of some sort
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/DrawRejected.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

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
    private void closeButton(){
        UtilityManager.popupClose(rootPane);
        System.out.println("Popup closed");
    }
}
