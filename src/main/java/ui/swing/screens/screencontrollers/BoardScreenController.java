package ui.swing.screens.screencontrollers;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.controllers.GameController;
import ui.swing.screens.MenuScreen;
import ui.swing.screens.PlayerDashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class BoardScreenController {
    @FXML
    private Label currentPlayerLabel;
    @FXML
    private Label currentTurnLabel;
    @FXML
    private Label currentRoundLabel;

    private GameController gameController = GameController.getInstance();
    
    private Frame boardScreenFrame;

    public void setBoardScreenFrame(Frame frame) {
        this.boardScreenFrame = frame;
    }

    public Frame getBoardScreenFrame() {
		return boardScreenFrame;
	}


	public void initialize() {
        updateLabels();
    }

    private void updateLabels() {
        currentPlayerLabel.setText("Current Player: " + gameController.getCurrentPlayer().getNickname());
        currentTurnLabel.setText("Current Turn: " + gameController.getCurrentTurn());
        currentRoundLabel.setText("Current Round: " + gameController.getCurrentRound());
    }

    @FXML
    protected void handleDashboardAction() {
        
        PlayerDashboard playerDashboard = new PlayerDashboard(gameController);
		playerDashboard.display();		
    }

    @FXML
    protected void handleDeductionBoardAction() {
        // Implementation of deduction board logic
    	// Create an instance of DeductionBoard and display it              
        //deductionBoard.display();
    	gameController.displayDeductionBoardForCurrentPlayer();
    }
    
    @FXML
    protected void handleMenuActionPerformed() {
        SwingUtilities.invokeLater(() -> {
            MenuScreen menuScreen = new MenuScreen(boardScreenFrame);
            menuScreen.display();
        });
    }

    @FXML
    protected void handleActionPerformed() {
        
        if(gameController.getActionPerformed()) {
        	gameController.updateState();
            currentPlayerLabel.setText("Current Player: " + gameController.getCurrentPlayer().getNickname());
            currentTurnLabel.setText("Current Turn: " + gameController.getCurrentTurn());
            currentRoundLabel.setText("Current Round: " + gameController.getCurrentRound());

        }
        updateLabels(); // Update UI labels to reflect the new state
    }
}
