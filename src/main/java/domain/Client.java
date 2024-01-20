package domain;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;
import domain.interfaces.EventListener;
import ui.swing.screens.scenes.*;
import ui.swing.screens.screencontrollers.BoardScreenController;


public class Client {
    private String hostname; // The IP address or hostname of the server
    private int port; // The port number the server is listening on
    private BufferedReader reader;
    private Socket socket; // The socket connecting to the server
    private PrintWriter writer; // To send messages to the server
    private EventListener eventListener;
    private boolean listening = true;
    private BoardScreen boardScreen;
    private MenuScreen menuScreen;
    private PauseScreen pauseScreen;
    private boolean isConnected = false;
    private List<Player> playerList = Player.getPlayerList(); 
    private GameController gameController = GameController.getInstance();
    private GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();
    private Map<String, Consumer<PotionCard>> callbacks = new HashMap<>();




    
    // Constructor to initialize the client with the server's host and port
    public Client(String hostname, int port, EventListener listener) {
        this.hostname = hostname;
        this.port = port;
        this.eventListener = listener;
    }

    // Connect to the server
    public boolean connect() {
        try {
            socket = new Socket(hostname, port);
            System.out.println("Connected to the game server");
            
            // Initialize reader and writer here
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            isConnected = true;
            startListening(); 
            return true;
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            isConnected = false;
            System.out.println("I/O Error: " + ex.getMessage());
        }
        return false;
    }

    // Send a message to the server
    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message + "\n");
        } else {
            System.err.println("Error: Attempted to send a message with no active connection.");
        }
    }
    

    // Method to receive a message from the server
    public String receiveMessage() {
        try {
            return reader.readLine(); // Read a line of text from the server
        } catch (IOException e) {
            System.err.println("Error receiving message from server: " + e.getMessage());
            return null;
        }
    }

    // Disconnect from the server
    public void disconnect() {
        try {
            if (socket != null) {
                socket.close(); // Close the socket connection
            }
            if (writer != null) {
                writer.close(); // Close the writer
            }
            System.out.println("Disconnected from the server");
        } catch (IOException ex) {
            System.out.println("Error while closing client: " + ex.getMessage());
        }
    }

    public void startListening() {
        new Thread(() -> {
            while (listening) {
                String message = receiveMessage();
                if (message != null) {
                    handleServerMessage(message);
                }
            }
        }).start();
    }

    public void stopListening() {
        listening = false;
    }

    //------- Get and Set related functionality 

    public boolean isConnected() {
        return isConnected;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void addCallback(String action, Consumer<PotionCard> callback) {
        callbacks.put(action, callback);
    }
    

    // ------ 
    public void setPlayerReady() {
        sendMessage("{\"action\":\"playerReady\"}");
    }

    public void sendPlayerReadiness(boolean isReady) {
        String readinessMessage = "{\"action\":\"playerReady\", \"isReady\":" + isReady + "}";
        sendMessage(readinessMessage);
    }


    // ---------- 

    // Send player info to the server
    public void sendPlayerInfo(String playerName, String avatarPath) {
        Map<String, String> playerInfo = new HashMap<>();
        playerInfo.put("playerName", playerName);
        playerInfo.put("avatarPath", avatarPath);
        String jsonPlayerInfo = new Gson().toJson(playerInfo);
        sendMessage(jsonPlayerInfo);
    }


    // Client side code to send pause/resume request
    public void sendPauseOrResumeRequest(boolean isPause) {
        String action = isPause ? "pauseGameRequest" : "resumeGameRequest";
        String message = "{\"action\":\"" + action + "\"}";
        sendMessage(message);
    }


    /*
     * This function handles the server messages accorind the message.
     */
    public void handleServerMessage(String message) {

        if (message.startsWith("PLAYER_LIST:")) {
            String json = message.substring("PLAYER_LIST:".length());
            Type type = new TypeToken<List<Map<String, String>>>() {}.getType();
            List<Map<String, String>> playerInfoList = new Gson().fromJson(json, type);
            
            // Extract player names for the UI update
            List<String> playerNames = new ArrayList<>();
            for (Map<String, String> playerInfo : playerInfoList) {
                playerNames.add(playerInfo.get("playerName"));
            }
    
            updateLocalPlayerList(playerInfoList);
            
            // Update UI using SwingUtilities.invokeLater
            SwingUtilities.invokeLater(() -> {
                if (eventListener != null) {
                    eventListener.onPlayerListUpdate(playerNames);
                } else {
                    System.out.println("EventListener is null");
                }
            });
        } else if (message.startsWith("START_GAME:")) {
            System.out.println("Game is started");
            String jsonState = message.substring("START_GAME:".length());
            GameState gameState = new Gson().fromJson(jsonState, GameState.class);
            gameController.setGameState(gameState);
        
            SwingUtilities.invokeLater(() -> {
    
                openBoardScreen(gameState);
            });
        } else if (message.startsWith("EXPERIMENT_RESULT:")) {
            String json = message.substring("EXPERIMENT_RESULT:".length());
            PotionCard potionCard = new Gson().fromJson(json, PotionCard.class);

            // Call the callback function with for retrieve potionCard
        
            Consumer<PotionCard> callback = callbacks.get("makeExperiment");
            if (callback != null) {
                callback.accept(potionCard);
            }
        } else if (message.startsWith("GAME_STATE:")) {
            String jsonState = message.substring("GAME_STATE:".length());
            GameState gameState = new Gson().fromJson(jsonState, GameState.class);
            
            // Adjust the game state
            gameController.setGameState(gameState);
            gameController.getGameState().setPublicationCards(gameState.gePublicationCards());
            gameController.getGameState().setPotionMap(gameState.getPotionMap());
        
            SwingUtilities.invokeLater(() -> {
                BoardScreenController boardController = BoardScreenController.getInstance();
                if (boardController != null) {
                    boardScreen.initializeJavaFXComponents();
                } else {
                    System.err.println("Error: BoardScreenController is null.");
                }
            });

        } else if (message.startsWith("GAME_PAUSED:")) {
            // Split the message to extract pausing player's name and GameState
            String[] parts = message.split(":", 3);
            if (parts.length >= 3) {
                String pausingPlayerName = parts[1];
                String jsonState = parts[2];
                GameState gameState = new Gson().fromJson(jsonState, GameState.class);
                gameController.setGameState(gameState);

                SwingUtilities.invokeLater(() -> {
                    openPauseScreen(pausingPlayerName);
                });
            }
        } else if (message.equals("GAME_RESUMED")) {
            // Assuming GAME_RESUMED message contains GameState information
            String jsonState = message.substring("GAME_RESUMED:".length());
            GameState gameState = new Gson().fromJson(jsonState, GameState.class);
            gameController.setGameState(gameState);

            SwingUtilities.invokeLater(this::closePauseScreen);
        }if (message.startsWith("ARTIFACT_BOUGHT:")) {
            String[] parts = message.split(":");
            if (parts.length == 3) {
                int playerId = Integer.parseInt(parts[1]);
                int artifactCardId = Integer.parseInt(parts[2]);
                // Handle the artifact card purchase
                handleArtifactPurchase(playerId, artifactCardId);
            }

        }
    }

    private void handleArtifactPurchase(int playerId, int artifactCardId) {
      
        Player player = gameController.getPlayerById(playerId);
        ArtifactCard artifactCard = gameController.getArtifactCardById(artifactCardId);

        // Add the artifact card to the player's inventory
        if (player != null && artifactCard != null) {
            player.addArtifactCard(artifactCard);
        } else {
            // Handle error: player or card not found
            System.err.println("Player or ArtifactCard not found for given IDs.");
        }
        requestUpdatedGameState();
    }

    private void updateLocalPlayerList(List<Map<String, String>> playerInfoList) {

        playerList.clear(); // Clear the existing list first
        
        for (Map<String, String> playerInfo : playerInfoList) {
            String playerName = playerInfo.get("playerName");
            String avatarPath = playerInfo.get("avatarPath");
            gameObjectFactory.createPlayer(playerName, avatarPath);
        }
    }

    private void openBoardScreen(GameState gameState) {
        System.out.println("Open Board screnn func içi: "+ gameState);
        boardScreen = new BoardScreen();
        boardScreen.display();
        BoardScreenController boardController = BoardScreenController.getInstance();
    
        if (boardController != null) {
            boardController.updateGameState(gameState);
        } else {
            System.err.println("Error: BoardScreenController is null.");
        }
    }
    

    // Method to display the pause screen
    private void openPauseScreen(String pausingPlayerName) {
        Game.getInstance().setPausedPlayer(pausingPlayerName);
        System.out.println(pausingPlayerName);
        pauseScreen = new PauseScreen(boardScreen, menuScreen.getInstance(boardScreen), pausingPlayerName);
        pauseScreen.display();

    }

    // Method to close the pause screen
    private void closePauseScreen() {
        if (pauseScreen != null) {
            Game.getInstance().setPausedPlayer("");
            pauseScreen.close();
        }
    }

    private void requestUpdatedGameState() {
        sendMessage("REQUEST_GAME_STATE");
     }


   
}