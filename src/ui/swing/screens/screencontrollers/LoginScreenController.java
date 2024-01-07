package ui.swing.screens.screencontrollers;

import domain.controllers.LoginController;

import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import ui.swing.screens.BoardScreen;
import ui.swing.screens.screencomponents.AvatarCardScreen;

public class LoginScreenController {
	
	private LoginController loginController = LoginController.getInstance();
	private AvatarCardScreen selectedCard = null;
	private Map<ImageIcon, String> iconPathMap = new HashMap<>(); 
	private int counter = 0;
	private Frame loginScreenFrame;
    @FXML
    private Pane avatarSelectionPane;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Button startButton;
    @FXML
    private Label playerCreatedLabel;

    @FXML
    public void initialize() {
        // Initialize your components here (e.g., populate avatar selections)
    }
    
    public void setLoginScreenFrame(Frame frame) {
        this.loginScreenFrame = frame;
    }

    @FXML
    private void handleStartButtonClick() {
        if (nicknameTextField.getText().isEmpty() || selectedCard == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a nickname and select an avatar.");
            alert.showAndWait();
            return;
        }

        String playerName = nicknameTextField.getText();
        // Assuming selectedCard is a JavaFX control that holds the selected avatar
        // and iconPathMap is a Map containing paths for the avatars.
        ImageIcon selectedIcon = (ImageIcon) selectedCard.getData().getIcon(); 
        String playerAvatarPath = iconPathMap.get(selectedIcon); // Update this line according to your data model

        // Assuming loginController is an instance of a class handling the login logic
        loginController.createPlayer(playerName, playerAvatarPath);
        nicknameTextField.setText("");
        playerCreatedLabel.setText("Player " + playerName + " created.");
        playerCreatedLabel.setVisible(true);

        counter++;

        if (counter > 1) {
            loginController.initializeGame();
            closeLoginScreen(); // Close the JavaFX screen, need to implement this method
            displayBoardScreen(); // Transition to the BoardScreen, need to implement this method
        }
    }

    // Additional helper methods (implement these based on your application's logic)
    private void closeLoginScreen() {
        // Logic to close the login screen
    	
    }

    private void displayBoardScreen() {
	    SwingUtilities.invokeLater(() -> {
	        BoardScreen boardScreen = new BoardScreen();
	        boardScreen.display(); // Assuming BoardScreen has a display method to make it visible
	    });
	}

    // Add additional methods for handling UI events and logic as needed
}