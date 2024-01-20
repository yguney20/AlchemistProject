package ui.swing.screens;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.MakeExperimentScreenController;
import ui.swing.screens.screencontrollers.PublicationCardsScreenController;

import javax.swing.*;
import java.awt.*;

public class PublicationCardsScreen extends JFrame{

    private JFrame frame;
    private JFXPanel jfxPanel;

    public PublicationCardsScreen() {
        initializeSwingComponents();
        initializeJavaFXComponents();
    }

    private void initializeSwingComponents() {
        // Create and set up the window
        frame = new JFrame("Publication Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(947, 735);
        frame.setLocationRelativeTo(null); // Center the frame

        // Create the JFXPanel to add JavaFX content
        jfxPanel = new JFXPanel();
        frame.add(jfxPanel, BorderLayout.CENTER);
    }

    private void initializeJavaFXComponents() {
        // This method is invoked on the JavaFX thread
        Platform.runLater(() -> {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/PublicationCardsScreen.fxml"));
                // Ensure the FXML file path is correct. It's often placed in the resources folder.
                Parent root = loader.load();
                PublicationCardsScreenController controller = loader.getController();
            	controller.setPublicationCardsScreen(this); // 'this' should be a reference to the JFrame.

                // Create a scene with the loaded FXML
                Scene scene = new Scene(root);

                jfxPanel.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exceptions here
            }
        });
    }

    public void display() {
        // Make the frame visible on the AWT Event Dispatching Thread
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
    
    public void close() {
        // Make the frame visible on the AWT Event Dispatching Thread
        SwingUtilities.invokeLater(() -> frame.setVisible(false));
    }
    
}
