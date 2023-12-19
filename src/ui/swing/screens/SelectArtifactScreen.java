package ui.swing.screens;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.gameobjects.ArtifactCard;
import domain.gameobjects.Player;
import domain.controllers.GameController;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class SelectArtifactScreen extends JFrame  implements ActionListener{

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
    

	/**
	 * Create the frame.
	 */
	public SelectArtifactScreen() {
		setTitle("Select Artifact to Use");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 293);
		
		try {
	        BufferedImage backgroundImage = ImageIO.read(getClass().getResourceAsStream("/UI/Swing/Images/loginbackground.jpg"));
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
	
	public void addArtifacts() {
		try {
			
			artifactCards = gameController.getPlayerArtifactCards(); 
	        int x = 60;
	        int y = 30;
	        int buttonWidth = 150;
	        int buttonHeight = 185;
	        int horizontalSpacing = 60;
	        
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
		
		selectButton = new JButton("SELECT");
		selectButton.setBounds(391, 222, 100, 23);
		contentPane.add(selectButton);
		
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
		selectButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {

		if(event.getSource()==selectButton) {
			if(artifactCard==null) {
				message.setVisible(true);
                contentPane.setComponentZOrder(message, 0);
			} else  { // This part is problemactic
				Player currentPlayer = gameController.getCurrentPlayer();
				gameController.UseArtifactCard(artifactCard, currentPlayer);
                openArtifactSpecificUI(artifactCard);
				this.setVisible(false);
                
                // If artifact card = "Elixir of Insight" then his Screen would be opend 
                //otherwise which name has the artifact then open its screen
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
    	ArtifactCard selectedArtifactCard = null;
        for (ArtifactCard card : artifactCards) {
            if (button.getText().equals(card.getName())) {
                selectedArtifactCard = card;
                break;
            }
        }

        if (selectedArtifactCard != null) {
            artifactCard = selectedArtifactCard;

            // Set the selected label position based on the clicked button
            selected.setBounds(button.getX(), button.getY() + button.getHeight(), button.getWidth() - 8, 20);
            selected.setVisible(true);
            contentPane.setComponentZOrder(selected, 0);
        }
        
    }

    private void openArtifactSpecificUI(ArtifactCard artifact) {
        // Logic to open the specific UI based on the artifact
        if ("Elixir of Insight".equals(artifact.getName())) {
            ElixirOfInsightArtifactScreen elixirOfInsightArtifactScreen = new ElixirOfInsightArtifactScreen();
            elixirOfInsightArtifactScreen.display();
        }
        // Handle other artifacts similarly
    }
}
