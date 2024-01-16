package ui.swing.screens.screenInterfaces;

import java.util.List;

public interface PlayerListUpdateListener {
    void onPlayerListUpdate(List<String> playerNames);
    void onDuplicatePlayer();
}