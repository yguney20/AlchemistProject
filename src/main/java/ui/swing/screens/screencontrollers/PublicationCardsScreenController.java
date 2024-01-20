package ui.swing.screens.screencontrollers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ui.swing.screens.PublicationCardsScreen;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import java.util.List;

import javax.swing.JFrame;

import domain.controllers.GameController;
import domain.gameobjects.PublicationCard;

public class PublicationCardsScreenController {

    @FXML
    private VBox publicationsVBox;

    @FXML
    private Button quitButton;
    
    private PublicationCardsScreen publicationCardsScreen;

    private GameController gameController = GameController.getInstance();

    @FXML
    public void initialize() {
        // Initialization logic, load publications, etc.
        loadPublications();
    }
    
    public void setPublicationCardsScreen(PublicationCardsScreen frame) {
        this.publicationCardsScreen = frame;
    }

    private void loadPublications() {
        List<PublicationCard> publicationCards = gameController.getPublicationCardList();

        for (PublicationCard publicationCard : publicationCards) {
            VBox publicationVBox = new VBox(10);
            publicationVBox.setStyle("-fx-background-color: white; -fx-padding: 10;");

            ImageView ingredientImage = new ImageView(new Image(publicationCard.getAssociatedIngredient().getImagePath()));
            ImageView moleculeImage = new ImageView(new Image(publicationCard.getAssociatedMolecule().getImagePath()));

            // Resize images
            ingredientImage.setFitHeight(150); // Set to your desired height
            ingredientImage.setFitWidth(100); // Set to your desired width
            ingredientImage.setPreserveRatio(true);

            moleculeImage.setFitHeight(100); // Set to your desired height
            moleculeImage.setFitWidth(100); // Set to your desired width
            moleculeImage.setPreserveRatio(true);

            Label ownerLabel = new Label("Owner: " + publicationCard.getOwner().getNickname());
            Label ingredientLabel = new Label("Ingredient: " + publicationCard.getTheory().getIngredient().getName());

            publicationVBox.getChildren().addAll(ingredientImage, moleculeImage, ownerLabel, ingredientLabel);
            publicationsVBox.getChildren().add(publicationVBox);
        }
    }


    @FXML
    private void handleQuitButtonAction(ActionEvent event) {
    	/*
    	if (publicationCardsScreenFrame != null) {
    		publicationCardsScreenFrame.dispose(); 
        }*/
    	publicationCardsScreen.close();
    }
    // Additional methods and event handlers as needed
}
