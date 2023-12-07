package UI.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpScreenControllerü extends JFrame {

    private JButton backButton = new JButton("Back");

    public HelpScreenControllerü(int width, int height, Frame frame) {
        backButton.setBounds(width - 100, height - 50, 100, 50);
        setSize(width, height);
        
        setTitle("KU Alchemists Help");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/UI/Swing/Images/logo.png");
        setIconImage(icon.getImage());

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

        // Add ActionListener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current HelpScreenControllerü
            	frame.setVisible(true);
            	dispose();
                
                
            }
        });

        // Add the Back button to the bottom of the frame
        add(backButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        
    }

    public void display() {
        // Make the frame visible
        setVisible(true);
    }
}
