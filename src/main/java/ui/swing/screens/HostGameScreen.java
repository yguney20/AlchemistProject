package ui.swing.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostGameScreen extends JFrame {
	private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JButton startGameButton;
    private JLabel ipLabel;
    private JList<String> playerList;

    public HostGameScreen(Frame frame) {
        playerList = new JList<>();
        int width = 1000;
        int height = 800;
        setTitle("Host Game");
        setSize(width, height);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);

        contentPane = new JPanel(null);
        setContentPane(contentPane);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/ui/swing/resources/images/helpUI/screenBackground.jpg")));
        backgroundLabel.setBounds(0, 0, width, height);
        contentPane.add(backgroundLabel);

        JLabel infoLabel = new JLabel("Host a Game", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        infoLabel.setBounds(0, 30, width, 80);
        backgroundLabel.add(infoLabel);
        
        // Add ActionListener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct user to the previous screen (replace with actual implementation)
                frame.setVisible(true);
                dispose();
            }
        });

        // Add the Back button to the top left of the frame
        backButton.setBounds(10, 10, 75, 25);
        backgroundLabel.add(backButton);

        try {
            InetAddress ip = InetAddress.getLocalHost();
            ipLabel = new JLabel("Your IP: " + ip.getHostAddress(), SwingConstants.CENTER);
            ipLabel.setForeground(Color.YELLOW);
            ipLabel.setFont(new Font("Arial", Font.BOLD, 30));
            ipLabel.setBounds(0, 120, width, 30);
            backgroundLabel.add(ipLabel);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        startGameButton = new JButton("Start Game");
        startGameButton.setEnabled(false); // Disabled initially
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to start the game
            }
        });
        startGameButton.setBounds(width / 2 - 100, height / 2, 200, 50);
        backgroundLabel.add(startGameButton);
    }

    public void enableStartButton() {
        startGameButton.setEnabled(true);
    }

    public void display() {
        setVisible(true);
    }

    public void updatePlayerList(String[] players) {
        playerList.setListData(players);
    }

    // Test Main Method
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                HostGameScreen screen = new HostGameScreen(null);
                screen.display();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}