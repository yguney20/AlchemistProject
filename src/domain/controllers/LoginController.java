package domain.controllers;

import domain.Game;
import domain.GameObjects.GameObjectFactory;

public class LoginController {
	
	private static LoginController instance;
	
	private final GameObjectFactory gameObjectFactory;
	private final Game game;

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }
	
    public static void destroyInstance() {
        instance = null;
    }
    
    //constructor should be private in Singleton
	private LoginController() {
		gameObjectFactory = GameObjectFactory.getInstance();
		game = Game.getInstance();
	}

    public void createPlayer(String nickname, String avatarPath) {
		gameObjectFactory.createPlayer(nickname, avatarPath);
    }
    
    public void initializeGame() {
    	game.initializeGame();
    }
    
}
