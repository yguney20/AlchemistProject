package domain;
import com.google.gson.Gson;

import domain.controllers.GameController;
import domain.controllers.LoginController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.Molecule;
import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;

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

    private static Server instance;

    // Constructor to initialize server with a specific port
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public static Server getInstance(int port) throws IOException {
        if (instance == null) {
            instance = new Server(port);
        }
        return instance;
    }

    public List<String> getConnectedPlayerNames() {
        return clients.stream().map(ClientHandler::getClientName).collect(Collectors.toList());
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

        public void broadcastStartGame() {
            GameState gameState = gameController.getGameState();
            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("START_GAME:" + gameStateJson);
            } else {
                System.err.println("Error: Start game is not ready for broadcasting.");
            }
        }
        public void broadcastGameState(GameState gameState) {

            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("GAME_STATE:" + gameStateJson);
            } else {
                System.err.println("Error: GameState is not ready for broadcasting.");
            }
        }


        public void sendPlayerList() {
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

        public boolean areAllPlayersReady() {
            return clients.stream().allMatch(ClientHandler::isReady);
        }

        public boolean isGameStarted() {
            return gameStarted;
        }

        public void setGameStarted(boolean gameStarted) {
            this.gameStarted = gameStarted;
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
         private final Game game;

        // Constructor for ClientHandler
        public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;
            isHost = server.getClients().isEmpty();
            game = Game.getInstance();

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
        

        // Main logic for handling communication with a client
        public void run() {
            try {
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    jsonBuilder.append(line);
                }
                

                String json = jsonBuilder.toString();
                processPlayerInfo(json);
            

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

        private void processPlayerInfo(String json) {
            Map<String, String> playerInfo = new Gson().fromJson(json, Map.class);
            String playerName = playerInfo.get("playerName");
            String avatarPath = playerInfo.get("avatarPath");
        
            if (!server.isUniquePlayer(playerName, avatarPath)) {
                writer.println("DUPLICATE");
                System.out.println("Duplicate player detected: " + playerName);
            } else {
                writer.println("PLAYER_CONFIRMED");
                clientName = playerName;
                this.clientAvatar = avatarPath;
                System.out.println("Player registered: " + playerName);
                server.broadcastPlayerList();
                if (server.checkAllPlayersReady()) {
                    server.notifyHostToStartGame();
                }
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
                    case "toggleReady":
                        isReady = !isReady; // Toggle the ready status
                        server.broadcastPlayerStatus(); // Update all clients with the new status
                        break;

                    case "startGame":
                        if (!server.isGameStarted()) {
                            server.setGameStarted(true); // Set the game as started
                            game.initializeGame(); // Initialize the game
                            server.broadcastStartGame(); // Broadcast the start of the game
                        }
                        break;

                    case "forageForIngredient":
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        System.out.println("Forage for ingredient");
                        game.forageForIngredient(playerId);
        
                        // Only broadcast the state once after processing the action
                        server.broadcastGameState();
                        break;
                     case "buyArtifactCard":
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int cardId = Integer.parseInt((String) messageMap.get("cardId"));
                        game.buyArtifactCard(playerId, cardId);
                        server.broadcastGameState(); // Update all clients with the new game state
                        break;

                    case "transmuteIngredient":
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int ingredientId = Integer.parseInt((String) messageMap.get("ingredientId"));
                        game.transmuteIngredient(playerId, ingredientId);
                        server.broadcastGameState();
                        break;

                    case "sellPotion":
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int cardId1 = Integer.parseInt((String) messageMap.get("cardId1"));
                        int cardId2 = Integer.parseInt((String) messageMap.get("cardId2"));
                        String guarantee = (String) messageMap.get("guarantee");
                        game.sellPotion(playerId, cardId1, cardId2, guarantee);
                        server.broadcastGameState();
                        break;

                    case "publishTheory":
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int ingredientIdPub = Integer.parseInt((String) messageMap.get("ingredientId"));
                        int moleculeId = Integer.parseInt((String) messageMap.get("moleculeId"));
                        game.publishTheory(playerId, ingredientIdPub, moleculeId);
                        server.broadcastGameState();
                        break;

                    case "debunkTheory":
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int publicationCardId = Integer.parseInt((String) messageMap.get("publicationCardId"));
                        Molecule.Component component = Molecule.Component.valueOf((String) messageMap.get("componentId"));
                        game.debunkTheory(playerId, publicationCardId, component);
                        server.broadcastGameState();
                        break;

                    case "useArtifactCard":
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        cardId = Integer.parseInt((String) messageMap.get("cardId"));
                        game.useArtifactCardById(cardId, playerId);
                        server.broadcastGameState();
                        break;

                     case "makeExperiment":
                        // Extract details from messageMap
                        playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int firstCardId = Integer.parseInt((String) messageMap.get("firstCardId"));
                        int secondCardId = Integer.parseInt((String) messageMap.get("secondCardId"));
                        boolean student = Boolean.parseBoolean((String) messageMap.get("student"));

                        // Perform the experiment
                        PotionCard potionCard = game.makeExperiment(playerId, firstCardId, secondCardId, student);

                        // Send the result back to the client
                        String potionCardJson = new Gson().toJson(potionCard);
                        sendMessage("EXPERIMENT_RESULT:" + potionCardJson);
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

        public boolean isReady() {
            return isReady; // Return the actual state of isReady
        }

        public void setReady(boolean readyStatus) {
            this.isReady = readyStatus;
        }


    }

    

    

   
}