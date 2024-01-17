package ui.swing.screens.scenes;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.HelpScreenController;
import ui.swing.screens.screencontrollers.MenuScreenController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class HelpScreen extends JFrame {

    private JButton backButton = new JButton("Back");
    private JPanel contentPane;
    private int initialX;
    private int initialY;
    private GameController gameController = GameController.getInstance();
	private JFXPanel jfxPanel;

	public HelpScreen(Frame frame) {
		initializeFrame();
		initializeJavaFXComponents();
	}

	private void initializeFrame() {
		setTitle("Help Screen");
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
						getClass().getResource("/ui/swing/screens/fxmlfiles/HelpScreen.fxml")); // Update
																													
				Parent root = loader.load();

				HelpScreenController controller = loader.getController();
				// Additional setup for the controller if needed
				controller.setHelpScreenFrame(this);

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