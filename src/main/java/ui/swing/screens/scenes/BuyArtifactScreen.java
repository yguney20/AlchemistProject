package ui.swing.screens.scenes;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import ui.swing.screens.screencontrollers.BuyArtifactScreenController;
import ui.swing.screens.screencontrollers.EntranceScreenController;
import domain.controllers.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.Player;

import javax.imageio.ImageIO;


public class BuyArtifactScreen extends JFrame {

    private GameController gameController = GameController.getInstance();
    List<ArtifactCard> artifactCards;
    private JFXPanel fxPanel; // The JavaFX panel to embed JavaFX content

	public BuyArtifactScreen() {
		initializeFrame();
        initializeJavaFXComponents();
        
	}
	
	private void initializeFrame() {
        setTitle("Ku Alchemist Buy Artifact Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 693);
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
    }
	
	private void initializeJavaFXComponents() {
	    Platform.runLater(() -> {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/BuyArtifactScreen.fxml"));
	            Parent root = loader.load();

	            BuyArtifactScreenController controller = loader.getController();
	            controller.setArtifactScreenFrame(this); // Pass the current JFrame to the controller

	            Scene scene = new Scene(root);
	            fxPanel.setScene(scene);
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
}
