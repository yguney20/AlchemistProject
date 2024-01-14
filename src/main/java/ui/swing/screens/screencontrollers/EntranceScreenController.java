package ui.swing.screens.screencontrollers;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.net.BindException;

import javax.swing.JOptionPane;

import animatefx.animation.Bounce;
import animatefx.animation.FadeInUp;
import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInRight;
import animatefx.animation.ZoomIn;
import domain.Server;
import domain.controllers.GameController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.swing.screens.scenes.ConnectGameScreen;
import ui.swing.screens.scenes.EntranceScreen;
import ui.swing.screens.scenes.HelpScreen;
import ui.swing.screens.scenes.HostGameScreen;
import ui.swing.screens.scenes.LoginOverlay;
import ui.swing.screens.scenes.SettingsScreen;
import ui.swing.screens.screencomponents.SettingsState;

public class EntranceScreenController {

	@FXML
    private Button playButton;
    @FXML
    private Button hostGameButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button connectGameButton;
    @FXML
    private Button helpButton;
    @FXML
    private ImageView wizardImage;
    
    private GameController gameController = GameController.getInstance();
    
    private Frame entranceScreenFrame;
    private static EntranceScreenController instance;
    private MediaPlayer backgroundMusicPlayer;
    private MediaPlayer buttonSoundPlayer;
    
    public EntranceScreenController() {
        initializeMediaPlayers();
        backgroundMusicPlayer.play(); // Start playing the background music
    }

    public static synchronized EntranceScreenController getInstance() {
        if (instance == null) {
            instance = new EntranceScreenController();
        }
        return instance;
    }
    
    private void initializeMediaPlayers() {
        String musicFile = getClass().getResource("/ui/swing/resources/sounds/medivalSoundtrack.wav").toExternalForm();
        Media backgroundMusic = new Media(musicFile);
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        
        String buttonSoundFile = getClass().getResource("/ui/swing/resources/sounds/buttonSound.wav").toExternalForm();
        Media buttonSound = new Media(buttonSoundFile);
        buttonSoundPlayer = new MediaPlayer(buttonSound);
    }
    
    
    public void setEntranceScreenFrame(Frame frame) {
        this.entranceScreenFrame = frame;
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
    	
        
        Image newWizardImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Büyücü.gif"));
        if (newWizardImage.isError()) {
            System.out.println("Error loading image.");
        }
        wizardImage.setImage(newWizardImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticWizardImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/entranceUI/WizardStatic.png"));
            wizardImage.setImage(staticWizardImage);

            Object source = event.getSource();
            if (source instanceof Button) {
                Button clickedButton = (Button) source;
                
                switch (clickedButton.getId()) {
                    case "playButton":
                        // Code for play button
                    	if (entranceScreenFrame instanceof EntranceScreen) {
                            ((EntranceScreen) entranceScreenFrame).close(); // Close the entrance screen
                        }
                    	LoginOverlay loginScreen = new LoginOverlay();
                        gameController.setOnlineMode(false);
                        break;
                    case "hostGameButton":
                        // Code for host game button
                    
                    	try {
                    	    startServer();
                    	    // Additional server setup code...
                    	} catch (Exception e1) {
                    	    if (e1 instanceof BindException) {
                    	        System.err.println("Cannot start the server: Port is already in use.");
                    	    } else {
                    	        System.err.println("An error occurred starting the server: " + e1.getMessage());
                    	    }
                    	}

                        HostGameScreen hostScreen = new HostGameScreen(entranceScreenFrame);
                        gameController.setOnlineMode(true);
                        loginScreenForHost.display();
                        break;
                    case "connectGameButton":
                        // Code for connect to game button
                    	
                    	ConnectGameScreen connectScreen = new ConnectGameScreen(entranceScreenFrame);
                        connectScreen.display();
                        gameController.setOnlineMode(true);
                        
                        break;
                        
                    case "settingsButton":
                    	EntranceScreenController entranceScreenController = EntranceScreenController.getInstance();
                    	SettingsScreen settingsScreen = new SettingsScreen(GameController.getSettingsState(), entranceScreenFrame);
                        settingsScreen.display();
                        break;
                    case "helpButton":
                        // Code for help button
                    	
                    	HelpScreen helpScreen = new HelpScreen(entranceScreenFrame);
                        helpScreen.display();
                        break;
                    default:
                        // Default action or no action
                        break;
                }
            }
        });
        wait.play();
        
        
        
    }
    
    public void stopMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }
    
    public void setMusicVolume(double volume) {
        try {
            if (backgroundMusicPlayer != null) {
                double clampedVolume = Math.min(Math.max(volume, 0.0), 1.0);
                backgroundMusicPlayer.setVolume(clampedVolume);
            }
        } catch (Exception e) {
            e.printStackTrace(); // or log the exception
        }
    }

   
    private void startServer() {
        new Thread(() -> {
            try {
                Server server = new Server(6666); // Replace 6666 with your actual server port
                server.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Server Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to start the server: " + ex.getMessage());
                    alert.showAndWait();
                });
            }
        }).start();
    }
}