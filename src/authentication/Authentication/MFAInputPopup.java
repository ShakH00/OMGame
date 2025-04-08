package authentication.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MFAInputPopup extends MFAAuthentication {

    private String userInput;

    /**
     * Displays a pop-up window for the user to enter the MFA code.
     *
     * @param verificationCode The MFA code to verify against (for testing purposes).
     * @return The user input from the pop-up window.
     */
    public String showPopup(String verificationCode) {
        // Create the frame
        JFrame frame = new JFrame("MFA Verification");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Create a label to display instructions
        JLabel label = new JLabel("Enter the MFA code sent to your email:", SwingConstants.CENTER);
        frame.add(label, BorderLayout.NORTH);

        // Create a text field for user input
        JTextField textField = new JTextField();
        frame.add(textField, BorderLayout.CENTER);

        // Create a button to submit the input
        JButton submitButton = new JButton("Submit");
        frame.add(submitButton, BorderLayout.SOUTH);

        // Add an action listener to the button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = textField.getText();
                if (userInput.equals(verificationCode)) {
                    JOptionPane.showMessageDialog(frame, "Code verified successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                frame.dispose(); // Close the window after submission
            }
        });

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

        return userInput;
    }
}