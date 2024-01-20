package ui.swing.screens.scenes;

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.*;

import domain.controllers.GameController;
import ui.swing.screens.screencontrollers.TransmuteIngredientScreenController;

import java.awt.*;
import java.io.IOException;

public class TransmuteIngredientScreen extends JFrame {

    private GameController gameController = GameController.getInstance();
    private JFXPanel fxPanel; // The JavaFX panel to embed JavaFX content

    public TransmuteIngredientScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }

    private void initializeFrame() {
        setTitle("Transmute Ingredient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 913, 693);
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
    }

    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/TransmuteIngredientScreen.fxml"));
                Parent root = loader.load();

                // Assuming you have a controller called TransmuteIngredientScreenController
                TransmuteIngredientScreenController controller = loader.getController();
                controller.setTransmuteIngredientScreen(this); // You need to implement this method in your controller

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
        SwingUtilities.invokeLater(() -> dispose());
    }
}
