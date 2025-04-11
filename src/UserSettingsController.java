import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserSettingsController extends Application {

    @FXML
    private Label nameDisplay;

    @FXML
    private TextField displayNameField;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/UserSettings.fxml"));
            Scene scene = new Scene(loader.load(), 950, 600);

            // Load custom fonts
            String pressStartFont = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            String retroGamingFont = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
            String pixeliteFont = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();

            Font.loadFont(pressStartFont, 40);
            Font.loadFont(retroGamingFont, 40);
            Font.loadFont(pixeliteFont, 40);

            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            primaryStage.setTitle("User Settings");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        if (nameDisplay != null && displayNameField != null) {
            nameDisplay.textProperty().bind(displayNameField.textProperty());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

