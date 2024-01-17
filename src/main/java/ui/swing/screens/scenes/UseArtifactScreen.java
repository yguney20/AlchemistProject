package ui.swing.screens.scenes;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.*;
import java.io.IOException;

public class UseArtifactScreen extends JFrame {

    private JFXPanel fxPanel; // The JavaFX panel to embed JavaFX content

    public UseArtifactScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }

    private void initializeFrame() {
        setTitle("Use Artifact");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 155, 900, 350); // Adjust size as needed
        setUndecorated(true);

        fxPanel = new JFXPanel(); // Initialize JFXPanel
        add(fxPanel);
    }

    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/UseArtifactScreen.fxml")); // Update path
                Parent root = loader.load();

                UseArtifactScreenController controller = loader.getController();
                // Additional setup for the controller if needed
                controller.setArtifactScreenFrame(this);

                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

}
