package domain;

import java.util.Collections;
import java.util.List;

import domain.GameObjects.*;


public class Game { //Singleton Pattern
	
	private static Game game_instance = null;

    private List<Player> players;
    private List<IngredientCard> ingredientDeck;
	private List<ArtifactCard> artifactDeck;
	private int totalRounds;
    private int currentRound;
    private int currentTurn;
    private Player currentPlayer;
    private boolean isPaused;
    private GameState gameState;
    Player winner = null;
    
    
    public Game() {
    	
        this.players = Player.getPlayerList();      
        this.ingredientDeck = GameObjectFactory.getInstance().createIngredientDeck();
        this.artifactDeck = GameObjectFactory.getInstance().createArtifactDeck();
        this.totalRounds = 3; // Set the total number of rounds
        this.currentRound = 1;
        this.currentTurn = 1;
        this.gameState = new GameState(players, currentRound, currentTurn, currentPlayer, isPaused);
        
    }
    //---------------------Singleton Methods----------------------------------------
    
    public static Game getInstance() {
        if (game_instance== null) {
            game_instance= new Game();
        }
        return game_instance;
    }

    public static void destroyInstance() {
        game_instance = null;
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
	
    public GameState getGameState() {
		return gameState;
	}

	public Player getWinner() {
		return winner;
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
        
        gameState.setCurrentPlayer(currentPlayer);
        System.out.println(gameState);
      
    }
    
    public void updateState() {
    	
    		int currentPlayerIndex = players.indexOf(currentPlayer); // Get the index of the current player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            currentPlayer = players.get(currentPlayerIndex);

            // Check if all players have completed their turns
            if (currentPlayerIndex == 0) {
                currentTurn++;

                // Check if all turns for the current round have been completed
                if (currentTurn > 3) {
                    currentTurn = 1;
                    currentRound++;

                    // Check if all rounds have been completed
                    if (currentRound > totalRounds) {
                        endGame();
                        return;
                    }
                }
            }

            gameState.setCurrentPlayer(currentPlayer);
            gameState.setCurrentRound(currentRound);
            gameState.setCurrentTurn(currentTurn);
            System.out.println(gameState);
    }
    
    //end game method
    private void endGame() {
    	double maxScore = Double.MIN_VALUE;

        for (Player player : players) {
            double score = calculateFinalScore(player);
            System.out.println("Player: " + player.getNickname() + " - Final Score: " + score);

            if (score > maxScore) {
                maxScore = score;
                winner = player;
            }
        }

        System.out.println("Game Over! Winner: " + winner.getNickname() + " with a score of " + maxScore);
    }

	//Takes a player and calculates the score (Bunu belki degistirebiliriz 
    // ya t�m playerlari hesaplar birinciyi doner gibi gibi... )
    public double calculateFinalScore(Player currentPlayer) {
        int goldsForScore = currentPlayer.getGolds();
        goldsForScore += currentPlayer.getArtifactCards().size() * 2;
        int score = currentPlayer.getReputationPoints() * 10;
        score += goldsForScore/3;
        return score;
    }
  
    public void pauseGame() {
        if (!gameState.isPaused()) {
            gameState.setPaused(true);
            // Notify all players
            notifyPlayers("The game has been paused.");
        }
    }

    public void resumeGame() {
        if (gameState.isPaused()) {
            gameState.setPaused(false);
            // Notify all players
            notifyPlayers("The game has resumed.");
        }
    }
    
    // Until implementing UI Component this will be used for testing
    private void notifyPlayers(String message) {
        // Implement notification logic here
        System.out.println(message);
    }
    
    private boolean isGameOver() {
        return currentRound > totalRounds;  // You can adjust the condition based on your game rules
    }

    //----------------------Ingredient Card Related Functions-------------------------------

    public IngredientCard drawIngredientCard(){
    	if (!ingredientDeck.isEmpty()) {
            return ingredientDeck.remove(0); // Remove and return the top card
        } else {
            notifyPlayers("The ingredient deck is empty.");
            return null;
        }
    }
    
    public void forageForIngredient(Player p) {
    	if (!ingredientDeck.isEmpty()) {
    		IngredientCard selectedCard = drawIngredientCard();
    		p.getIngredientInventory().add(selectedCard);
            System.out.println(p.getIngredientInventory());

    	} else {
            notifyPlayers("The ingredient deck is empty.");
    	}
    }

    //-----------------------Artifact Related Functions ------------------------------------
    // Additional logic will be added (Not finished)
    public void buyArtifactCard(ArtifactCard card, Player player) {
        try {
            if (card.isImmadiate()){
                card.applyEffect(this);
            } else {
                player.addArtifactCard(card);
            }
            artifactDeck.remove(card);
            player.reduceGold(card.getGoldValue());
            System.out.println(player.getArtifactCards());
            System.out.println(player);
            
        } catch (IllegalStateException e) {
            // Handle the case where a one-time use card is attempted to be used again
            System.out.println(e.getMessage());
        }
    }
     public ArtifactCard getArtifactCardByPath(String path) {
        return artifactDeck.stream()
                .filter(card -> card.getImagePath().equals(path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Artifact Card not found for path: " + path));
    }


    //-----------------------Transmute Function ------------------------------------
    
    public void transmuteIngredient(Player player, IngredientCard selectedIngredient) {
        // Preconditions
        if (player.getIngredientInventory().isEmpty()) {
        	notifyPlayers("ingredient card not found.");
        }
        else {
        	// Flow
            player.getIngredientInventory().remove(selectedIngredient); 
            ingredientDeck.add(selectedIngredient);
            player.increaseGold(1);      	
        }
    }




}



