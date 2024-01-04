package ui.swing.screens;

import javafx.fxml.FXML;
import ui.swing.screens.fxmlfiles.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class BoardScreen {

    @FXML
    private Label playerInfoLabel;

    @FXML
    private GridPane gameBoardGrid;

    @FXML
    private void handleStartGame() {
        // Logic to start the game
        playerInfoLabel.setText("Game Started");
        populateGameBoard();
    }

    @FXML
    private void handleEndGame() {
        // Logic to end the game
        playerInfoLabel.setText("Game Ended");
    }

    private void populateGameBoard() {
        // Dynamically add buttons or other components to the game board
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                gameBoardGrid.add(new Button("Cell " + (row * 10 + col)), col, row);
            }
        }
    }
}
