package ui.swing.screens.screencontrollers;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JFrame;

import animatefx.animation.Pulse;
import domain.Game;
import domain.controllers.GameController;
import domain.controllers.LoginController;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.Player;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ui.swing.screens.ConnectGameScreen;
import ui.swing.screens.HelpScreen;
import ui.swing.screens.HostGameScreen;
import ui.swing.screens.scenes.BoardScreen;
import ui.swing.screens.scenes.EntranceScreen;
import ui.swing.screens.scenes.LoginOverlay;
import ui.swing.screens.scenes.MenuScreen;
import ui.swing.screens.scenes.PauseScreen;
import ui.swing.screens.scenes.SettingsScreen;

public class MenuScreenController {
	@FXML
    private Button pauseGameButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button quitButton;
    @FXML
    private ImageView wizardImage;
    
    private Frame previousFrame;

    private GameController gameController = GameController.getInstance();
    private BoardScreen boardFrame = BoardScreen.getInstance();
    private Frame entranceScreenFrame;
    private JFrame menuScreenFrame;
    private static MenuScreenController instance;
    private MediaPlayer buttonSoundPlayer;
    
    public MenuScreenController() {
    	initializeMediaPlayers();
    }

    public static synchronized MenuScreenController getInstance() {
        if (instance == null) {
            instance = new MenuScreenController();
        }
        return instance;
    }
    
    private void initializeMediaPlayers() {
        
        String buttonSoundFile = getClass().getResource("/ui/swing/resources/sounds/buttonSound.wav").toExternalForm();
        Media buttonSound = new Media(buttonSoundFile);
        buttonSoundPlayer = new MediaPlayer(buttonSound);
    }
    
    public void setMenuScreenFrame(JFrame frame) {
        this.menuScreenFrame = frame;
    }
    
    @FXML
    private void handleMouseEnter(MouseEvent event) {
        new Pulse((Button) event.getSource()).play();
        if (buttonSoundPlayer != null) {
            buttonSoundPlayer.stop(); // Stop the sound to reset it if it was already playing
            buttonSoundPlayer.play(); // Play the sound
        }
    }

    // Method to animate a button on mouse pressed
    @FXML
    private void handleMousePress(MouseEvent event) {
    	
        
        Image newWizardImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Wizard.gif"));
        wizardImage.setImage(newWizardImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticWizardImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/entranceUI/WizardStatic.png"));
            wizardImage.setImage(staticWizardImage);

            Object source = event.getSource();
            if (source instanceof Button) {
                Button clickedButton = (Button) source;
                
                switch (clickedButton.getId()) {
                    case "pauseGameButton":
                        // Code for play button
                    	if (menuScreenFrame instanceof MenuScreen) {
                            ((MenuScreen) menuScreenFrame).close(); // Close the entrance screen
                        }
                    	boardFrame.setVisible(false);
                        PauseScreen pauseScreen = new PauseScreen(boardFrame, menuScreenFrame);
                        pauseScreen.display();
                        break;
                    case "helpButton":
                    	if (menuScreenFrame instanceof MenuScreen) {
                            ((MenuScreen) menuScreenFrame).close(); // Close the entrance screen
                        }
                    	HelpScreen helpScreen = new HelpScreen(entranceScreenFrame);
                        helpScreen.display();
                        break;
                    case "settingsButton":
                        // Code for connect to game button
                    	if (menuScreenFrame instanceof MenuScreen) {
                            ((MenuScreen) menuScreenFrame).close(); // Close the entrance screen
                        }
                    	EntranceScreenController entranceScreenController = EntranceScreenController.getInstance();
                    	SettingsScreen settingsScreen = new SettingsScreen(GameController.getSettingsState(), menuScreenFrame);
                        settingsScreen.display();
                        
                        break;
                        
                    case "quitButton":
                    	Game.destroyInstance();
                        GameObjectFactory.destroyInstance();
                        GameController.destroyInstance();
                        LoginController.destroyInstance();
                        Player.setPlayerList(new ArrayList<Player>());
                        boardFrame.dispose();
                        EntranceScreen entranceScreen = new EntranceScreen();
                        entranceScreen.display();
                        if (menuScreenFrame instanceof MenuScreen) {
                            ((MenuScreen) menuScreenFrame).close(); // Close the entrance screen
                        }
                    default:
                        // Default action or no action
                        break;
                }
            }
        });
        wait.play();
        
        
        
    }
    
    public void setPreviousFrame(Frame frame) {
        this.previousFrame = frame;
    }

    @FXML
    private void handleBackAction() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // Make the previous frame visible
        }
        if (menuScreenFrame != null) {
            menuScreenFrame.dispose(); // Close the menu screen
        }
    }
}
