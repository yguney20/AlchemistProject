package ui.swing.screens.scenes;

import javax.swing.*;

import domain.controllers.GameController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.HostGameScreenController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostGameScreen extends JFrame {
	private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JButton startGameButton;
    private JLabel ipLabel;
    private GameController gameController = GameController.getInstance();
	private JFXPanel jfxPanel;

	public HostGameScreen(Frame frame) {
		initializeFrame();
		initializeJavaFXComponents();
	}

	private void initializeFrame() {
		setTitle("Host a Game");
		setSize(900, 650); // Set to your preferred size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center on screen

		jfxPanel = new JFXPanel();
		add(jfxPanel);
	}

	private void initializeJavaFXComponents() {
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/ui/swing/screens/fxmlfiles/HostGameScreen.fxml")); // Update
																													
				Parent root = loader.load();

				HostGameScreenController controller = loader.getController();
				// Additional setup for the controller if needed
				controller.setHostGameScreenFrame(this);

				Scene scene = new Scene(root);
				jfxPanel.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace(); // Handle exceptions appropriately
			}
		});
	}

	public void display() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	public void close() {
		SwingUtilities.invokeLater(() -> setVisible(false));
	}

    
}