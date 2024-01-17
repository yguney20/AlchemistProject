package ui.swing.screens.screencontrollers;
import java.awt.Frame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

import javax.swing.JFrame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;

public class BuyArtifactScreenController {

    @FXML
    private FlowPane artifactButtonContainer;
    @FXML
    private Label messageLabel;
    @FXML
    private Button buyButton;
    @FXML
    private Button quitButton;

    private GameController gameController;
    private ArtifactCard selectedArtifact;
    private static BuyArtifactScreenController instance;
    private JFrame buyArtifactScreenFrame;

    public BuyArtifactScreenController() {
        this.gameController = GameController.getInstance();
    }
    
    public static synchronized BuyArtifactScreenController getInstance() {
        if (instance == null) {
            instance = new BuyArtifactScreenController();
        }
        return instance;
    }

    @FXML
    public void initialize() {
        loadArtifacts();
    }
    
    public void setArtifactScreenFrame(JFrame frame) {
        this.buyArtifactScreenFrame = frame;
    }
    
    
    @FXML
    private void handleBackAction() {
    	if (buyArtifactScreenFrame != null) {
            buyArtifactScreenFrame.dispose(); // Close the JFrame
        }
    }
    private void loadArtifacts() {
    	
    	try {
        for (ArtifactCard card : gameController.getAvailableArtifacts()) {
            ImageView artifactImageView = new ImageView();
            Image artifactImage = new Image(getClass().getResourceAsStream(card.getImagePath()));
            artifactImageView.setImage(artifactImage);
            artifactImageView.setFitHeight(100); // Set the height
            artifactImageView.setFitWidth(100); // Set the width
            artifactImageView.setPreserveRatio(true);
            
            

            Button artifactButton = new Button();
            artifactButton.setGraphic(artifactImageView);
            artifactButton.setOnAction(event -> handleArtifactSelection(card));

            artifactButtonContainer.getChildren().add(artifactButton);
        }
        
    } catch (Exception e) {
        e.printStackTrace();}
    }

    private void handleArtifactSelection(ArtifactCard artifact) {
        this.selectedArtifact = artifact;
        messageLabel.setText("Selected: " + artifact.getName());
    }

    @FXML
    private void handleBuyAction() {
        if (selectedArtifact == null) {
            messageLabel.setText("Please select an artifact first.");
            return;
        }

        int currentPlayerId = gameController.getCurrentPlayer().getPlayerId();
        gameController.buyArtifactCard(currentPlayerId, selectedArtifact.getArtifactId());
        messageLabel.setText("Purchased: " + selectedArtifact.getName());
    }
    
    
    
}