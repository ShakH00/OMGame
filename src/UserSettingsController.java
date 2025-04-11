import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserSettingsController extends Application {

    @FXML
    private Label nameDisplay;

    @FXML
    private TextField displayNameField;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private StackPane backButton;
    @FXML
    private StackPane button1;
    @FXML
    private StackPane button2;

    @FXML private RadioButton hideStatsYes;
    @FXML private RadioButton hideStatsFriends;
    @FXML private RadioButton hideStatsNo;

    @FXML private RadioButton friendVisYes;
    @FXML private RadioButton friendVisFriends;
    @FXML private RadioButton friendVisNo;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/UserSettings.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

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
        UtilityManager.createScaleTransition(backButton);
        UtilityManager.createScaleTransition(button1);
        UtilityManager.createScaleTransition(button2);

        // Bind display name field to label
        if (nameDisplay != null && displayNameField != null) {
            nameDisplay.textProperty().bind(displayNameField.textProperty());
        }

        // Set up exclusive toggle groups for privacy sections
        ToggleGroup hideStatsGroup = new ToggleGroup();
        hideStatsYes.setToggleGroup(hideStatsGroup);
        hideStatsFriends.setToggleGroup(hideStatsGroup);
        hideStatsNo.setToggleGroup(hideStatsGroup);

        ToggleGroup friendVisGroup = new ToggleGroup();
        friendVisYes.setToggleGroup(friendVisGroup);
        friendVisFriends.setToggleGroup(friendVisGroup);
        friendVisNo.setToggleGroup(friendVisGroup);
    }

    @FXML
    public void backButton() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "screens/UserProfile.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

