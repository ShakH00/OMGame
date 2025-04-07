import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class UtilityManager {
    @FXML
    public static void popupControl() {
        // TODO; Create logic for popup
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
}
