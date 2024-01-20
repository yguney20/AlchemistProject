package ui.swing.screens.scenes;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import domain.controllers.GameController;

public class MakeExperimentScreen extends JFrame {

    private GameController gameController = GameController.getInstance();
    private JFXPanel fxPanel; // JavaFX panel to embed JavaFX content

    public MakeExperimentScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }

    private void initializeFrame() {
        setTitle("Make Experiment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 904, 550); // Adjust the size as needed
        setResizable(false);
        fxPanel = new JFXPanel(); // Prepares JavaFX toolkit and environment
        add(fxPanel);
    }

    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles//MakeExperimentScreen.fxml"));
                Parent root = loader.load();

                // If you need to pass the current JFrame to the controller:
                // MakeExperimentScreenController controller = loader.getController();
                // controller.setExperimentScreenFrame(this);

                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
	
	public void close() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }

    
}
