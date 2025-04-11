import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class SceneManager {
    private static final HashMap<String, String> scenePaths = new HashMap<>();

    // Register scenes with their FXML paths
    public static void registerScenes(String... fxmlPaths) {
        for (String path : fxmlPaths) {
            scenePaths.put(path, path);  // Store the path to each scene
        }
    }

    // Switch scene and handle fade transitions
    public static void switchScene(Stage stage, String fxmlPath) {
        if (!scenePaths.containsKey(fxmlPath)) {
            System.err.println("Scene not registered: " + fxmlPath);
            return;
        }

        try {
            // Load the scene from the FXML file each time
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent newRoot = loader.load();

            Scene currentScene = stage.getScene();
            if (currentScene == null) {
                stage.setScene(new Scene(newRoot, 800, 570));
                return;
            }

            Parent oldRoot = currentScene.getRoot();

            // Create a color overlay for fade effect
            Region colorOverlay = new Region();
            colorOverlay.setStyle("-fx-background-color: #169FD4;");
            colorOverlay.setOpacity(0);

            StackPane transitionPane = new StackPane(oldRoot, colorOverlay);
            Scene transitionScene = new Scene(transitionPane, 800, 570);
            stage.setScene(transitionScene);

            // Fade in the color overlay
            FadeTransition fadeToColor = new FadeTransition(Duration.millis(300), colorOverlay);
            fadeToColor.setFromValue(0.0);
            fadeToColor.setToValue(1.0);
            fadeToColor.setOnFinished(e -> {
                // After fading to the color, switch to the new scene
                Scene newScene = new Scene(newRoot, 800, 570);
                stage.setScene(newScene);

                // Optional: fade in the new scene from the color
                newRoot.setOpacity(0.0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeToColor.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
