package ui.swing.screens;

import javax.imageio.ImageIO;
import javax.swing.*;

import domain.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameEntranceScreen extends JFrame{
    /**
	 * 
	 */
	private JFrame frame;
	private JPanel backgroundPanel;
    private JButton playButton = new JButton("Play");
    private JButton hostGame = new JButton("Host a game");
    private JButton connectGame = new JButton("Connect a game");
    private JButton settingsButton = new JButton("Settings");
    private JButton helpButton = new JButton("Help");
    private JButton quitButton = new JButton("X");

    private int buttonWidth;
    private int buttonHeight;
    private int initialX;
    private int initialY;

    public GameEntranceScreen() {
    	this.frame = this;
        setTitle("KU Alchemist");
        setSize(1000, 800);
        setUndecorated(true);  // Make the JFrame undecorated
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        buttonWidth = 500;
        buttonHeight = 50;
        // Load the background image using ImageIO
        
        JLabel titleLabel = new JLabel("Ku Alchemists", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 48));; // Replace with your font path and size
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color, you can change it as per your theme
        
        
        try {
           BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/UI/Swing/Resources/Images/entranceUI/background.jpeg"));
           
            // Create a custom JPanel with the background image
            backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };
                        

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        titleLabel.setBounds(250, 300, 500, 300);
        backgroundPanel.add(titleLabel);
        
        // Play button
        playButton.setBounds(getWidth() / 4, 175, buttonWidth, buttonHeight);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close current frame
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.display();
            }
        });
        this.add(playButton);
        
     // Host button
        hostGame.setBounds(getWidth() / 4, 275, buttonWidth, buttonHeight);
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	startServer();
            	setVisible(false);
                HostGameScreen hostScreen = new HostGameScreen(frame);
                hostScreen.display();
            }
        });
        this.add(hostGame);
        
     // Connect button
        connectGame.setBounds(getWidth() / 4, 375, buttonWidth, buttonHeight);
        connectGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                ConnectGameScreen connectScreen = new ConnectGameScreen(frame);
                connectScreen.display();
            }
        });
        this.add(connectGame);
        
        
        // Settings button
        settingsButton.setBounds(getWidth() / 4, 475, buttonWidth, buttonHeight);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SettingsScreen settingsScreen = new SettingsScreen(frame);
                settingsScreen.display();
            }
        });
        add(settingsButton);

        // Help button,
        helpButton.setBounds(getWidth() / 4, 575, buttonWidth, buttonHeight);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);  // Close current frame
                HelpScreen helpScreen = new HelpScreen(frame);
                helpScreen.display();
            }
        });
        add(helpButton);
        
        addButtonHoverEffect(playButton);
        addButtonHoverEffect(hostGame);
        addButtonHoverEffect(connectGame);
        addButtonHoverEffect(settingsButton);
        addButtonHoverEffect(helpButton);

     
        quitButton.setBounds(getWidth() - 70, 20, 80, 20);
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        // Increase the font size
        Font pacificoFont = new Font("Pacifico", Font.PLAIN, 12);
        Font largerFont = pacificoFont.deriveFont(pacificoFont.getSize() + 24f);
        quitButton.setFont(largerFont);
       
        // Set the red "X" as the text
        quitButton.setForeground(Color.RED);
        quitButton.setText("X");
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Terminate the application
            }
        });

        add(quitButton);

        
        
        // Add a MouseListener to make the frame movable
        backgroundPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        backgroundPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialX;
                int y = getLocation().y + e.getY() - initialY;
                setLocation(x, y);
            }
        });

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height -getHeight()) / 2;
        setLocation(x, y);

        // Add the backgroundPanel to the content pane
        getContentPane().add(backgroundPanel);
    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(UIManager.getColor("Button.background"));
            }
        });
    }

    
    public void display() {
        setVisible(true);
    }
    
    
    //when user passes to HostGameScreen, a server will be started
    private void startServer() {
        // Start the server in a new thread to prevent UI freezing
        new Thread(() -> {
            try {
                Server server = new Server(6666); // Replace 6666 with your actual server port
                server.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Failed to start the server: " + ex.getMessage(),
                        "Server Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    
}