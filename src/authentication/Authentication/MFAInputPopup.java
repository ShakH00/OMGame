package authentication.Authentication;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class MFAInputPopup {

    /**
     * Displays a pop-up window for the user to enter the MFA code.
     *
     * @param verificationCode The MFA code to verify against.
     * @return The user input from the pop-up window.
     */
    public String showPopup(String verificationCode) {
        // Use a TextInputDialog to get user input
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("MFA Verification");
        dialog.setHeaderText("Enter the MFA code sent to your email:");
        dialog.setContentText("Code:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();

        // Check the user input
        if (result.isPresent()) {
            String userInput = result.get();
            if (userInput.equals(verificationCode)) {
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Code verified successfully!", ButtonType.OK);
                alert.showAndWait();
                return userInput; // Return the valid input
            } else {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid code. Please try again.", ButtonType.OK);
                alert.showAndWait();
                return null; // Return null if the code is invalid
            }
        } else {
            // User canceled the dialog
            return null; // Return null if the user cancels
        }
    }
}