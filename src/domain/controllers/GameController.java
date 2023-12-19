package domain.controllers;

import java.util.List;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;
import domain.Game;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;
import domain.gameobjects.artifacteffects.ElixirOfInsightEffect;
import ui.swing.screens.BoardScreen;
import ui.swing.screens.DeductionBoard;

public class GameController {
	
	private static GameController instance;
	private BoardScreen boardScreen;
	private final Game game;
	private Map<Player, DeductionBoard> playerDeductionBoards = new HashMap<>();
	
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
	
    public static void destroyInstance() {
        instance = null;
    }
    
    //constructor should be private in Singleton
    private GameController() {
		game = Game.getInstance();
		initializeDeductionBoards();
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
    
    public void forageForIngredient(Player p) {
    	game.forageForIngredient(p);
    }

    public List<ArtifactCard> getAvailableArtifacts() {
        return game.getArtifactDeck();
    }

    public List<IngredientCard> getIngredientDeck() {
        return game.getIngredientDeck();
    }
    

    public void buyArtifactCard(ArtifactCard artifact, Player currentPlayer) {
        game.buyArtifactCard(artifact, currentPlayer);
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
    
    public void transmuteIngredient(Player player, IngredientCard selectedIngredient) {
    	game.transmuteIngredient(player, selectedIngredient);
    }
    
    public void updateState() {
    	game.updateState();
    }

    public boolean getActionPerformed() {
    	return game.getActionPerformed();
    }
    
    public List<ArtifactCard> getPlayerArtifactCards() {
        return game.getGameState().getCurrentPlayer().getArtifactCards();
    }

    public void UseArtifactCard(ArtifactCard artifactCard, Player currentPlayer) {
        game.useArtifactCard(artifactCard, currentPlayer);
    }

    public void swapRight(IngredientCard ingredientCard){
       game.swapRight(ingredientCard);

    }

    public void swapLeft(IngredientCard ingredientCard){
        game.swapLeft(ingredientCard);

    }
}
