package ui.swing.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PublishTheoryFXScreen {

    public void display() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/PublishTheory.fxml"));
            Parent root = loader.load();

            // Create and set up a new stage (window)
            Stage stage = new Stage();
            stage.setTitle("Publish Theory");
            stage.setScene(new Scene(root)); // Set the scene on the stage

            stage.initStyle(StageStyle.UNDECORATED);

            // Disable resizing of the stage
            stage.setResizable(false);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, possibly by showing an error message
        }
    }


}
