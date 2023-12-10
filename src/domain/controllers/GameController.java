package domain.controllers;

import java.util.List;

import domain.Game;
import domain.GameObjects.ArtifactCard;
import domain.GameObjects.IngredientCard;
import domain.GameObjects.Player;
import domain.GameObjects.ArtifactEffects.ElixirOfInsightEffect;

public class GameController {
	
	private static GameController instance;

	private final Game game;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
	
    public static void destroyInstance() {
        instance = null;
    }
    
    //constructor should be private in Singleton
    private GameController() {
		game = Game.getInstance();
    }
    
    public Player getCurrentPlayer(){
    	return game.getGameState().getCurrentPlayer();
    }
    
    public int getCurrentTurn() {
    	return game.getGameState().getCurrentTurn();
    }
    
    public int getCurrentRound() {
    	return game.getGameState().getCurrentRound();
    }
    
    public void forageForIngredient(Player p) {
    	game.forageForIngredient(p);
    }

    public List<ArtifactCard> getAvailableArtifacts() {
        return game.getArtifactDeck();
    }

    public List<IngredientCard> getIngredientDeck() {
        return game.getIngredientDeck();
    }
    

    public void buyArtifactCard(ArtifactCard artifact, Player currentPlayer) {
        game.buyArtifactCard(artifact, currentPlayer);
    }
    
    public ArtifactCard getArtifactCardByPath(String path) {
    	return game.getArtifactCardByPath(path);
    }
    
    public void pauseGame() {
        game.pauseGame();
    }

    public void resumeGame() {
        game.resumeGame();
    }
    
    public void transmuteIngredient(Player player, IngredientCard selectedIngredient) {
    	game.transmuteIngredient(player, selectedIngredient);
    }
    
    public void updateState() {
    	game.updateState();
    }

    public boolean getActionPerformed() {
    	return game.getActionPerformed();
    }
    
    public List<ArtifactCard> getPlayerArtifactCards() {
        return game.getGameState().getCurrentPlayer().getArtifactCards();
    }

    public void UseArtifactCard(ArtifactCard artifactCard, Player currentPlayer) {
        game.useArtifactCard(artifactCard, currentPlayer);
    }

    public void swapRight(IngredientCard ingredientCard){
       game.swapRight(ingredientCard);

    }

    public void swapLeft(IngredientCard ingredientCard){
        game.swapLeft(ingredientCard);

    }
}
