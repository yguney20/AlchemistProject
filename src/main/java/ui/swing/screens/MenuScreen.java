package ui.swing.screens;

import javax.swing.*;

import domain.Game;
import domain.controllers.GameController;
import domain.controllers.LoginController;
import domain.gameobjects.GameObjectFactory;
import domain.gameobjects.Player;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.swing.screens.screencontrollers.EntranceScreenController;
import ui.swing.screens.screencontrollers.MenuScreenController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MenuScreen extends JFrame {

    //private GameController gameController = GameController.getInstance();
    
    private Frame boardFrame; // Reference to the main game frame
    private JFXPanel fxPanel;
    private static EntranceScreenController entranceScreenController = EntranceScreenController.getInstance();
    private static MenuScreen instance;

    private MenuScreen(Frame boardFrame) {
        this.boardFrame = boardFrame;
        initializeFrame();
        initializeJavaFXComponents();
    }
    
    private void initializeFrame() {
        setTitle("Ku Alchemist Menu Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 900, 505); // Adjust the size accordingly
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
        
    
    }
    
    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/MenuScreen.fxml"));
                loader.setController(MenuScreenController.getInstance());
                Parent root = loader.load();

                MenuScreenController.getInstance().setMenuScreenFrame(this);

                Scene scene = new Scene(root);
                fxPanel.setScene(scene);

                scene.getStylesheets().add("/ui/swing/screens/cssfiles/screenstyles.css");
                root.getStyleClass().add("root");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static synchronized MenuScreen getInstance(Frame frame) {
        if (instance == null) {
            instance = new MenuScreen(frame);
        }
        return instance;
    }
    
    
    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
    
    
    public void close() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }
    /*
    private void initializeUI() {
        setTitle("Game Menu");
        setSize(1000, 800);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);

        contentPane = new CustomPanel();
        contentPane.setLayout(null);

        setupButtons();  // Now contentPane is initialized before this call
        setupButtonListeners();

        setContentPane(contentPane);
    }


    private void setupButtons() {
        backButton.setBounds(10, 10, 75, 25);
        pauseButton.setBounds(400, 200, 200, 50);
        helpButton.setBounds(400, 300, 200, 50);
        settingsButton.setBounds(400, 400, 200, 50);
        quitGameButton.setBounds(400, 500, 200, 50);
        quitButton.setBounds(getWidth() - 70, 20, 80, 20);
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        
        Font pacificoFont = new Font("Pacifico", Font.PLAIN, 12);
        Font largerFont = pacificoFont.deriveFont(pacificoFont.getSize() + 24f);
        quitButton.setFont(largerFont);
        quitButton.setForeground(Color.RED);

        // Add buttons to the custom JPanel
        contentPane.add(backButton);
        contentPane.add(pauseButton);
        contentPane.add(helpButton);
        contentPane.add(quitGameButton);
        contentPane.add(quitButton);
        contentPane.add(settingsButton);
    }

    private void setupButtonListeners() {
        backButton.addActionListener(e -> {
            boardFrame.setVisible(true);
            dispose();
        });

        pauseButton.addActionListener(e -> {
            boardFrame.setVisible(false);
            PauseScreen pauseScreen = new PauseScreen(1000, 800, boardFrame, MenuScreen.this);
            pauseScreen.display();
        });

        helpButton.addActionListener(e -> {
            setVisible(false);
            HelpScreen helpScreen = new HelpScreen(this);
            helpScreen.display();
        });
        
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SettingsScreen settingsScreen = new SettingsScreen(GameController.getSettingsState(), MenuScreen.this);
                settingsScreen.display();
            }
        });


        quitGameButton.addActionListener(e -> {
            Game.destroyInstance();
            GameObjectFactory.destroyInstance();
            GameController.destroyInstance();
            LoginController.destroyInstance();
            Player.setPlayerList(new ArrayList<Player>());
            boardFrame.dispose();
            GameEntranceScreen entranceScreen = new GameEntranceScreen();
            entranceScreen.display();
            dispose();
        });

        quitButton.addActionListener(e -> dispose());

        addButtonHoverEffect(pauseButton);
        addButtonHoverEffect(helpButton);
        addButtonHoverEffect(quitGameButton);
        addButtonHoverEffect(settingsButton);

    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(UIManager.getColor("Button.background"));
            }
        });
    }

    private class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image backgroundImage = new ImageIcon(getClass().getResource("/ui/swing/resources/images/helpUI/screenBackground.jpg")).getImage();
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }*/

}
