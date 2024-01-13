package ui.swing.screens.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.ElixirOfInsightArtifactScreenController;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ElixirOfInsightArtifactScreen extends JFrame {

	private JLabel selected;
	private GameController gameController = GameController.getInstance();
	private JFXPanel jfxPanel;

	public ElixirOfInsightArtifactScreen() {
		initializeFrame();
		initializeJavaFXComponents();
	}

	private void initializeFrame() {
		setTitle("Elixir of Insight");
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
						getClass().getResource("/ui/swing/screens/fxmlfiles/ElixirOfInsightArtifactScreen.fxml")); // Update
																													// path
				Parent root = loader.load();

				ElixirOfInsightArtifactScreenController controller = loader.getController();
				// Additional setup for the controller if needed
				controller.setArtifactScreenFrame(this);

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
