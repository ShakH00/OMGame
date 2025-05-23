import account.LoggedInAccount;
import authentication.Authentication.CAPTCHAAuthentication;
import authentication.ExceptionsAuthentication.CAPTCHAAuthenticationFailedException;
import database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

public class CaptchaPopupController extends Application  {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane submitButton;
    @FXML
    private javafx.scene.image.ImageView img;
    @FXML
    private Text textBased;
    @FXML
    private Text prompt;
    @FXML
    private javafx.scene.control.TextField input;

    private CAPTCHAAuthentication.captcha cap;

    private Random rand = new Random();
    private int randomNumber = rand.nextInt(3); // 0 to 3 inclusive
    private String imageAddress;

    public void initialize(){
        img.setVisible(false);
        textBased.setText("");
        prompt.setText("");


        if(randomNumber == 0){
            // Image captcha test
            imageAddress = "/authentication/CAPTCHAImages/";
            imageAddress += CAPTCHAAuthentication.chooseImage();
            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResource(imageAddress).toExternalForm());
            img.setImage(image);
            img.setVisible(true);
            prompt.setText("Enter the text from the image to continue.");
        }else if(randomNumber == 1){
            // Text Captcha
            cap = CAPTCHAAuthentication.generateTextProblem();
            textBased.setText(cap.getInstructions());
            prompt.setText(cap.getPrompt());
        }else{
            // Math captcha
            cap = CAPTCHAAuthentication.generateProblem();
            textBased.setText(cap.getInstructions());
            prompt.setText(cap.getPrompt());
        }

        UtilityManager.createScaleTransition(submitButton);
    }




    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("screens/CaptchaPopup.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();

            Font pressStartFont = Font.loadFont(fontPath, 40);
            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
            Font pixeliteFont = Font.loadFont(pixelitePath, 40);

            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            primaryStage.setResizable(false);

            // Set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    when the submit button is pressed, check the captcha test, grant access if correct
     */
    @FXML
    private void submitButton(){
        try {
            if (randomNumber == 0) {
                // Image based
                String expected = imageAddress.substring(30, imageAddress.length()-4);
                CAPTCHAAuthentication.captchaAuthenticatorDriver(input.getText(), "image", expected);
            } else if (randomNumber == 1) {
                // Text based
                CAPTCHAAuthentication.captchaAuthenticatorDriver(input.getText(), "image", cap.getAnswer());
            } else {
                // Math based
                CAPTCHAAuthentication.captchaAuthenticatorDriver(input.getText(), "image", cap.getAnswer());
            }
            UtilityManager.popupClose(rootPane);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            SceneManager.switchScene(stage, "screens/MatchType.fxml");
        }catch (CAPTCHAAuthenticationFailedException e){
            // If captcha test was failed, do not grant access to the account
            DatabaseManager.deleteAccountByEmail(LoggedInAccount.getAccount().getEmail());
            UtilityManager.popupClose(rootPane);
        }

    }
}
