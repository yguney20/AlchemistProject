package UI.Swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameEntranceScreen {
    private JFrame frame;
    private JPanel backgroundPanel;
    private JButton playButton = new JButton("Play");
    private JButton settingsButton = new JButton("Settings");
    private JButton helpButton = new JButton("Help");
    private int buttonWidth;
    private int buttonHeight;

    public GameEntranceScreen() {
        frame = new JFrame("KU Alchemist");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        buttonWidth = frame.getWidth() / 2;
        buttonHeight = 50;
        // Load the background image using ImageIO
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("src/UI/Swing/Images/background.jpeg"));
            ImageIcon icon = new ImageIcon("src/UI/Swing/Images/logo.png");
            frame.setIconImage(icon.getImage());
            
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

        // Set layout manager to OverlayLayout
        frame.setLayout(new OverlayLayout(frame.getContentPane()));

        // Create and add buttons
        createButtons();

        // Add a ComponentListener to track frame size changes
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustButtonPositions();
            }
        });

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Add the backgroundPanel to the content pane
        frame.getContentPane().add(backgroundPanel);
    }

    private void createButtons() {
        // Play button
        playButton.setBounds((frame.getWidth() - buttonWidth) / 2, (frame.getHeight() / 2) - 175, buttonWidth, buttonHeight);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Close current frame
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.display();
            }
        });
        frame.add(playButton);

        // Settings button
        settingsButton.setBounds((frame.getWidth() - buttonWidth) / 2, (frame.getHeight() / 2) - 75, buttonWidth, buttonHeight);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Settings button click
                // Add your logic here
            }
        });
        frame.add(settingsButton);

        // Help button,
        helpButton.setBounds((frame.getWidth() - buttonWidth) / 2, (frame.getHeight() / 2) + 25, buttonWidth, buttonHeight);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();  // Close current frame
                HelpScreen helpScreen = new HelpScreen(frame.getWidth(), frame.getHeight());
                helpScreen.display();
            }
        });
        frame.add(helpButton);

        // Adjust the initial button positions
        adjustButtonPositions();
    }

    private void adjustButtonPositions() {
        

        playButton.setBounds((frame.getWidth() - buttonWidth) / 2, (frame.getHeight() / 2) - 175, buttonWidth, buttonHeight);
        settingsButton.setBounds((frame.getWidth() - buttonWidth) / 2, (frame.getHeight() / 2) - 75, buttonWidth, buttonHeight);
        helpButton.setBounds((frame.getWidth() - buttonWidth) / 2, (frame.getHeight() / 2) + 25, buttonWidth, buttonHeight);
    }

    public void display() {
        frame.setVisible(true);
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
