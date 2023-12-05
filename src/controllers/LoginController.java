package controllers;

import domain.Game;
import domain.GameObjects.GameObjectFactory;

public class LoginController {
	
	private final GameObjectFactory gameObjectFactory;
	private final Game game;
	
    private static class LoginControllerContainer {
        private static LoginController instance;
    }

    public static LoginController getInstance() {
        if (LoginControllerContainer.instance == null) {
            LoginControllerContainer.instance = new LoginController();
        }
        return LoginControllerContainer.instance;
    }
	
    public static void destroyInstance() {
        LoginControllerContainer.instance = null;
    }
    
	public LoginController() {
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
