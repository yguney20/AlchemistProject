package ui.swing.screens;

import ui.swing.screens.LoginOverlay;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Client;
import domain.controllers.ConnectController;
import domain.controllers.LoginController;
import domain.controllers.OnlineGameAdapter;
import domain.interfaces.EventListener;

import java.util.List;
import java.util.Map;

public class ConnectGameScreen extends JFrame implements PlayerListUpdateListener,EventListener {
    private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JLabel ipTextInf;
    private JTextField ipTextField;
    private JButton connectButton;
    private JLabel statusLabel; // To display connection status
    private JLabel statusLabel2;
    private JButton readyButton;
    private JList<String> playerList;
    private String playerName;
    private String avatarPath;

    private Client client;

    private LoginController loginController = LoginController.getInstance();
    private ConnectController connectController;

    public ConnectGameScreen(Frame frame) {
        int width = 1000;
        int height = 800;
        setTitle("Connect to Game");
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

        JLabel infoLabel = new JLabel("Connect to a Game", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        infoLabel.setBounds(0, 30, width, 80);
        backgroundLabel.add(infoLabel);

        readyButton = new JButton("Ready");
        readyButton.setFont(new Font("Arial", Font.BOLD, 20));
        readyButton.setBounds(450, 600, 100, 40);
        backgroundLabel.add(readyButton);

        playerList = new JList<>(new DefaultListModel<>());
        playerList.setBounds(400, 275, 200, 250); // Set bounds as needed
        contentPane.add(playerList);
        


        // Back button logic
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                dispose();
            }
        });
        backButton.setBounds(10, 10, 75, 25);
        backgroundLabel.add(backButton);
        
        //Inform Text Line
        ipTextInf = new JLabel("Enter Host IP", SwingConstants.CENTER);
        ipTextInf.setForeground(Color.WHITE);
        ipTextInf.setFont(new Font("Arial", Font.BOLD, 16));
        ipTextInf.setBounds(width / 2 - 150, 120, 300, 30);
        backgroundLabel.add(ipTextInf);
        
        // Input field for the IP address
        ipTextField = new JTextField("");
        ipTextField.setBounds(width / 2 - 150, 150, 300, 30);
        backgroundLabel.add(ipTextField);

        // Connect button logic
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                String hostIp = ipTextField.getText();
                initiateConnection(hostIp); // Initiate connection attempt
            }
        });
        connectButton.setBounds(width / 2 - 100, height / 2 - 200, 200, 50);
        backgroundLabel.add(connectButton);

        // Status label to show connection success or failure
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBounds(0, 550, width, 30);
        backgroundLabel.add(statusLabel);

        statusLabel2 = new JLabel("", SwingConstants.CENTER);
        statusLabel2.setForeground(Color.RED);
        statusLabel2.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel2.setBounds(0, 590, width, 30);
        backgroundLabel.add(statusLabel2);


        // Ready button logic
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client != null && client.isConnected()) {
                    // If connected, send the ready status to the server
                    client.togglePlayerReady();
          
                } else {
                    // If not connected, show a message to connect first
                    JOptionPane.showMessageDialog(ConnectGameScreen.this,
                        "You need to connect to a game first.",
                        "Not Connected",
                        JOptionPane.WARNING_MESSAGE);
                }
            
            }
        });
    }

    public void display() {
        setVisible(true);
    }
    
    

    private void initiateConnection(String hostIp) {
        client = new Client(hostIp, 6666, this);
        if (client.connect()) {
            client.sendPlayerInfo(playerName, avatarPath); // Send player info to the server
            statusLabel.setText("Connected successfully!");
            statusLabel.setForeground(Color.GREEN);
            client.setEventListener(this);
            // Additional logic for successful connection, if needed
        } else {
            statusLabel.setText("Failed to connect. Check the IP and try again.");
        }
    }

    public void setPlayerInfo(String playerName, String avatarPath) {
        this.playerName = playerName;
        this.avatarPath = avatarPath;
    }
    

    @Override
    public void onMessageReceived(String message) {
        System.out.println("AA Message received: " + message);
        if (message.startsWith("YOUR_STATUS:")) {
            String statusJson = message.substring("YOUR_STATUS:".length());
            updatePlayerReadinessStatus(statusJson);
        }
        if (message.equals("PLAYER_CONFIRMED")) {
            createPlayer();
        }
        if (message.equals("DUPLICATE")) {
            handleDuplicatePlayer();
        
        }else if (message.startsWith("PLAYER_LIST:")) {
            String json = message.substring("PLAYER_LIST:".length());
            List<String> playerNames = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
            System.out.println("Updating player list with: " + playerNames);
            updatePlayerList(playerNames);
 
        }

    }

    @Override
    public void onPlayerStatusUpdate(String statusUpdate) {
        System.out.println("Player status update received: " + statusUpdate);
        updatePlayerReadinessStatus(statusUpdate);
    }

    
    private void updatePlayerReadinessStatus(String statusJson) {
       Map<String, String> statusMap = new Gson().fromJson(statusJson, new TypeToken<Map<String, String>>(){}.getType());
    if (statusMap.containsKey(playerName)) {
        String status = statusMap.get(playerName);
        System.err.println("status");
        SwingUtilities.invokeLater(() -> {
            if ("Ready".equals(status)) {
                statusLabel2.setText("You are Ready");
                statusLabel2.setForeground(Color.GREEN);
            } else {
                statusLabel2.setText("You are Not Ready");
                statusLabel2.setForeground(Color.RED);
            }
        });
    }
    }

    private void createPlayer() {
        SwingUtilities.invokeLater(() -> {
            // Now that the server has confirmed the uniqueness, create the player locally
            loginController.createPlayer(playerName, avatarPath);
            // Additional logic for successful player creation
        });
    }

    @Override
    public void onPlayerListUpdate(List<String> playerNames) {
        updatePlayerList(playerNames);
    }
    public void updatePlayerList(List<String> playerNames) {
        System.out.println("Received player list update: " + playerNames);
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = (DefaultListModel<String>) playerList.getModel();
            model.clear();
            for (String playerName : playerNames) {
                System.out.println("Updating player list in connect screen: " + playerName); // Debug print
                model.addElement(playerName);
            }
        });
    }

    @Override
    public void onDuplicatePlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onDuplicatePlayer'");
    }


    // Test Main Method
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ConnectGameScreen screen = new ConnectGameScreen(null);
                screen.display();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void handleDuplicatePlayer() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                "Duplicate player name or avatar detected. Please choose another.", 
                "Duplicate Player", 
                JOptionPane.ERROR_MESSAGE);
            // Additional logic for handling duplicate player
        });
    }
   
}