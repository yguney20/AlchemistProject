package UI.Swing;

import javax.swing.*;

import domain.Game;
import domain.GameObjects.GameObjectFactory;
import domain.GameObjects.Player;
import domain.controllers.GameController;
import domain.controllers.LoginController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MenuScreen extends JFrame {

    //private GameController gameController = GameController.getInstance();
    private JButton quitButton = new JButton("X");
    private JButton pauseButton = new JButton("Pause the Game");
    private JButton helpButton = new JButton("Help");
    private JButton quitGameButton = new JButton("Quit the Game");
    private JButton backButton = new JButton("Back");
    private JButton settingsButton = new JButton("Settings");
    private Frame boardFrame; // Reference to the main game frame
    private CustomPanel contentPane;

    public MenuScreen(Frame boardFrame) {
        this.boardFrame = boardFrame;
        initializeUI();
    }

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
                SettingsScreen settingsScreen = new SettingsScreen(MenuScreen.this);
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

    public void display() {
        setVisible(true);
    }

    private class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image backgroundImage = new ImageIcon(getClass().getResource("/UI/Swing/Images/screenBackground.jpg")).getImage();
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
