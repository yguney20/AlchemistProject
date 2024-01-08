package ui.swing.screens.screencontrollers;

import java.awt.Frame;
import java.io.IOException;

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
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ui.swing.screens.ConnectGameScreen;
import ui.swing.screens.EntranceScreen;
import ui.swing.screens.HelpScreen;
import ui.swing.screens.HostGameScreen;
import ui.swing.screens.LoginOverlay;
import ui.swing.screens.SettingsScreen;

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
    
    // Initialize method if needed
    public void initialize() {
        // You can apply an initial animation when the view is loaded, if desired
        //new SlideInRight(playButton).play();
    }
    
    public void setEntranceScreenFrame(Frame frame) {
        this.entranceScreenFrame = frame;
    }
    
    @FXML
    private void handleMouseEnter(MouseEvent event) {
        new Pulse((Button) event.getSource()).play();
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
                    	startServer();
                    	if (entranceScreenFrame instanceof EntranceScreen) {
                            ((EntranceScreen) entranceScreenFrame).close(); // Close the entrance screen
                        }
                        HostGameScreen hostScreen = new HostGameScreen(entranceScreenFrame);
                        gameController.setOnlineMode(true);
                        hostScreen.display();
                        break;
                    case "connectGameButton":
                        // Code for connect to game button
                    	if (entranceScreenFrame instanceof EntranceScreen) {
                            ((EntranceScreen) entranceScreenFrame).close(); // Close the entrance screen
                        }
                    	ConnectGameScreen connectScreen = new ConnectGameScreen(entranceScreenFrame);
                        connectScreen.display();
                        gameController.setOnlineMode(true);
                        
                        break;
                        
                    case "settingsButton":
                        // Code for settings button
                    	if (entranceScreenFrame instanceof EntranceScreen) {
                            ((EntranceScreen) entranceScreenFrame).close(); // Close the entrance screen
                        }
                    	SettingsScreen settingsScreen = new SettingsScreen(entranceScreenFrame);
                        settingsScreen.display();
                        break;
                    case "helpButton":
                        // Code for help button
                    	if (entranceScreenFrame instanceof EntranceScreen) {
                            ((EntranceScreen) entranceScreenFrame).close(); // Close the entrance screen
                        }
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