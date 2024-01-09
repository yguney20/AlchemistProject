package ui.swing.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.swing.screens.screencomponents.SettingsState;
import ui.swing.screens.screencontrollers.SettingsScreenController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import ui.swing.screens.screencontrollers.EntranceScreenController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class SettingsScreen extends JFrame {

    //private JButton backButton = new JButton("Back");
   // private JButton saveButton = new JButton("Save");
   // private JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // Volume slider with a range of 0 to 100
    //private JButton muteButton = new JButton("Mute/Unmute");
    
    private int initialX;
    private int initialY;

    private JPanel contentPane;
	private static EntranceScreenController entranceScreenController = EntranceScreenController.getInstance();
	private JFXPanel fxPanel;
    private SettingsState settingsState = GameController.getSettingsState();
    private Frame previousFrame;
    
    public SettingsScreen(SettingsState settingsState, Frame previousFrame) {
    	this.previousFrame = previousFrame;
        this.settingsState = settingsState;
        initializeFrame();
        initializeJavaFXComponents();
    }

    private void initializeFrame() {
        setTitle("Settings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 800); // Adjust size as necessary
        setResizable(false);
        fxPanel = new JFXPanel(); // Prepare JavaFX environment
        add(fxPanel);
    }
    


    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/SettingsScreen.fxml"));
                SettingsScreenController controller = new SettingsScreenController();
                controller.setPreviousFrame(previousFrame); // Assuming you have a reference to the previous frame
                controller.setSettingsScreen(this); // Pass the current screen
                loader.setController(controller);
                Parent root = loader.load();

                controller.setSettingsState(settingsState);

                Scene scene = new Scene(root);
                fxPanel.setScene(scene);

                // Load CSS if necessary
                scene.getStylesheets().add("/ui/swing/screens/cssfiles/screenstyles.css");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
    
    public void close() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }
    
    
/*
    public SettingsScreen(Frame frame, EntranceScreenController controller, SettingsState settingsState) {
        this.entranceScreenController = controller;
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(getClass().getResource("/ui/swing/resources/images/helpUI/screenBackground.jpg")).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the background color of the JFrame
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        
        // Add a MouseListener to make the frame movable
        contentPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        contentPane.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialX;
                int y = getLocation().y + e.getY() - initialY;
                setLocation(x, y);
            }
        });

        setSize(1000, 800);
        setUndecorated(true); // Remove title bar
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/ui/swing/resources/images/entranceUI/logo.png"));
        setIconImage(icon.getImage());

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
        contentPane.add(backButton);

        // Add "Game Music Level" label
        JLabel volumeLabel = new JLabel("Game Music Level");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setBounds(10, 50, 150, 25);
        contentPane.add(volumeLabel);

        // Add Volume slider
        volumeSlider.setBounds(160, 50, 200, 25);
        contentPane.add(volumeSlider);

        // Add Mute/Unmute button
        muteButton.setBounds(370, 50, 120, 25);
        contentPane.add(muteButton);

        // Add Save button
        saveButton.setBounds(10, 100, 100, 25);
        contentPane.add(saveButton);

        // Add ActionListener to the Save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the settings (replace with actual implementation)
                JOptionPane.showMessageDialog(SettingsScreen.this, "Settings saved!");
            }
        });
        
        // Inside SettingsScreen constructor
        this.volumeSlider.setValue((int) (settingsState.getVolume() * 100));
        // When the volume slider is changed
        volumeSlider.addChangeListener(e -> {
            if (!volumeSlider.getValueIsAdjusting()) {
                double volume = volumeSlider.getValue() / 100.0;
                settingsState.setVolume(volume);
                entranceScreenController.setMusicVolume(volume);
            }
        });

        muteButton.addActionListener(new ActionListener() {
            private double lastVolume = 0.5; // Default volume level before mute

            @Override
            public void actionPerformed(ActionEvent e) {
                if (volumeSlider.getValue() > 0) {
                    // Mute the music
                    lastVolume = volumeSlider.getValue() / 100.0;
                    volumeSlider.setValue(0);
                    entranceScreenController.setMusicVolume(0);
                } else {
                    // Unmute to the last volume level
                    volumeSlider.setValue((int) (lastVolume * 100));
                    entranceScreenController.setMusicVolume(lastVolume);
                }
            }
        });


        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Set the content pane to the custom JPanel
        setContentPane(contentPane);
        setLayout(new BorderLayout()); // Set layout for JFrame
    }

    public void display() {
        // Make the frame visible
        setVisible(true);
    }*/

}