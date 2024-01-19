package ui.swing.screens.scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

import ui.swing.screens.screencontrollers.EntranceScreenController;
import ui.swing.screens.screencontrollers.PauseScreenController;

public class PauseScreen extends JFrame {
    private JPanel contentPane;
    private JButton resumeButton;
    private Frame mainFrame;
    private JFrame menuScreen;
    private String pausingPlayerName = null;

    

    public PauseScreen(Frame mainFrame, JFrame menuScreen) {
        this.mainFrame = mainFrame;
        this.menuScreen = menuScreen;

        setTitle("Pause Screen");
        setUndecorated(true);
        setSize(900, 505); // Adjust the size as needed
        setLocationRelativeTo(null);

        JFXPanel fxPanel = new JFXPanel();
        getContentPane().add(fxPanel);
        initializeJavaFXComponents(fxPanel);
    }
    
    public PauseScreen(Frame mainFrame, JFrame menuScreen, String pausingPlayerName) {
        this.mainFrame = mainFrame;
        this.menuScreen = menuScreen;
        this.pausingPlayerName = pausingPlayerName;

        setTitle("Pause Screen");
        setUndecorated(true);
        setSize(900, 505);
        setLocationRelativeTo(null);

        JFXPanel fxPanel = new JFXPanel();
        getContentPane().add(fxPanel);
        initializeJavaFXComponents(fxPanel);
    }

    
    private void initializeJavaFXComponents(JFXPanel fxPanel) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/PauseScreen.fxml"));
                Parent root = loader.load();

                PauseScreenController controller = loader.getController();
                controller.setPauseScreen(this);
                controller.setMainFrame(mainFrame);
                controller.setMenuScreen(menuScreen);
                controller.setPausingPlayerName(pausingPlayerName); //

                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    
/*
    public PauseScreen(int width, int height, Frame frame, JFrame menuScreen) {
        this.mainFrame = frame;
        this.menuScreen = menuScreen;
        
        // Set the title and size of the pause screen
        this.mainFrame = frame;
        setTitle("Pause Screen");
        setSize(width, height);
        setResizable(false);
        setUndecorated(true);  // Make the JFrame undecorated

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Create a JPanel as the contentPane with null layout
        contentPane = new JPanel(null);
        setContentPane(contentPane);

        // Add a JLabel for the background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/ui/swing/resources/images/helpUI/screenBackground.jpg")));
        backgroundLabel.setBounds(0, 0, width, height);
        contentPane.add(backgroundLabel);

        // Add a label for information text at the top center
        JLabel infoLabel = new JLabel("Game Paused", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE); // Set text color to white
        infoLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size to 30
        infoLabel.setBounds(0, 30, width, 80); // Adjust as needed
        backgroundLabel.add(infoLabel); // Add to the background label

        // Initialize the JButton for the resume action in the center
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame != null && mainFrame instanceof JFrame) {
                    JFrame mainJFrame = (JFrame) mainFrame;
                    mainJFrame.setState(Frame.NORMAL);
                    mainJFrame.toFront();
                    mainJFrame.requestFocus();
                    mainJFrame.setVisible(true);
                }
                if (menuScreen != null) {
                    menuScreen.dispose(); // or menuScreen.setVisible(false); based on your need
                }
                PauseScreen.this.dispose();
            }
        });

        resumeButton.setBounds(width / 2 - 50, height / 2 - 25, 100, 50); // Centered button
        backgroundLabel.add(resumeButton); // Add to the background label
    }
*/
    public void display() {
        setVisible(true);
    }
    
    public void close() {
    	setVisible(false);
    }
}