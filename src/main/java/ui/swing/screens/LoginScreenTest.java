package ui.swing.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import domain.controllers.LoginController;
import ui.swing.screens.screencomponents.AvatarCardScreen;
import ui.swing.screens.screencomponents.LoginBackground;


import java.awt.BorderLayout;

import ui.swing.model.*;
import java.awt.Cursor;



public class LoginScreenTest extends JFrame{

	private JFrame frame;
    private JPanel contentPane;
    private JLabel selected;
    private String player1Avatar;
    private String player2Avatar;
    private int initialX;
    private int initialY;
    private LoginController loginController = LoginController.getInstance();
	private Point initialClick;
	
	private LoginBackground background;
	
    /**
     * @throws IOException 
     */
    public LoginScreenTest(){
    	this.frame = this;
        setTitle("KU Alchemist Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 1200, 600);
        setUndecorated(true);
        setResizable(false);
        
      
        background = new LoginBackground();
        setContentPane(background);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setOpaque(false); // Make mainPanel transparent
        
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(null);
        
        
        //mainPanel.setBackground(Color.BLACK);
        
        JPanel panel = new JPanel();
        panel.setBounds(501, 115, 689, 213);
        mainPanel.add(panel);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                background.play();
            }
        });
        
        AvatarCardScreen avatarCardScreen = new AvatarCardScreen(new CardModel(new ImageIcon(getClass().getResource(
        		"/ui/swing/resources/images/avatar/a1.png")), 
        		"Avatar 1", 
        		"A wise and enigmatic wizard, known for his mastery over elemental magic and an uncanny ability to foresee events before they unfold."));
        avatarCardScreen.setBounds(393, 11, 150, 200);
        avatarCardScreen.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		avatarCardScreen.setSelected(!avatarCardScreen.isSelected());
        		repaint();
        	}
        });
        panel.setLayout(null);
        avatarCardScreen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(avatarCardScreen);
        
        
        AvatarCardScreen avatarCardScreen2 = new AvatarCardScreen(new CardModel(new ImageIcon(getClass().getResource(
        		"/ui/swing/resources/images/avatar/a1.png")), 
        		"Lean Java UI", 
        		"Leaning java\nswing ui design\nlike and Subscribe\nthank for watch"));
        avatarCardScreen.setBounds(180, 11, 150, 200);

        panel.add(avatarCardScreen2);
        
        JLabel lblNewLabel = new JLabel("New label");
        //lblNewLabel.setIcon(new ImageIcon(LoginScreen.class.getResource("/ui/swing/resources/animations/dragon.mp4")));
        lblNewLabel.setBounds(72, 172, 197, 126);
        mainPanel.add(lblNewLabel);
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }

            public void mouseDragged(MouseEvent e) {
                // get location of Window
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        };
        
       
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
	}

    public void display() {
        setVisible(true);
    }
   }

