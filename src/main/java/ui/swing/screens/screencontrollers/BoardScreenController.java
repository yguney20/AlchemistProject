package ui.swing.screens.screencontrollers;

import java.awt.Frame;




import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import domain.GameState;
import domain.controllers.GameController;
import domain.gameobjects.Player;
import ui.swing.screens.scenes.MenuScreen;
import ui.swing.screens.scenes.PlayerDashboard;
import ui.swing.screens.scenes.SettingsScreen;
import ui.swing.screens.ArtifactCardsScreen;
import ui.swing.screens.PotionCardsScreen;
import ui.swing.screens.PublicationCardsScreen;
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
    private ImageView magicBall;
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
    @FXML
    private ImageView cauldron;

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
        if (gameController == null) {
            System.err.println("Debug: GameController instance is null in BoardScreenController.initialize");
        }
    }

    public void manualUIUpdate() {
        if (gameController.getGameState() != null) {
            updateLabels();
        } else {
            System.err.println("Debug: GameState is null in BoardScreenController.manualUIUpdate");
        }
    }

    private void updateLabels() {
        GameState gameState = gameController.getGameState();
        if (gameState != null && gameState.getCurrentPlayer() != null) {
            currentPlayerLabel.setText("Player: " + gameState.getCurrentPlayer().getNickname());
            currentTurnLabel.setText("Turn: " + gameState.getCurrentTurn());
            currentRoundLabel.setText("Round: " + gameState.getCurrentRound());
        } else {
            // Handle null cases or show default values
            System.err.println("Debug: GameState or CurrentPlayer is null in updateLabels");
        }
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
    protected void handleMagicBallClick() {
    	
    	
    	if(gameController.getActionPerformed()) {
    		Image newBallImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Magic_Ball.gif"));
        	magicBall.setImage(newBallImage);
        	gameController.updateState();
            currentPlayerLabel.setText("Player: " + gameController.getCurrentPlayer().getNickname());
            currentTurnLabel.setText("Turn: " + gameController.getCurrentTurn());
            currentRoundLabel.setText("Round: " + gameController.getCurrentRound());

        } else {
            JOptionPane.showMessageDialog(null, "No action performed!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        updateLabels(); // Update UI labels to reflect the new state
        
        
        PauseTransition wait = new PauseTransition(Duration.seconds(2)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticBallImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Magic_Ball.png"));
            magicBall.setImage(staticBallImage);
            
    		
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
            ArtifactCardsScreen acs = new ArtifactCardsScreen();
            acs.display();
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
            PublicationCardsScreen pb = new PublicationCardsScreen();
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
    
    @FXML
    protected void handleCauldronClick() {
    	Image newCauldronImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/animations/Cauldron.gif"));
    	cauldron.setImage(newCauldronImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticCauldronImage = new Image(getClass().getResourceAsStream("/ui/swing/resources/images/gameBoardUI/Cauldron.png"));
            cauldron.setImage(staticCauldronImage);	
            PotionCardsScreen pcs = new PotionCardsScreen();
            pcs.display();
        });
        wait.play();
    	
    }

    public void updateGameState(GameState gameState) {
        // Update the UI elements with information from gameState
        SwingUtilities.invokeLater(() -> {
            currentPlayerLabel.setText("Current Player: " + gameState.getCurrentPlayer().getNickname());
            currentTurnLabel.setText("Current Turn: " + gameState.getCurrentTurn());
            currentRoundLabel.setText("Current Round: " + gameState.getCurrentRound());
            // You might also need to update other parts of the UI
            // such as player positions, scores, etc.
        });
    }
    
    
}
