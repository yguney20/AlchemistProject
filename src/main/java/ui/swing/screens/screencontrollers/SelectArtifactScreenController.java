package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.scenes.ElixirOfInsightArtifactScreen;
import ui.swing.screens.scenes.SelectArtifactScreen;

public class SelectArtifactScreenController {

	@FXML
	private HBox artifactContainer;
	
    @FXML
    private Label messageLabel;

	private SelectArtifactScreen selectArtifactScreen;
	private GameController gameController;
	private ArtifactCard selectedArtifactCard;
	private String clientName = gameController.getClientPlayer().getNickname();

	public SelectArtifactScreenController() {
		gameController = GameController.getInstance();
	}

	public void setSelectArtifactScreen(SelectArtifactScreen screen) {
		this.selectArtifactScreen = screen;
	}

	@FXML
	public void initialize() {
		loadArtifacts();
	}

	private void loadArtifacts() {
		List<ArtifactCard> artifactCards;
		if (gameController.isOnlineMode()){
			artifactCards = gameController.getPlayerByClientName(clientName).getArtifactCards();
		}else{
			artifactCards = gameController.getPlayerArtifactCards();
		}
		for (ArtifactCard card : artifactCards) {
			Button artifactButton = new Button();
			artifactButton.setOnAction(event -> handleArtifactSelection(card));

			try (InputStream is = getClass().getResourceAsStream(card.getImagePath())) {
				ImageView imageView = new ImageView(new Image(is));
				imageView.setFitHeight(100);
				imageView.setFitWidth(100);
				artifactButton.setGraphic(imageView);
			} catch (IOException e) {
				e.printStackTrace();
			}

			artifactContainer.getChildren().add(artifactButton);
		}
	}

	private void handleArtifactSelection(ArtifactCard card) {
		this.selectedArtifactCard = card;
        messageLabel.setText("Selected: " + card.getName());

		// UI updates or further actions can be implemented here

		// Add cases for other artifacts
	

	}

	private void loadElixirOfInsightUI() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ElixirOfInsightUI.fxml"));
//            Parent elixirUI = loader.load();
//            Scene scene = new Scene(elixirUI);
//            selectArtifactScreen.setNewScene(scene); // You need to implement this method in SelectArtifactScreen
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

		ElixirOfInsightArtifactScreen elixirOfInsightArtifactScreen = new ElixirOfInsightArtifactScreen();
		elixirOfInsightArtifactScreen.display();

		// Handle other artifacts similarly
	}

	@FXML
    private void handleSelectAction() {
        
        if (selectedArtifactCard != null) {
            // Perform actions using the selected artifact card
        	System.out.println("card id: " + selectedArtifactCard.getArtifactId());
        	System.out.println("player id: " + gameController.getCurrentPlayer().getPlayerId());

            gameController.UseArtifactCard(selectedArtifactCard.getArtifactId(), gameController.getCurrentPlayer().getPlayerId());
            closeScreen();
            switch (selectedArtifactCard.getName()) {
            case "Elixir of Insight":
                loadElixirOfInsightUI();
                break;
            }
        } else {
        	messageLabel.setText("Please select an artifact first.");
            return;        }
    }

	@FXML
	private void handleQuitAction() {
		closeScreen();
	}

	private void closeScreen() {
		// Close the Swing frame containing the JFXPanel
		if (selectArtifactScreen != null) {
			selectArtifactScreen.dispose();
		}
	}
}
