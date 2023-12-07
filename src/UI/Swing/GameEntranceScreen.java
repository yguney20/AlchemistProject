package UI.Swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameEntranceScreen extends JFrame{
    /**
	 * 
	 */
	private JFrame frame;
	private JPanel backgroundPanel;
    private JButton playButton = new JButton("Play");
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
        try {
           BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/UI/Swing/Images/background.jpeg"));
           ImageIcon icon = new ImageIcon(getClass().getResource("/UI/Swing/Images/logo.png"));

            setIconImage(icon.getImage());
            
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

        

        // Create and add buttons
        createButtons();

        
        
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

    private void createButtons() {
        // Play button
        playButton.setBounds(getWidth() / 4, 225, buttonWidth, buttonHeight);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close current frame
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.display();
            }
        });
        this.add(playButton);

        // Settings button
        settingsButton.setBounds(getWidth() / 4, 375, buttonWidth, buttonHeight);
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
        helpButton.setBounds(getWidth() / 4, 525, buttonWidth, buttonHeight);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);  // Close current frame
                HelpScreen helpScreen = new HelpScreen(frame);
                helpScreen.display();
            }
        });
        add(helpButton);

     
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
            	dispose();
            }
        });
        add(quitButton);
    }

    

    public void display() {
        setVisible(true);
    }

    // Add this method to set up the UI components
    public void setupUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createButtons();
                display();
            }
        });
    }
}
