package ui.swing.screens;


import ui.swing.screens.LoginOverlay;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;
import ui.swing.screens.screencontrollers.BoardScreenController;
import domain.Client;
import domain.controllers.LoginController;
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
    private JLabel statusLabel; 
    private JLabel ipLabel;
    private JList<String> playerList;
    String selectedPlayerName = LoginOverlayForHost.selectedPlayerName; 
    String selectedAvatarPath =LoginOverlayForHost.selectedAvatarPath;
    private LoginController loginController = LoginController.getInstance();
    BoardScreenController boardController;


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

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBounds(0,550, width, 30);
        backgroundLabel.add(statusLabel);

        playerList = new JList<>(new DefaultListModel<>());
        DefaultListModel<String> model = (DefaultListModel<String>) playerList.getModel();
        playerList.setBounds(400, 275, 200, 250); // Set bounds as needed
        contentPane.add(playerList);

        OnlineGameAdapter adapter = new OnlineGameAdapter("localhost", 6666, this); // Replace with actual host and port
        adapter.setPlayerListUpdateListener(this);
        adapter.simulateAnotherPlayer();
        loginController.createPlayer(selectedPlayerName, selectedAvatarPath);
        
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
            try {
                if (adapter.connect()) {
                    adapter.sendPlayerInfo(selectedPlayerName, selectedAvatarPath);
                    adapter.sendReadySignal();
                    adapter.areAllPlayersReady(allReady -> {
                        System.out.println("Checking if all players are ready: " + allReady);
                        if (allReady) {
                            System.out.println("Start Game"); 
                            adapter.startGame();
                            dispose();
                        } else {
                            statusLabel.setText("Not all players are ready");
                        }
                    });
                } else {
                    statusLabel.setText("Failed to connect to the server");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error: " + ex.getMessage());
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