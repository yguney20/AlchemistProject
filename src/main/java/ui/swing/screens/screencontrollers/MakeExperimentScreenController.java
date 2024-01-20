package ui.swing.screens.screencontrollers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import ui.swing.screens.PublicationCardsScreen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.PotionCard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MakeExperimentScreenController {

    @FXML
    private AnchorPane ingredientContainer;
    @FXML
    private Button studentButton, yourselfButton, makeExperimentButton;
    @FXML
    private Label ingredient1Label, ingredient2Label, messageLabel;
    @FXML
    private ImageView potionImageView;

    private GameController gameController;
    private IngredientCard ingredientCard1, ingredientCard2;
    private Boolean tester;
    private Map<IngredientCard, Button> ingredientButtonMap = new HashMap<>();

    public MakeExperimentScreenController() {
        this.gameController = GameController.getInstance();
    }

    @FXML
    public void initialize() {
        try {
            addIngredients();
            addEventHandlers();
        } catch (IOException e) {
            e.printStackTrace(); // Handle this exception as per your application's policy
        }
    }
    private void addIngredients() throws IOException {
        int x = 0; // Horizontal position
        int y = 0; // Vertical position
        int buttonWidth = 140; // Width of the buttons
        int buttonHeight = 170; // Height of the buttons
        int gap = 10; // Gap between buttons

        for (IngredientCard card : gameController.getCurrentPlayer().getIngredientInventory()) {
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(card.getImagePath())));
            imageView.setFitHeight(150); // Slightly smaller than button for padding
            imageView.setFitWidth(120);

            Button ingredientButton = new Button(card.getName(), imageView);
            ingredientButton.setLayoutX(x);
            ingredientButton.setLayoutY(y);
            ingredientButton.setMinSize(buttonWidth, buttonHeight);
            ingredientButton.setMaxSize(buttonWidth, buttonHeight);
            ingredientButton.setStyle("-fx-content-display: top;");

            ingredientContainer.getChildren().add(ingredientButton);

            ingredientButtonMap.put(card, ingredientButton);
            ingredientButton.setOnAction(event -> handleIngredientSelection(card));

            // Update x and y for next button
            x += buttonWidth + gap;
            if (x + buttonWidth > ingredientContainer.getPrefWidth()) {
                x = 0;
                y += buttonHeight + gap;
            }
        }
    }


    private void addEventHandlers() {
        studentButton.setOnAction(event -> handleTesterSelection(true));
        yourselfButton.setOnAction(event -> handleTesterSelection(false));
        makeExperimentButton.setOnAction(event -> handleMakeExperiment());
    }

    private void handleIngredientSelection(IngredientCard card) {
        if (ingredientCard1 == null) {
            ingredientCard1 = card;
            ingredient1Label.setText("Ingredient 1: " + card.getName());
        } else if (ingredientCard1.equals(card)) {
            ingredientCard1 = null;
            ingredient1Label.setText("");
        } else if (ingredientCard2 == null) {
            ingredientCard2 = card;
            ingredient2Label.setText("Ingredient 2: " + card.getName());
        } else if (ingredientCard2.equals(card)) {
            ingredientCard2 = null;
            ingredient2Label.setText("");
        } else {
            // Handle the case where more than two ingredients are selected, if needed
        }
    }

    private void handleTesterSelection(boolean isStudent) {
        tester = isStudent;
        updateTesterButtonStyles();
    }

    private void updateTesterButtonStyles() {
        if (studentButton != null && yourselfButton != null) {
            studentButton.setStyle(tester != null && tester ? "-fx-border-color: green; -fx-border-width: 2;" : "");
            yourselfButton.setStyle(tester != null && !tester ? "-fx-border-color: green; -fx-border-width: 2;" : "");
        }
    }


    private void handleMakeExperiment() {
        if (ingredientCard1 == null || ingredientCard2 == null || tester == null) {
            messageLabel.setText("Please select two ingredients and a tester.");
            return;
        }

        int currentPlayerId = gameController.getCurrentPlayer().getPlayerId();
        Consumer<PotionCard> callback = this::processExperimentResult;

        gameController.makeExperiment(currentPlayerId, ingredientCard1.getCardId(), ingredientCard2.getCardId(), tester, callback);

        resetExperiment();
    }

    private void processExperimentResult(PotionCard potionCard) {
        if (potionCard != null) {
        	String imagePathWithExtensionGif = potionCard.getImagePath() + ".gif";
        	String imagePathWithExtensionPng = potionCard.getImagePath() + ".png";
        	Image potionImage = new Image(getClass().getResourceAsStream(imagePathWithExtensionGif));
        	potionImageView.setImage(potionImage);
            
            PauseTransition wait = new PauseTransition(Duration.seconds(0.85)); // Adjust the duration to match your GIF
            wait.setOnFinished(e -> {
                Image staticPotionImage = new Image(getClass().getResourceAsStream(imagePathWithExtensionPng));
                potionImageView.setImage(staticPotionImage);
                
            });
            wait.play();
        	
           

            // Optionally, you can also update the UI to show a success message
            messageLabel.setText("Potion created: " + potionCard.getPotionName());
        } else {
            // Handle the case where no potion is created or the experiment fails
            potionImageView.setImage(null); // Clear any existing image
            messageLabel.setText("Experiment failed. No potion was created.");
        }
    }


    private void resetExperiment() {
        ingredientCard1 = null;
        ingredientCard2 = null;
        tester = null;
        ingredient1Label.setText("");
        ingredient2Label.setText("");
        updateTesterButtonStyles();
    }
}
