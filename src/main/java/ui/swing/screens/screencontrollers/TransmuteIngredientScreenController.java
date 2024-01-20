package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ui.swing.screens.scenes.MenuScreen;
import ui.swing.screens.scenes.TransmuteIngredientScreen;

import java.util.List;

import javax.swing.SwingUtilities;

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class TransmuteIngredientScreenController {

    @FXML
    private VBox ingredientContainer; // VBox to contain the ingredient buttons

    @FXML
    private Button transmuteButton;
    
    @FXML
    private ImageView bank;

    @FXML
    private Label selectedLabel;

    @FXML
    private Label messageLabel;

    private GameController gameController;
    private IngredientCard selectedIngredientCard;

	private TransmuteIngredientScreen transmuteIngredientScreen;

    public TransmuteIngredientScreenController() {
        gameController = GameController.getInstance();
    }

    @FXML
    public void initialize() {
        loadIngredients();
    }

    private void loadIngredients() {
        List<IngredientCard> ingredientCards = gameController.getCurrentPlayer().getIngredientInventory();
        ingredientContainer.getChildren().clear(); // Clear any existing children
        
        for (IngredientCard card : ingredientCards) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResourceAsStream(card.getImagePath())));
            imageView.setFitHeight(100); // Or the desired height
            imageView.setFitWidth(100); // Or the desired width

            Button ingredientButton = new Button(card.getName(), imageView);
            ingredientButton.setOnAction(event -> handleIngredientSelection(card));
            ingredientContainer.getChildren().add(ingredientButton);
        }
    }

    @FXML
    private void handleTransmuteAction(ActionEvent event) {
        Image newBankImage = new Image(getClass().getResourceAsStream("/animations/Bank.gif"));
        bank.setImage(newBankImage);
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
        wait.setOnFinished(e -> {
            Image staticBankImage = new Image(getClass().getResourceAsStream("/images/transmuteUI/Bank.png"));
            bank.setImage(staticBankImage);
            
            // Schedule a runnable for execution on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (selectedIngredientCard != null) {
                    gameController.transmuteIngredient(gameController.getCurrentPlayer().getPlayerId(), selectedIngredientCard.getCardId());
                    messageLabel.setText("Transmuted: " + selectedIngredientCard.getName());
                    // Close the screen or update as needed
                } else {
                    messageLabel.setText("Please select an ingredient first.");
                }
            });
        });
        wait.play();
    }


    private void handleIngredientSelection(IngredientCard card) {
        selectedIngredientCard = card;
        selectedLabel.setText("Selected: " + card.getName());
        messageLabel.setText(""); // Clear any message
        // Highlight the selected button or update the UI as needed
    }

	public void setTransmuteIngredientScreen(TransmuteIngredientScreen transmuteIngredientScreen) {
		this.transmuteIngredientScreen = transmuteIngredientScreen;
		
	}

    // Additional methods and logic as necessary
}
