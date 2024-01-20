package domain.controllers;

import domain.Client;
import domain.Game;
import domain.GameState;
import domain.gameobjects.PotionCard;
import domain.interfaces.EventListener;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


public class OnlineGameAdapter implements GameActionHandler {
	
	private Client client;
	private Gson gson;
	private PlayerListUpdateListener updateListener;


	public OnlineGameAdapter (Client client){
		this.client = client;
		this.gson = new Gson();

	}

	public void sendMessage(String message) {
		client.sendMessage(message);
	}


	@Override
	public void startGame() {
		Map<String, String> message = new HashMap<>();
        message.put("action", "startGame");
        client.sendMessage(gson.toJson(message));
		
	}

	@Override
	public void sendAction(String actionType, Map<String, String> actionDetails) {
        actionDetails.put("action", actionType);
        client.sendMessage(gson.toJson(actionDetails));
    }

	@Override
	public void endGame() {
		Map<String, String> message = new HashMap<>();
        message.put("action", "endGame");
        client.sendMessage(gson.toJson(message));
		
	}


	// Forage for Ingredient
	public void forageForIngredient(String playerId) {
		System.out.println("B");
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", playerId);
		sendAction("forageForIngredient", actionDetails);

	}

	// Buy Artifact Card
    public void buyArtifactCard(String playerId, String cardId) {
        Map<String, String> actionDetails = new HashMap<>();
        actionDetails.put("playerId", playerId);
        actionDetails.put("cardId", cardId);
        actionDetails.put("action", "buyArtifactCard");
        sendAction("buyArtifactCard", actionDetails);
    }

	public void useArtifactCard(String playerId, String cardId) {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", playerId);
		actionDetails.put("cardId", cardId);
		actionDetails.put("action", "useArtifactCard");
		sendAction("useArtifactCard", actionDetails);
	}

	 // Transmute Ingredient
	 public void transmuteIngredient(String playerId, String ingredientId) {
        Map<String, String> actionDetails = new HashMap<>();
        actionDetails.put("playerId", playerId);
        actionDetails.put("ingredientId", ingredientId);
        actionDetails.put("action", "transmuteIngredient");
        sendAction("transmuteIngredient", actionDetails);
    }

	// Make Experiment
    public void makeExperiment(int playerId, int firstCardId, int secondCardId, boolean student ,Consumer<PotionCard> callback) {
    // Create a request payload
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", String.valueOf(playerId));
		actionDetails.put("firstCardId", String.valueOf(firstCardId));
		actionDetails.put("secondCardId", String.valueOf(secondCardId));
		actionDetails.put("student", String.valueOf(student));
		actionDetails.put("action", "makeExperiment");

		// Send the request
		client.addCallback("makeExperiment", callback); 
		sendAction("makeExperiment",actionDetails);

	}

	// Sell a Potion
	public void sellPotion(String playerId, String cardId1, String cardId2, String guarantee) {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", playerId);
		actionDetails.put("cardId1", cardId1);
		actionDetails.put("cardId2", cardId2);
		actionDetails.put("guarantee", guarantee);
		actionDetails.put("action", "sellPotion");
		sendAction("sellPotion", actionDetails);
	}

	// Publish a Theory
	public void publishTheory(String playerId, String ingredientId, String moleculeId) {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", playerId);
		actionDetails.put("ingredientId", ingredientId);
		actionDetails.put("moleculeId", moleculeId);
		actionDetails.put("action", "publishTheory");
		sendAction("publishTheory", actionDetails);
	}

	// Debunk a Theory
	public void debunkTheory(String playerId, String publicationCardId, domain.gameobjects.Molecule.Component component) {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", playerId);
		actionDetails.put("publicationCardId", publicationCardId);
		actionDetails.put("componentId", component.toString());
		actionDetails.put("action", "debunkTheory");
		sendAction("debunkTheory", actionDetails);
	}

	public void updateState() {
		Map<String, String> message = new HashMap<>();
		message.put("action", "updateState");
		client.sendMessage(gson.toJson(message));
	}

	public void sendReadySignal() {
        Map<String, String> actionDetails = new HashMap<>();
        actionDetails.put("action", "playerReady");
        client.sendMessage(gson.toJson(actionDetails));
    }

	public void sendPlayerInfo(String playerName, String avatarPath) {
		Map<String, String> playerInfo = new HashMap<>();
		playerInfo.put("playerName", playerName);
		playerInfo.put("avatarPath", avatarPath);
		client.sendMessage(gson.toJson(playerInfo));
	}

	
	public void areAllPlayersReady(Consumer<Boolean> callback) {
		new Thread(() -> {
			client.sendMessage("{\"action\":\"areAllPlayersReady\"}");
			String response = client.receiveMessage();
			if (response != null && response.startsWith("ALL_PLAYERS_READY:")) {
				boolean allReady = Boolean.parseBoolean(response.split(":")[1].trim());
				SwingUtilities.invokeLater(() -> {
					callback.accept(allReady);
				});
			} else {
				System.out.println("Debug: No valid response received for areAllPlayersReady");
			}
		}).start();
	}
	
	

	public List<String> getConnectedPlayers() {
    // Send a request to the server
		client.sendMessage("{\"action\":\"getConnectedPlayers\"}");

		// Wait for and process the server's response
		String response = client.receiveMessage();
		if (response != null && response.startsWith("PLAYER_LIST:")) {
			String jsonList = response.substring("PLAYER_LIST:".length());
			return new Gson().fromJson(jsonList, new TypeToken<List<String>>(){}.getType());
		}

		return Collections.emptyList();
	}	

	public void setPlayerListUpdateListener(PlayerListUpdateListener listener) {
		this.updateListener = listener;
	}


	public void sendPauseGameRequest() {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("action", "pauseGame");
		sendMessage(gson.toJson(actionDetails));
	}

	public void sendResumeGameRequest() {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("action", "resumeGame");
		sendMessage(gson.toJson(actionDetails));
	}

}