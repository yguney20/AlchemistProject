package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.swing.screens.scenes.PauseScreen;

import java.awt.Frame;
import javax.swing.JFrame;

import animatefx.animation.Pulse;
import domain.Game;

public class PauseScreenController {
	
    @FXML
    private Button resumeGameButton;

    private Frame mainFrame;
    private JFrame menuScreen;
    private PauseScreen pauseScreen;
    private String pausingPlayerName;
    private Game game = Game.getInstance();

    private MediaPlayer buttonSoundPlayer;
    
    public PauseScreenController() {
		initializeMediaPlayers();
	}

    public void setMainFrame(Frame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setMenuScreen(JFrame menuScreen) {
        this.menuScreen = menuScreen;
    }

    public void setPauseScreen(PauseScreen pauseScreen) {
        this.pauseScreen = pauseScreen;
    }
    
    public void setPausingPlayerName(String pausingPlayerName) {
        this.pausingPlayerName = pausingPlayerName;
        updateResumeButtonVisibility();
    }
    
    
    private void initializeMediaPlayers() {
        
        String buttonSoundFile = getClass().getResource("/ui/swing/resources/sounds/buttonSound.wav").toExternalForm();
        Media buttonSound = new Media(buttonSoundFile);
        buttonSoundPlayer = new MediaPlayer(buttonSound);
    }
    
    //-------------
    private void updateResumeButtonVisibility() {
        // Only show the resume button if the current player is the one who paused the game
        resumeGameButton.setVisible(pausingPlayerName.equals(game.getCurrentPlayer().getNickname()));
    }


    @FXML
    private void handleMousePress() {
        if (mainFrame != null) {
            mainFrame.setVisible(true);
        }
        if (menuScreen != null) {
            menuScreen.dispose();
        }
        if (pauseScreen != null) {
            pauseScreen.dispose();
        }
    }
    
    @FXML
    private void handleMouseEnter(MouseEvent event) {
        new Pulse((Button) event.getSource()).play();
        if (buttonSoundPlayer != null) {
            buttonSoundPlayer.stop(); // Stop the sound to reset it if it was already playing
            buttonSoundPlayer.play(); // Play the sound
        }
    }
}
