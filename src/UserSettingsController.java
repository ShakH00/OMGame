import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserSettingsController extends Application {

    private static final String AVATAR_PATH = "file:/D:/seng300-project/src/images/sprites/tetrisCatIcon.png";
    private static final String RETRO_FONT_PATH = "file:/D:/seng300-project/src/resources/fonts/RetroGaming.ttf";
    private static final String BACKGROUND_PATH = "file:/D:/seng300-project/src/images/adjustedstars.png";
    private Font retroFont;

    @Override
    public void start(Stage primaryStage) {
        retroFont = Font.loadFont(RETRO_FONT_PATH, 20);

        HBox content = new HBox(50);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);

        // === LEFT PANEL ===
        VBox leftPanel = new VBox(18); // slightly tighter spacing
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(25, 20, 25, 20));
        leftPanel.setPrefWidth(250);
        leftPanel.setMaxHeight(320); // limit height to trim bottom area
        leftPanel.setStyle(
                "-fx-background-color: #2b355e;" +
                "-fx-border-color: #fda4ba;" +
                "-fx-border-width: 3px;" +
                "-fx-border-radius: 20px;" +
                "-fx-background-radius: 20px;"
        );

        ImageView avatar = new ImageView(new Image(AVATAR_PATH));
        avatar.setFitWidth(70);
        avatar.setFitHeight(70);

        Label username = new Label("TetrisCat");
        username.setFont(Font.font(retroFont.getFamily(), 20));
        username.setTextFill(Color.WHITE);

        Rectangle divider = new Rectangle(120, 5, Color.CYAN);
        divider.setArcWidth(10);
        divider.setArcHeight(10);

        Label quote = new Label("I may be a cat,\nBut around these\nparts, they call\nme the GOAT 🐐🐐");
        quote.setTextFill(Color.LIGHTGRAY);
        quote.setFont(Font.font("Courier New", 14));
        quote.setAlignment(Pos.CENTER);

        leftPanel.getChildren().addAll(avatar, username, divider, quote);

        // === RIGHT PANEL ===
        VBox rightPanel = new VBox(25);
        rightPanel.setAlignment(Pos.TOP_LEFT);

        Text title = new Text("EDIT PROFILE");
        title.setFont(Font.font(retroFont.getFamily(), 26));
        title.setFill(Color.WHITE);

        VBox formBox = new VBox(20);
        formBox.getChildren().addAll(
                createFormRow("Display name:", new TextField("theGOAT"), createButton("Change Avatar", "#f4b400")),
                createFormRow("Old Password:", new PasswordField(), null),
                createFormRow("New Password:", new PasswordField(), createButton("Change Banner", "#00bcd4"))
        );

        VBox privacyBox = new VBox(15);
        Text privacyTitle = new Text("Privacy");
        privacyTitle.setFont(Font.font(retroFont.getFamily(), 20));
        privacyTitle.setFill(Color.WHITE);

        privacyBox.getChildren().addAll(
                privacyTitle,
                createToggleRow("Hide stats profile"),
                createToggleRow("Friend visibility")
        );

        rightPanel.getChildren().addAll(title, formBox, privacyBox);
        content.getChildren().addAll(leftPanel, rightPanel);

        // === BACKGROUND ===
        ImageView background = new ImageView(new Image(BACKGROUND_PATH));
        background.setFitWidth(950);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        StackPane root = new StackPane();
        root.getChildren().addAll(background, content);

        Scene scene = new Scene(root, 950, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Settings");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private HBox createFormRow(String labelText, Control field, Button optionalButton) {
        Label label = new Label(labelText);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(retroFont.getFamily(), 16));

        field.setStyle(
                "-fx-background-color: #dddddd;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-radius: 12px;" +
                "-fx-padding: 6 10;" +
                "-fx-font-family: 'Courier New';"
        );

        if (field instanceof TextField tf) {
            tf.setPrefWidth(160);
        } else if (field instanceof PasswordField pf) {
            pf.setPrefWidth(160);
        }

        HBox row = new HBox(10, label, field);
        if (optionalButton != null) row.getChildren().add(optionalButton);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Button createButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setFont(Font.font(retroFont.getFamily(), 14));
        button.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 6 12;" +
                "-fx-text-fill: black;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 2, 0.5, 0.5, 1);"
        );
        return button;
    }

    private HBox createToggleRow(String labelText) {
        Label label = new Label(labelText);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(retroFont.getFamily(), 16));

        ToggleGroup group = new ToggleGroup();
        RadioButton yes = styledRadio("yes");
        RadioButton friends = styledRadio("friends");
        RadioButton no = styledRadio("no");

        yes.setToggleGroup(group);
        friends.setToggleGroup(group);
        no.setToggleGroup(group);
        yes.setSelected(true);

        HBox row = new HBox(10, label, yes, friends, no);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private RadioButton styledRadio(String label) {
        RadioButton rb = new RadioButton(label);
        rb.setFont(Font.font("Courier New", 14));
        rb.setStyle(
                "-fx-background-color: #dddddd;" +
                "-fx-background-radius: 15px;" +
                "-fx-border-radius: 15px;" +
                "-fx-padding: 4 8;" +
                "-fx-font-family: 'Courier New';"
        );
        return rb;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
