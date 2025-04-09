import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.registerScenes("/screens/Start.fxml");








//        Parent root = FXMLLoader.load(getClass().getResource("/screens/LeaderboardScreen.fxml"));
//        primaryStage.setTitle("Leaderboard");




        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/DrawScreen.fxml"));
        Parent popupRoot = loader.load();


//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/WinScreen.fxml"));
//        Parent popupRoot = loader.load();

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/LoseScreen.fxml"));
//        Parent popupRoot = loader.load();
//
        Stage popupStage = new Stage();
        popupStage.setScene(new Scene(popupRoot));
        popupStage.setTitle("You Lose!");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);
        popupStage.showAndWait();

//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}