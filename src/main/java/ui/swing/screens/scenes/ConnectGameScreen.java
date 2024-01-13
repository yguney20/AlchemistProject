package ui.swing.screens.scenes;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import domain.controllers.GameController;
import domain.controllers.OnlineGameAdapter;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.ConnectGameScreenController;

public class ConnectGameScreen extends JFrame {
    private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private JLabel ipTextInf;
    private JTextField ipTextField;
    private JButton connectButton;
    private JLabel statusLabel; // To display connection status
    private JLabel selected;
	private GameController gameController = GameController.getInstance();
	private JFXPanel jfxPanel;

	public ConnectGameScreen(Frame frame) {
		initializeFrame();
		initializeJavaFXComponents();
	}

	private void initializeFrame() {
		setTitle("Connect to a Game");
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
						getClass().getResource("/ui/swing/screens/fxmlfiles/ConnectGameScreen.fxml")); // Update
																													// path
				Parent root = loader.load();

				ConnectGameScreenController controller = loader.getController();
				// Additional setup for the controller if needed
				controller.setConnectGameScreenFrame(this);

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