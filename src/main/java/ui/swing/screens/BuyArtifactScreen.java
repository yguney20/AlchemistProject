package ui.swing.screens;

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

	private JPanel contentPane;
	private JButton buyButton;
    private int initialX;
    private int initialY;
    private int artifactCardId;
    private JLabel selected;
    private GameController gameController = GameController.getInstance();
    private JLabel message;
    List<ArtifactCard> artifactCards;
    private JButton quitButton = new JButton("X");
    
    private JFXPanel fxPanel; // The JavaFX panel to embed JavaFX content

	/**
	 * Create the frame.
	 */
	public BuyArtifactScreen() {
		initializeFrame();
        initializeJavaFXComponents();
        
        
    /*    
		setTitle("Buy Artifact");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 293);
		setUndecorated(true);
		
		try {
	        BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/ui/swing/resources/images/entranceUI/loginbackground.jpg"));
	        contentPane = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	            }
	        };       
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	        
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
	        add(quitButton);
	        addArtifacts();
	        addJButtons();
	        addJLabels();
	        addActionEvent();
	        
	        //ImageIcon icon = new ImageIcon(getClass().getResource("UI/Swing/Images/logo.png"));
	        //setIconImage(icon.getImage());
	        
	        
		
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        // Add a MouseListener to make the frame movable
        contentPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        contentPane.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialX;
                int y = getLocation().y + e.getY() - initialY;
                setLocation(x, y);
            }
        });*/
		
	}
	
	private void initializeFrame() {
        setTitle("Ku Alchemist Buy Artifact Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 293);
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
    

	/*
	public void addArtifacts() {
		try {
			
			artifactCards = gameController.getAvailableArtifacts(); 
	        int x = 25;
	        int y = 20;
	        int buttonWidth = 139;
	        int buttonHeight = 165;
	        int horizontalSpacing = 20;
	        
	        Map<ArtifactCard, JButton> artifactButtonMap = new HashMap<>();


	        for (ArtifactCard card : artifactCards) {
	        	JButton artifactButton = new JButton(card.getName());
	        	artifactButton.setBounds(x, y, buttonWidth, buttonHeight);
	        	artifactButton.setContentAreaFilled(false);
	        	artifactButton.setBorder(BorderFactory.createEmptyBorder());
	        	setImage(card.getImagePath(), artifactButton);
	        	contentPane.add(artifactButton);

	        	// Add action listener for each artifact button
	        	artifactButton.addActionListener(e -> handleArtifactSelection(artifactButton));
	        
	        	artifactButtonMap.put(card, artifactButton);
            
	        	// Create label for the artifact
	        	JLabel artifactLabel = new JLabel(card.getName());
	        	artifactLabel.setForeground(Color.WHITE);
	        	artifactLabel.setFont(new Font("Arial", Font.BOLD, 12));
            	artifactLabel.setBounds(x, y+buttonHeight+20, buttonWidth, 14);
            	contentPane.add(artifactLabel);
            
            	// Adjust the position for the next button
            	x += buttonWidth + horizontalSpacing;
	        }		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addJButtons() {
		
		buyButton = new JButton("BUY");
		buyButton.setBounds(391, 222, 89, 23);
		contentPane.add(buyButton);
		
	}
	
	public void addJLabels() {
		
        selected = new JLabel("SELECTED");
        selected.setBackground(Color.RED);
        selected.setForeground(Color.WHITE); 
        selected.setFont(new Font("Arial", Font.BOLD, 15)); 
        selected.setOpaque(true);
        selected.setVisible(false);
        contentPane.add(selected);
        
        message = new JLabel("Please select an artifact");
        message.setBounds(350, 0, 200, 30); // Increased height for better visibility
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15)); // Larger and italic
        message.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message.setOpaque(true);
        message.setVisible(false);
        contentPane.add(message);
	}
	
	public void addActionEvent() {
		//elixirOfInsight.addActionListener(this);
		buyButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {

		if(event.getSource()==buyButton) {
			if(artifactCardId == 0) {
				message.setVisible(true);
                contentPane.setComponentZOrder(message, 0);
			} else {
				int currentPlayerId = gameController.getCurrentPlayer().getPlayerId();
				gameController.buyArtifactCard(currentPlayerId,artifactCardId);
				this.setVisible(false);
			}

		}
	}
	
	public void setImage(String path, JButton button) throws IOException {
		
		// Read the original image
        BufferedImage originalImage = ImageIO.read(getClass().getResource(path));

        // Get the dimensions of the button
        int buttonWidth = button.getWidth();
        int buttonHeight = button.getHeight();

        // Create a new image with better quality
        BufferedImage scaledImage = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_ARGB);
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance((double) buttonWidth / originalImage.getWidth(),
                        (double) buttonHeight / originalImage.getHeight()),
                AffineTransformOp.TYPE_BILINEAR);
        scaledImage = op.filter(originalImage, scaledImage);

        // Set the scaled image as the icon for the button
        button.setIcon(new ImageIcon(scaledImage));
        
	}
	
    private void handleArtifactSelection(JButton button) {            
    	int selectedArtifactCardId = 0;
        for (ArtifactCard card : artifactCards) {
            if (button.getText().equals(card.getName())) {
                selectedArtifactCardId = card.getArtifactId();
                break;
            }
        }

        if (selectedArtifactCardId != 0) {
            artifactCardId = selectedArtifactCardId;

            // Set the selected label position based on the clicked button
            selected.setBounds(button.getX(), button.getY() + button.getHeight(), button.getWidth() - 8, 20);
            selected.setVisible(true);
            contentPane.setComponentZOrder(selected, 0);
		}
	}*/
	
	
}
