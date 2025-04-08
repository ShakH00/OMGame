import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class UtilityManager {
    /**
     * Method to open up the resignation/offer draw popup
     *
     * @param mouseEvent
     * @param path Path to page
     * @param rootPane Pane that the popup is added to
     *
     * @author Emily M
     */
    // TODO: Implement separately from main screen help popup
    @FXML
    public static void popupControl(MouseEvent mouseEvent, String path, AnchorPane rootPane) {
        // TODO; check if it works with other not help popups
        try {
            // load help.fxml
            FXMLLoader loader = new FXMLLoader(UtilityManager.class.getResource(path));
            Parent helpRoot = loader.load();

            rootPane.getChildren().add(helpRoot);  // rootPane is the main container in Start.fxml

            // set the helpRoot visible (it will be hidden initially)
            helpRoot.setVisible(true);

            // TODO; make popup close in different ways

            // to close the popup, click anywhere
            helpRoot.setOnMouseClicked(event -> {
                helpRoot.setVisible(false);  // Hide the popup
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Popup initialized");
    }

    /**
     * Method to open up the chat window
     *
     * @param
     *
     * @author
     */
    @FXML
    public static void chatControl() {
        // TODO; Create logic for chat
        System.out.println("Chat initialized");
    }

    /**
     * Method to scale a button when hovered over
     *
     * @param button Button that is hovered on
     *
     * @author Shakil H
     */
    public static void createScaleTransition(StackPane button) {
        button.setOnMouseEntered(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), button);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
            button.setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), button);
            scaleTransition.setFromX(1.1);
            scaleTransition.setFromY(1.1);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
            button.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * Method to translate a game cartridge when hovered over
     *
     * @param cartridge Cartridge that is hovered on
     *
     * @author Emily M, edited by Shakil H
     */
    public static void createTranslationTransition(StackPane cartridge) {
        cartridge.setOnMouseEntered(event -> {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), cartridge);
            translateTransition.setToY(-10);
            translateTransition.play();
            cartridge.setCursor(Cursor.HAND);
        });

        cartridge.setOnMouseExited(event -> {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), cartridge);
            translateTransition.setToY(10);
            translateTransition.play();
            cartridge.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * Method to change the color of text when hovered over
     *
     * @param text Text that is hovered on
     *
     * @author Emily M
     */
    public static void colourTransition(Text text) {
        text.setOnMouseEntered(event -> {
            text.setFill(Color.BLUE);
        });
        text.setOnMouseExited(event -> {
            text.setFill(Color.color(0.6235, 0.5961, 0.549, 1.0));
        });
    }


    /**
     * Method to add hover effects to user info panes
     *
     * @param pane Pane that is hovered on
     *
     * @author Arwa A
     */
    public static void addHoverEffect (Pane pane){
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), pane);
        scaleIn.setToX(1.05);
        scaleIn.setToY(1.05);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), pane);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        pane.setOnMouseEntered(e -> scaleIn.play());
        pane.setOnMouseExited(e -> scaleOut.play());
    }
}
