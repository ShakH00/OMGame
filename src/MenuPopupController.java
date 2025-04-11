import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPopupController extends Application {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane closeButton;

    @FXML
    private StackPane drawButton;

    @FXML
    private StackPane resignButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/MenuPopup.fxml"));
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

    // TODO; This won't work for some reason...
    public void initialize() {
        UtilityManager.createScaleTransition(closeButton);
        UtilityManager.createScaleTransition(drawButton);
        UtilityManager.createScaleTransition(resignButton);
    }

    @FXML
    private void closeButton(){
      UtilityManager.popupClose(rootPane);
    }

    @FXML
    private void draw(javafx.scene.input.MouseEvent mouseEvent) {
        UtilityManager.popupOpen(mouseEvent, "screens/DrawSent.fxml", rootPane);
        //UtilityManager.popupClose(rootPane);
    }

    // TODO; send signal to p2 of win screen
    @FXML
    private void resign(javafx.scene.input.MouseEvent mouseEvent) {
        //P1
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/LoseScreen.fxml");

        // P2
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        SceneManager.switchScene(stage, "screens/WinScreen.fxml");

    }



    public static void main(String[] args) {
        launch(args);
    }
}

