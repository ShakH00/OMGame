import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class HelpController extends Application {

    @FXML
    private Label closeButton;
    @FXML
    private Text text;

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

    public void initialize() {
        UtilityManager.colourTransition(text, Color.color(0.1059, 0.2314, 0.3647, 1.0));
    }
    @FXML
    private void closeButton(javafx.scene.input.MouseEvent mouseEvent){
        UtilityManager.popupClose(rootPane);
    }

    @FXML
    public void helpWebsite() {
        // I wrote this :) 👍
        String url ="https://omgame.club/";
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        public static void main(String[] args) {
            launch(args);
        }
    }