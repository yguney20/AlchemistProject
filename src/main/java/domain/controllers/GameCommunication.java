package domain.controllers;

import java.util.Map;

public interface GameCommunication {
    void startGame();
    void sendAction(String actionType, Map<String, String> actionDetails);
    String receiveUpdate();
    void endGame();
}