package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;
import domain.gameobjects.PublicationCard;

public class GameState {
	
	
    private static GameState instance;
  
    private List<Player> players;
    private int currentRound;
    private int currentPlayerID;
    private Player currentPlayer;
    private boolean isPaused;
    private int currentTurn;
    private PotionCard lastCreatedPotion;
    private boolean actionPerformed;
    private ArrayList<PublicationCard> publicationCardList = new ArrayList<PublicationCard>();
    private  Map<Player, List<PotionCard>> potionMap = new HashMap<>();



    public GameState(List<Player> players, int initialRound, int firstTurn, int initialPlayer, boolean isPaused, boolean actionPerformed) {
        this.players = players;
        this.currentRound = initialRound;
        this.currentPlayerID = initialPlayer;
        this.isPaused = isPaused;
        this.currentTurn = firstTurn;
        this.actionPerformed = actionPerformed;
        
    }

    public static synchronized GameState getInstance(List<Player> players, int initialRound, int firstTurn, int initialPlayer, boolean isPaused, boolean actionPerformed) {
        if (instance == null) {
            instance = new GameState(players, initialRound, firstTurn, initialPlayer, isPaused, actionPerformed);
        }
        return instance;
    }

    public static synchronized GameState getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GameState is not initialized. Call getInstance with initial parameters first.");
        }
        return instance;
    }
    
    public static synchronized void destroyInstance() {
        instance = null;
    }
    
    @Override
    public String toString() {
        return "GameState{" +
                "\n\tcurrentPlayerName=" + getCurrentPlayer().getNickname()+
                "\n\tcurrentPlayerIngredi=" + getCurrentPlayer().getIngredientInventory()+
                "\n\tcurrentRound=" + currentRound +
                "\n\tcurrentTurn=" + currentTurn +
                "\n\tcurrentPlayerID=" + currentPlayerID +
                "\n\tisPaused=" + isPaused +
                "\n\tisActionPerformed=" + actionPerformed +
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

    public boolean getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed(boolean actionPerformed) {
        this.actionPerformed = actionPerformed;
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

    public boolean actionPerformed() {
        return actionPerformed;
    }

    public  ArrayList<PublicationCard> getPublicationCardList() {
        for(Player player: players){
            for(PublicationCard publicationCard: player.getPublicationCards()){
                publicationCardList.add(publicationCard);
            }
        }
        return publicationCardList;
    }

    public  Map<Player, List<PotionCard>> getPotionMap() {
        for(Player player: players){
            potionMap.put(player, player.getPotionInventory());
        }
        return potionMap;
    }
}