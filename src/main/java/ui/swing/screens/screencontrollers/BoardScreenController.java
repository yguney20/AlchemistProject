package ui.swing.screens.screencontrollers;

import java.awt.Frame;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.controllers.GameController;
import ui.swing.screens.PlayerDashboard;
import ui.swing.screens.scenes.MenuScreen;
import ui.swing.screens.scenes.SettingsScreen;
import ui.swing.screens.PublishTheoryScreen;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.control.Button;

public class BoardScreenController {
    @FXML
    private Label currentPlayerLabel;
    @FXML
    private Label currentTurnLabel;
    @FXML
    private Label currentRoundLabel;
    
    @FXML
    private ImageView medievalBanner;
    
    @FXML
    private ImageView wizardHatStand;
    
    @FXML
    private ImageView hourglass;
    
    @FXML
    private ImageView wizardBook;
    
    @FXML
    private ImageView publicationboard;
    
    @FXML
    private ImageView deductionBoard;

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
        currentPlayerLabel.setText("Player: " + gameController.getCurrentPlayer().getNickname());
        currentTurnLabel.setText("Turn: " + gameController.getCurrentTurn());
        currentRoundLabel.setText("Round: " + gameController.getCurrentRound());
    }
    
    public static synchronized BoardScreenController getInstance() {
        if (instance == null) {
            instance = new BoardScreenController();
        }
        return instance;
    }
    

    @FXML
    protected void handleActionPerformed() {
        
        if(gameController.getActionPerformed()) {
        	gameController.updateState();
            currentPlayerLabel.setText("Player: " + gameController.getCurrentPlayer().getNickname());
            currentTurnLabel.setText("Turn: " + gameController.getCurrentTurn());
            currentRoundLabel.setText("Round: " + gameController.getCurrentRound());

        }
        updateLabels(); // Update UI labels to reflect the new state
    }
    
    @FXML
    protected void handleMedievalBannerEnter() {
    	Image newBannerImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Medieval_Banner.gif"));
        medievalBanner.setImage(newBannerImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticBannerImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Medieval_Banner.png"));
            medievalBanner.setImage(staticBannerImage);
        });
        wait.play();
    	
    }
    
    @FXML
    protected void handleWizardHatStandClick() {
    	Image newHatImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Wizard_Hat_Stand.gif"));
    	wizardHatStand.setImage(newHatImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticHatImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Wizard_Hat_Stand.png"));
            wizardHatStand.setImage(staticHatImage);
            
            PlayerDashboard playerDashboard = new PlayerDashboard(gameController);
    		playerDashboard.display();
    		
        });
        wait.play();
    	
    }
    
    @FXML
    protected void handleHourglassClick() {
    	Image newHourglassImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Hourglass.gif"));
        hourglass.setImage(newHourglassImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticHourglassImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Hourglass.png"));
            hourglass.setImage(staticHourglassImage);
            SwingUtilities.invokeLater(() -> {
            	MenuScreen menuScreen = MenuScreen.getInstance(boardScreenFrame);
            	MenuScreenController controller = MenuScreenController.getInstance(); // Obtain the controller instance
                controller.setPreviousFrame(boardScreenFrame); // Set the previous frame
                menuScreen.display();
            });
        });
        wait.play();
    	
    	
    }
    
    @FXML
    protected void handleWizardBookClick() {
    	Image newBookImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Wizard_Book.gif"));
        wizardBook.setImage(newBookImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticBookImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Wizard_Book.png"));
            wizardBook.setImage(staticBookImage);
            
        });
        wait.play();
    	
    }
    
    @FXML
    protected void handlePublicationBoardClick() {
    	Image newPublicationImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Bulletin_Board.gif"));
    	publicationboard.setImage(newPublicationImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticPublicationImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Bulletin_Board.png"));
            publicationboard.setImage(staticPublicationImage);
            PublishTheoryScreen pb = new PublishTheoryScreen();
    		pb.display();
        });
        wait.play();
    	
    }
    
    @FXML
    protected void handleDeductionClick() {
    	Image newDeductImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Deduction_Board.gif"));
    	deductionBoard.setImage(newDeductImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticDeductImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Deduction_Board.png"));
            deductionBoard.setImage(staticDeductImage);
            gameController.displayDeductionBoardForCurrentPlayer();	
        });
        wait.play();
    	
    }
    
    
}
