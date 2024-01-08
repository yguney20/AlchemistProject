package ui.swing.screens;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.controllers.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.swing.screens.screencontrollers.EntranceScreenController;


public class EntranceScreen extends JFrame{

    private JPanel contentPane;
    private GameController gameController = GameController.getInstance();
    private JFXPanel fxPanel;
    private JFrame frame;
    
    
    public EntranceScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }
    
    private void initializeFrame() {
        setTitle("Ku Alchemist Entrance Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 900, 505); // Adjust the size accordingly
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
    }
    
    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/EntranceScreen.fxml"));
                Parent root = loader.load();
                EntranceScreenController controller = loader.getController();
                controller.setEntranceScreenFrame(this);
                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
    
    
    public void close() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }
    
	
	private void playSound(String soundFilePath) {
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    
    
}