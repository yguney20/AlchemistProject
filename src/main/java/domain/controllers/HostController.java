package domain.controllers;

import domain.GameState;
import domain.Server;
import domain.gameobjects.Player;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

public class HostController {
    private Server server;
    private GameController gameController;

    public HostController(GameController gameController) {
        this.gameController = gameController;
         try {
            this.server = Server.getInstance(6666); // Get the singleton instance
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public boolean areAllPlayersReady() {
        return server.areAllPlayersReady();
       
    }

    public void startGame() {
        gameController.initializeGame();
        server.broadcastGameState(gameController.getGameState());
    }

    public List<String> getConnectedPlayers() {
        return server.getConnectedPlayerNames();
    }

     public Player getCurrentPlayer(){
        return gameController.getCurrentPlayer();
    }
    
    public int getCurrentTurn() {
        return gameController.getCurrentTurn();
    }
    
    public int getCurrentRound() {
        return gameController.getCurrentRound();
    }

    public GameState getGameState() {
        return gameController.getGameState();
    }


}
