package domain.controllers;

import java.util.List;
import java.util.Map;

import domain.Game;
import domain.GameState;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Molecule;
import domain.gameobjects.Molecule.Component;
import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;
import domain.gameobjects.artifacteffects.ElixirOfInsightEffect;
import domain.controllers.*;

import java.awt.Point;
import java.util.HashMap;

import ui.swing.screens.BoardScreen;
import ui.swing.screens.DeductionBoard;

public class GameController {
	
	private static GameController instance;
	private BoardScreen boardScreen;
	private final Game game;
	private final GameObjectFactory gameObjectFactory;
	private Map<Player, DeductionBoard> playerDeductionBoards = new HashMap<>();
    private boolean isOnlineMode = false;
    private OnlineGameAdapter onlineGameAdapter;
	
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
	
    public static void destroyInstance() {
        instance = null;
    }

    // Method to set the game mode
    public void setOnlineMode(boolean isOnline) {
        this.isOnlineMode = isOnline;
    }

    // Method to check if the game is in online mode
    public boolean isOnlineMode() {
        return this.isOnlineMode;
    }
    
    //constructor should be private in Singleton
    private GameController() {
		game = Game.getInstance();
		gameObjectFactory = GameObjectFactory.getInstance();
		//initializeDeductionBoards();
    }
    
    public void setBoardScreen(BoardScreen boardScreen) {
        this.boardScreen = boardScreen;
    }
    
    private void initializeDeductionBoards() {
        for (Player player : game.getGameState().getPlayers()) {
            playerDeductionBoards.put(player, new DeductionBoard(boardScreen));
        }
    }

    public DeductionBoard getDeductionBoardForPlayer(Player player) {
        return playerDeductionBoards.get(player);
    }

    // Call this method when it's a player's turn
    public void displayDeductionBoardForCurrentPlayer() {
        Player currentPlayer = getCurrentPlayer();
        DeductionBoard board = new DeductionBoard(boardScreen);
        loadPlayerDeductionsIntoBoard(currentPlayer, board);
        board.display();
    }

    private void loadPlayerDeductionsIntoBoard(Player player, DeductionBoard board) {
        // Logic to load player's deductions into the board
    	board.clearSignPlacements();
        for (Player.Deduction deduction : player.getDeductions()) {
            board.addSignPlacement(new Point(deduction.getX(), deduction.getY()), convertNumToSign(deduction.getSign_num()));
        }
        board.repaint();
    }
    
    private DeductionBoard.Sign convertNumToSign(int signNum) {
        // Assuming signNum starts from 0 and maps directly to the order of enum constants
        DeductionBoard.Sign[] signs = DeductionBoard.Sign.values();
        if (signNum >= 0 && signNum < signs.length) {
            return signs[signNum];
        }
        // Handle invalid signNum (e.g., return a default sign or throw an exception)
        return DeductionBoard.Sign.NEUTRAL; // Default or error handling
    }
    
    public void playerMadeDeduction(int x, int y, int signNum) {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.addDeduction(x, y, signNum);

        // Now update the DeductionBoard for the current player
        DeductionBoard currentBoard = playerDeductionBoards.get(currentPlayer);
        if (currentBoard != null) {
            loadPlayerDeductionsIntoBoard(currentPlayer, currentBoard);
        }
    }

    public Player getCurrentPlayer(){
    	return game.getGameState().getCurrentPlayer();
    }
    
    public int getCurrentTurn() {
    	return game.getGameState().getCurrentTurn();
    }
    
    public int getCurrentRound() {
    	return game.getGameState().getCurrentRound();
    }
    
    public void forageForIngredient(int playerId) {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.forageForIngredient(String.valueOf(playerId));
        } else {
            // Offline mode: Directly call game logic
            game.forageForIngredient(playerId);
        }
    
    }

    public List<ArtifactCard> getAvailableArtifacts() {
        return game.getArtifactDeck();
    }

    public List<IngredientCard> getIngredientDeck() {
        return game.getIngredientDeck();
    }
    

    public void buyArtifactCard(int playerId, int cardId) {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.buyArtifactCard(String.valueOf(playerId), String.valueOf(cardId));
        } else {
            // Offline mode: Directly call game logic
            game.buyArtifactCard(playerId, cardId);
        }
        
    }
    
    public ArtifactCard getArtifactCardByPath(String path) {
    	return game.getArtifactCardByPath(path);
    }
    
    public void pauseGame() {
        game.pauseGame();
    }

    public void resumeGame() {
        game.resumeGame();
    }
    
    public void transmuteIngredient(int playerId, int ingredientId) {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.transmuteIngredient(String.valueOf(playerId), String.valueOf(ingredientId));
        } else {
            // Offline mode: Directly call game logic
            game.transmuteIngredient(playerId, ingredientId);
        }
    	
    }

    public void updateState() {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.updateState();
        } else {
            // Offline mode: Directly call game logic
           game.updateState();
        }
    	
    }

    public boolean getActionPerformed() {
    	return game.getActionPerformed();
    }

   
    
    public List<ArtifactCard> getPlayerArtifactCards() {
        
        return game.getGameState().getCurrentPlayer().getArtifactCards();
    }

    //DEĞİŞECEK
    public void UseArtifactCard(ArtifactCard artifactCard, Player currentPlayer) {
        
        game.useArtifactCard(artifactCard, currentPlayer);
    }

    public void swapRight(IngredientCard ingredientCard){
       game.swapRight(ingredientCard);

    }

    public void swapLeft(IngredientCard ingredientCard){
        game.swapLeft(ingredientCard);

    }
    
    public void sellPotion(int playerId, int ingredientCardId1, int ingredientCardId2, String guarantee) {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.sellPotion(String.valueOf(playerId), String.valueOf(ingredientCardId1), String.valueOf(ingredientCardId2), guarantee);
        } else {
            // Offline mode: Directly call game logic
           game.sellPotion(playerId, ingredientCardId1, ingredientCardId2, guarantee);
        }
    	
    }
    
    public PotionCard makeExperiment(int playerId, int firstCardId, int secondCardId, boolean student) {
        if (isOnlineMode) {
            // Send action to server via adapter and wait for response
            return onlineGameAdapter.makeExperiment(playerId, firstCardId, secondCardId, student);
        } else {
            // Offline mode: Directly call game logic
            return game.makeExperiment(playerId, firstCardId, secondCardId, student);
        }
    }
    
    public Map<String, String> createIngredientNameAndPathList(){
    	return gameObjectFactory.createIngredientNameAndPathList();
    }
    
    public void publishTheory(int playerId, int ingredientId, int moleculeId) {
        if (isOnlineMode) {
            // Send action to server via adapter and wait for response
           onlineGameAdapter.publishTheory(String.valueOf(playerId),String.valueOf(ingredientId), String.valueOf(moleculeId));
        } else {
            // Offline mode: Directly call game logic
            game.publishTheory(playerId,ingredientId, moleculeId);
        }
    	
    }

    public void debunkTheory(int playerId, int publicationCardId, Component component){
        if (isOnlineMode) {
            // Send action to server via adapter and wait for response
           onlineGameAdapter.debunkTheory(String.valueOf(playerId),String.valueOf(publicationCardId), component);
        } else {
            // Offline mode: Directly call game logic
           game.debunkTheory(playerId, publicationCardId, component);
        }
       
    }

    public GameState getInitialState() {
        // Use the existing players from the Game instance
        List<Player> players = game.getPlayers(); // Assuming there is a getPlayers() method in Game
        int currentRound = game.getRound();
        int currentTurn = game.getCurrentTurn();
        int currentPlayer = game.getCurrentPlayerID(); // Check if the list is empty
        boolean isPaused = false;

        return new GameState(players, currentRound, currentTurn, currentPlayer, isPaused);
    }

    public GameState getGameState(){
        GameState gameState = game.getGameState();
        if (gameState != null) {
            System.out.println("Retrieving GameState: " + gameState);
        } else {
            System.err.println("GameState is null in GameController.");
        }
        return gameState;
    }
}
