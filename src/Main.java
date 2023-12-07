import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the GameEntranceScreen FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Swing/GameEntranceScreen.fxml"));
        Parent root = loader.load();

        // Set the scene and show the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Entrance");
        primaryStage.show();
    }
}
