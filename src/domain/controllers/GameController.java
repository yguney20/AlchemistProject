package domain.controllers;

import java.util.List;

import domain.Game;
import domain.GameObjects.ArtifactCard;
import domain.GameObjects.IngredientCard;
import domain.GameObjects.Player;
import domain.GameObjects.ArtifactEffects.ElixirOfInsightEffect;

public class GameController {
	
	private final Game game;

	
	private static class GameControllerContainer {
        private static GameController instance;
    }

    public static GameController getInstance() {
        if (GameControllerContainer.instance == null) {
            GameControllerContainer.instance = new GameController();
        }
        return GameControllerContainer.instance;
    }
	
    public static void destroyInstance() {
        GameControllerContainer.instance = null;
    }
    
    public GameController() {
		game = Game.getInstance();
    }
    
    public Player getCurrentPlayer(){
    	return game.getGameState().getCurrentPlayer();
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
