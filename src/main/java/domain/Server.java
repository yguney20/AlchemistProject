package domain;
import com.google.gson.Gson;

import domain.controllers.GameController;
import domain.controllers.LoginController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.GameObjectFactory;
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
    //private GameController gameController = GameController.getInstance();
    private List<Player> serverPlayerList = new ArrayList<>(); 
    private Game game = Game.getInstance();

    

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
        System.out.println("Broadcasting player list");
    
        // Create a list of player info maps
        List<Map<String, String>> playerInfoList = new ArrayList<>();
        for (ClientHandler client : clients) {
            Map<String, String> playerInfo = new HashMap<>();
            playerInfo.put("playerName", client.getClientName());
            playerInfo.put("avatarPath", client.getClientAvatar());
            playerInfoList.add(playerInfo);
        }
    
        // Convert the list to JSON and broadcast it
        String playerListJson = new Gson().toJson(playerInfoList);
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
            String statusUpdate = clients.stream()
                .map(client -> client.getClientName() + ": " + (client.isReady() ? "Ready" : "Not Ready"))
                .collect(Collectors.joining(", "));
            broadcast("PLAYER_STATUS_UPDATE:" + statusUpdate);
        }

        public void broadcastGameState() {
            GameState gameState = game.getGameState();
            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("GAME_STATE:" + gameStateJson);
            } else {
                System.err.println("Error: GameState is not ready for broadcasting.");
            }
        }

        public void broadcastStartGame() {
            GameState gameState = game.getGameState();
            System.out.println("Debug: GameState before broadcasting - " + gameState);
            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("START_GAME:" + gameStateJson);
                System.out.println("Debug: Broadcasting START_GAME withGameState");
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

        public void broadcastPauseGame(GameState gameState) {
            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("GAME_PAUSED:" + gameStateJson);
            } else {
                System.err.println("Error: GameState is not ready for broadcasting.");
            }
        }

        public void broadcastResumeGame() {
            GameState gameState = game.getGameState();
            if (gameState != null && gameState.isInitialized()) { // assuming you have an isInitialized() method
                String gameStateJson = new Gson().toJson(gameState);
                broadcast("GAME_RESUMED" + gameStateJson);
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

        private synchronized void addPlayerToList(String playerName, String avatarPath) {
            boolean playerExists = serverPlayerList.stream()
                .anyMatch(player -> player.getNickname().equals(playerName));
            
            System.out.println("Attempting to add player: " + playerName + " Exists: " + playerExists);
        
            if (!playerExists) {
                Player newPlayer = new Player(playerName, avatarPath);
                serverPlayerList.add(newPlayer);
                System.out.println("Added new player: " + playerName);
            } else {
                System.out.println("Player already exists: " + playerName);
            }
        }

    public void pauseGame(String pausingPlayerName) {
        game.getGameState().setPaused(true);
        broadcast("GAME_PAUSED:" + pausingPlayerName);
    }

    public void resumeGame() {
        game.getGameState().setPaused(false);
        broadcast("GAME_RESUMED");
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
        private int clientId;
        private int clientCounter = 0;

        // Constructor for ClientHandler
        public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;
            isHost = server.getClients().isEmpty();
            clientId  = clientCounter++;

            game = Game.getInstance();

            if(isHost){
                isReady = true;
            }

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
                clientName = playerName;
                this.clientAvatar = avatarPath;
                System.out.println("Player registered: " + playerName);
        
                server.addPlayerToList(playerName, avatarPath); // Add player here after uniqueness check
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
                        if (messageMap.containsKey("isReady")) {
                            Boolean isReady = (Boolean) messageMap.get("isReady");
                            if (isReady != null) {
                                this.isReady = isReady;
                            }
                        }
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
                        if (!server.isGameStarted()) {
                            server.setGameStarted(true); // Set the game as started
                            game.initializeGame(); // Initialize the game
                            server.broadcastStartGame(); // Broadcast the start of the game
                        }
                        break;
                    case "updateState":
                        game.updateState();
                        server.broadcastGameState();
                        break;

                    case "forageForIngredient":
                        System.out.println("C");
                        String currentPlayerName = game.getCurrentPlayer().getNickname();
                        if(clientName.equals(currentPlayerName)){
                            int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                            System.out.println("Forage for ingredient");
                            System.out.println("D");
                            game.forageForIngredient(playerId);
                            broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        
                        break;
                     case "buyArtifactCard":
                        currentPlayerName = game.getCurrentPlayer().getNickname();
                        if(clientName.equals(currentPlayerName)){
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int cardId = Integer.parseInt((String) messageMap.get("cardId"));
                        game.buyArtifactCard(playerId, cardId);
                        broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        break;

                    case "transmuteIngredient":
                    currentPlayerName = game.getCurrentPlayer().getNickname();
                    if(clientName.equals(currentPlayerName)){
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int ingredientId = Integer.parseInt((String) messageMap.get("ingredientId"));
                        game.transmuteIngredient(playerId, ingredientId);
                        broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        break;

                    case "sellPotion":
                        currentPlayerName = game.getCurrentPlayer().getNickname();
                        if(clientName.equals(currentPlayerName)){
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int cardId1 = Integer.parseInt((String) messageMap.get("cardId1"));
                        int cardId2 = Integer.parseInt((String) messageMap.get("cardId2"));
                        String guarantee = (String) messageMap.get("guarantee");
                        game.sellPotion(playerId, cardId1, cardId2, guarantee);
                        broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        break;

                    case "publishTheory":
                        currentPlayerName = game.getCurrentPlayer().getNickname();
                        if(clientName.equals(currentPlayerName)){
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int ingredientIdPub = Integer.parseInt((String) messageMap.get("ingredientId"));
                        int moleculeId = Integer.parseInt((String) messageMap.get("moleculeId"));
                        game.publishTheory(playerId, ingredientIdPub, moleculeId);
                        broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        break;

                    case "debunkTheory":
                        currentPlayerName = game.getCurrentPlayer().getNickname();
                        if(clientName.equals(currentPlayerName)){
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int publicationCardId = Integer.parseInt((String) messageMap.get("publicationCardId"));
                        Molecule.Component component = Molecule.Component.valueOf((String) messageMap.get("componentId"));
                        game.debunkTheory(playerId, publicationCardId, component);
                        broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        break;

                    case "useArtifactCard":
                        int playerId = Integer.parseInt((String) messageMap.get("playerId"));
                        int cardId = Integer.parseInt((String) messageMap.get("cardId"));
                        game.useArtifactCardById(cardId, playerId);
                        broadcastGameState();
                        break;

                    case "makeExperiment":
                        currentPlayerName = game.getCurrentPlayer().getNickname();
                        if(clientName.equals(currentPlayerName)){
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
                        broadcastGameState();
                        }else {
                            sendMessage("ERROR: Not your turn");
                        }
                        break;
                    case "pauseGame":
                        // Handling pause game request
                        String pausingPlayerName = clientName; // The client who requested the pause
                        server.pauseGame(pausingPlayerName);
                        break;

                    case "resumeGame":
                        // Handling resume game request
                        server.resumeGame();
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

        String getClientAvatar() {
            return clientAvatar;
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

        int getClientId() {
            return clientId;
        }


    }


}