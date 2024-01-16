package domain;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private boolean gameStarted = false; // Flag to indicate if the game has started

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
                System.out.println("New player connected");

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
        System.out.println("Player left: " + client.getClientName());
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
        private boolean isHost;

        // Constructor for ClientHandler
        public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;
            isHost = false;

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
                // Read the client's name as the first message
                clientName = reader.readLine();  
                if (clients.size() == 1) { // First connected client is the host
                    isHost = true;
                }

                String serverMessage;

                // Continuously read messages from the client and broadcast them
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(clientName + ": " + serverMessage);
                    server.broadcast(clientName + ": " + serverMessage, this);
                }
            } catch (IOException ex) {
                System.out.println("Error in ClientHandler: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                // Remove client from the list and close its socket when done
                server.removeClient(this);
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Error while closing the socket: " + ex.getMessage());
                }
            }
        }

        // Method to send a message to the client
        void sendMessage(String message) {
            writer.println(message);
        }

        // Getters for client's name and host status
        String getClientName() {
            return clientName;
        }

        boolean isHost() {
            return isHost;
        }
    }
}