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

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.swing.screens.screencontrollers.SelectArtifactScreenController;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.Player;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class SelectArtifactScreen extends JFrame {

	private JPanel contentPane;
	private JButton selectButton;
    private int initialX;
    private int initialY;
    private ArtifactCard artifactCard;
    private JLabel selected;
    private GameController gameController = GameController.getInstance();
    private JLabel message;
    List<ArtifactCard> artifactCards;
    private JButton quitButton = new JButton("X");
    private JFXPanel fxPanel;
       private String clientName = gameController.getClientPlayer();

    public SelectArtifactScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }

    private void initializeFrame() {
        setTitle("Select Artifact to Use");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 293);
        setUndecorated(true);
    }

    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            fxPanel = new JFXPanel();
            add(fxPanel); // Add JFXPanel to JFrame

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/SelectArtifactScreen.fxml"));
                Parent root = loader.load();

                SelectArtifactScreenController controller = loader.getController();
                controller.setSelectArtifactScreen(this);

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
    
    public void setNewScene(Scene newScene) {
        Platform.runLater(() -> fxPanel.setScene(newScene));
    }

}
