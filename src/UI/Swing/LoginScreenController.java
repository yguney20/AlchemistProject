package UI.Swing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import domain.GameObjects.Player;

public class LoginScreenController {
    @FXML private Button avatar1, avatar2, avatar3, avatar4, avatar5, avatar6;
    @FXML private TextField player1Name, player2Name;
    @FXML private Button confirmButton, startGameButton;
    @FXML private Label player1Label, player2Label, message1, message2, selected;

    private String player1Avatar, player2Avatar;
    private Player player1, player2;

    @FXML
    private void initialize() {
        // Initialize UI components, if needed
    }

    @FXML
    private void handleAvatarSelection(ActionEvent event) {
        Button selectedAvatar = (Button) event.getSource();
        String avatarPath = ""; // Get avatar path based on the selected button

        if (player1Label.isVisible()) {
            player1Avatar = avatarPath;
            // Set avatar image, etc.
        } else {
            player2Avatar = avatarPath;
            // Set avatar image, etc.
        }

        // Update UI to show the selected avatar
        selected.setText("SELECTED: " + selectedAvatar.getText());
    }

    @FXML
    private void handleConfirm() {
        if (!player1Name.getText().isEmpty() && player1Avatar != null) {
            player1 = new Player(player1Name.getText(), player1Avatar);
            // Hide player 1 UI components and show player 2's
            player1Label.setVisible(false);
            player1Name.setVisible(false);
            player2Label.setVisible(true);
            player2Name.setVisible(true);
            message1.setVisible(true);
            startGameButton.setVisible(true);
            confirmButton.setVisible(false);
        } else {
            message2.setText("Please enter a nickname and choose an avatar for Player 1");
            message2.setVisible(true);
        }
    }

    @FXML
    private void handleStartGame() {
        if (!player2Name.getText().isEmpty() && player2Avatar != null) {
            player2 = new Player(player2Name.getText(), player2Avatar);
            // Load the board screen
            loadBoardScreen();
        } else {
            message1.setText("Please enter a nickname and choose an avatar for Player 2");
            message1.setVisible(true);
        }
    }

    private void loadBoardScreen() {
        // Logic to load and display the board screen
        // For example, load a new FXML file or switch scenes

        // Close the current window
        Stage currentStage = (Stage) startGameButton.getScene().getWindow();
        currentStage.close();
    }
}
