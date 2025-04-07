import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WinScreenController implements Initializable {

    @FXML
    private Button startOverBtn;



    @FXML
    private Button mainMenuBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    private void handleStartOver(ActionEvent event) {
        System.out.println("Started  over");

    }

    @FXML
    private void handleMainMenu(ActionEvent event) {
        System.out.println("Main menu");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //
        SceneManager.registerScenes("/screens/Start.fxml");
        SceneManager.switchScene(stage, "/screens/Start.fxml");

    }



}