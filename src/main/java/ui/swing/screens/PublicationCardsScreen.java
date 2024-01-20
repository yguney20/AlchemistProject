package ui.swing.screens;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.PublicationCard;

public class PublicationCardsScreen extends JFrame{
	
	private JPanel contentPane;
    private int initialX;
    private int initialY;
    private GameController gameController = GameController.getInstance();
    private JLabel publicationLabel;
    List<PublicationCard> publicationCards;
    private JButton quitButton = new JButton("X");
    

	/**
	 * Create the frame.
	 */
	public PublicationCardsScreen() {
		setTitle("Publication Cards");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1500, 500);
		setUndecorated(true);
		
		try {
	        BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/images/entranceUI/loginbackground.jpg"));
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
	        
	        quitButton.setBounds(getWidth() - 70, 0, 80, 20);
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

	        addJLabels();
	        addPublications();

		
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
        });
		
	}
	
	public void display() {
		this.setVisible(true);
	}
	
    public void addPublications() {
        int x = 20;
        int y = 40;
        int width = 140;
        int height = 300;
        int horizontalSpacing = 20;
        List<PublicationCard> publicationCards;

        if (gameController.isOnlineMode()){
            	publicationCards = gameController.getGameState().getPublicationCardList();
            } else {
            	publicationCards = GameController.getPublicationCardList();
        }
        
       

        for (int i = 0; i < publicationCards.size(); i++) {


            PublicationCard publicationCard = publicationCards.get(i);

            // Create a panel for each publication card
            JPanel publicationPanel = new JPanel(new GridLayout(2, 1));
            publicationPanel.setBounds(x, y, width, height);

            // Add the ingredient image to the panel
			addImage(publicationCard.getAssociatedIngredient().getImagePath(), publicationPanel, width);

			// Add the molecule image to the panel
			addImage(publicationCard.getAssociatedMolecule().getImagePath(), publicationPanel, width);

            // Add the publication panel to the contentPane
            contentPane.add(publicationPanel);
            
            JLabel ownerLabel = new JLabel("Owner: " + publicationCard.getOwner().getNickname());
            ownerLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            ownerLabel.setForeground(Color.WHITE); 
            ownerLabel.setBounds(x, y+height+10, width, 30);
            contentPane.add(ownerLabel);
            
            JLabel ingredientLabel = new JLabel("Ingredient: " + publicationCard.getTheory().getIngredient().getName());
            ingredientLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            ingredientLabel.setForeground(Color.WHITE); 
            ingredientLabel.setBounds(x, y+height+40, width, 30);
            contentPane.add(ingredientLabel);

            x += width + horizontalSpacing;
        }
    }

    private void addImage(String path, JPanel panel, int targetWidth) {
        try {
            // Load the image
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));

            // Get the original image
            Image originalImage = originalIcon.getImage();

            // Calculate the target height while maintaining the original aspect ratio
            int targetHeight = (int) ((double) originalIcon.getIconHeight() / originalIcon.getIconWidth() * targetWidth);

            // Scale the image to fit the target dimensions
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            // Create a new ImageIcon from the scaled image
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Create a JLabel with the scaled image
            JLabel imageLabel = new JLabel(scaledIcon);

            // Add the JLabel to the panel
            panel.add(imageLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public void addJLabels() {
		
		publicationLabel = new JLabel("Publication Cards");
        publicationLabel.setBounds(20,10,250,25);
        publicationLabel.setForeground(Color.WHITE); 
        publicationLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        contentPane.add(publicationLabel);
        
		
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
	

}

