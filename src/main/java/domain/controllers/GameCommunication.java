package domain.controllers;

public interface GameCommunication {
    void startGame();
    void sendAction(String action);
    void receiveUpdate();
    void endGame();
}