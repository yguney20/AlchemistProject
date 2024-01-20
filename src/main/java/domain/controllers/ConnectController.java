package domain.controllers;

import domain.Client;
import domain.interfaces.EventListener;

/* This controller handles the connection for players
 *  Call comes from Connect Screen.
 */
public class ConnectController {
    private Client client;
    private GameController gameController;

    public ConnectController(GameController gameController) {
        this.gameController = gameController;
    }   

    public void connectToServer(String host, int port, EventListener listener) {
        client = new Client(host, port, listener);
        if (client.connect()) {
            System.out.println("Connected to server.");
        } else {
            System.err.println("Failed to connect to server.");
        }
    }
}