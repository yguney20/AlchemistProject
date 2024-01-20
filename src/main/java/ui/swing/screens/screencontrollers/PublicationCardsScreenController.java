package ui.swing.screens.screencontrollers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.swing.screens.PublicationCardsScreen;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.List;

import domain.controllers.GameController;
import domain.gameobjects.PublicationCard;

public class PublicationCardsScreenController {

    @FXML
    private HBox publicationsHBox;


    @FXML
    private ImageView exitButton;
    
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
            VBox publicationVBox = new VBox(10); // Keep using VBox for vertical arrangement inside each publication
            publicationVBox.setStyle(
                    "-fx-background-color: #FFFFFF;" + // White background
                            "-fx-background-radius: 10;" + // Rounded corners
                            "-fx-padding: 10;" + // Padding
                            "-fx-border-color: #FFFFFF;" + // Border color
                            "-fx-border-width: 2;" + // Border width
                            "-fx-border-radius: 10;" + // Border radius
                            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.1), 10, 0, 0, 5);" // Shadow effect
            );

            ImageView ingredientImage = new ImageView(new Image(publicationCard.getAssociatedIngredient().getImagePath()));
            ImageView moleculeImage = new ImageView(new Image(publicationCard.getAssociatedMolecule().getImagePath()));

            // Resize images
            ingredientImage.setFitHeight(150);
            ingredientImage.setFitWidth(100);
            ingredientImage.setPreserveRatio(true);

            moleculeImage.setFitHeight(100);
            moleculeImage.setFitWidth(100);
            moleculeImage.setPreserveRatio(true);

            Label ownerLabel = new Label("Owner: " + publicationCard.getOwner().getNickname());
            ownerLabel.setStyle("-fx-font-weight: bold;");

            Label ingredientLabel = new Label("Ingredient: " + publicationCard.getTheory().getIngredient().getName());
            ingredientLabel.setStyle("-fx-font-weight: bold;");

            publicationVBox.getChildren().addAll(ingredientImage, moleculeImage, ownerLabel, ingredientLabel);
            publicationsHBox.getChildren().add(publicationVBox); // Add to HBox
        }
    }


    @FXML
    private void handleQuitButtonAction() {
    	/*
    	if (publicationCardsScreenFrame != null) {
    		publicationCardsScreenFrame.dispose(); 
        }*/
    	publicationCardsScreen.close();
    }
    // Additional methods and event handlers as needed
}
