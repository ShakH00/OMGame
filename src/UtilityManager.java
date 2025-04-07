import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class UtilityManager {
    @FXML
    public static void popupControl(javafx.scene.input.MouseEvent mouseEvent, String path, AnchorPane rootPane) {
        // TODO; check if it works with other not help popups
        try {
            // load help.fxml
            FXMLLoader loader = new FXMLLoader(UtilityManager.class.getResource(path));
            Parent helpRoot = loader.load();

            rootPane.getChildren().add(helpRoot);  // rootPane is the main container in Start.fxml

            // set the helpRoot visible (it will be hidden initially)
            helpRoot.setVisible(true);

// TODO; make popup close in different ways

//            // to close the popup, click anywhere
//            helpRoot.setOnMouseClicked(event -> {
//                helpRoot.setVisible(false);  // Hide the popup
//            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Popup initialized");
    }

    @FXML
    public static void chatControl() {
        // TODO; Create logic for chat
        System.out.println("Chat initialized");
    }
    public static void createScaleTransition(StackPane button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        button.setOnMouseEntered(event -> {
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
            button.setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
            button.setCursor(Cursor.DEFAULT);
        });
    }
    public static void createtranslationTransition(StackPane cartridge) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), cartridge);
        translateTransition.setToY(-10);

        cartridge.setOnMouseEntered(event -> {
            translateTransition.play();
            cartridge.setCursor(Cursor.HAND);
        });

        cartridge.setOnMouseExited(event -> {
            translateTransition.setToY(10);
            translateTransition.play();
            cartridge.setCursor(Cursor.DEFAULT);
        });
    }
    public static void colourTransition(Text text) {
        text.setOnMouseEntered(event -> {
            text.setFill(Color.BLUE);
        });
        text.setOnMouseExited(event -> {
            text.setFill(Color.color(0.6235, 0.5961, 0.549, 1.0));
        });
    }
}
