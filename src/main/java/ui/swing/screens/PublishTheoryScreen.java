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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Game;
import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Molecule;

public class PublishTheoryScreen extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JButton publishButton;
    private int initialX;
    private int initialY;
    private IngredientCard ingredientCard;
    private Molecule molecule;
    private GameController gameController = GameController.getInstance();
    private JLabel selected1;
    private JLabel selected2;
    private JLabel message;
    private JLabel message2;
    private JLabel ingredientLabel;
    private JLabel moleculeLabel;
    Map<String, String> ingredientNameAndPaths;
    List<IngredientCard> ingredientCards = IngredientCard.getIngredientList();
    List<Molecule> molecules = Molecule.getMoleculeList();
    private JButton quitButton = new JButton("X");
    

	/**
	 * Create the frame.
	 */
	public PublishTheoryScreen() {
		setTitle("Publish Theory");
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
	        addIngredients();
	        addMolecules();
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
			
			ingredientNameAndPaths = gameController.createIngredientNameAndPathList(); 
	        int x = 20;
	        int y = 40;
	        int buttonWidth = 139;
	        int buttonHeight = 165;
	        int horizontalSpacing = 20;
	        
	        Map<String, JButton> ingredientButtonMap = new HashMap<>();


	        for (Map.Entry<String, String> entry : ingredientNameAndPaths.entrySet()) {
	        	
	            String cardName = entry.getKey();
	            String imagePath = entry.getValue();
	            
	            JButton ingredientButton = new JButton(cardName);
	        	ingredientButton.setBounds(x, y, buttonWidth, buttonHeight);
	        	ingredientButton.setContentAreaFilled(false);
	        	ingredientButton.setBorder(BorderFactory.createEmptyBorder());
	            setImage(imagePath, ingredientButton);
	        	contentPane.add(ingredientButton);

	        	// Add action listener for each ingredient button
	        	ingredientButton.addActionListener(e -> handleIngredientSelection(ingredientButton));
	        
	            ingredientButtonMap.put(cardName, ingredientButton);
            
	        	// Create label for the ingredient
	        	JLabel ingredientLabel = new JLabel(cardName);
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
	
	public void addMolecules() {
		try {
		int x = 20;
        int y = 280;
        int buttonWidth = 139;
        int buttonHeight = 165;
        int horizontalSpacing = 20;
        
        for(Molecule molecule : molecules) {
            JButton moleculeButton = new JButton(molecule.getImagePath());
            moleculeButton.setBounds(x, y, buttonWidth, buttonHeight);
            moleculeButton.setContentAreaFilled(false);
            moleculeButton.setBorder(BorderFactory.createEmptyBorder());
			setImage(molecule.getImagePath(), moleculeButton);
        	contentPane.add(moleculeButton);

        	// Add action listener for each molecule button
        	moleculeButton.addActionListener(e -> handleMoleculeSelection(moleculeButton));
        	// Adjust the position for the next button
        	x += buttonWidth + horizontalSpacing;
        }
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void addJButtons() {
		
		publishButton = new JButton("Publish");
		publishButton.setBounds(500, 500, 200, 23);
		contentPane.add(publishButton);
		
	}
	
	public void addJLabels() {
		
		ingredientLabel = new JLabel("Select an ingredient");
        ingredientLabel.setBounds(20,10,250,25);
        ingredientLabel.setForeground(Color.WHITE); 
        ingredientLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        contentPane.add(ingredientLabel);
        
		moleculeLabel = new JLabel("Select a molecule");
        moleculeLabel.setBounds(20,250,250,25);
        moleculeLabel.setForeground(Color.WHITE); 
        moleculeLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        contentPane.add(moleculeLabel);
		
        selected1 = new JLabel("SELECTED");
        selected1.setBackground(Color.RED);
        selected1.setForeground(Color.WHITE); 
        selected1.setFont(new Font("Arial", Font.BOLD, 15)); 
        selected1.setOpaque(true);
        selected1.setVisible(false);
        contentPane.add(selected1);
        
        selected2 = new JLabel("SELECTED");
        selected2.setBackground(Color.RED);
        selected2.setForeground(Color.WHITE); 
        selected2.setFont(new Font("Arial", Font.BOLD, 15)); 
        selected2.setOpaque(true);
        selected2.setVisible(false);
        contentPane.add(selected2);
        
        message = new JLabel("Please select an ingredient and a molecule");
        message.setBounds(350, 0, 400, 30); // Increased height for better visibility
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15)); // Larger and italic
        message.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message.setOpaque(true);
        message.setVisible(false);
        contentPane.add(message);
        
        message2 = new JLabel("A theory has already been published for this ingredient");
        message2.setBounds(250, 0, 500, 30); // Increased height for better visibility
        message2.setForeground(Color.WHITE);
        message2.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15)); // Larger and italic
        message2.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message2.setOpaque(true);
        message2.setVisible(false);
        contentPane.add(message2);
        
        
	}
	
	public void addActionEvent() {
		publishButton.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource()==publishButton) {
			if(ingredientCard == null || molecule == null) {
				message.setVisible(true);
                contentPane.setComponentZOrder(message, 0);
			} else {
				gameController.publishTheory(ingredientCard, molecule);
				if(!gameController.getActionPerformed()) {
					message.setVisible(false);
					message2.setVisible(true);
				} else {
					this.setVisible(false);
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
	            selected1.setBounds(button.getX(), button.getY() + button.getHeight(), button.getWidth() - 8, 20);
	            selected1.setVisible(true);
	            contentPane.setComponentZOrder(selected1, 0);
	        }
	    }

	 private void handleMoleculeSelection(JButton button) {            
		 Molecule selectedMolecule = null;
	        for (Molecule molecule : molecules) {
	            if (button.getText().equals(molecule.getImagePath())) {
	                selectedMolecule = molecule;
	                break;
	            }
	        }

	        if (selectedMolecule != null) {
	            molecule = selectedMolecule;

	            // Set the selected label position based on the clicked button
	            selected2.setBounds(button.getX(), button.getY() + button.getHeight(), button.getWidth() - 8, 20);
	            selected2.setVisible(true);
	            contentPane.setComponentZOrder(selected2, 0);
	        }
	    }     
	  
}
