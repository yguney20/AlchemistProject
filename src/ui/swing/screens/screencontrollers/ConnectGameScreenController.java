package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ui.swing.screens.scenes.ConnectGameScreen;
import ui.swing.screens.scenes.ElixirOfInsightArtifactScreen;

import javax.swing.JFrame;

import domain.Client;
import domain.controllers.OnlineGameAdapter;

public class ConnectGameScreenController {

    @FXML
    private Button backButton;
    @FXML
    private TextField ipTextField;
    @FXML
    private Button connectButton;
    @FXML
    private Label statusLabel;

    private OnlineGameAdapter onlineGameAdapter;
    private JFrame connectGameScreen;
    private Client client;

    public ConnectGameScreenController() {
        // Constructor logic if needed
    }

    @FXML
    private void initialize() {
        // Initialization logic, if needed
    }

    @FXML
    private void handleBackButtonAction() {
    	if (connectGameScreen != null) {
    		connectGameScreen.dispose(); // Close the JFrame
        }
    }
    
    public void setConnectGameScreenFrame(ConnectGameScreen connectGameScreen) {
		// TODO Auto-generated method stub
		this.connectGameScreen = connectGameScreen;
	}

    @FXML
    private void handleConnectButtonAction() {
        String hostIp = ipTextField.getText();
        initiateConnection(hostIp);
    }

    private void initiateConnection(String hostIp) {
        
    }

}

