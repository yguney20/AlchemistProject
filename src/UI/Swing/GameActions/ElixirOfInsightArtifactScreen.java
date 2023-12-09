package UI.Swing.GameActions;

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

import domain.GameObjects.ArtifactCard;
import domain.GameObjects.IngredientCard;
import domain.GameObjects.Player;
import domain.controllers.GameController;


import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;



    public class ElixirOfInsightArtifactScreen extends JFrame  implements ActionListener {
    

	private JPanel contentPane;
    private JButton rightButton;
    private JButton leftButton;    
	private JButton doneButton;
    private int initialX;
    private int initialY;
    private JLabel selected;
    private GameController gameController = GameController.getInstance();
    private JLabel message;
    private JButton quitButton = new JButton("X");
    private IngredientCard selectedIngredientCard = null;
    private JButton ingredientButton0;
    private JButton ingredientButton1;
    private JButton ingredientButton2;
    private JLabel ingredientLabel0;
    private JLabel ingredientLabel1;
    private JLabel ingredientLabel2;
    private IngredientCard ingredientCard0 = gameController.getIngredientDeck().get(0); 
    private IngredientCard ingredientCard1 = gameController.getIngredientDeck().get(1); 
    private IngredientCard ingredientCard2 = gameController.getIngredientDeck().get(2); 
    

	/**
	 * Create the frame.
	 */
	public ElixirOfInsightArtifactScreen() {
        
		setTitle("Elixir of Insight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 555, 900, 350);
		
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

	        
	        int buttonWidth = 150;
	        int buttonHeight = 185;

            int x1 = 175;
	        int y1 = 40;

            int x2 = 375;
	        int y2 = 40;

            int x3 = 575;
	        int y3 = 40;

            ingredientButton0 = new JButton(ingredientCard0.getName());
            ingredientButton1 = new JButton(ingredientCard1.getName());
            ingredientButton2 = new JButton(ingredientCard2.getName());

            ingredientButton0.setBounds(x1, y1, buttonWidth, buttonHeight);
	        ingredientButton0.setContentAreaFilled(false);
	        ingredientButton0.setBorder(BorderFactory.createEmptyBorder());
	        setImage(ingredientCard0.getImagePath(), ingredientButton0);
	        contentPane.add(ingredientButton0);

            ingredientButton1.setBounds(x2, y2, buttonWidth, buttonHeight);
	        ingredientButton1.setContentAreaFilled(false);
	        ingredientButton1.setBorder(BorderFactory.createEmptyBorder());
	        setImage(ingredientCard1.getImagePath(), ingredientButton1);
	        contentPane.add(ingredientButton1);

            ingredientButton2.setBounds(x3, y3, buttonWidth, buttonHeight);
	        ingredientButton2.setContentAreaFilled(false);
	        ingredientButton2.setBorder(BorderFactory.createEmptyBorder());
	        setImage(ingredientCard2.getImagePath(), ingredientButton2);
	        contentPane.add(ingredientButton2);

            ingredientLabel0 = new JLabel(ingredientCard0.getName());
	        ingredientLabel0.setForeground(Color.WHITE);
	        ingredientLabel0.setFont(new Font("Arial", Font.BOLD, 12));
            ingredientLabel0.setBounds(x1 + 20, y1+buttonHeight+20, buttonWidth, 14);
            contentPane.add(ingredientLabel0);

            ingredientLabel1 = new JLabel(ingredientCard1.getName());
	        ingredientLabel1.setForeground(Color.WHITE);
	        ingredientLabel1.setFont(new Font("Arial", Font.BOLD, 12));
            ingredientLabel1.setBounds(x2 + 20, y2+buttonHeight+20, buttonWidth, 14);
            contentPane.add(ingredientLabel1);

            ingredientLabel2 = new JLabel(ingredientCard2.getName());
	        ingredientLabel2.setForeground(Color.WHITE);
	        ingredientLabel2.setFont(new Font("Arial", Font.BOLD, 12));
            ingredientLabel2.setBounds(x3 + 20, y3+buttonHeight+20, buttonWidth, 14);
            contentPane.add(ingredientLabel2);


            JLabel firstCard = new JLabel("First Card");
	        firstCard.setForeground(Color.WHITE);
	        firstCard.setFont(new Font("Arial", Font.BOLD, 12));
            firstCard.setBounds(x1 + 30, 15, 100, 15);
            contentPane.add(firstCard);

            JLabel secondCard = new JLabel("Second Card");
	        secondCard.setForeground(Color.WHITE);
	        secondCard.setFont(new Font("Arial", Font.BOLD, 12));
            secondCard.setBounds(x2 + 30, 15, 100, 15);
            contentPane.add(secondCard);

            JLabel thirdCard = new JLabel("Third Card");
	        thirdCard.setForeground(Color.WHITE);
	        thirdCard.setFont(new Font("Arial", Font.BOLD, 13));
            thirdCard.setBounds(x3 + 30, 15, 100, 15);
            contentPane.add(thirdCard);
            
            
            ingredientButton0.addActionListener(e -> handleIngredientSelection(ingredientButton0));
            ingredientButton1.addActionListener(e -> handleIngredientSelection(ingredientButton1));
            ingredientButton2.addActionListener(e -> handleIngredientSelection(ingredientButton2));
	        
	        


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addJButtons() {

        rightButton = new JButton("SWAP RIGHT ->");
		leftButton = new JButton("<- SWAP LEFT");
		doneButton = new JButton("DONE!");
		doneButton.setBounds(400, 277, 100, 23);
		contentPane.add(doneButton);

        rightButton.setBounds(750, 140, 130, 23);
		contentPane.add(rightButton);

        leftButton.setBounds(20, 140, 130, 23);
		contentPane.add(leftButton);


		
	}
	
	public void addJLabels() {
		
        selected = new JLabel("SELECTED");
        selected.setBackground(Color.RED);
        selected.setForeground(Color.WHITE); 
        selected.setFont(new Font("Arial", Font.BOLD, 15)); 
        selected.setOpaque(true);
        selected.setVisible(false);
        contentPane.add(selected);
        
        message = new JLabel("Please Make the arrangement");
        message.setBounds(350, 0, 200, 30); // Increased height for better visibility
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15)); // Larger and italic
        message.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message.setOpaque(true);
        message.setVisible(false);
        contentPane.add(message);
	}
	
	
	public void addActionEvent() {
		doneButton.addActionListener(this);
        rightButton.addActionListener(this);
        leftButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
        if (event.getSource() == rightButton) {
            swapRight();
        } else if (event.getSource() == leftButton) {
            swapLeft();
        } else if (event.getSource() == doneButton) {
            // ... handle done action
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
	


    private IngredientCard handleIngredientSelection(JButton button) {            
    	
       
            if (button.getText().equals(ingredientCard0.getName())) {
                selectedIngredientCard = ingredientCard0;
            } else if (button.getText().equals(ingredientCard1.getName())) {
                selectedIngredientCard = ingredientCard1;
            } else {
                selectedIngredientCard = ingredientCard2;
            }
            
        if (selectedIngredientCard != null) {
            // Set the selected label position based on the clicked button
            selected.setBounds(button.getX(), button.getY() + button.getHeight(), button.getWidth() - 8, 20);
            selected.setVisible(true);
            contentPane.setComponentZOrder(selected, 0);
        }
        return selectedIngredientCard;
    }



    /*
     * Burası değişicek logic yanlış cartlartı değiştiriyoruz a
     */

    private void swapRight() {
        if (selectedIngredientCard == ingredientCard0) {
            // Swap ingredientCard0 with ingredientCard1
            IngredientCard temp = ingredientCard0;
            ingredientCard0 = ingredientCard1;
            ingredientCard1 = temp;
            updateCardPositions();
            selected.setVisible(false);
            contentPane.setComponentZOrder(selected, 0);
            selectedIngredientCard = null;
        } else if (selectedIngredientCard == ingredientCard1) {
            // Swap ingredientCard1 with ingredientCard2
            IngredientCard temp = ingredientCard1;
            ingredientCard1 = ingredientCard2;
            ingredientCard2 = temp;
            updateCardPositions();
            selected.setVisible(false);
            contentPane.setComponentZOrder(selected, 0);
            selectedIngredientCard = null;
        }else{
             System.out.println("III bu kart ingredientCard2 sağa gidemez");
        }
        // No action for ingredientCard2, as it can't be swapped right
    }
    
    // Method to swap the currently selected card to the left
    private void swapLeft() {
        if (selectedIngredientCard == ingredientCard1) {
            // Swap ingredientCard1 with ingredientCard0
            IngredientCard temp = ingredientCard1;
            ingredientCard1 = ingredientCard0;
            ingredientCard0 = temp;
            updateCardPositions();
            selected.setVisible(false);
            contentPane.setComponentZOrder(selected, 0);
            selectedIngredientCard = null;
        } else if (selectedIngredientCard == ingredientCard2) {
            // Swap ingredientCard2 with ingredientCard1
            IngredientCard temp = ingredientCard2;
            ingredientCard2 = ingredientCard1;
            ingredientCard1 = temp;
            updateCardPositions();
            selected.setVisible(false);
            contentPane.setComponentZOrder(selected, 0);
            selectedIngredientCard = null;
            


        }
        else{
            System.out.println("III bu kart ingredientCard0 sola gidemez");
            
        }
        // No action for ingredientCard0, as it can't be swapped left
    }
    
    // Method to update the UI after swapping cards
    private void updateCardPositions() {
        try {
            setImage(ingredientCard0.getImagePath(), ingredientButton0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ingredientLabel0.setText(ingredientCard0.getName());
        
        try {
            setImage(ingredientCard1.getImagePath(), ingredientButton1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ingredientLabel1.setText(ingredientCard1.getName());
        
        try {
            setImage(ingredientCard2.getImagePath(), ingredientButton2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ingredientLabel2.setText(ingredientCard2.getName());
        
        // Repaint the panel to reflect changes
        contentPane.repaint();
    }
    
    
}

