package domain.interfaces;

import java.util.List;

public interface EventListener {
    void onMessageReceived(String message);
    void onPlayerListUpdate(List<String> playerNames);
}