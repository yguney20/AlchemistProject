package domain;

import java.util.List;

import domain.gameobjects.Player;

public class GameState {
	
    private List<Player> players;
    private int currentRound;
    private Player currentPlayer;
    private boolean isPaused;
    private int currentTurn;

    public GameState(List<Player> players, int initialRound, int firstTurn, Player initialPlayer, boolean isPaused) {
        this.players = players;
        this.currentRound = initialRound;
        this.currentPlayer = initialPlayer;
        this.isPaused = isPaused;
        this.currentTurn = firstTurn;
    }
    
    @Override
    public String toString() {
        return "GameState{" +
                "\n\tcurrentRound=" + currentRound +
                "\n\tcurrentTurn=" + currentTurn +
                "\n\tcurrentPlayer=" + currentPlayer +
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

    public Player getCurrentPlayer() {
        return currentPlayer;
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
}
