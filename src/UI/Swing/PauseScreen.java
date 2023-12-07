package UI.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseScreen extends JFrame {
    private JPanel contentPane;
    private JButton resumeButton;

    public PauseScreen(int width, int height, Frame frame) {
        // Set the title and size of the pause screen
        setTitle("Pause Screen");
        setSize(width, height);
        setResizable(false);
        setUndecorated(true);  // Make the JFrame undecorated

        // Center the frame on the screen
        setLocationRelativeTo(null);
        
        

        // Create a JPanel as the contentPane with null layout
        contentPane = new JPanel(null);
        setContentPane(contentPane);

        // Add a JLabel for the background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("src/UI/Swing/Images/screenBackground.jpg"));
        backgroundLabel.setBounds(0, 0, width, height);
        contentPane.add(backgroundLabel);

        // Add a label for information text at the top center
        JLabel infoLabel = new JLabel("Game Paused", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE); // Set text color to white
        infoLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size to 30
        infoLabel.setBounds(0, 30, width, 80); // Adjust as needed
        backgroundLabel.add(infoLabel); // Add to the background label

        // Initialize the JButton for the resume action in the center
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the pause screen when the "Resume" button is clicked
                frame.setVisible(true);
            	dispose();
            }
        });
        resumeButton.setBounds(width / 2 - 50, height / 2 - 25, 100, 50); // Centered button
        backgroundLabel.add(resumeButton); // Add to the background label
    }

    public void display() {
        setVisible(true);
    }
}