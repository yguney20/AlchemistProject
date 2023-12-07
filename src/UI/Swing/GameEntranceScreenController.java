package UI.Swing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameEntranceScreenController {

    @FXML
    private Button playButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button quitButton;

    @FXML
    private void initialize() {
        // Example: setting an image on a button
        setImageOnButton(playButton, "/UI/Swing/Images/a1.png");
        setImageOnButton(playButton, "/UI/Swing/Images/a2.png");
        setImageOnButton(playButton, "/UI/Swing/Images/a3.png");
        setImageOnButton(playButton, "/UI/Swing/Images/a4.png");
        setImageOnButton(playButton, "/UI/Swing/Images/a5.png");
        setImageOnButton(playButton, "/UI/Swing/Images/a6.png");
        // Initialize other buttons similarly if they need icons or special setup
    }

    @FXML
    private void handlePlay() {
        openScreen("LoginScreen.fxml"); // Assuming you have a LoginScreen.fxml
    }

    @FXML
    private void handleSettings() {
        openScreen("SettingsScreen.fxml"); // Replace with your settings screen FXML
    }

    @FXML
    private void handleHelp() {
        openScreen("HelpScreenControllerü.fxml"); // Replace with your help screen FXML
    }

    @FXML
    private void handleQuit() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    private void openScreen(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) quitButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }

    private void setImageOnButton(Button button, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20); // Set the size as needed
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
    }
}
