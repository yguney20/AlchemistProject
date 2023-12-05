package controllers;

import domain.Game;
import domain.GameObjects.Player;

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
}
