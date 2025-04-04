import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.HashMap;

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

            // Create a fade-out transition for the current scene
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), currentScene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                Platform.runLater(() -> {
                    // Switch to the new scene with a fade-in effect
                    stage.setScene(new Scene(newRoot, 800, 570));

                    FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });
            });
            fadeOut.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
