package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.GameObjects.*;



public class Game {
	
    private List<Player> players;
    private List<IngredientCard> ingredientDeck;
	private List<ArtifactCard> artifactDeck;
    private int currentRound;
    private Player currentPlayer;
    
    
    public Game() {
        this.players = Player.getPlayerList();
        
        this.ingredientDeck = GameObjectFactory.createIngredientDeck();
        this.artifactDeck = new ArrayList<>();
        this.currentRound = 1; 

    }
    

    //----------------------Getters and Setters------------------------

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

    public List<IngredientCard> getIngredientDeck() {
		return ingredientDeck;
	}
    
	public void setIngredientDeck(List<IngredientCard> ingredientDeck) {
		this.ingredientDeck = ingredientDeck;
	}

	public List<ArtifactCard> getArtifactDeck() {
		return artifactDeck;
	}

	public void setArtifactDeck(List<ArtifactCard> artifactDeck) {
		this.artifactDeck = artifactDeck;
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

    //-----------------------Game Related Functions--------------------------------------

    public void initializeGame() {
    	
    	Collections.shuffle(players);
    	 
    	for (Player p : players) {
    		p.setGolds(10);
    		IngredientCard i1= drawIngredientCard();
    		IngredientCard i2= drawIngredientCard();
    		p.getIngredientInventory().add(i1);
    		p.getIngredientInventory().add(i2);
    	}
    	
        currentPlayer = players.get(0);
    	
    }

    //Takes a player and calculates the score (Bunu belki değiştirebiliriz 
    // ya tüm playerları hesaplar birinciyi döner gibi gibi... )
    public double calculateFinalScore(Player currentPlayer) {
        int goldsForScore = currentPlayer.getGolds();
        goldsForScore += currentPlayer.getArtifactCards().size() * 2;
        int score = currentPlayer.getReputationPoints() * 10;
        score += goldsForScore/3;
        return score;
    }

    //----------------------Ingredient Card Related Functions-------------------------------

    public IngredientCard drawIngredientCard(){
            return ingredientDeck.remove(0); // Remove and return the top card
    }

    //-----------------------Artifact Related Functions ------------------------------------
    // Additional logic will be added (Not finished)
    public void buyArtifactCard(ArtifactCard card, Player player) {
        try {
            if (card.isImmadiate()){
                card.applyEffect();
            } else {
                player.addArtifactCard(card);
            }
            
        } catch (IllegalStateException e) {
            // Handle the case where a one-time use card is attempted to be used again
            System.out.println(e.getMessage());
        }
    }


}



