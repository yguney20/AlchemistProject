package domain.controllers;

import domain.Client;
import domain.Game;
import domain.GameState;
import domain.gameobjects.PotionCard;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class OnlineGameAdapter implements GameCommunication {
	
	private Client client;
	private Gson gson;
	private final Game game;


    public OnlineGameAdapter(String host, int port) {
        this.client = new Client(host, port);
		this.gson = new Gson();
		game = Game.getInstance();
    }	

    public boolean connect() {
        return client.connect(); // Attempt to connect using the Client class
    }

	public void disconnect() {
		client.disconnect(); // Disconnect using the Client class
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
	public String receiveUpdate() {
		try {
				// Receive message from the server
				String response = client.receiveMessage();
				if (response != null && !response.isEmpty()) {
					// Log the response for debugging
					System.out.println("Response received: " + response);
					
					GameState updatedState;
					try {
						// Parse the response into a GameState object
						updatedState = gson.fromJson(response, GameState.class);
					} catch (JsonSyntaxException e) {
						System.err.println("Error parsing GameState: " + e.getMessage());

						return null;
					}
					// Update the game state
					Game.getInstance().updateGameState(updatedState);
					return response;
				}
			} catch (Exception e) {
				System.err.println("Error receiving update: " + e.getMessage());
			}
		return null;
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

		// Wait for the server's response
		String response = receiveUpdate();

		// Handle the response
		if (response != null) {
			GameState updatedState = gson.fromJson(response, GameState.class);
			// Update the game state or UI based on `updatedState`
		} else {
			// Handle the case where no response is received
			System.err.println("No response received for forageForIngredient action.");
		}
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
    public PotionCard makeExperiment(int playerId, int firstCardId, int secondCardId, boolean student) {
    // Create a request payload
    Map<String, String> actionDetails = new HashMap<>();
    actionDetails.put("playerId", String.valueOf(playerId));
    actionDetails.put("firstCardId", String.valueOf(firstCardId));
    actionDetails.put("secondCardId", String.valueOf(secondCardId));
    actionDetails.put("student", String.valueOf(student));
    actionDetails.put("action", "makeExperiment");

    // Send the request
    sendAction("makeExperiment",actionDetails);

    // Wait for and handle the response (asynchronously)
		String response = receiveUpdate(); // This needs to be handled asynchronously
		if (response != null) {
			// Update the game state based on the response
			GameState updatedState = gson.fromJson(response, GameState.class);
			game.updateGameState(updatedState);

			// Return the new potion card from the updated state
			return updatedState.getLastCreatedPotion();
		} else {
			// Handle the case where no response is received
			System.err.println("No response received for makeExperiment action.");
			return null;
		}
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

}
