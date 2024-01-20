package ui.swing.screens;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.GameOverScreenController;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameOverScreen extends JFrame {

    private JFXPanel fxPanel; // JavaFX panel to embed JavaFX content

    public GameOverScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }

    private void initializeFrame() {
        setTitle("Game Over");
        setSize(702, 570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        fxPanel = new JFXPanel(); // Create a JavaFX panel
        add(fxPanel); // Add JavaFX panel to JFrame
    }

    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/GameOverScreen.fxml"));
                Parent root = loader.load();
                GameOverScreenController controller = loader.getController();
            	controller.setGameOverScreen(this); // 'this' should be a reference to the JFrame.
                Scene scene = new Scene(root);

                fxPanel.setScene(scene); // Set the JavaFX scene to the JFXPanel
            } catch (Exception e) {
                e.printStackTrace(); // Handle exception as needed
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
    
    public void close() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameOverScreen gameOverScreen = new GameOverScreen();
            gameOverScreen.display();
        });
    }

   
}
