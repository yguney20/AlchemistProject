package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.JFrame;

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;

public class UseArtifactScreenController {

    @FXML
    private HBox ingredientContainer; // Container for ingredient images
    @FXML
    private Label messageLabel; // Label for displaying messages
    @FXML
    private Button doneButton; // Button for completing the action

    private GameController gameController; // Instance of GameController
    private IngredientCard selectedIngredientCard; // Currently selected ingredient card
    
    private JFrame useArtifactScreenFrame;

    public UseArtifactScreenController() {
        this.gameController = GameController.getInstance(); // Initialize GameController
    }

    @FXML
    public void initialize() {
        loadIngredients(); // Load ingredient cards when the screen initializes
    }

    private void loadIngredients() {
        for (IngredientCard card : gameController.getIngredientDeck()) {
            ImageView ingredientImageView = new ImageView(new Image(getClass().getResourceAsStream(card.getImagePath())));
            ingredientImageView.setFitHeight(100); // Adjust height as needed
            ingredientImageView.setFitWidth(100); // Adjust width as needed
            ingredientImageView.setPreserveRatio(true);

            // Wrap ImageView in a Button or add a mouse click listener
            Button ingredientButton = new Button("", ingredientImageView);
            ingredientButton.setOnAction(event -> handleIngredientSelection(card));

            ingredientContainer.getChildren().add(ingredientButton);
        }
    }

    private void handleIngredientSelection(IngredientCard card) {
        this.selectedIngredientCard = card; // Set the selected card
        messageLabel.setText("Selected: " + card.getName()); // Update message label
    }

    @FXML
    private void handleDoneAction() {
        // Logic for when the "Done" button is clicked
        if (selectedIngredientCard != null) {
            // Perform action with the selected ingredient card
            // For example: gameController.useIngredientCard(selectedIngredientCard);
            messageLabel.setText(selectedIngredientCard.getName() + " used.");
        } else {
            messageLabel.setText("Please select an ingredient first.");
        }
    }
    
    @FXML
    private void handleBackAction() {
    	if (useArtifactScreenFrame != null) {
            useArtifactScreenFrame.dispose(); // Close the JFrame
        }
    }
    
    public void setArtifactScreenFrame(JFrame frame) {
        this.useArtifactScreenFrame = frame;
    }

    // Additional methods as needed...
}
