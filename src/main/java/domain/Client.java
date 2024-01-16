package domain;

import java.io.*;
import java.net.*;
import java.net.http.WebSocket.Listener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.controllers.GameController;
import domain.controllers.LoginController;
import domain.controllers.OnlineGameAdapter;
import domain.gameobjects.PotionCard;
import domain.interfaces.EventListener;
import ui.swing.screens.BoardScreen;
import ui.swing.screens.HostGameScreen;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;
import ui.swing.screens.screencontrollers.BoardScreenController;
import ui.swing.screens.screencontrollers.*;

public class Client {
    private String hostname; // The IP address or hostname of the server
    private int port; // The port number the server is listening on
    private BufferedReader reader;
    private Socket socket; // The socket connecting to the server
    private PrintWriter writer; // To send messages to the server
    private EventListener eventListener;
    private boolean listening = true;
    private BoardScreen boardScreen;
    private boolean isConnected = false;

    private LoginController loginController = LoginController.getInstance();


    // Constructor to initialize the client with the server's host and port
    public Client(String hostname, int port, EventListener listener) {
        this.hostname = hostname;
        this.port = port;
        this.eventListener = listener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
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

    public void sendPlayerInfo(String playerName, String avatarPath) {
        Map<String, String> playerInfo = new HashMap<>();
        playerInfo.put("playerName", playerName);
        playerInfo.put("avatarPath", avatarPath);
        String jsonPlayerInfo = new Gson().toJson(playerInfo);
        System.out.println("Sending player info: " + jsonPlayerInfo);
        sendMessage(jsonPlayerInfo);
    }

    public void setPlayerReady() {
        sendMessage("{\"action\":\"playerReady\"}");
        System.out.println("Sent player ready message for " + hostname);
    }

    public void togglePlayerReady() {
        sendMessage("{\"action\":\"toggleReady\"}");
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

    public boolean isConnected() {
        return isConnected;
    }

    private Map<String, Consumer<PotionCard>> callbacks = new HashMap<>();


    public void addCallback(String action, Consumer<PotionCard> callback) {
        callbacks.put(action, callback);
    }


    public void handleServerMessage(String message) {
        System.out.println("Client received message: " + message);
    
        if (message.startsWith("PLAYER_LIST:")) {
            String json = message.substring("PLAYER_LIST:".length());
            List<String> playerNames = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
            System.out.println("Updating player list with: " + playerNames);
            
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
           
            SwingUtilities.invokeLater(() -> {
    
                openBoardScreen(gameState);
            });
        } else if (message.startsWith("EXPERIMENT_RESULT:")) {
            String json = message.substring("EXPERIMENT_RESULT:".length());
            PotionCard potionCard = new Gson().fromJson(json, PotionCard.class);

            // Call the callback function with potionCard
            // This assumes you have a way to pass the callback to this method
            // For example, you could use a Map to store callbacks based on request types
            
            
            Consumer<PotionCard> callback = callbacks.get("makeExperiment");
            if (callback != null) {
                callback.accept(potionCard);
            }
        } else if (message.startsWith("PLAYER_STATUS_UPDATE:")) {
            // New code to handle player status updates
            String statusUpdate = message.substring("PLAYER_STATUS_UPDATE:".length());
            SwingUtilities.invokeLater(() -> {
                if (eventListener != null) {
                    eventListener.onPlayerStatusUpdate(statusUpdate);
                } else {
                    System.out.println("EventListener is null");
                }
            });
        } else if (message.startsWith("YOUR_STATUS:")) {
            String yourStatus = message.substring("YOUR_STATUS:".length());
            SwingUtilities.invokeLater(() -> {
                if (eventListener != null) {
                    eventListener.onPlayerStatusUpdate(yourStatus);
                }
            });
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

    private void openBoardScreen(GameState gameState) {
        boardScreen = new BoardScreen();
        boardScreen.display();
        BoardScreenController boardController = boardScreen.getController();
    
        if (boardController != null) {
            boardController.updateGameState(gameState);
        } else {
            System.err.println("Error: BoardScreenController is null.");
            // Additional error handling here
        }
    }

    public void simulateAnotherPlayer() {
        String hostname = "localhost";
        int port = 6666;
    
        // Create a new client for the simulated player
        Client simulatedPlayer = new Client(hostname, port, new SimulatedPlayerEventListener());
        if (simulatedPlayer.connect()) {
            System.out.println("Simulated player connected!");
    
            // Set unique player name and avatar path for the simulated player
            String playerName = "mert2";
            String avatarPath ="/ui/swing/resources/images/avatar/a3.jpg";
    
            System.out.println("Simulated player sending info: Name - " + playerName + ", Avatar - " + avatarPath);
            simulatedPlayer.sendPlayerInfo(playerName, avatarPath);
            loginController.createPlayer(playerName, avatarPath);

            // You can add additional automated actions for the simulated player here

            simulatedPlayer.setPlayerReady();
        } else {
            System.err.println("Failed to connect the simulated player.");
        }
    }
    
    // Inner class for handling events for the simulated player
    class SimulatedPlayerEventListener implements EventListener {
        @Override
        public void onMessageReceived(String message) {
            // Handle messages from the server for the simulated player
            // This can be as simple or complex as needed for your testing
            System.out.println("Simulated player received message: " + message);
        }
        @Override
        public void onPlayerListUpdate(List<String> playerNames) {
            // Handle messages from the server for the simulated player
            // This can be as simple or complex as needed for your testing
            System.out.println("Simulated player received player list update: " + playerNames);
           
        }
        @Override
        public void onPlayerStatusUpdate(String statusUpdate) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'onPlayerStatusUpdate'");
        }
    }
   
}