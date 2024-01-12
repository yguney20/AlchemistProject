package domain;

import java.io.*;
import java.net.*;
import java.net.http.WebSocket.Listener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.controllers.OnlineGameAdapter;
import ui.swing.screens.HostGameScreen;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;

public class Client {
    private String hostname; // The IP address or hostname of the server
    private int port; // The port number the server is listening on
    private BufferedReader reader;
    private Socket socket; // The socket connecting to the server
    private PrintWriter writer; // To send messages to the server
    private static PlayerListUpdateListener listener;
    private boolean listening = true;

    // Constructor to initialize the client with the server's host and port
    public Client(String hostname, int port, PlayerListUpdateListener listener) {
        this.hostname = hostname;
        this.port = port;
        this.listener = listener;
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
    
            startListening(); // Start listening for messages
            return true;
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
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


    public void sendPlayerInfo(String playerName, String avatarPath) {
        Map<String, String> playerInfo = new HashMap<>();
        playerInfo.put("playerName", playerName);
        playerInfo.put("avatarPath", avatarPath);
        String jsonPlayerInfo = new Gson().toJson(playerInfo);
        System.out.println("Sending player info: " + jsonPlayerInfo);
        sendMessage(jsonPlayerInfo);
    }

    // Main method for testing the client
    public static void main(String[] args) {
        OnlineGameAdapter adapter = new OnlineGameAdapter("localhost", 6666, listener);
        if (adapter.connect()) {
            
            System.out.println("Client successfully connected to server");

            // Create an instance of OnlineGameAdapter

            // Example: Simulate a player foraging for an ingredient
            adapter.forageForIngredient("1");  // Assuming '1' is a player ID

            // Example: Simulate a player buying an artifact card
            adapter.buyArtifactCard("1", "101");  // Assuming '1' is a player ID and '101' is a card ID

            // Add more actions as needed...

            // Disconnect when done
            adapter.disconnect();
        }
    }

    public void handleServerMessage(String message) {
        System.out.println("Client received message: " + message);

        if (message.equals("DUPLICATE")) {
            // Notify the listener that the chosen name or avatar is not unique
            if (listener != null) {
                listener.onDuplicatePlayer();
            }
        } else if (message.startsWith("PLAYER_LIST:")) {
            System.out.println("Goes into the Player List serves message");
            String jsonList = message.substring("PLAYER_LIST:".length());
            // Parse the JSON List of player names as a List<String>
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> playerNames = new Gson().fromJson(jsonList, listType);
            System.out.println("Parsed player list: " + playerNames);

            
            if (listener != null) {
                System.out.println("Updating player list: " + playerNames);
                listener.onPlayerListUpdate(playerNames);
            }
        
        }else if (message.startsWith("ALL_PLAYERS_READY:")) {
            boolean allPlayersReady = Boolean.parseBoolean(message.split(":")[1].trim());
            System.out.println("Client: All players ready status received: " + allPlayersReady); // Debug print
            if (listener != null) {
                listener.onAllPlayersReady(allPlayersReady);
            }
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

    public void setPlayerListUpdateListener(PlayerListUpdateListener listener) {
        this.listener = listener;
    }

    public void stopListening() {
        listening = false;
    }

    public void simulateAnotherPlayer() {
        // Assuming localhost and port 6666 for simplicity
        String hostname = "localhost";
        int port = 6666;
    
        // Create a new client for the simulated player
        Client simulatedPlayer = new Client(hostname, port, this.listener); // Use a proper listener if needed
        // Connect the simulated player to the server
        if (simulatedPlayer.connect()) {
            System.out.println("Simulated player connected!");
    
            // Set player name and avatar path for the simulated player
            String playerName = "mert2";
            String avatarPath = "/ui/swing/resources/images/avatar/a3.jpg"; // Placeholder path

            System.out.println("Simulated player sending info: Name - " + playerName + ", Avatar - " + avatarPath);
    
            // Send player info to the server
            simulatedPlayer.sendPlayerInfo(playerName, avatarPath);
    
            // Additional actions for the simulated player can be added here if needed
        } else {
            System.err.println("Failed to connect the simulated player.");
        }
    }
}