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

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;
import ui.swing.screens.scenes.ElixirOfInsightArtifactScreen;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class TransmuteIngredientScreen extends JFrame  implements ActionListener{

	private JPanel contentPane;
	private JButton transmuteButton;
    private int initialX;
    private int initialY;
    private IngredientCard ingredientCard;
    private JLabel selected;
    private GameController gameController = GameController.getInstance();
    private JLabel message;
    List<IngredientCard> ingredientCards;
    private JButton quitButton = new JButton("X");
    

	/**
	 * Create the frame.
	 */
	public TransmuteIngredientScreen() {
		setTitle("Transmute Ingredient");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 913, 293);
		
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
	        
	        //ImageIcon icon = new ImageIcon(getClass().getResource("UI/Swing/Images/logo.png"));
	        //setIconImage(icon.getImage());
	        
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

	        
	        addIngredients();
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
	
	public void addIngredients() {
		try {
			
			ingredientCards = gameController.getCurrentPlayer().getIngredientInventory(); 
	        int x = 25;
	        int y = 20;
	        int buttonWidth = 139;
	        int buttonHeight = 165;
	        int horizontalSpacing = 20;
	        
	        Map<IngredientCard, JButton> ingredientButtonMap = new HashMap<>();


	        for (IngredientCard card : ingredientCards) {
	        	JButton ingredientButton = new JButton(card.getName());
	        	ingredientButton.setBounds(x, y, buttonWidth, buttonHeight);
	        	ingredientButton.setContentAreaFilled(false);
	        	ingredientButton.setBorder(BorderFactory.createEmptyBorder());
	        	setImage(card.getImagePath(), ingredientButton);
	        	contentPane.add(ingredientButton);

	        	// Add action listener for each artifact button
	        	ingredientButton.addActionListener(e -> handleIngredientSelection(ingredientButton));
	        
	        	ingredientButtonMap.put(card, ingredientButton);
            
	        	// Create label for the artifact
	        	JLabel ingredientLabel = new JLabel(card.getName());
	        	ingredientLabel.setForeground(Color.WHITE);
	        	ingredientLabel.setFont(new Font("Arial", Font.BOLD, 12));
	        	ingredientLabel.setBounds(x, y+buttonHeight+20, buttonWidth, 14);
            	contentPane.add(ingredientLabel);
            
            	// Adjust the position for the next button
            	x += buttonWidth + horizontalSpacing;
	        }		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addJButtons() {
		
		transmuteButton = new JButton("TRANSMUTE");
		transmuteButton.setBounds(391, 222, 120, 23);
		contentPane.add(transmuteButton);
		
	}
	
	public void addJLabels() {
		
        selected = new JLabel("SELECTED");
        selected.setBackground(Color.RED);
        selected.setForeground(Color.WHITE); 
        selected.setFont(new Font("Arial", Font.BOLD, 15)); 
        selected.setOpaque(true);
        selected.setVisible(false);
        contentPane.add(selected);
        
        message = new JLabel("Please select an ingredient");
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
		transmuteButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {

		if(event.getSource()==transmuteButton) {
			if(ingredientCard==null) {
				message.setVisible(true);
                contentPane.setComponentZOrder(message, 0);
			} else {
				int currentPlayerId = gameController.getCurrentPlayer().getPlayerId();
				int ingredientCardId = ingredientCard.getCardId();
				gameController.transmuteIngredient(currentPlayerId, ingredientCardId);
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
	
    private void handleIngredientSelection(JButton button) {            
    	IngredientCard selectedIngredientCard = null;
        for (IngredientCard card : ingredientCards) {
            if (button.getText().equals(card.getName())) {
                selectedIngredientCard = card;
                break;
            }
        }

        if (selectedIngredientCard != null) {
            ingredientCard = selectedIngredientCard;

            // Set the selected label position based on the clicked button
            selected.setBounds(button.getX(), button.getY() + button.getHeight(), button.getWidth() - 8, 20);
            selected.setVisible(true);
            contentPane.setComponentZOrder(selected, 0);
        }
    }
}

