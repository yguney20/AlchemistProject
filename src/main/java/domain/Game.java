package domain;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import domain.gameobjects.*;
import domain.gameobjects.Molecule.Component;
import domain.gameobjects.artifacteffects.ArtifactEffect;
import domain.gameobjects.artifacteffects.MagicMortarEffect;


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

    public static void resetInstance(){
        
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setActionPerformed(boolean actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    //-----------------------Game Related Functions--------------------------------------

    public void initializeGame() {
        // Null check for players
        if (players == null || players.isEmpty()) {
            throw new IllegalStateException("Player list is null or empty.");
        }

        // Null check for ingredient deck
        if (ingredientDeck == null || ingredientDeck.isEmpty()) {
            throw new IllegalStateException("Ingredient deck is null or empty.");
        }
        
        // Check the number of players
        int numPlayers = players.size();
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalStateException("Invalid number of players. The game supports 2 to 4 players.");
        }

        // Shuffle the players
        Collections.shuffle(players);

        for (Player p : players) {
            p.setGolds(10); // give 10 golds to each player
            // give 2 ingredient cards to each player
            IngredientCard i1 = drawIngredientCard();
            IngredientCard i2 = drawIngredientCard();
            p.getIngredientInventory().add(i1);
            p.getIngredientInventory().add(i2);
        }

        currentPlayer = players.get(0); // set the current player to the first player in list (list is already shuffled)

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
            
            // update the game state attributes
            gameState.setCurrentPlayer(currentPlayer);
            gameState.setCurrentRound(currentRound);
            gameState.setCurrentTurn(currentTurn);
            // set actionPerformed to false since we moved on to the next player
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

	//Takes a player and calculates the score 
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
        return currentRound > totalRounds; 
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
    	if(!actionPerformed) {
        	if (!ingredientDeck.isEmpty()) {
        		IngredientCard selectedCard = drawIngredientCard();
        		p.getIngredientInventory().add(selectedCard);
                actionPerformed = true;
                System.out.println(p.getIngredientInventory());

        	} else {
                notifyPlayers("The ingredient deck is empty.");
        	}
    	} else {
            notifyPlayers("Action already performed.");

    	}

    }

    //-----------------------Artifact Related Functions ------------------------------------
    
    /**
     * Attempts to purchase an artifact card for the player.
     * If the player has enough golds and the action hasn't been performed,
     * the card is purchased and, if immediate, used.
     *
     * @param card   the artifact card to be purchased
     * @param player the player attempting to buy the artifact card
     */
    public void buyArtifactCard(ArtifactCard card, Player player) {
        if (card == null || player == null) {
            throw new IllegalArgumentException("Card or player cannot be null.");
        }

        if (actionPerformed) {
            notifyPlayers("Action already performed.");
            return;
        }

        if (!playerCanAfford(player, card)) {
            notifyPlayers("You don't have enough golds.");
            return;
        }

        completeArtifactPurchase(card, player);
    }

    private boolean playerCanAfford(Player player, ArtifactCard card) {
        return player.getGolds() >= card.getGoldValue();
    }
    
    private void completeArtifactPurchase(ArtifactCard card, Player player) {
        if (card.isImmadiate()) {
            useArtifactCard(card, player);
        }
    
        artifactDeck.remove(card);
        player.addArtifactCard(card);
        player.reduceGold(card.getGoldValue());
        actionPerformed = true;
    }

    /**
     * Applies the effect of an artifact card to the player.
     *
     * @param card   the artifact card whose effect is to be applied
     * @param player the player who is using the artifact card
     */
    public void useArtifactCard(ArtifactCard card, Player player) {
        if (card == null || player == null) {
            throw new IllegalArgumentException("Card or player cannot be null.");
        }

        try {
            card.applyEffect(this, player);
        } catch (IllegalStateException e) {
            System.out.println("Error using artifact card: " + e.getMessage());
            // Consider logging the error or rethrowing a custom exception
        }
    }

    private void resetArtifactCards(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
    
        for (ArtifactCard card : player.getArtifactCards()) {
            if (card != null) {
                card.resetForNewRound();
            }
        }
    }
    
    public ArtifactCard getArtifactCardByPath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty.");
        }
        
        return artifactDeck.stream()
                .filter(card -> card.getImagePath().equals(path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Artifact Card not found for path: " + path));
    }

    //-----------------------Transmute Function ------------------------------------
    
    /**
     * Transmutes the selected ingredient for a player, converting it into gold.
     * Only allows the transmutation to occur if the action has not been performed already.
     *
     * @param player The player performing the transmutation.
     * @param selectedIngredient The ingredient card to be transmuted.
     */
    public void transmuteIngredient(Player player, IngredientCard selectedIngredient) {
        // Check if the action has already been performed
        if (!actionPerformed) {
            // Preconditions
            if (player.getIngredientInventory().isEmpty()) {
                // Notify players if the player's ingredient inventory is empty
                notifyPlayers("Ingredient card not found.");
            } else {
                // Flow
                // Remove the selected ingredient from the player's inventory
                player.getIngredientInventory().remove(selectedIngredient);
                // Add the selected ingredient to the main ingredient deck
                ingredientDeck.add(selectedIngredient);
                // Increase the player's gold by 1 as a result of the transmutation
                player.increaseGold(1);
                // Mark the action as performed
                actionPerformed = true;
            }
        } else {
            // Notify players if the action has already been performed
            notifyPlayers("Action already performed.");
        }
    }

    
    //-----------------------Make Experiment Function ------------------------------------

    /**
     * Conducts an experiment to create a potion using two ingredient cards.
     * If a student is conducting the experiment, different outcomes are considered.
     *
     * @param firstCard the first ingredient card
     * @param secondCard the second ingredient card
     * @param student flag indicating if a student is conducting the experiment
     * @return the created potion card, or null if the experiment cannot be conducted
     */
    public PotionCard makeExperiment(IngredientCard firstCard, IngredientCard secondCard, boolean student) {
        if (firstCard == null || secondCard == null) {
            throw new IllegalArgumentException("Ingredient cards cannot be null.");
        }

        if (actionPerformed) {
            notifyPlayers("Action already performed.");
            return null;
        }

        if (currentPlayer.getIngredientInventory().size() < 2) {
            notifyPlayers("There are not enough ingredient cards.");
            return null;
        }

        PotionCard potionCard = makePotion(firstCard, secondCard);
        processExperimentOutcome(potionCard, student);
        actionPerformed = true;
        return potionCard;
    }

    private PotionCard makePotion(IngredientCard firstCard, IngredientCard secondCard) {
        PotionCard potionCard = GameObjectFactory.getInstance().potionMaker(firstCard, secondCard);
        currentPlayer.getPotionInventory().add(potionCard);

        if (currentPlayer.isMagicMortarActive()) {
            ArtifactEffect currentEffect = currentPlayer.getArtifactCard("Magic Mortar").getEffect();
            if (currentEffect != null) {
                currentEffect.applyOnMakeExperiment(currentPlayer, firstCard, secondCard);
            }
        } else {
            removeUsedIngredients(firstCard, secondCard);
        }

        return potionCard;
    }

    private void removeUsedIngredients(IngredientCard firstCard, IngredientCard secondCard) {
        currentPlayer.getIngredientInventory().remove(firstCard);
        currentPlayer.getIngredientInventory().remove(secondCard);
    }

    private void processExperimentOutcome(PotionCard potionCard, boolean student) {
        if (potionCard == null) {
            throw new IllegalArgumentException("Potion card cannot be null.");
        }

        if (potionCard.getPotionType().equals("NEGATIVE")) {
            if (student) {
                currentPlayer.reduceGold(1);
            } else {
                currentPlayer.increaseSickness(1);
                if (currentPlayer.getSicknessLevel() == 3) {
                    currentPlayer.setGolds(0);
                }
            }
        }
    }



    //--------------------Swap functions for Elixir Of Insight -----------------------------
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
    
    //----------------------Publish Theory Functions-------------------------------
    
    public Theory findTheorybyIngredient(IngredientCard ingredient) {
    	for(Theory t : Theory.getTheoryList()) {
    		if(t.getIngredient().equals(ingredient)) {
    			return t;
    		}
    	}
    	return null;    	
    }
    
    /**
     * Allows the player to publish a theory based on an ingredient and molecule.
     * If the player has the Printing Press artifact, the gold cost is waived.
     *
     * @param ingredient the ingredient card used in the theory
     * @param molecule the molecule associated with the theory
     */
    public void publishTheory(IngredientCard ingredient, Molecule molecule) {
        if (ingredient == null || molecule == null) {
            throw new IllegalArgumentException("Ingredient and molecule cannot be null.");
        }

        if (actionPerformed) {
            notifyPlayers("Action already performed.");
            return;
        }

        if (currentRound < 2) {
            notifyPlayers("Cannot publish theory before round 2.");
            return;
        }

        if (findTheorybyIngredient(ingredient) != null) {
            notifyPlayers("A theory already published about this ingredient");
            return;
        }

        createAndPublishTheory(currentPlayer, ingredient, molecule);
    }

    private void createAndPublishTheory(Player player, IngredientCard ingredient, Molecule molecule) {
        Theory theory = new Theory(ingredient, molecule);
        PublicationCard pcard = new PublicationCard(player, theory);
        player.increaseReputation(1);

        if (!player.isPrintingPressActive()){
            player.reduceGold(1);
        }

        actionPerformed = true;
    }


    //----------------------Debunk Theory Functions-------------------------------
    /**
    * Debunks a theory presented in a publication card.
    * Reduces the reputation of the current player if the theory is correct,
    * otherwise increases it and reduces the reputation of the theory's owner.
    * Pre-conditions are validated before proceeding with debunking.
    *
    * @param publicationCard the publication card containing the theory to be debunked
    * @param component the component being used to debunk the theory
    * @throws IllegalArgumentException if publicationCard is null
    * @throws IllegalStateException if action already performed or other preconditions are not met
    */
    public void debunkTheory(PublicationCard publicationCard, Component component) {
        validateDebunkingPreconditions(publicationCard);

        IngredientCard ingredient = publicationCard.getTheory().getIngredient();
        Molecule theoryMolecule = publicationCard.getTheory().getMolecule();
        Molecule ingredientMolecule = ingredient.getMolecule();
        Molecule.Sign componentSign = ingredientMolecule.getComponentSign(component);

        if (theoryMolecule.compareComponent(ingredientMolecule, component)) {
            handleCorrectTheory(publicationCard);
        } else {
            handleIncorrectTheory(publicationCard);
        }
        ValidatedAspect validatedAspect = new ValidatedAspect(ingredient, component,componentSign);
        actionPerformed = true;
    }

    /* Checks if publication card is null, if action is already performed, if round is appropriate,
    * and if there are existing theories about the ingredient. */ 
    private void validateDebunkingPreconditions(PublicationCard publicationCard) {
        if (publicationCard == null) {
            throw new IllegalArgumentException("publicationCard cannot be null.");
        }

        if (actionPerformed) {
            notifyPlayers("Action already performed.");
            throw new IllegalStateException("Cannot perform action again in this round.");
        }

        if (currentRound < 3) {
            throw new IllegalStateException("Cannot debunk theory before round 3.");
        }

        if (findTheorybyIngredient(publicationCard.getTheory().getIngredient()) == null) {
            throw new IllegalStateException("No published theories about this ingredient.");
        }
    }

    private void handleCorrectTheory(PublicationCard publicationCard) {
        currentPlayer.reduceReputation(1);
       
        Theory.getTheoryList().remove(publicationCard.getTheory());
        notifyPlayers("Theory validated and debunking failed.");
    }

    private void handleIncorrectTheory(PublicationCard publicationCard) {
        currentPlayer.increaseReputation(2);
        publicationCard.getOwner().reduceReputation(1);
        notifyPlayers("Theory debunked successfully.");
    }




}



