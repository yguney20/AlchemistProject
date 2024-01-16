package domain.controllers;

import java.util.Map;

public interface GameActionHandler {
    void startGame();
    void sendAction(String actionType, Map<String, String> actionDetails);
    void endGame();
    
}