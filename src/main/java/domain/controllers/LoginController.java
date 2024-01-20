package domain.controllers;

import java.util.List;

import domain.Game;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.Player;

public class LoginController {

	private static LoginController instance;
	private final GameObjectFactory gameObjectFactory;
	private final Game game;

    //HostController for handling host connection and initializing the game.

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }
	
    public static void destroyInstance() {
        instance = null;
    }
        
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
    
    public List<Player> getPlayerList(){
    	return Player.getPlayerList();
    }
    
}
