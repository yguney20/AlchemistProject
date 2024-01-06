package domain.controllers;

import domain.Client;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class OnlineGameAdapter implements GameCommunication {
	
	private Client client;
	private Gson gson;


    public OnlineGameAdapter(String host, int port) {
        this.client = new Client(host, port);
		this.gson = new Gson();
    }	

    public boolean connect() {
        return client.connect(); // Attempt to connect using the Client class
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
	public void receiveUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame() {
		Map<String, String> message = new HashMap<>();
        message.put("action", "endGame");
        client.sendMessage(gson.toJson(message));
		
	}

	 // Forage for Ingredient
	public void forageForIngredient(String playerId) {
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

	 // Transmute Ingredient
	 public void transmuteIngredient(String playerId, String ingredientId) {
        Map<String, String> actionDetails = new HashMap<>();
        actionDetails.put("playerId", playerId);
        actionDetails.put("ingredientId", ingredientId);
        actionDetails.put("action", "transmuteIngredient");
        sendAction("transmuteIngredient", actionDetails);
    }

	// Make Experiment
    public void makeExperiment(String playerId, String firstCardId, String secondCardId, boolean student) {
        Map<String, String> actionDetails = new HashMap<>();
        actionDetails.put("playerId", playerId);
        actionDetails.put("firstCardId", firstCardId);
        actionDetails.put("secondCardId", secondCardId);
        actionDetails.put("student", String.valueOf(student));
        actionDetails.put("action", "makeExperiment");
        sendAction("makeExperimen", actionDetails);
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
	public void debunkTheory(String playerId, String publicationCardId, String componentId) {
		Map<String, String> actionDetails = new HashMap<>();
		actionDetails.put("playerId", playerId);
		actionDetails.put("publicationCardId", publicationCardId);
		actionDetails.put("componentId", componentId);
		actionDetails.put("action", "debunkTheory");
		sendAction("debunkTheory", actionDetails);
	}

}
