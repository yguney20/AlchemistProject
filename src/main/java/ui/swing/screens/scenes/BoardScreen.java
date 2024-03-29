package ui.swing.screens.scenes;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import domain.controllers.GameController;
import ui.swing.screens.screencontrollers.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class BoardScreen extends JFrame{

    private JPanel contentPane;
    private JLabel currentPlayerLabel;
    private JLabel currentTurnLabel;
    private JLabel currentRoundLabel;
    private GameController gameController = GameController.getInstance();
    private JFXPanel fxPanel;
    private JFrame frame;
    private static BoardScreen instance;
    BoardScreenController controller;
    
    public static synchronized BoardScreen getInstance() {
        if (instance == null) {
            instance = new BoardScreen();
        }
        return instance;
    }

    public static synchronized void destroyInstance() {
        instance = null;
    }
    
    public BoardScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }
    
    private void initializeFrame() {
        setTitle("Ku Alchemist Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 1300, 705); // Adjust the size accordingly
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
    }
    
    public void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ui/swing/screens/fxmlfiles/BoardScreen.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent root = loader.load();
                controller = loader.getController();
                if (controller == null) {
                    throw new IllegalStateException("BoardScreenController is null after FXMLLoader");
                }
                controller.setBoardScreenFrame(this);
                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
                // Consider exiting the application or handling the error more gracefully
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


    public BoardScreenController getController() {
        return controller;
    }

    public void updateLabels() {
        Platform.runLater(() -> {
            System.out.println("Update labels içi :"+ gameController.getGameState());
            //Online kısmında client ile vermemiz gerekebilir
            //Player playerToShow = gameController.isOnlineMode() ? gameController.getClientPlayer() : gameController.getCurrentPlayer();
            currentPlayerLabel.setText("Player: " + gameController.getGameState().getCurrentPlayer().getNickname());
            currentTurnLabel.setText("Turn: " + gameController.getGameState().getCurrentTurn());
            currentRoundLabel.setText("Round: " + gameController.getGameState().getCurrentRound());
        });
    }

}
