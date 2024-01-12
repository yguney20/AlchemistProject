package ui.swing.screens;

import ui.swing.screens.LoginOverlay;
import ui.swing.screens.screenInterfaces.PlayerListUpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import domain.controllers.OnlineGameAdapter;
import java.util.List;

public class ConnectGameScreen extends JFrame implements PlayerListUpdateListener {
    private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JLabel ipTextInf;
    private JTextField ipTextField;
    private JButton connectButton;
    private JLabel statusLabel; // To display connection status
    private JList<String> playerList;
    private String playerName;
    private String avatarPath;

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

        JButton readyButton = new JButton("Ready");
        readyButton.setForeground(Color.WHITE);
        readyButton.setFont(new Font("Arial", Font.BOLD, 20));
        readyButton.setBounds(200, 350, 100, 40);
        backgroundLabel.add(readyButton);

        playerList = new JList<>(new DefaultListModel<>());
        playerList.setBounds(350, 250, 200, 300); // Set bounds as needed
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
        connectButton.setBounds(width / 2 - 100, height / 2, 200, 50);
        backgroundLabel.add(connectButton);

        // Status label to show connection success or failure
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBounds(0, height / 2 + 60, width, 30);
        backgroundLabel.add(statusLabel);
    }

    

    private void initiateConnection(String hostIp) {
        OnlineGameAdapter adapter = new OnlineGameAdapter(hostIp, 6666, this);
        adapter.setPlayerListUpdateListener(this);
        boolean isConnected = adapter.connect();
    
        if (isConnected) {
            adapter.sendPlayerInfo(playerName, avatarPath); // Send player info after connecting
            String response = adapter.receiveMessage();
            if ("DUPLICATE".equals(response)) {
                JOptionPane.showMessageDialog(this,
                        "Player name or avatar already in use. Please choose another.",
                        "Duplicate Player",
                        JOptionPane.ERROR_MESSAGE);
                adapter.disconnect();
            } else {
                statusLabel.setText("Connected successfully!");
                statusLabel.setForeground(Color.GREEN);
            }
        } else {
            statusLabel.setText("Failed to connect. Check the IP and try again.");
        }
    }

    public void updatePlayerList(String[] players) {
        playerList.setListData(players);
    }

    public void display() {
        setVisible(true);
    }

    public void onPlayerListUpdate(List<String> playerNames) {
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
    public void onAllPlayersReady(boolean allReady) {
    }
 
    public void onDuplicatePlayer() {
        // Handle duplicate player scenario
    }

    public void setPlayerInfo(String playerName, String avatarPath) {
        this.playerName = playerName;
        this.avatarPath = avatarPath;
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
}