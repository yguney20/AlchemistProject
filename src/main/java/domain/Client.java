package domain;

import java.io.*;
import java.net.*;

import domain.controllers.OnlineGameAdapter;

public class Client {
    private String hostname; // The IP address or hostname of the server
    private int port; // The port number the server is listening on
    private Socket socket; // The socket connecting to the server
    private PrintWriter writer; // To send messages to the server

    // Constructor to initialize the client with the server's host and port
    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    // Connect to the server
    public boolean connect() {
        try {
            socket = new Socket(hostname, port); // Create a new socket connection
            System.out.println("Connected to the game server");

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true); // Set up PrintWriter for sending messages

            return true; // Return true if connection is successful
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage()); // Handle unknown host exception
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage()); // Handle IO exception
        }
        return false; // Return false if connection fails
    }

    // Send a message to the server
    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
        } else {
            System.err.println("Error: Attempted to send a message with no active connection.");
        }
    }
    

    // Method to receive a message from the server
    public String receiveMessage() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Blocking call, waits for a line of text from the server
            return reader.readLine(); 
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

    // Main method for testing the client
    public static void main(String[] args) {
        OnlineGameAdapter adapter = new OnlineGameAdapter("localhost", 6666);
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
}