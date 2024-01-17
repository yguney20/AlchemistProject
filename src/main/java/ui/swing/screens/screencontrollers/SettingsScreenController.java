package ui.swing.screens.screencontrollers;

import java.awt.Frame;

import animatefx.animation.Pulse;
import domain.controllers.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.swing.screens.scenes.SettingsScreen;
import ui.swing.screens.screencomponents.SettingsState;

public class SettingsScreenController {

    @FXML
    private Button backButton, saveButton, muteButton;
    @FXML
    private Slider volumeSlider;

    private SettingsState settingsState = GameController.getSettingsState();
    private double lastVolume;
    private Frame previousFrame; // Reference to the previous frame
    private SettingsScreen settingsScreen;
    private static EntranceScreenController entranceScreenController = EntranceScreenController.getInstance();
    
    private MediaPlayer buttonSoundPlayer;
    

    public void setSettingsState(SettingsState settingsState) {
        this.settingsState = settingsState;
    }

    public void initialize() {
        if (settingsState != null) {
            volumeSlider.setValue(settingsState.getVolume() * 100);
        }
        initializeMediaPlayers();
    }
    
    private void initializeMediaPlayers() {
        
        String buttonSoundFile = getClass().getResource("/sounds/buttonSound.wav").toExternalForm();
        Media buttonSound = new Media(buttonSoundFile);
        buttonSoundPlayer = new MediaPlayer(buttonSound);
    }
    
    // Setter for the previous frame
    public void setPreviousFrame(Frame frame) {
        this.previousFrame = frame;
    }

    // Setter for the current settings screen
    public void setSettingsScreen(SettingsScreen screen) {
        this.settingsScreen = screen;
    }
    
    @FXML
    private void handleMouseEnter(MouseEvent event) {
        new Pulse((Button) event.getSource()).play();
        if (buttonSoundPlayer != null) {
            buttonSoundPlayer.stop(); // Stop the sound to reset it if it was already playing
            buttonSoundPlayer.play(); // Play the sound
        }
    }


    @FXML
    private void handleBackAction() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // Make the previous frame visible
        }
        if (settingsScreen != null) {
            settingsScreen.dispose(); // Close the settings screen
        }
    }

    @FXML
    private void handleSaveAction() {
        if (settingsState != null) {
            settingsState.setVolume(volumeSlider.getValue() / 100.0);
            // Additional save logic if necessary
        }
    }


    @FXML
    private void handleMuteAction() {
        if (volumeSlider.getValue() > 0) {
            lastVolume = volumeSlider.getValue() / 100.0;
            volumeSlider.setValue(0);
            settingsState.setVolume(0);
            entranceScreenController.setMusicVolume(0);
        } else {
            volumeSlider.setValue(lastVolume * 100);
            entranceScreenController.setMusicVolume(lastVolume);
            settingsState.setVolume(volumeSlider.getValue() / 100.0);
        }
    }

    
    @FXML
    private void handleVolumeChange() {
        double volume = volumeSlider.getValue() / 100.0;
        settingsState.setVolume(volume);
        entranceScreenController.setMusicVolume(volume);
    }

}
