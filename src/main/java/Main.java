import javax.swing.SwingUtilities;

import ui.swing.screens.*;
import ui.swing.screens.scenes.EntranceScreen;

public class Main {
    public static void main(String[] args) {
        
        
        SwingUtilities.invokeLater(() -> {
            EntranceScreen frame = new EntranceScreen();
            frame.display();
        });
        

    }
    
}