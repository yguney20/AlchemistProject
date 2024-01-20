package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.swing.screens.scenes.HostGameScreen;
import ui.swing.screens.scenes.PlayerIngredientsScreen;
import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

import java.io.InputStream;
import java.util.List;

import javax.swing.JFrame;

public class PlayerIngredientsScreenController {

    @FXML
    private HBox ingredientsContainer;
    @FXML
    private Button quitButton;

    private GameController gameController;
    private JFrame playerIngredientsScreen;
    Player playerToShow;

    public void initialize() {
        gameController = GameController.getInstance();
        updateIngredients();
    }

    private void updateIngredients() {
        if(gameController.isOnlineMode()){
            playerToShow = gameController.getGameState().getPlayerByNickname(gameController.getClientPlayer());
        } else{
            playerToShow = gameController.getCurrentPlayer();
        }

        if (playerToShow != null) {
            ingredientsContainer.getChildren().clear(); // Clear previous items
            System.out.println(playerToShow.getIngredientInventory());
            List<IngredientCard> ingredientCards = playerToShow.getIngredientInventory();
            for (IngredientCard card : ingredientCards) {
                // Create the image view
                ImageView imageView = createImageView(card.getImagePath());
                
                // Create the label
                Label ingredientLabel = new Label(card.getName());
                ingredientLabel.setAlignment(Pos.CENTER); // Center the text

                // Create a VBox and add the image and label to it
                VBox vbox = new VBox(5); // 5 is the spacing between elements
                vbox.getChildren().addAll(imageView, ingredientLabel);
                vbox.setAlignment(Pos.CENTER); // Center align the VBox contents

                ingredientsContainer.getChildren().add(vbox); // Add the VBox to the HBox
            }
        } else {
            System.err.println("Player is null.");
            // Handle the null case appropriately
        }
    }

    private ImageView createImageView(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            Image image = new Image(is);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(165); // example height
            imageView.setFitWidth(139); // example width
            return imageView;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., image not found)
            return null;
        }
    }

    private void setImage(String path, Label label) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            Image image = new Image(is);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(165); // example height
            imageView.setFitWidth(139); // example width
            label.setGraphic(imageView); // Set the image as the graphic of the label
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., image not found)
        }
    }

    
    public void setPlayerIngredientsScreenFrame(PlayerIngredientsScreen playerIngredientsScreen) {
		// TODO Auto-generated method stub
		this.playerIngredientsScreen = playerIngredientsScreen;
	}

    
    @FXML
    private void handleQuitAction() {
        // Close the window. Implementation depends on how you want to manage your windows.
        // For example, if this is part of a larger application, you might need to notify a parent controller.
    	if (playerIngredientsScreen != null) {
    		playerIngredientsScreen.dispose(); // Close the JFrame
        }
    }
}