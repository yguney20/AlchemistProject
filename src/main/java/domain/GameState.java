package domain;

import java.util.List;

import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;

public class GameState {
	
    private List<Player> players;
    private int currentRound;
    private int currentPlayerID;
    private Player currentPlayer;
    private boolean isPaused;
    private int currentTurn;
    private PotionCard lastCreatedPotion;

    public GameState(List<Player> players, int initialRound, int firstTurn, int initialPlayer, boolean isPaused) {
        this.players = players;
        this.currentRound = initialRound;
        this.currentPlayerID = initialPlayer;
        this.isPaused = isPaused;
        this.currentTurn = firstTurn;
    }
    
    @Override
    public String toString() {
        return "GameState{" +
                "\n\tcurrentRound=" + currentRound +
                "\n\tcurrentTurn=" + currentTurn +
                "\n\tcurrentPlayerID=" + currentPlayerID +
                "\n\tisPaused=" + isPaused +
                "\n}";
    }

    // Getters and setters
    

    public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentPlayerID() {
        System.out.println("Getting current playerID: " + currentPlayerID);
        return currentPlayerID;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public PotionCard getLastCreatedPotion() {
        return lastCreatedPotion;
    }

    // Method to set the last created potion (used when updating the state)
    public void setLastCreatedPotion(PotionCard potionCard) {
        this.lastCreatedPotion = potionCard;
    }

    public boolean isInitialized() {
        return players != null && !players.isEmpty() && currentPlayerID != -1;
    }

    public Player getCurrentPlayer(){
        for (Player player : players) {
            if (player.getPlayerId() == getCurrentPlayerID()) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayerById(String playerID){
        for (Player player : players) {
            if (player.getPlayerId() == Integer.parseInt(playerID)) {
                return player;
            }
        }
        return null;
    }
}