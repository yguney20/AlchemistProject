package UI.Swing;

import javax.swing.*;
import java.awt.*;

public class HelpScreen extends JFrame {

    public HelpScreen() {
        setTitle("KU Alchemists Help");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea helpText = new JTextArea();
        helpText.setEditable(false);
        helpText.setText("Welcome to KU Alchemists!\n\n" +
                "Instructions:\n" +
                "- Your goal is to uncover the secrets of magical ingredients and create powerful potions.\n" +
                "- Conduct experiments, publish theories, and earn reputation points to climb the ranks.\n" +
                "- Beware of hidden truths and unforeseen consequences in the world of Alchemy.\n" +
                
                "\nControls:\n" +
                "- Use the Player Dashboard to perform actions like collecting ingredients, brewing potions, etc.\n" +
              
                "\nScoring:\n" +
                "- Reputation points are multiplied by 10 to calculate score points.\n" +
                "- Artifact cards can be converted into gold, contributing to additional score points.\n" +
                "- 1 score point is earned for every 3 gold pieces.\n" +
              
                "\nEnjoy the game!");

        JScrollPane scrollPane = new JScrollPane(helpText);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HelpScreen helpScreen = new HelpScreen();
            helpScreen.setVisible(true);
        });
    }
}
