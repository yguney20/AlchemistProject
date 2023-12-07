package UI.Swing;

import controllers.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuScreen extends JFrame {

	private GameController gameController = new GameController();
	private JFrame frame;
	private JButton quitButton = new JButton("X");
    private JButton pauseButton = new JButton("Pause the Game");
    private JButton helpButton = new JButton("Help");
    private JButton quitGameButton = new JButton("Quit the Game");
    private JButton backButton = new JButton("Back");

    public MenuScreen(Frame boardFrame) {
        // Set up the frame
    	this.frame = this;
        setTitle("Game Menu");
        setSize(1000, 800);
        setResizable(false);
        setUndecorated(true);  // Make the JFrame undecorated
        setLocationRelativeTo(null);

        /// Create a custom JPanel to set a background image
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                Image backgroundImage = new ImageIcon("src/UI/Swing/Images/screenBackground.jpg").getImage();
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set layout to null to use absolute positioning
        contentPane.setLayout(null);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/UI/Swing/Images/logo.png"));
        setIconImage(icon.getImage());
        
        
        // Add ActionListener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct user to GameEntranceScreen
                boardFrame.setVisible(true);
                dispose();
            }
        });
        
        // Add the Back button to the top left of the frame
        backButton.setBounds(10, 10, 75, 25);
        
        
        // Add buttons to the frame
        pauseButton.setBounds(400, 200, 200, 50);
        helpButton.setBounds(400, 300, 200, 50);
        quitGameButton.setBounds(400, 400, 200, 50);

        // Add mouse listeners for button hover effects
        addButtonHoverEffect(pauseButton);
        addButtonHoverEffect(helpButton);
        addButtonHoverEffect(quitGameButton);

        // Add action listeners for button clicks
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	setVisible(false);  // Close current frame
            	PauseScreen pauseScreen = new PauseScreen(1000, 800, boardFrame);
            	pauseScreen.display();
            }
        });
        

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);  // Close current frame
                HelpScreen helpScreen = new HelpScreen(frame);
                helpScreen.display();
            }
        });

        quitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.destroyInstance();
                boardFrame.dispose();
                GameEntranceScreen entranceScreen = new GameEntranceScreen();
                entranceScreen.display();
                dispose();
            }
        });

    
        quitButton.setBounds(getWidth() - 70, 20, 80, 20);
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
     // Increase the font size
        Font pacificoFont = new Font("Pacifico", Font.PLAIN, 12);
        Font largerFont = pacificoFont.deriveFont(pacificoFont.getSize() + 24f);
        quitButton.setFont(largerFont);
       
        // Set the red "X" as the text
        quitButton.setForeground(Color.RED);
        quitButton.setText("X");
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });

        // Add buttons to the custom JPanel
        contentPane.add(backButton);
        contentPane.add(pauseButton);
        contentPane.add(helpButton);
        contentPane.add(quitGameButton);
        contentPane.add(quitButton);

        // Set the content pane to the custom JPanel
        setContentPane(contentPane);
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
}
