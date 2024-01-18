package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private int currentPlayerID;
    private boolean isPaused;
    private GameState gameState = null;
    private Player winner = null;

	private boolean actionPerformed;
    private int publicationCounter = 1;
    private int theoryCounter = 1;
    private int validatedAspectCounter = 1;
    
    //constructor should be private in Singleton
    private Game() {
    	
        this.players = Player.getPlayerList();      
        this.ingredientDeck = GameObjectFactory.getInstance().createIngredientDeck();
        this.artifactDeck = GameObjectFactory.getInstance().createArtifactDeck();
        this.totalRounds = 3; // Set the total number of rounds
        this.currentRound = 1;
        this.currentTurn = 1;
        this.isPaused = false;
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

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getRound() {
       return currentRound;
    }

    //-----------------------Game Related Functions--------------------------------------

    public void initializeGame() {
    	
        GameState.destroyInstance();

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

        System.out.println(players);

        for (Player p : players) {
            
            p.setGolds(10); // give 10 golds to each player
            // give 2 ingredient cards to each player
            IngredientCard i1 = drawIngredientCard();
            IngredientCard i2 = drawIngredientCard();
            p.getIngredientInventory().add(i1);
            p.getIngredientInventory().add(i2);

            System.out.println("Player: " + p.getNickname() +" gets ingredients: "+ i1.getName() + ", " + i2.getName());
        }

   

        currentPlayer = players.get(0); // set the current player to the first player in list (list is already shuffled)
        this.currentPlayerID = currentPlayer.getPlayerId();
        this.gameState = GameState.getInstance(players, currentRound, currentTurn, currentPlayerID, isPaused);
        gameState.setCurrentPlayerID(currentPlayerID);
       
        System.out.println("Debug: GameState initialized - " + gameState);
    }

    
    public void updateState() {
    	
			int currentPlayerIndex = players.indexOf(currentPlayer); // Get the index of the current player
            System.out.println("currentPlayer Index: " + currentPlayerIndex);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            currentPlayer = players.get(currentPlayerIndex);
            currentPlayerID = currentPlayer.getPlayerId();
            gameState.setCurrentPlayerID(currentPlayerID);

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
            gameState.setCurrentPlayerID(currentPlayerID);
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

    public void updateGameState(GameState newGameState) {
        // Update the game state with the new information
        this.currentRound = newGameState.getCurrentRound();
        this.currentTurn = newGameState.getCurrentTurn();
        this.currentPlayerID = newGameState.getCurrentPlayerID();
        this.isPaused = newGameState.isPaused();
        // Additionally update other relevant state attributes if necessary
        // For example, players, scores, etc., based on what GameState contains

        // Debug print to check initialization
        System.out.println("Game initialized from GameState:");
        System.out.println("Current Round: " + currentRound);
        System.out.println("Current Turn: " + currentTurn);
        System.out.println("Current Player: " + (currentPlayer != null ? currentPlayer.getNickname() : "null"));

        // Printing player details
        if (players != null) {
            for (Player player : players) {
                System.out.println("Player: " + player.getNickname() + ", Golds: " + player.getGolds());
                System.out.println("Ingredient Inventory: " + player.getIngredientInventory());
            }
        }
    }


    
    //end game method
    public double endGame() {
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

        return maxScore;
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
    
    public boolean isGameOver() {
        return currentRound > totalRounds; 
    }

    //---------------------Functions for finding object by Id----------------------------
    // Method to find a player by their ID
    public Player getPlayerById(int playerId) {
        for (Player player : players) {
            if (player.getPlayerId() == playerId) {
                return player;
            }
        }
        return null; // or throw an exception if a player is not found
    }

    public ArtifactCard getArtifactCardById(int cardId) {
        return artifactDeck.stream()
               .filter(card -> card.getArtifactId() == cardId)
               .findFirst()
               .orElse(null); // Return null if no card is found
    }

    private IngredientCard getIngredientById(int ingredientId) {
        
       for (IngredientCard ingredient : IngredientCard.getIngredientList()) {
            if (ingredient.getCardId() == ingredientId) {
                return ingredient;
            }
        }
        
        return null; // or throw an exception if appropriate
    }

    private Molecule getMoleculeById(int moleculeId) {
        for (Molecule molecule : Molecule.getMoleculeList()) {
            if (molecule.getMoleculeId() == moleculeId) {
                return molecule;
            }
        }
        return null; // Molecule not found
    }

    private PublicationCard getPublicationCardById(int publicationCardId){
        for (PublicationCard publicationCard : PublicationCard.getPublicationCardList()) {
            if (publicationCard.getPublicationId() == publicationCardId) {
                return publicationCard;
            }
        }
        return null; // Publication Card not found
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
    
    public void forageForIngredient(int playerId) {
        // Check if an action has already been performed in this turn
        if (!actionPerformed) {
            // Check if the ingredient deck is not empty
            if (!ingredientDeck.isEmpty()) {
                // Find the player by ID
                Player player = getPlayerById(playerId);
                
                // Check if the player exists
                if (player == null) {
                    throw new IllegalArgumentException("Player with ID " + playerId + " not found.");
                }
    
                // Draw an ingredient card
                IngredientCard selectedCard = drawIngredientCard();
    
                // Add the drawn card to the player's inventory
                player.getIngredientInventory().add(selectedCard);
    
                // Mark that an action has been performed
                actionPerformed = true;
    
                // Print the player's updated inventory (for testing or logging)
                System.out.println(player.getIngredientInventory());
    
            } else {
                // Notify all players if the ingredient deck is empty
                notifyPlayers("The ingredient deck is empty.");
            }
        } else {
            // Notify all players if an action has already been performed in this turn
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
    public void buyArtifactCard(int playerId, int cardId) {
        // Check if an action has already been performed this turn
        if (actionPerformed) {
            notifyPlayers("Action already performed.");
            return;
        }
    
        // Find the player and artifact card by their IDs
        Player player = getPlayerById(playerId);
        ArtifactCard card = getArtifactCardById(cardId);
    
        // Check if the player and card exist
        if (player == null) {
            throw new IllegalArgumentException("Player with ID " + playerId + " not found.");
        }
        if (card == null) {
            throw new IllegalArgumentException("Artifact Card with ID " + cardId + " not found.");
        }
    
        // Check if the player can afford the artifact card
        if (!playerCanAfford(player, card)) {
            notifyPlayers("You don't have enough golds.");
            return;
        }

        // Complete the purchase of the artifact card
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

    public void useArtifactCardById(int cardId, int playerId) {
        Player player = getPlayerById(playerId);
        ArtifactCard card = getArtifactCardById(cardId);
    
        // Check if both player and card are not null
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
    public void transmuteIngredient(int playerId, int ingredientId) {
        // Check if the action has already been performed
        if (actionPerformed) {
            notifyPlayers("Action already performed.");
            return;
        }
    
        Player player = getPlayerById(playerId);
        IngredientCard selectedIngredient = getIngredientById(ingredientId);
    
        // Preconditions
        if (player == null || selectedIngredient == null) {
            throw new IllegalArgumentException("Player or ingredient card cannot be null.");
        }
    
        // Check if player has the ingredient
        if (!player.getIngredientInventory().contains(selectedIngredient)) {
            notifyPlayers("Ingredient card not found in player's inventory.");
            return;
        }
    

        // Remove the selected ingredient from the player's inventory
        player.getIngredientInventory().remove(selectedIngredient);
        // Add the selected ingredient to the main ingredient deck
        ingredientDeck.add(selectedIngredient);
        // Increase the player's gold by 1 as a result of the transmutation
        player.increaseGold(1);
        // Mark the action as performed
        actionPerformed = true;
    }

    
    //-----------------------Make Experiment Function ------------------------------------

    /**
 * Conducts an experiment to create a potion using two ingredient cards.
 * If a student is conducting the experiment, different outcomes are considered.
 *
 * @param playerId the ID of the player conducting the experiment.
 * @param firstCardId the ID of the first ingredient card.
 * @param secondCardId the ID of the second ingredient card.
 * @param student flag indicating if a student is conducting the experiment.
 * @return the created potion card, or null if the experiment cannot be conducted.
 */
public PotionCard makeExperiment(int playerId, int firstCardId, int secondCardId, boolean student) {
    if (actionPerformed) {
        notifyPlayers("Action already performed.");
        return null;
    }

    Player player = getPlayerById(playerId);
    IngredientCard firstCard = getIngredientById(firstCardId);
    IngredientCard secondCard = getIngredientById(secondCardId);
    if (player == null) {
        throw new IllegalArgumentException("Player cannot be null.");
    }
    if (firstCard == null || secondCard == null) {
        throw new IllegalArgumentException("Ingredient cards cannot be null.");
    }

    if (player.getIngredientInventory().size() < 2) {
        notifyPlayers("There are not enough ingredient cards.");
        return null;
    }

    PotionCard potionCard = makePotion(firstCard, secondCard);
    gameState.setLastCreatedPotion(potionCard);
    processExperimentOutcome(player, potionCard, student);
    actionPerformed = true;
    PotionCard.getPotionMap().computeIfAbsent(player, k -> new ArrayList<>()).add(potionCard);

    return potionCard;
}
    private PotionCard makePotion(IngredientCard firstCard, IngredientCard secondCard) {
        PotionCard potionCard = GameObjectFactory.getInstance().potionMaker(firstCard, secondCard);
        currentPlayer.getPotionInventory().add(potionCard);
        System.out.println(currentPlayer.isMagicMortarActive());
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

    private void processExperimentOutcome(Player player,PotionCard potionCard, boolean student) {
        if (potionCard == null) {
            throw new IllegalArgumentException("Potion card cannot be null.");
        }

        if (potionCard.getPotionType().equals("NEGATIVE")) {
            if (student) {
                player.reduceGold(1);
            } else {
                player.increaseSickness(1);
                if (player.getSicknessLevel() == 3) {
                    player.setGolds(0);
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

    public void sellPotion(int playerId, int ingredientCardId1, int ingredientCardId2, String guarantee) {
        Player player = getPlayerById(playerId);
        if (player == null) {
            notifyPlayers("Player not found.");
            return;
        }
    
        IngredientCard i1 = getIngredientById(ingredientCardId1);
        IngredientCard i2 = getIngredientById(ingredientCardId2);
    
        if (i1 == null || i2 == null) {
            notifyPlayers("Ingredient card not found.");
            return;
        }
    
        if (!actionPerformed && currentRound >= 2 && player.getIngredientInventory().size() >= 2 && player.getGolds() >= 1) {
            PotionCard potion = GameObjectFactory.getInstance().potionMaker(i1, i2);
            player.getIngredientInventory().remove(i1);
            player.getIngredientInventory().remove(i2);
    
            if (guarantee.equals("Positive") && potion.getPotionType().equals("POSITIVE")) {
                player.increaseGold(3);
            } else if (guarantee.equals("Positive") && !potion.getPotionType().equals("POSITIVE")) {
                player.reduceGold(1);
            } else if (guarantee.equals("Positive/Neutral") && (potion.getPotionType().equals("POSITIVE") || potion.getPotionType().equals("Neutral"))) {
                player.increaseGold(2);
            } else if (guarantee.equals("Positive/Neutral") && potion.getPotionType().equals("NEGATIVE")) {
                player.reduceGold(1);
            } else if (guarantee.equals("No Guarantee")) {
                player.increaseGold(1);
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
    public void publishTheory(int playerId, int ingredientId, int moleculeId) {
        Player player = getPlayerById(playerId);
        if (player == null) {
            notifyPlayers("Player not found.");
            return;
        }
    
        IngredientCard ingredient = getIngredientById(ingredientId);
        Molecule molecule = getMoleculeById(moleculeId);
    
        if (ingredient == null || molecule == null) {
            notifyPlayers("Ingredient or molecule not found.");
            return;
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
    
        createAndPublishTheory(player, ingredient, molecule);
    }

    private void createAndPublishTheory(Player player, IngredientCard ingredient, Molecule molecule) {
        Theory theory = new Theory(theoryCounter++,ingredient, molecule);
        PublicationCard pcard = new PublicationCard(publicationCounter++,player, theory);
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
    public void debunkTheory(int playerId, int publicationCardId, Component component) {
        Player player = getPlayerById(playerId);
        PublicationCard publicationCard = getPublicationCardById(publicationCardId);
    
        if (player == null || publicationCard == null) {
            notifyPlayers("Player or Publication Card not found.");
            return;
        }
    
        if (actionPerformed) {
            notifyPlayers("Action already performed.");
        	  return;
        }

        if (currentRound < 3) {
            notifyPlayers("Cannot debunk theory before round 3.");
  	  	  return;
        }

        if (findTheorybyIngredient(publicationCard.getTheory().getIngredient()) == null) {
            notifyPlayers("No published theories about this ingredient.");
            return;
        }
        
        if(findValidatedAspectByIngredientComponent(publicationCard.getTheory().getIngredient(), component) != null){
            notifyPlayers("This aspect has already been debunked.");
            return;
        }    
        
        IngredientCard ingredient = publicationCard.getTheory().getIngredient();
        Molecule theoryMolecule = publicationCard.getTheory().getMolecule();
        Molecule ingredientMolecule = ingredient.getMolecule();
        Molecule.Sign componentSign = ingredientMolecule.getComponentSign(component);
        Molecule.Size componentSize = ingredientMolecule.getComponentSize(component);
    
        if (theoryMolecule.compareComponent(ingredientMolecule, component)) {
            handleCorrectTheory(publicationCard, player);
        } else {
            handleIncorrectTheory(publicationCard, player);
        }
    
        ValidatedAspect validatedAspect = new ValidatedAspect(validatedAspectCounter++, ingredient, component, componentSign, componentSize);
        actionPerformed = true;
    }

    private void handleCorrectTheory(PublicationCard publicationCard, Player player) {
        player.reduceReputation(1);
        //Theory.getTheoryList().remove(publicationCard.getTheory());
        notifyPlayers("Theory validated and debunking failed.");
    }

    private void handleIncorrectTheory(PublicationCard publicationCard, Player player) {
        player.increaseReputation(2);
        if(publicationCard.getOwner().isWisdomIdolActive()){
            ArtifactEffect effect = publicationCard.getOwner().getArtifactCard("Wisdom Idol").getEffect();
            if(effect != null){
                effect.applyOnDebunkTheory(publicationCard.getOwner());
            }
        } else{
            publicationCard.getOwner().reduceReputation(1);
        }
        notifyPlayers("Theory debunked successfully.");
    }
    
    public ValidatedAspect findValidatedAspectByIngredientComponent(IngredientCard ingredient, Component component) {
    	for(ValidatedAspect v : ValidatedAspect.getValidatedList()) {
    		if(v.getIngredient().equals(ingredient) && v.getValidatedComponent().equals(component)) {
    			return v;
    		}
    	}
    	return null;
    }
    
    //----------------------Function to get the players' artifact cards-------------------------------
    public Map<Player, List<ArtifactCard>> getPlayersArtifacts() {
        Map<Player, List<ArtifactCard>> playerArtifactMap = new HashMap<>();

        for (Player player : players) {
            List<ArtifactCard> playerArtifactCards = player.getArtifactCards();
            playerArtifactMap.put(player, playerArtifactCards);
        }

        return playerArtifactMap;
    }
    

    public int getCurrentPlayerID() {
       return currentPlayerID;
    }



}