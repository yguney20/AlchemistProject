package domain.controllers;

import domain.Client;

public class OnlineGameAdapter implements GameCommunication {
	
	private Client client;

    public OnlineGameAdapter(String host, int port) {
        this.client = new Client(host, port);
    }

    public boolean connect() {
        return client.connect(); // Attempt to connect using the Client class
    }

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAction(String action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}

}
