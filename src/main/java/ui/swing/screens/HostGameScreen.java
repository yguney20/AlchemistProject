package ui.swing.screens;


import ui.swing.screens.LoginOverlay;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;
import domain.Client;
import domain.controllers.OnlineGameAdapter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


public class HostGameScreen extends JFrame implements PlayerListUpdateListener {
	private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JButton startGameButton;
    private JLabel ipLabel;
    private JList<String> playerList;
    String selectedPlayerName = LoginOverlayForHost.selectedPlayerName; 
    String selectedAvatarPath =LoginOverlayForHost.selectedAvatarPath; 


    public HostGameScreen(Frame frame) {
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

        playerList = new JList<>(new DefaultListModel<>());
        DefaultListModel<String> model = (DefaultListModel<String>) playerList.getModel();
        playerList.setBounds(400, 275, 200, 250); // Set bounds as needed
        contentPane.add(playerList);

        OnlineGameAdapter adapter = new OnlineGameAdapter("localhost", 6666, this); // Replace with actual host and port
        adapter.setPlayerListUpdateListener(this);
        System.out.println("Creating the bot client");
        adapter.simulateAnotherPlayer();
        
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
            // Send host player info to server
            if (adapter.connect()) {
                adapter.sendPlayerInfo(selectedPlayerName, selectedAvatarPath);
                updatePlayerList(adapter.getConnectedPlayers());
    
                // Send a request to check if all players are ready
                boolean allPlayersReady = adapter.areAllPlayersReady();
    
                if (allPlayersReady) {
                    // All players are ready, start the game
                    adapter.startGame();
                    // Proceed to the game screen or other relevant UI component
                } else {
                    // Not all players are ready
                    JOptionPane.showMessageDialog(HostGameScreen.this, "Not all players are ready", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // Handle connection failure
                JOptionPane.showMessageDialog(
                    HostGameScreen.this,
                    "Failed to connect to the server.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    });
        startGameButton.setBounds(width / 2 - 100, height / 2- 200, 200, 50);
        backgroundLabel.add(startGameButton);
    }

    public void enableStartButton() {
        startGameButton.setEnabled(true);
    }

    public void display() {
        setVisible(true);
    }


    public void updatePlayerList(List<String> players) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Received player list update: " + players);
            DefaultListModel<String> model = (DefaultListModel<String>) playerList.getModel();
            model.addElement(selectedPlayerName);
            model.clear();
            for (String player : players) {
                System.out.println("Adding player to list: " + player); // Debug print
                model.addElement(player);
            }
            startGameButton.setEnabled(model.size() > 1);
        });
    }

    public void onAllPlayersReady(boolean allReady) {
        if (allReady) {
            // All players are ready, enable the start game button or show a message
        } else {
            // Not all players are ready, disable the start game button or show a message
            JOptionPane.showMessageDialog(this, "Not all players are ready", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void onDuplicatePlayer() {
        // Handle duplicate player scenario
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


    @Override
    public void onPlayerListUpdate(List<String> playerNames) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Host Game UI: Received player list update, updating UI: " + playerNames);
            DefaultListModel<String> model = (DefaultListModel<String>) playerList.getModel();
            model.clear();
            // Check if the host player is already in the model
            if (!model.contains(selectedPlayerName)) {
                model.addElement(selectedPlayerName);
            }
    
            // Update the list with the new players
            for (String playerName : playerNames) {
                if (!model.contains(playerName)) {
                    model.addElement(playerName);
                }
            }
            startGameButton.setEnabled(model.size() > 1);
        });
    }

}