package ui.swing.screens.scenes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.DeductionBoardController;
import java.io.IOException;

public class DeductionBoard extends JFrame {

    private JPanel contentPane;
    private JFXPanel jfxPanel;
    private DeductionBoardController controller;

    public DeductionBoard() {
        initializeFrame();
        initializeJavaFXComponents();
    }
    
    private void initializeFrame() {
        setTitle("Deduction Board");
        setSize(1500, 1000); // Set to your preferred size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        jfxPanel = new JFXPanel();
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(jfxPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/ui/swing/screens/fxmlfiles/DeductionBoard.fxml")); // Ensure the path is correct
                Parent root = loader.load();

                controller = loader.getController();
                controller.setDeductionBoardFrame(this);
                // You can set up controller here if needed
                // controller.setDeductionBoardScreen(this);

                Scene scene = new Scene(root);
                jfxPanel.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        });
    }
    
    public void clearSignPlacements() {
        Platform.runLater(() -> {
            if (controller != null) {
                controller.clearSignPlacements();
            }
        });
    }
    
    public void addSignPlacement(Point point, DeductionBoardController.Sign sign) {
        Platform.runLater(() -> {
            if (controller != null) {
                controller.addSignPlacement(point, sign);
            }
        });
    }
    
    public DeductionBoardController getController() {
        return controller;
    }

    public void display() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	public void close() {
		SwingUtilities.invokeLater(() -> setVisible(false));
	}

    
}
