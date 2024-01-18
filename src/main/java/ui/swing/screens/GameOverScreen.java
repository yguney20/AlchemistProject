package ui.swing.screens;

import javax.swing.*;

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
import ui.swing.screens.scenes.BoardScreen;
import ui.swing.screens.scenes.EntranceScreen;
import ui.swing.screens.screencontrollers.BoardScreenController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameOverScreen extends JFrame {
	
    private GameController gameController = GameController.getInstance();
	
	private Player winner = gameController.getWinner();
	private double maxScore = gameController.endGame();
	private List<Player> players = gameController.getPlayers();

    public GameOverScreen() {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel winnerLabel = new JLabel("Winner: " + winner.getNickname() + " with a score of " + maxScore);
        winnerLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(players.size() + 1, 2));

        JLabel playerNameLabel = new JLabel("Player Name");
        playerNameLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        scoresPanel.add(playerNameLabel);

        JLabel playerScoreLabel = new JLabel("Score");
        playerScoreLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        scoresPanel.add(playerScoreLabel);

        for (Player player : players) {
            JLabel playerName = new JLabel(player.getNickname());
            playerName.setFont(new Font("Verdana", Font.PLAIN, 14));
            scoresPanel.add(playerName);

            double score = player.calculateScore(); // Use your calculateScore method
            JLabel playerScore = new JLabel(Double.toString(score));
            playerScore.setFont(new Font("Verdana", Font.PLAIN, 14));
            scoresPanel.add(playerScore);
        }
        
        JButton startNewGameButton = new JButton("Start New Game");
        startNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                dispose();
                EntranceScreen entranceScreen = new EntranceScreen();
                entranceScreen.display();
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(scoresPanel, BorderLayout.CENTER);
        bottomPanel.add(startNewGameButton, BorderLayout.SOUTH);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(winnerLabel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
    
	public void display() {
		this.setVisible(true);
	}
}

