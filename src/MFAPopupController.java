package authentication;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MFAPopupController {

    @FXML
    private TextField codeField;

    private String verificationCode; // The code to verify

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @FXML
    private void verifyCode() {
        String userInput = codeField.getText();

        if (userInput.equals(verificationCode)) {
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Verification Successful");
            alert.setHeaderText(null);
            alert.setContentText("The code is correct!");
            alert.showAndWait();

            // Close the pop-up
            Stage stage = (Stage) codeField.getScene().getWindow();
            stage.close();
        } else {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Verification Failed");
            alert.setHeaderText(null);
            alert.setContentText("The code you entered is incorrect. Please try again.");
            alert.showAndWait();
        }
    }
}