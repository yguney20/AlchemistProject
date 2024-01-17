package ui.swing.screens;


import ui.swing.screens.LoginOverlayForHost;

import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;
import ui.swing.screens.screencontrollers.BoardScreenController;
import domain.Client;
import domain.GameState;
import domain.Server;
import domain.controllers.GameController;
import domain.controllers.HostController;
import domain.controllers.LoginController;
import domain.controllers.OnlineGameAdapter;
import domain.interfaces.EventListener;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;


public class HostGameScreen extends JFrame implements PlayerListUpdateListener, EventListener {
	private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JButton startGameButton;
    private JLabel statusLabel; 
    private JLabel ipLabel;
    private JList<String> playerList;

    private LoginController loginController = LoginController.getInstance();
    private GameController gameController = GameController.getInstance();
    private HostController hostController;
    private BoardScreenController boardController;

    private String selectedPlayerName = LoginOverlayForHost.selectedPlayerName; 
    private String selectedAvatarPath =LoginOverlayForHost.selectedAvatarPath;
    private Client client;

   

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
        DefaultListModel<String> model = new DefaultListModel<>();
        playerList.setModel(model);
        playerList.setBounds(400, 275, 200, 250); // Set bounds as needed
        contentPane.add(playerList);

        
        hostController = new HostController(gameController);
        loginController.createPlayer(selectedPlayerName, selectedAvatarPath);
        setupClient();
        
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
        startGameButton.addActionListener(e -> initiateStartGame());
        startGameButton.setBounds(width / 2 - 100, height / 2 - 200, 200, 50);
        backgroundLabel.add(startGameButton);

        
    }

    private void initiateStartGame() {
        try {
            if (hostController.areAllPlayersReady()) {
                System.out.println("All players are ready. Starting the game...");
                hostController.startGame();
                client.sendMessage("{\"action\":\"startGame\"}");
                dispose(); // Close the host game screen
            } else {
                statusLabel.setText("Not all players are ready");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    public void enableStartButton() {
        startGameButton.setEnabled(true);
    }

    public void display() {
        setVisible(true);
    }

    private void setupClient() {
        String serverIp = "localhost"; // Replace with actual server IP
        int serverPort = 6666;        // Replace with actual server port
        client = new Client(serverIp, serverPort, this);
        client.simulateAnotherPlayer();
        if (client.connect()) {
            OnlineGameAdapter onlineGameAdapter = new OnlineGameAdapter(client);
            GameController.getInstance().setOnlineGameAdapter(onlineGameAdapter);
            GameController.getInstance().setOnlineMode(true);

            client.sendPlayerInfo(selectedPlayerName, selectedAvatarPath);
            client.setPlayerReady();
            client.startListening();
            // You can also send an initial message to the server her(e if needed
        } else {
            // Handle connection failure
        }
    }


    @Override
    public void onPlayerListUpdate(List<String> playerNames) {
        updatePlayerList(playerNames);
    }

    private void updatePlayerList(List<String> playerNames) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Host Game UI: Updating player list UI: " + playerNames);
            DefaultListModel<String> model = (DefaultListModel<String>) playerList.getModel();
            model.clear();
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
    public void onDuplicatePlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onDuplicatePlayer'");
    }

    @Override
    public void onMessageReceived(String message) {
        System.out.println("Message received: " + message);
        if (message.startsWith("PLAYER_LIST:")) {
            String json = message.substring("PLAYER_LIST:".length());
            List<String> playerNames = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
            System.out.println("Updating player list with: " + playerNames);
            updatePlayerList(playerNames);
 
        }
    }

}