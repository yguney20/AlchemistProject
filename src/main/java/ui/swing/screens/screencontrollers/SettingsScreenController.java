package ui.swing.screens.screencontrollers;

import java.awt.Frame;

import domain.controllers.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import ui.swing.screens.SettingsScreen;
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

    public void setSettingsState(SettingsState settingsState) {
        this.settingsState = settingsState;
    }

    public void initialize() {
        if (settingsState != null) {
            volumeSlider.setValue(settingsState.getVolume() * 100);
        }
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
