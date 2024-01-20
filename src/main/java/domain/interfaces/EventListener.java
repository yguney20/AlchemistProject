package domain.interfaces;

import java.util.List;

//EventListener for updating UI when messages recieved etc.
public interface EventListener {
    void onMessageReceived(String message);
    void onPlayerListUpdate(List<String> playerNames);
}