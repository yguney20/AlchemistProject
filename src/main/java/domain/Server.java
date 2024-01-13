package domain;
import com.google.gson.Gson;

import domain.controllers.GameController;
import domain.controllers.LoginController;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class Server {
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private boolean gameStarted = false; // Flag to indicate if the game has started
    private Set<String> playerNames = new HashSet<>();
    private Set<String> avatarPaths = new HashSet<>();
    private LoginController loginController = LoginController.getInstance();
    private GameController gameController = GameController.getInstance();

    // Constructor to initialize server with a specific port
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    // Main logic to execute the server
    public void execute() {
        System.out.println("Waiting for players...");

        // Continuously listen for new clients until the game is started
        while (!gameStarted) {
            try {
                // Accept new client connections
                Socket socket = serverSocket.accept();
                System.out.println("New player connected: " + socket.getInetAddress().getHostAddress());

                // Create and start a new ClientHandler thread for each connected client
                ClientHandler newUser = new ClientHandler(socket, this);
                clients.add(newUser);
                newUser.start();

                // If at least two clients are connected, notify the host to start the game
                if (clients.size() >= 2) {
                    notifyHostToStartGame();
                }

            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Broadcast a message to all clients except the sender
    void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler aClient : clients) {
            if (aClient != excludeUser) {
                aClient.sendMessage(message);
            }
        }
    }

    // Overloaded broadcast method for sending to all clients
    void broadcast(String message) {
        System.out.println("Broadcasting message: " + message);
        for (ClientHandler aClient : clients) {
            aClient.sendMessage(message);
        }
    }

    void broadcastPlayerList() {
        System.out.println("Broadcasting Start"); // Debug print
        List<String> playerNames = clients.stream()
                                        .map(ClientHandler::getClientName)
                                        .collect(Collectors.toList());
        String playerListJson = new Gson().toJson(playerNames);
        broadcast("PLAYER_LIST:" + playerListJson);

    }

    // Notify the host to start the game
    void notifyHostToStartGame() {
        for (ClientHandler aClient : clients) {
            if (aClient.isHost()) {
                aClient.sendMessage("READY_TO_START");
            }
        }
    }

    // Remove a client from the list and close its socket
    void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcastPlayerList();
        System.out.println("Player left: " + client.getClientName());
    }
    // A method to send current players and their statuses
        void sendPlayerStatus() {
            String playerStatusJson = createPlayerStatusJson();
            broadcast(playerStatusJson);
        }

        String createPlayerStatusJson() {
            Map<String, String> playerStatuses = new HashMap<>();
            for (ClientHandler client : clients) {
                playerStatuses.put(client.getClientName(), client.isReady() ? "Ready" : "Not Ready");
            }
            return new Gson().toJson(playerStatuses);
        }

        public void broadcastPlayerStatus() {
            // Optionally, you can broadcast the status of all players
            // This can be useful for updating UIs or for debugging
            String statusUpdate = clients.stream()
                .map(client -> client.getClientName() + ": " + (client.isReady() ? "Ready" : "Not Ready"))
                .collect(Collectors.joining(", "));
            broadcast("PLAYER_STATUS_UPDATE:" + statusUpdate);
        }

        public void broadcastGameState() {
            GameState gameState = gameController.getGameState();
            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("GAME_STATE:" + gameStateJson);
            } else {
                System.err.println("Error: GameState is not ready for broadcasting.");
            }
        }

        public void broadcastInitialState() {
            String initialStateJson = new Gson().toJson(gameController.getInitialState());
            broadcast("GAME_STATE:" + initialStateJson);
        }

        // public void broadcastupdateGameState(GameState gameState) {
        //     String gameStateJson = new Gson().toJson(gameState);
        //     broadcast("GAME_STATE:" + gameStateJson);
        // }
        
        
        
        void sendPlayerList() {
            // Extract just the player names into a list
            List<String> playerNames = clients.stream()
            .map(ClientHandler::getClientName)
            .collect(Collectors.toList());

            // Convert the list of player names to JSON
            String playerListJson = new Gson().toJson(playerNames);
            broadcast("PLAYER_LIST:" + playerListJson);

            // For debugging
            System.out.println("Server: Broadcasted player list - " + playerListJson);
        }
        
        public boolean checkAllPlayersReady() {
            boolean allReady = clients.stream().allMatch(ClientHandler::isReady);
            return allReady;
        }

        public synchronized boolean isUniquePlayer(String playerName, String avatarPath) {
            return playerNames.add(playerName) && avatarPaths.add(avatarPath);
        }

        public List<ClientHandler> getClients() {
            return clients;
        }


    // Main method to start the server
    public static void main(String[] args) {
        int port = 6666; // Set your port here
        try {
            Server server = new Server(port);
            server.execute();
        } catch (IOException e) {
            System.out.println("Cannot start the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
     
    // Inner class: ClientHandler
    class ClientHandler extends Thread {
        private Socket socket;
        private Server server;
        private PrintWriter writer;
        private BufferedReader reader;
        private String clientName;
        private String clientAvatar;
        private boolean isHost;
        private boolean isReady = false; 

        // Constructor for ClientHandler
        public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;
            isHost = server.getClients().isEmpty();

            try {
                // Set up input and output streams for communication with the client
                InputStream input = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
                
            } catch (IOException ex) {
                System.out.println("Error in ClientHandler: " + ex.getMessage());
                ex.printStackTrace();
            }

            
            
        }
        
        public boolean isReady() {
            return isReady; // Return the actual state of isReady
        }

        // Main logic for handling communication with a client
        public void run() {
            try {
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    jsonBuilder.append(line);
                }

                String json = jsonBuilder.toString();
                Map<String, String> playerInfo = new Gson().fromJson(json, Map.class);
                clientName = playerInfo.get("playerName");
                String avatarPath = playerInfo.get("avatarPath");



                if (!server.isUniquePlayer(clientName, avatarPath)) {
                    writer.println("DUPLICATE");
                    closeConnection();
                    return;
                }
                server.broadcastPlayerList();

                if (clients.size() == 1) {
                    isHost = true;
                }

                processClientMessages();
            } catch (IOException ex) {
                System.out.println("Error in ClientHandler: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        private void processClientMessages() {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println("Received message from client " + clientName + ": " + serverMessage); // Debug print
                    processMessage(serverMessage);
                }
            } catch (IOException ex) {
                System.out.println("Error in ClientHandler: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        private void processMessage(String message) {
            if (message == null || message.trim().isEmpty()) {
                return; // Ignore empty or null messages
            }
            Map<String, Object> messageMap = new Gson().fromJson(message, Map.class);
            System.out.println("Processing message from client: " + clientName + " - " + message); // Debug print

            if (messageMap.containsKey("action")) {
                String action = (String) messageMap.get("action");
        
                switch (action) {
                    case "playerReady":
                        isReady = true;
                        server.broadcastPlayerStatus();
                        break;
        
                    case "areAllPlayersReady":
                        boolean allReady = server.checkAllPlayersReady();
                        sendMessage("ALL_PLAYERS_READY:" + allReady);
                        break;

                    case "playerName":
                        String playerName = (String) messageMap.get("playerName");
                        String avatarPath = (String) messageMap.get("avatarPath");
                        // Update this client's name and other info as needed
                        this.clientAvatar = avatarPath;
                        this.clientName = playerName;
                        // Broadcast updated player list
                        server.broadcastPlayerList(); 
                        break;
                    case "getConnectedPlayers":
                        List<String> connectedPlayers = getClients().stream()
                                .map(ClientHandler::getClientName)
                                .collect(Collectors.toList());
                        String playerListJson = new Gson().toJson(connectedPlayers);
                        sendMessage("PLAYER_LIST:" + playerListJson);
                        break;

                    case "startGame":
                        System.out.println("Start Game");
                        loginController.initializeGame(); // Initialize the game state
                        // You might also want to send the initial game state to all clients
                        server.broadcastGameState();
                        break;

                    case "forageForIngredient":
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        System.out.println(" Forage for ingredient");
                        gameController.forageForIngredient(playerId);
                        break;
                }
            }
    
        }
        
        private void closeConnection() {
            server.removeClient(this);
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error while closing the socket: " + ex.getMessage());
            }
        }

        void sendMessage(String message) {
            writer.println(message);
        }

        // Getters for client's name and host status
        String getClientName() {
            return clientName;
        }

        public boolean isHost() {
            return isHost;
        }

    }
}