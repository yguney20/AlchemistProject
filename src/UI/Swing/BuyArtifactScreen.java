package UI.Swing;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.GameController;
import domain.GameObjects.ArtifactCard;
import domain.GameObjects.Player;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class BuyArtifactScreen extends JFrame  implements ActionListener{

	private JPanel contentPane;
	private JButton elixirOfInsight;
	private JButton buyButton;
    private int initialX;
    private int initialY;
    private ArtifactCard artifactCard;
    private JLabel selected;
    private GameController gameController = GameController.getInstance();
    private JLabel message;
    

	/**
	 * Create the frame.
	 */
	public BuyArtifactScreen() {
		setTitle("Buy Artifact");
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
	        
	        //ImageIcon icon = new ImageIcon(getClass().getResource("UI/Swing/Images/logo.png"));
	        //setIconImage(icon.getImage());
	        
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
	
	public void addJButtons() {
		try {
			
		elixirOfInsight = new JButton("New button");
		elixirOfInsight.setBounds(25, 20, 139, 165);
        elixirOfInsight.setContentAreaFilled(false);
        elixirOfInsight.setBorder(BorderFactory.createEmptyBorder());
        setImage("/UI/Swing/Images/ArtifactCards/Elixir Of Insight.png", elixirOfInsight);
		contentPane.add(elixirOfInsight);
		
		buyButton = new JButton("BUY");
		buyButton.setBounds(391, 222, 89, 23);
		contentPane.add(buyButton);
		
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addJLabels() {
		JLabel artifact1Label = new JLabel("Elixir of Insight");
		artifact1Label.setForeground(Color.WHITE);
		artifact1Label.setFont(new Font("Arial", Font.BOLD, 12));
		artifact1Label.setBounds(55, 200, 150, 14);
		contentPane.add(artifact1Label);
		
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
		elixirOfInsight.addActionListener(this);
		buyButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==elixirOfInsight) {
			handleArtifactSelection("/UI/Swing/Images/ArtifactCards/Elixir Of Insight.png", elixirOfInsight);
		}
		if(event.getSource()==buyButton) {
			if(artifactCard==null) {
				message.setVisible(true);
                contentPane.setComponentZOrder(message, 0);
			} else {
				Player currentPlayer = gameController.getCurrentPlayer();
				gameController.buyArtifactCard(artifactCard, currentPlayer);
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
	
    private void handleArtifactSelection(String artifactPath, JButton button) {            
        if (button == elixirOfInsight) {
        		artifactCard = gameController.getArtifactCardByPath(artifactPath);
                selected.setBounds(button.getX(), button.getHeight(), button.getWidth()-8, 20);
                selected.setVisible(true);
                contentPane.setComponentZOrder(selected, 0);

        }
    }
}
