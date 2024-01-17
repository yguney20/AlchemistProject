package ui.swing.screens.scenes;

import java.awt.Color;

import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.PlayerIngredientsScreenController;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class PlayerIngredientsScreen extends JFrame{

	private JPanel contentPane;
    private int initialX;
    private int initialY;
    private IngredientCard ingredientCard;
    private GameController gameController = GameController.getInstance();
    List<IngredientCard> ingredientCards;
    private JButton quitButton = new JButton("X");
	private JFXPanel jfxPanel;

	public PlayerIngredientsScreen() {
		initializeFrame();
		initializeJavaFXComponents();
	}

	private void initializeFrame() {
		setTitle("My Ingredients");
		setSize(900, 450); // Set to your preferred size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center on screen

		jfxPanel = new JFXPanel();
		add(jfxPanel);
	}

	private void initializeJavaFXComponents() {
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/ui/swing/screens/fxmlfiles/PlayerIngredientsScreen.fxml")); // Update
																													
				Parent root = loader.load();

				PlayerIngredientsScreenController controller = loader.getController();
				// Additional setup for the controller if needed
				controller.setPlayerIngredientsScreenFrame(this);

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

