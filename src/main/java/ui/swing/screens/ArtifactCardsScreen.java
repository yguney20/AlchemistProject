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
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.Player;
import domain.gameobjects.ArtifactCard;

public class ArtifactCardsScreen extends JFrame{
	
	private JPanel contentPane;
    private int initialX;
    private int initialY;
    private GameController gameController = GameController.getInstance();
    private JLabel artifactLabel;
    private JButton quitButton = new JButton("X");
    

	/**
	 * Create the frame.
	 */
	public ArtifactCardsScreen() {
		setTitle("Artifact Cards");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1500, 400);
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

	        addJLabels();
	        addArtifacts();

		
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
	
	private void addArtifacts() {
	    int x = 20;
	    int y = 40;
	    int width = 140;
	    int height = 200;
	    int horizontalSpacing = 20;

	    Map<Player, List<ArtifactCard>> artifactMap = gameController.getPlayersArtifacts();

	    for (Map.Entry<Player, List<ArtifactCard>> entry : artifactMap.entrySet()) {
	        Player player = entry.getKey();
	        List<ArtifactCard> artifactCards = entry.getValue();

	        for (ArtifactCard artifactCard : artifactCards) {
	            // Create the label for the artifact card's image
	            JLabel artifactImageLabel = new JLabel();
	            artifactImageLabel.setBounds(x, y, width, height);
	            try {
	                setImage(artifactCard.getImagePath(), artifactImageLabel);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	            JLabel playerNameLabel = new JLabel("Owner: " + player.getNickname());
	            playerNameLabel.setBounds(x, y + height + 40, width, 30);
	            playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
	            playerNameLabel.setForeground(Color.WHITE);
	            
	            JLabel artifactNameLabel = new JLabel(artifactCard.getName());
	            artifactNameLabel.setBounds(x, y + height + 10, width, 30);
	            artifactNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
	            artifactNameLabel.setForeground(Color.WHITE);

	            contentPane.add(artifactImageLabel);
	            contentPane.add(playerNameLabel);
	            contentPane.add(artifactNameLabel);

	            x += width + horizontalSpacing;
	        }
	    }
	}

    
	public void addJLabels() {
		
		artifactLabel = new JLabel("Artifact Cards");
        artifactLabel.setBounds(20,10,250,25);
        artifactLabel.setForeground(Color.WHITE); 
        artifactLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
        contentPane.add(artifactLabel);
        
		
	}	
		
	public void setImage(String path, JLabel label) throws IOException {
		
		// Read the original image
        BufferedImage originalImage = ImageIO.read(getClass().getResource(path));

        // Get the dimensions of the button
        int labelWidth = label.getWidth();
        int labelHeight = label.getHeight();

        // Create a new image with better quality
        BufferedImage scaledImage = new BufferedImage(labelWidth, labelHeight, BufferedImage.TYPE_INT_ARGB);
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance((double) labelWidth / originalImage.getWidth(),
                        (double) labelHeight / originalImage.getHeight()),
                AffineTransformOp.TYPE_BILINEAR);
        scaledImage = op.filter(originalImage, scaledImage);

        // Set the scaled image as the icon for the button
        label.setIcon(new ImageIcon(scaledImage));
        
	}


}

