package domain;

import java.util.Collections;
import java.util.List;
import java.util.Random;


import domain.gameobjects.*;


public class Game { //Singleton Pattern
	
	private static Game game_instance;

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
    private boolean actionPerformed;
    private boolean gameOver = false;
    
    //constructor should be private in Singleton
    private Game() {
    	
        this.players = Player.getPlayerList();      
        this.ingredientDeck = GameObjectFactory.getInstance().createIngredientDeck();
        this.artifactDeck = GameObjectFactory.getInstance().createArtifactDeck();
        this.totalRounds = 3; // Set the total number of rounds
        this.currentRound = 1;
        this.currentTurn = 1;
        this.gameState = new GameState(players, currentRound, currentTurn, currentPlayer, isPaused);
        this.actionPerformed = false;
        
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
	
	public boolean getActionPerformed() {
		return actionPerformed;
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
            actionPerformed = false;
            System.out.println(gameState);
    }

    public void endRound() {
        for (Player player : players) {
            resetArtifactCards(player);
        }
        // Include any other end-of-round logic here
    }
    
    //end game method
    public void endGame() {
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
        gameOver = true;
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

    public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setActionPerformed(boolean actionPerformed) {
		this.actionPerformed = actionPerformed;
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
    
    public boolean isGameOver() {
    	return gameOver;  // You can adjust the condition based on your game rules
    }

    //----------------------Forage for Ingredient Functions-------------------------------

    public IngredientCard drawIngredientCard(){
    	if (!ingredientDeck.isEmpty()) {
            return ingredientDeck.remove(0); // Remove and return the top card
        } else {
            notifyPlayers("The ingredient deck is empty.");
            return null;
        }
    }
    
    public void forageForIngredient(Player p) {
    	if(!actionPerformed && !isGameOver()) {
        	if (!ingredientDeck.isEmpty()) {
        		IngredientCard selectedCard = drawIngredientCard();
        		p.getIngredientInventory().add(selectedCard);
                actionPerformed = true;
                System.out.println(p.getIngredientInventory());

        	} else {
                notifyPlayers("The ingredient deck is empty.");
        	}
    	} else {
            notifyPlayers("Action already performed or Game Over.");

    	}

    }

    //-----------------------Artifact Related Functions ------------------------------------
    
    // Additional logic will be added (Not finished)
    public void buyArtifactCard(ArtifactCard card, Player player) {
        try {
        	if(!actionPerformed) {
        		if(currentPlayer.getGolds() >= card.getGoldValue()) {
                    if (card.isImmadiate()){
                        useArtifactCard(card, player);
                    } 
                    artifactDeck.remove(card);
                    player.addArtifactCard(card);
                    player.reduceGold(card.getGoldValue());
                    actionPerformed = true;
                    System.out.println(player.getArtifactCards());
                    System.out.println(player);
        		} else {
        			notifyPlayers("You don't have enough golds.");
        		}

        	} else {
                notifyPlayers("Action already performed.");
        	}
        
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

    public void useArtifactCard(ArtifactCard card, Player player){
        card.applyEffect(this, player);
    }

    //Resets the each players artifact

    private void resetArtifactCards(Player player) {
        for (ArtifactCard card : player.getArtifactCards()) {
            card.resetForNewRound();
        }
    }

    //-----------------------Transmute Function ------------------------------------
    
    public void transmuteIngredient(Player player, IngredientCard selectedIngredient) {
    	if(!actionPerformed) {
            // Preconditions
            if (player.getIngredientInventory().isEmpty()) {
            	notifyPlayers("ingredient card not found.");
            }
            else {
            	// Flow
                player.getIngredientInventory().remove(selectedIngredient); 
                ingredientDeck.add(selectedIngredient);
                player.increaseGold(1);
                actionPerformed = true;
            }
    	} else {
            notifyPlayers("Action already performed.");
    	}

    }
    
    //-----------------------Make Experiment Function ------------------------------------

    public void makeExperiment(IngredientCard firstCard,IngredientCard secondCard, boolean student ) {
    	if(!actionPerformed) {
    		PotionCard potionCard = null;
    		if(currentPlayer.getIngredientInventory().size()< 2) {
    			notifyPlayers("There are not enough ingredient cards.");
    		}
    		else {
                potionCard =  GameObjectFactory.getInstance().potionMaker(firstCard, secondCard);
                currentPlayer.getPotionInventory().add(potionCard);
                if (currentPlayer.isMagicMortarActive()){
                    Random random = new Random();
                    boolean keepFirstCard = random.nextBoolean(); // Randomly returns true or false
                    if (keepFirstCard) {
                         currentPlayer.getIngredientInventory().remove(secondCard);
                    } else {
                         currentPlayer.getIngredientInventory().remove(firstCard);
                    }
                } else {
                    currentPlayer.getIngredientInventory().remove(firstCard);
                    currentPlayer.getIngredientInventory().remove(secondCard);
                    System.out.println(potionCard);
                }
    			//for student
    			if(student) {
    				if(potionCard.getPotionType().equals("NEGATIVE")) {
    					currentPlayer.reduceGold(1);
    				}
    			}
    			//for themselves
    			else {
    				if(potionCard.getPotionType().equals("NEGATIVE")) {
    					currentPlayer.increaseSickness(1);
    					if(currentPlayer.getSicknessLevel() == 3) {
    						currentPlayer.setGolds(0);
    					}				
    				}
    			}
    			actionPerformed = true;
    		}
    		//
    	}
    	else {
    		notifyPlayers("Action already performed.");
    	}	
    }

    //----------------------------------------------------------------
    public void swapRight(IngredientCard ingredientCard) {
        // Find the index of the ingredientCard in the first three cards
        int index = -1;
        for (int i = 0; i < 3; i++) {
            if (ingredientDeck.get(i).equals(ingredientCard)) {
                index = i;
            }
        }
        if (index != -1) {
            int swapIndex = (index + 1) % 3; // Calculate the index to swap with (circular)
            IngredientCard temp = ingredientDeck.get(swapIndex);
            ingredientDeck.set(swapIndex, ingredientDeck.get(index));
            ingredientDeck.set(index, temp);
        }
    }

    public void swapLeft(IngredientCard ingredientCard) {
        // Find the index of the ingredientCard in the first three cards
        int index = -1;
        for (int i = 0; i < 3; i++) {
            if (ingredientDeck.get(i).equals(ingredientCard)) {
                index = i;
                break;
            }
        }
    
        if (index != -1) {
            int swapIndex = (index - 1 + 3) % 3; // Calculate the index to swap with (circular)
            IngredientCard temp = ingredientDeck.get(swapIndex);
            ingredientDeck.set(swapIndex, ingredientDeck.get(index));
            ingredientDeck.set(index, temp);
        }
    }
    
    //-----------------------Sell a Potion Function ------------------------------------

    public void sellPotion(IngredientCard i1, IngredientCard i2, String guarantee) {
    	if(!actionPerformed && (currentRound >= 2) && (currentPlayer.getIngredientInventory().size() >= 2) && (currentPlayer.getGolds() >= 1) ) {
    		PotionCard potion = GameObjectFactory.getInstance().potionMaker(i1, i2);
    		currentPlayer.getIngredientInventory().remove(i1);
    		currentPlayer.getIngredientInventory().remove(i2);
    		if(guarantee.equals("Positive") && potion.getPotionType().equals("POSITIVE")){
    			currentPlayer.increaseGold(3);
    		}
    		if(guarantee.equals("Positive") && !potion.getPotionType().equals("POSITIVE")){
    			currentPlayer.reduceGold(1);
    		}
    		if(guarantee.equals("Positive/Neutral") && (potion.getPotionType().equals("POSITIVE") || potion.getPotionType().equals("Neutral"))){
    			currentPlayer.increaseGold(2);
    		}
    		if(guarantee.equals("Positive/Neutral") && potion.getPotionType().equals("NEGATIVE")){
    			currentPlayer.reduceGold(1);
    		}
    		if(guarantee.equals("No Guarantee")){
    			currentPlayer.increaseGold(1);
    		}
    		actionPerformed = true;
    		System.out.println(potion);
    		
    	} else {
    		notifyPlayers("Action already performed or preconditions are not met");
    	}
    }
}



