package domain.controllers;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import domain.Game;
import domain.GameState;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Molecule.Component;
import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;
import domain.gameobjects.PublicationCard;
import domain.gameobjects.ValidatedAspect;
import javafx.application.Platform;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import ui.swing.screens.scenes.DeductionBoard;
import ui.swing.screens.screencomponents.SettingsState;
import ui.swing.screens.screencontrollers.DeductionBoardController;

public class GameController {
	
	private static GameController instance;
    private final Game game;
    private final GameObjectFactory gameObjectFactory;
    private OnlineGameAdapter onlineGameAdapter;
    private boolean isOnlineMode = false;
	
	private Map<Player, DeductionBoard> playerDeductionBoards = new HashMap<>();
    private static final SettingsState settingsState = new SettingsState();
    
    private String clientPlayerName; //For online game, just keep the client name for further check

	
    public static GameController getInstance() {
        if (instance == null) {
            synchronized (GameController.class) {
                if (instance == null) {
                    instance = new GameController();
                }
            }
        }
        return instance;
    }

    /**
    * Singleton game controller 
    * MVV - this controller redirects the call from UI to game
    */
    public GameController() {
		game = Game.getInstance();
		gameObjectFactory = GameObjectFactory.getInstance();
		initializeDeductionBoards();
    }
	
    public static void destroyInstance() {
        instance = null;
    }
    
    // ----------- Getter and Setter Methods -----------

    public Player getCurrentPlayer(){
    	return game.getGameState().getCurrentPlayer();
    }
    
    public List<Player> getPlayers(){
    	return game.getPlayers();
    }
    
    public int getCurrentTurn() {
    	return game.getGameState().getCurrentTurn();
    }
    
    public int getCurrentRound() {
    	return game.getGameState().getCurrentRound();
    }

    public List<ArtifactCard> getAvailableArtifacts() {
        return game.getArtifactDeck();
    }

    public List<IngredientCard> getIngredientDeck() {
        return game.getIngredientDeck();
    }

    public List<ArtifactCard> getPlayerArtifactCards() {
        return game.getGameState().getCurrentPlayer().getArtifactCards();
    }


    public ArtifactCard getArtifactCardByPath(String path) {
    	return game.getArtifactCardByPath(path);
    }

    public Map<String, String> createIngredientNameAndPathList(){
    	return gameObjectFactory.createIngredientNameAndPathList();
    }

    public boolean getActionPerformed() {
    	return game.getActionPerformed();
    }

    public DeductionBoard getDeductionBoardForPlayer(Player player) {
        return playerDeductionBoards.get(player);
    }

    public String getClientPlayer() {
        return clientPlayerName;
    }

    public Player getPlayerByClientName(String name){
        return game.getPlayerByClientName(name);
    }
    public Player getPlayerById(int playerId) {
        return game.getPlayerById(playerId);
    }
    public ArtifactCard getArtifactCardById(int artifactCardId) {
       return game.getGameState().getArtifactCardById(artifactCardId);
    }

    public void updateGameState(GameState gameState) {
        game.updateGameState(gameState);
    }

    public void setGameState(GameState gameState) {
        game.setGameState(gameState);
    }
 
    public void setClientPlayer(String player) {
        this.clientPlayerName = player;
    }

    public void setOnlineGameAdapter(OnlineGameAdapter adapter) {
        this.onlineGameAdapter = adapter;
    }

    public Player getWinner() {
    	return game.getWinner();   	
    }
    
    public double endGame() {
    	return game.endGame();
    }
    
    public boolean isGameOver() {
        return game.isGameOver(); 
    }


    //-------- Aditional sets and checks to system ------


    // Method to check if the game is in online mode
    public boolean isOnlineMode() {
        return this.isOnlineMode;
    }

    public void setOnlineMode(boolean isOnline) {
        this.isOnlineMode = isOnline;
    }

    // Provide a way to access the settings state
    public static SettingsState getSettingsState() {
        return settingsState;
    }


    //------------- Action calls --------

    public void updateState() {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.updateState();
        } else {
            // Offline mode: Directly call game logic
           game.updateState();
        }
    	
    }

    public void initializeGame(){
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.startGame();
        } else {
            // Offline mode: Directly call game logic
           game.initializeGame();
        }
    }

    public void pauseGame() {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.sendPauseGameRequest();
        } else {
            // Offline mode: Directly call game logic
            game.pauseGame();
        }
    }

    public void resumeGame() {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.sendResumeGameRequest();
        } else {
            // Offline mode: Directly call game logic
            game.resumeGame();
        }
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

    

    public void buyArtifactCard(int playerId, int cardId) {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.buyArtifactCard(String.valueOf(playerId), String.valueOf(cardId));
        } else {
            // Offline mode: Directly call game logic
            game.buyArtifactCard(playerId, cardId);
        }
        
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



    public void UseArtifactCard(int cardId, int playerId) {
        if (isOnlineMode) {
            onlineGameAdapter.useArtifactCard(String.valueOf(playerId), String.valueOf(cardId));
        } else {
            game.useArtifactCardById(cardId, playerId);
        }
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
    
   public void makeExperiment(int playerId, int firstCardId, int secondCardId, boolean student, Consumer<PotionCard> callback) {
        if (isOnlineMode) {
            // Online mode: Send action to server via adapter
            onlineGameAdapter.makeExperiment(playerId, firstCardId, secondCardId, student, callback);
        } else {
            // Offline mode: Directly call game logic
            PotionCard result = game.makeExperiment(playerId, firstCardId, secondCardId, student);
            callback.accept(result);
        }
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
    

    /// DeductionBoard handler functions for each players own deductionBoard


    private void initializeDeductionBoards() {
        for (Player player : game.getGameState().getPlayers()) {
            playerDeductionBoards.put(player, new DeductionBoard());
        }
    }
   
    // this method for keeping unique deductions
    public void displayDeductionBoardForCurrentPlayer() {
        Player currentPlayer = getCurrentPlayer();
        DeductionBoard board = playerDeductionBoards.computeIfAbsent(currentPlayer, k -> new DeductionBoard());
        Platform.runLater(() -> {
            board.display();
            loadPlayerDeductionsIntoBoard(currentPlayer, board);
        });
    }

    private void loadPlayerDeductionsIntoBoard(Player player, DeductionBoard board) {
        List<Player.Deduction> deductions = player.getDeductions(); // Retrieve the list of deductions for the player
        Platform.runLater(() -> {
            board.clearSignPlacements();
            for (Player.Deduction deduction : deductions) {
                board.addSignPlacement(new Point(deduction.getX(), deduction.getY()), convertNumToSign(deduction.getSign_num()));
            }
        });
    }
    
    
    private DeductionBoardController.Sign convertNumToSign(int signNum) {
        DeductionBoardController.Sign[] signs = DeductionBoardController.Sign.values();
        if (signNum >= 0 && signNum < signs.length) {
            return signs[signNum];
        }
        return DeductionBoardController.Sign.NEUTRAL; 
    }
    
    public void playerMadeDeduction(int x, int y, int signNum) {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.addDeduction(x, y, signNum);

        DeductionBoard board = playerDeductionBoards.get(currentPlayer);
        if (board != null) {
            Platform.runLater(() -> {
                board.addSignPlacement(new Point(x, y), convertNumToSign(signNum));
            });
        }
    }

   
    //---------- Elixir of insight artifact swap calls redirection --------     

    public void swapRight(IngredientCard ingredientCard){
       game.swapRight(ingredientCard);

    }

    public void swapLeft(IngredientCard ingredientCard){
        game.swapLeft(ingredientCard);

    }
    
    public ValidatedAspect findValidatedAspectByIngredientComponent(IngredientCard ingredient, Component component) {
    	return game.findValidatedAspectByIngredientComponent(ingredient, component);
    }
    
    public static ArrayList<PublicationCard> getPublicationCardList() {
    	return PublicationCard.getPublicationCardList();
    }
    
    public Map<Player, List<ArtifactCard>> getPlayersArtifacts() {
    	return game.getPlayersArtifacts();
    }
    
 	public static Map<Player, List<PotionCard>> getPotionMap() {
    	return PotionCard.getPotionMap();
    }

    public GameState getGameState(){
        GameState gameState = game.getGameState();
        if (gameState != null) {
            System.out.println("Retrieving GameState: " + gameState);
        } else {
            System.err.println("Debug: GameState is null in GameController.getGameState()");
        }
        return gameState;
    }
}