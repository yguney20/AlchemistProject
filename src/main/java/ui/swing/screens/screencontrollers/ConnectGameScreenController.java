package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ui.swing.screens.scenes.ConnectGameScreen;
import ui.swing.screens.scenes.ElixirOfInsightArtifactScreen;

import javax.swing.JFrame;

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
        onlineGameAdapter = new OnlineGameAdapter(hostIp, 6666); // Using the specified port

        boolean isConnected = onlineGameAdapter.connect();
        if (isConnected) {
            statusLabel.setText("Connected successfully!");
            statusLabel.setTextFill(Color.GREEN);

            // TODO: Navigate to the next screen in your JavaFX application
        } else {
            statusLabel.setText("Failed to connect. Check the IP and try again.");
            statusLabel.setTextFill(Color.RED);
        }
    }

}

