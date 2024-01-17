package ui.swing.screens.screencontrollers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ui.swing.screens.scenes.ConnectGameScreen;
import ui.swing.screens.scenes.HostGameScreen;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class HostGameScreenController {

    @FXML private AnchorPane rootPane;
    @FXML private Button backButton;
    @FXML private Button startGameButton;
    @FXML private Label ipLabel;
    private JFrame hostGameScreen;

    public void initialize() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            ipLabel.setText("Your IP: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        
    }
    
    public void setHostGameScreenFrame(HostGameScreen hostGameScreen) {
		// TODO Auto-generated method stub
		this.hostGameScreen = hostGameScreen;
	}

    @FXML
    private void handleBackAction() {
    	if (hostGameScreen != null) {
    		hostGameScreen.dispose(); // Close the JFrame
        }
    }

    @FXML
    private void handleStartGameAction() {
        // Logic to start the game
    	handleBackAction();
    }
}
