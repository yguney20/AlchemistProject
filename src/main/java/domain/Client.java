package domain;

import java.io.*;
import java.net.*;

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
        writer.println(message);
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
        // Replace with the server's IP address and port
        Client client = new Client("localhost", 6666);
        if (client.connect()) {
            System.out.println("Client successfully connected to server");
            client.sendMessage("Hello from the client!"); // Send a test message
            // More interactions or game logic here

            // Disconnect when done
            client.disconnect();
        }
    }
}