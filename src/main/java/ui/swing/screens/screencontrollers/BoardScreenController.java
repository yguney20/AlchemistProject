package ui.swing.screens.screencontrollers;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.controllers.GameController;
import ui.swing.screens.MenuScreen;
import ui.swing.screens.PlayerDashboard;
import ui.swing.screens.SettingsScreen;
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
    private static BoardScreenController instance;
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
    
    public static synchronized BoardScreenController getInstance() {
        if (instance == null) {
            instance = new BoardScreenController();
        }
        return instance;
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
        	MenuScreen menuScreen = MenuScreen.getInstance(boardScreenFrame);
        	MenuScreenController controller = MenuScreenController.getInstance(); // Obtain the controller instance
            controller.setPreviousFrame(boardScreenFrame); // Set the previous frame
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
