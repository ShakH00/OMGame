import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController extends Application {

    @FXML
    AnchorPane rootPane;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Signup.fxml"));
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
    private void switchToLogin(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            // load help.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Login.fxml"));
            Parent helpRoot = loader.load();

            // get help controller
            LoginController loginController = loader.getController();

            rootPane.getChildren().add(helpRoot);  // rootPane is the main container in Start.fxml

            // set the helpRoot visible (it will be hidden initially)
            helpRoot.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCloseButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/Signup.fxml"));
        Parent helpRoot = loader.load();
        helpRoot.setOnMouseClicked(event -> {
            helpRoot.setVisible(false);  // Hide the popup
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
