package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.swing.screens.GameOverScreen;
import ui.swing.screens.PublicationCardsScreen;
import ui.swing.screens.scenes.BoardScreen;
import ui.swing.screens.scenes.EntranceScreen;

import javax.swing.SwingUtilities;

import domain.Game;
import domain.controllers.GameController;
import domain.controllers.LoginController;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Molecule;
import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;
import domain.gameobjects.PublicationCard;
import domain.gameobjects.Theory;
import domain.gameobjects.ValidatedAspect;

public class GameOverScreenController {

    private GameController gameController;
    private GameOverScreen gameOverScreen;

    @FXML
    private VBox mainContainer;

    @FXML
    private Label titleLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private GridPane scoresGrid;

    @FXML
    private Button startNewGameButton;

    public GameOverScreenController() {
        gameController = GameController.getInstance();
    }

    @FXML
    private void initialize() {
        titleLabel.setText("Game Over");
        Player winner = gameController.getWinner();
        double maxScore = gameController.endGame();
        winnerLabel.setText("Winner: " + winner.getNickname() + " with a score of " + maxScore);

        setupScoresGrid();
        setupNewGameButton();
    }

    private void setupScoresGrid() {
        // Add headers
        scoresGrid.add(new Label("Player Name"), 0, 0);
        scoresGrid.add(new Label("Score"), 1, 0);

        // Add player scores
        int row = 1;
        for (Player player : gameController.getPlayers()) {
            scoresGrid.add(new Label(player.getNickname()), 0, row);
            scoresGrid.add(new Label(String.valueOf(player.calculateScore())), 1, row);
            row++;
        }
    }

    private void setupNewGameButton() {
        startNewGameButton.setOnAction(event -> {
            // Reset game state
        	Game.destroyInstance();
            GameObjectFactory.destroyInstance();
            GameController.destroyInstance();
            LoginController.destroyInstance();
            Player.resetPlayerList();
            PotionCard.resetPotionMap();
            PublicationCard.resetPublicationList();
            Theory.resetTheoryList();
            IngredientCard.resetIngredientList();
            Molecule.resetMoleculeList();
            ValidatedAspect.resetValidatedList();
            BoardScreen.destroyInstance();
            BoardScreenController.destroyInstance();
            gameOverScreen.close();
            EntranceScreen entranceScreen = new EntranceScreen();
            entranceScreen.display();

        });
    }
    
    
    public void setGameOverScreen(GameOverScreen frame) {
        this.gameOverScreen = frame;
    }
    
    
}
