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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Molecule;
import domain.gameobjects.PublicationCard;
import domain.gameobjects.ValidatedAspect;

public class DebunkTheoryScreen extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JButton debunkButton;
    private JButton selectedAspectButton;
    private JButton redButton;
    private JButton greenButton;
    private JButton blueButton;
    private int initialX;
    private int initialY;
    private Molecule.Component component;
    private GameController gameController = GameController.getInstance();
    private JLabel trueAspect;
    private JLabel message;
    private JLabel message2;
    private JLabel publicationLabel;
    private JLabel aspectLabel;
    private JPanel selectedPublicationPanel;
    List<PublicationCard> publicationCards = PublicationCard.getPublicationCardList();
    private PublicationCard publicationCard;
    private JButton quitButton = new JButton("X");
    

	/**
	 * Create the frame.
	 */
	public DebunkTheoryScreen() {
		setTitle("Debunk Theory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1500, 600);
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

	        addJButtons();
	        addJLabels();
	        addPublications();
	        addActionEvent();

		
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

            // Set up click event for the publication card
            publicationPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    handlePublicationCardSelection(publicationPanel, publicationCard);
                }
            });
            
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


	public void addJButtons() {
		
		debunkButton = new JButton("Debunk");
		debunkButton.setBounds(500, 500, 200, 23);
		contentPane.add(debunkButton);
		
		redButton = new JButton("RED");
		redButton.setBounds(10,400,150,25);
		contentPane.add(redButton);
		
		greenButton = new JButton("GREEN");
		greenButton.setBounds(210,400,150,25);
		contentPane.add(greenButton);
		
		blueButton = new JButton("BLUE");
		blueButton.setBounds(410,400,150,25);
		contentPane.add(blueButton);
		
	}
	
	public void addJLabels() {
		
		publicationLabel = new JLabel("Select a publication card");
        publicationLabel.setBounds(20,10,250,25);
        publicationLabel.setForeground(Color.WHITE); 
        publicationLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        contentPane.add(publicationLabel);
        
		aspectLabel = new JLabel("Select an aspect");
        aspectLabel.setBounds(20,350,250,25);
        aspectLabel.setForeground(Color.WHITE); 
        aspectLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        contentPane.add(aspectLabel);
        
        message = new JLabel("Please select a publication card and an aspect");
        message.setBounds(350, 0, 400, 30); // Increased height for better visibility
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15)); // Larger and italic
        message.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message.setOpaque(true);
        message.setVisible(false);
        contentPane.add(message);
        
        message2 = new JLabel("This aspect has already been debunked");
        message2.setBounds(250, 0, 500, 30); // Increased height for better visibility
        message2.setForeground(Color.WHITE);
        message2.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15)); // Larger and italic
        message2.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message2.setOpaque(true);
        message2.setVisible(false);
        contentPane.add(message2);        
        
        trueAspect = new JLabel();
        trueAspect.setBounds(150, 250, 900, 50);
        trueAspect.setForeground(Color.WHITE);
        trueAspect.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        trueAspect.setBackground(new Color(0, 0, 0, 200));
        trueAspect.setOpaque(true);
        trueAspect.setVisible(false);
        contentPane.add(trueAspect);
	}
	
	public void addActionEvent() {
		debunkButton.addActionListener(this);
		redButton.addActionListener(this);
		greenButton.addActionListener(this);
		blueButton.addActionListener(this);	
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource()==redButton) {
			handleAspectSelection(redButton);
			component = Molecule.Component.RED;
		}
		if(event.getSource()==greenButton) {
			handleAspectSelection(greenButton);
			component = Molecule.Component.GREEN;
		}
		if(event.getSource()==blueButton) {
			handleAspectSelection(blueButton);
			component = Molecule.Component.BLUE;
		}
		
		if(event.getSource()==debunkButton) {
			if(publicationCard == null || component == null) {
				message.setVisible(true);
                contentPane.setComponentZOrder(message, 0);
			} else {
				if(gameController.findValidatedAspectByIngredientComponent(publicationCard.getAssociatedIngredient(), component) != null) {
					message.setVisible(false);
					message2.setVisible(true);
				} else {
					int playerId = gameController.getCurrentPlayer().getPlayerId();
					int publicationId = publicationCard.getPublicationId();
					gameController.debunkTheory(playerId, publicationId, component);				
					ValidatedAspect validatedAspect = gameController.findValidatedAspectByIngredientComponent(publicationCard.getAssociatedIngredient(), component);
					trueAspect.setText(validatedAspect.toString());
					message.setVisible(false);
					message2.setVisible(false);
					trueAspect.setVisible(true);
				}
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
	
	private void handleAspectSelection(JButton button) {
			// Revert the color of the previously selected aspect button
			if (selectedAspectButton != null) {
				selectedAspectButton.setBackground(null);
			}	

			// Set the color of the newly selected aspect button
			button.setBackground(Color.GREEN);
			selectedAspectButton = button;
	} 
	
	private void handlePublicationCardSelection(JPanel publicationPanel, PublicationCard publicationCard) {
	    // Update the selected publication card
	    this.publicationCard = publicationCard;

	    // Display the selected publication card information or perform other actions
	    publicationLabel.setText("Selected Publication Card: " + publicationCard.getPublicationId());

	    // Reset the border of previously selected panel (if any)
	    if (selectedPublicationPanel != null) {
	        selectedPublicationPanel.setBorder(null);
	    }

	    // Add a border to indicate the selected panel
	    publicationPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

	    // Store the reference to the currently selected panel
	    selectedPublicationPanel = publicationPanel;

	}


}
