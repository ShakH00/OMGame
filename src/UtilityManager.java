import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.sun.glass.ui.Cursor.setVisible;

public class UtilityManager {

    private static Parent currentPopup;

    /**
     * Method to open up the resignation/offer draw popup
     *
     * @param mouseEvent
     * @param path Path to page
     * @param rootPane Pane that the popup is added to
     *
     * @author Emily M & Arwa
     */
    // TODO: Implement separately from main screen help popup
    @FXML
    public static void popupOpen(MouseEvent mouseEvent, String path, AnchorPane rootPane) {
        try {
            // load help.fxml
            FXMLLoader loader = new FXMLLoader(UtilityManager.class.getResource(path));
            currentPopup = loader.load();
            currentPopup.setId("popupHelp");

            rootPane.getChildren().add(currentPopup);  // rootPane is the main container in Start.fxml
            currentPopup.getStylesheets().add(UtilityManager.class.getResource("styles.css").toExternalForm());

            // set the helpRoot visible (it will be hidden initially)
            currentPopup.setVisible(true);

            // TODO; make popup close in different ways

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public static void popupClose(AnchorPane rootPane) {
        if (currentPopup != null) {
            currentPopup.setVisible(false);
            rootPane.getChildren().remove(currentPopup);
        }
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
    public static void colourTransition(Text text, Color color) {
        text.setOnMouseEntered(event -> {
            text.setFill(Color.BLUE);
        });
        text.setOnMouseExited(event -> {
            text.setFill(color);
        });
    }

    /**
     * Method to change the color of arrow labels when hovered over
     *
     * @param label Label that is hovered on
     *
     * @author Shakil H
     */
    public static void colourTransition1(Label label) {
        Color hoverColor = Color.web("#254663");
        Color normalColor = Color.web("#13283b");

        label.setTextFill(normalColor);

        label.setOnMouseEntered(event -> {
            label.setTextFill(hoverColor);
        });

        label.setOnMouseExited(event -> {
            label.setTextFill(normalColor);
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

    /**
     * Method to add hover effects to match panes (which changes rectangle opacity too!)
     *
     * @param pane Pane that is hovered on
     *
     * @author Arwa A
     */
    public static void addHoverEffectRectangle(Pane pane) {
        Rectangle rect = (Rectangle) pane.getChildren().get(0);
        rect.setOpacity(0.86);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), pane);
        scaleIn.setToX(1.05);
        scaleIn.setToY(1.05);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), pane);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // add hover effect to adjust the rectangle opacity and play scale transition
        pane.setOnMouseEntered(e -> {
            rect.setOpacity(1.0);
            scaleIn.play();
        });

        pane.setOnMouseExited(e -> {
            rect.setOpacity(0.86);
            scaleOut.play();
        });
    }
}
