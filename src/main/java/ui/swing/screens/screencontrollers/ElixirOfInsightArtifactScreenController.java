package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ui.swing.screens.scenes.ElixirOfInsightArtifactScreen;

import java.awt.Frame;

import javax.swing.JFrame;

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;

public class ElixirOfInsightArtifactScreenController {

    @FXML
    private Pane mainPane;
    @FXML
    private Button doneButton, rightButton, leftButton, quitButton;
    @FXML
    private Button ingredientButton0, ingredientButton1, ingredientButton2;
    @FXML
    private Label ingredientLabel0, ingredientLabel1, ingredientLabel2, selectedLabel, messageLabel;

    private GameController gameController;
    private IngredientCard selectedIngredientCard, ingredientCard0, ingredientCard1, ingredientCard2;
    private JFrame elixirOfInsightArtifactScreen;

    public ElixirOfInsightArtifactScreenController() {
        gameController = GameController.getInstance();
        // Initialize other necessary components
    }

    @FXML
    private void initialize() {
        loadIngredients();
        setupButtonActions();
    }

    private void loadIngredients() {
        // Load ingredient cards from gameController and set them to buttons
        ingredientCard0 = gameController.getIngredientDeck().get(0);
        ingredientCard1 = gameController.getIngredientDeck().get(1);
        ingredientCard2 = gameController.getIngredientDeck().get(2);
        updateButton(ingredientButton0, ingredientCard0, ingredientLabel0);
        updateButton(ingredientButton1, ingredientCard1, ingredientLabel1);
        updateButton(ingredientButton2, ingredientCard2, ingredientLabel2);
    }

    private void setupButtonActions() {
        ingredientButton0.setOnAction(event -> handleIngredientSelection(ingredientButton0, ingredientCard0));
        ingredientButton1.setOnAction(event -> handleIngredientSelection(ingredientButton1, ingredientCard1));
        ingredientButton2.setOnAction(event -> handleIngredientSelection(ingredientButton2, ingredientCard2));

        rightButton.setOnAction(event -> handleRightButtonAction());
        leftButton.setOnAction(event -> handleLeftButtonAction());
        doneButton.setOnAction(event -> handleDoneButtonAction());
    }

    private void handleIngredientSelection(Button button, IngredientCard ingredientCard) {
        selectedIngredientCard = ingredientCard;
        selectedLabel.setLayoutX(button.getLayoutX());
        selectedLabel.setLayoutY(button.getLayoutY() + button.getHeight());
        selectedLabel.setVisible(true);
    }

    private void handleRightButtonAction() {
        if (selectedIngredientCard == null) {
            showMessage("Select a card before swapping");
            return; 
        }
        gameController.swapRight(selectedIngredientCard);
        updateCardPositions();
        selectedIngredientCard = null;
    }

    private void handleLeftButtonAction() {
        if (selectedIngredientCard == null) {
            showMessage("Select a card before swapping");
            return; 
        }
        gameController.swapLeft(selectedIngredientCard);
        updateCardPositions();
        selectedIngredientCard = null;
    }

    private void handleDoneButtonAction() {
        mainPane.setVisible(false); 
        if (elixirOfInsightArtifactScreen != null) {
        	elixirOfInsightArtifactScreen.dispose(); // Close the JFrame
        }
    }

    private void updateCardPositions() {
        updateCardReferences();

        updateButton(ingredientButton0, ingredientCard0, ingredientLabel0);
        updateButton(ingredientButton1, ingredientCard1, ingredientLabel1);
        updateButton(ingredientButton2, ingredientCard2, ingredientLabel2);

        selectedIngredientCard = null;
        selectedLabel.setVisible(false);
    }

    private void updateCardReferences() {
        ingredientCard0 = gameController.getIngredientDeck().get(0);
        ingredientCard1 = gameController.getIngredientDeck().get(1);
        ingredientCard2 = gameController.getIngredientDeck().get(2);
    }
    private void updateButton(Button button, IngredientCard card, Label label) {
        try {
            Image image = new Image(getClass().getResourceAsStream(card.getImagePath()), 
                                    button.getPrefWidth(), 
                                    button.getPrefHeight(), 
                                    true, 
                                    true);
            ImageView imageView = new ImageView(image);
            button.setGraphic(imageView);
            label.setText(card.getName());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


    private void showMessage(String messageText) {
        messageLabel.setText(messageText);
        messageLabel.setVisible(true);
    }

	public void setArtifactScreenFrame(ElixirOfInsightArtifactScreen elixirOfInsightArtifactScreen) {
		// TODO Auto-generated method stub
		this.elixirOfInsightArtifactScreen = elixirOfInsightArtifactScreen;
	}
}
