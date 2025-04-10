import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class HelpController extends Application {

    @FXML
    private Label closeButton;

    @FXML
    private AnchorPane rootPane;

    @Override
        public void start(Stage primaryStage) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Help.fxml"));
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
    private void closeButton(javafx.scene.input.MouseEvent mouseEvent){
        UtilityManager.popupClose(rootPane);
    }

        public static void main(String[] args) {
            launch(args);
        }
    }