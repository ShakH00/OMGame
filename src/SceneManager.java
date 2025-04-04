import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static final HashMap<String, Parent> sceneCache = new HashMap<>();

    public static void preloadScenes(String... fxmlPaths) {
        Task<Void> preloadTask = new Task<>() {
            @Override
            protected Void call() {
                for (String path : fxmlPaths) {
                    try {
                        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
                        Parent root = loader.load();
                        sceneCache.put(path, root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        new Thread(preloadTask).start();
    }

    public static void switchScene(Stage stage, String fxmlPath) {
        Parent newRoot = sceneCache.get(fxmlPath);
        if (newRoot == null) {
            System.err.println("Scene not preloaded: " + fxmlPath);
            return;
        }

        Scene currentScene = stage.getScene();
        if (currentScene == null) {
            stage.setScene(new Scene(newRoot, 800, 570));
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            Platform.runLater(() -> {
                stage.setScene(new Scene(newRoot, 800, 570));

                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
        });

        fadeOut.play();
    }
}
