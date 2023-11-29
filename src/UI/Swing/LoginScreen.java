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
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import domain.GameObjects.Player;

public class LoginScreen extends JFrame implements ActionListener{

	private JFrame frame;
    private JPanel contentPane;
    private JTextField player1Name;
    private JTextField player2Name;
    private JButton avatar1;
    private JButton avatar2;
    private JButton avatar3;
    private JButton avatar4;
    private JButton avatar5;
    private JButton avatar6;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel chooseAvatar;
    private JLabel nickname;
    private JLabel message1;
    private JLabel message2;
    private JLabel selected;
    private JButton confirmButton;
    private JButton startGameButton;
    private String player1Avatar;
    private String player2Avatar;
    private int initialX;
    private int initialY;

    /**
     * @throws IOException 
     */
    public LoginScreen(){
    	this.frame = this;
        setTitle("KU Alchemist Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 505);
        setResizable(false);

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
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/UI/Swing/Images/logo.png"));
        setIconImage(icon.getImage());
        addTextFields();
        addJLabels();
        addJButtons();
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

    public void display() {
        setVisible(true);
    }
    
    public void addJButtons() {
    	try {
        avatar1 = new JButton("New button");
        avatar1.setBounds(440, 93, 123, 148);
        avatar1.setContentAreaFilled(false);
        avatar1.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(avatar1);
        setImage("/UI/Swing/Images/a1.png", avatar1);
        
        avatar2= new JButton("New button");
        avatar2.setBounds(40, 93, 123, 148);
        avatar2.setContentAreaFilled(false);
        avatar2.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(avatar2);
        setImage("/UI/Swing/Images/a2.png", avatar2);
        
        avatar3 = new JButton("New button");
        avatar3.setBounds(174, 93, 123, 148);
        avatar3.setContentAreaFilled(false);
        avatar3.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(avatar3);
        setImage("/UI/Swing/Images/a3.png", avatar3);

        avatar4 = new JButton("New button");
        avatar4.setBounds(714, 93, 123, 148);
        avatar4.setContentAreaFilled(false);
        avatar4.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(avatar4);
        setImage("/UI/Swing/Images/a4.png", avatar4);

        avatar5 = new JButton("New button");
        avatar5.setBounds(307, 93, 123, 148);
        avatar5.setContentAreaFilled(false);
        avatar5.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(avatar5);
        setImage("/UI/Swing/Images/a5.png", avatar5);

        avatar6 = new JButton("New button");
        avatar6.setBounds(573, 93, 123, 148);
        avatar6.setContentAreaFilled(false);
        avatar6.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(avatar6);
        setImage("/UI/Swing/Images/a6.png", avatar6);
        
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(390, 369, 89, 23);
        contentPane.add(confirmButton);
        
        startGameButton = new JButton("Start Game");
        startGameButton.setBounds(370, 369, 120, 23);
        startGameButton.setVisible(false); // Initially hidden
        contentPane.add(startGameButton);
    	} catch(IOException e) {
            e.printStackTrace();
    		
    	}
    }
    
    public void addJLabels() {
        player1Label= new JLabel("PLAYER 1");
        player1Label.setBounds(390, 312, 80, 14);
        player1Label.setForeground(Color.WHITE);
        player1Label.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(player1Label);
        
        player2Label = new JLabel("PLAYER 2");
        player2Label.setBounds(390, 312, 80, 14);
        player2Label.setForeground(Color.WHITE);
        player2Label.setFont(new Font("Arial", Font.BOLD, 16));
        player2Label.setVisible(false); // Initially hidden
        contentPane.add(player2Label);

        nickname = new JLabel("Nickname");
        nickname.setBounds(196, 340, 80, 14);
        nickname.setForeground(Color.WHITE);
        nickname.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(nickname);
        
        chooseAvatar = new JLabel("Choose an avatar");
        chooseAvatar.setBounds(40, 60, 299, 14);
        chooseAvatar.setForeground(Color.WHITE);
        chooseAvatar.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(chooseAvatar);
        
        message1 = new JLabel("PLAYER 1 is created!!! Now it is time for PLAYER 2");
        message1.setBounds(40, 25, 500, 30); // Increased height for better visibility
        message1.setForeground(Color.WHITE);
        message1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20)); // Larger and italic
        message1.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message1.setOpaque(true);
        message1.setVisible(false);
        contentPane.add(message1);
        
        message2 = new JLabel("Please enter a nickname and choose an avatar");
        message2.setBounds(40, 25, 500, 30); // Increased height for better visibility
        message2.setForeground(Color.WHITE);
        message2.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20)); // Larger and italic
        message2.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        message2.setOpaque(true);
        message2.setVisible(false);
        contentPane.add(message2);
        
        selected = new JLabel("SELECTED");
        selected.setBackground(Color.RED);
        selected.setForeground(Color.WHITE); 
        selected.setFont(new Font("Arial", Font.BOLD, 20)); 
        selected.setOpaque(true);
        selected.setVisible(false);
        contentPane.add(selected);
    }
    
    public void addTextFields() {
    	 player1Name = new JTextField();
    	 player1Name.setBounds(335, 337, 190, 20);
         contentPane.add(player1Name);
         player1Name.setColumns(10);
         
    	 player2Name = new JTextField();
    	 player2Name.setBounds(335, 337, 190, 20);
         contentPane.add(player2Name);
         player2Name.setColumns(10);
         player2Name.setVisible(false);
    }
    
    public void addActionEvent() {
	       avatar1.addActionListener(this);
	       avatar2.addActionListener(this);
	       avatar3.addActionListener(this);
	       avatar4.addActionListener(this);
	       avatar5.addActionListener(this);
	       avatar6.addActionListener(this);
	       confirmButton.addActionListener(this);
	       startGameButton.addActionListener(this);
 }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		//sets what will happen when the user clicks a button
		if(e.getSource() == avatar1) {
	        handleAvatarSelection("/UI/Swing/Images/a1.png", avatar1);
		}
			
		if(e.getSource() == avatar2) {
	        handleAvatarSelection("/UI/Swing/Images/a2.png", avatar2);
		}
		
		if(e.getSource() == avatar3) {
	        handleAvatarSelection("/UI/Swing/Images/a3.png", avatar3);
		}
		
		if(e.getSource() == avatar4) {
	        handleAvatarSelection("/UI/Swing/Images/a4.png", avatar4);

		}
		
		if(e.getSource() == avatar5) {
	        handleAvatarSelection("/UI/Swing/Images/a5.png", avatar5);
		}
		
		if(e.getSource() == avatar6) {
	        handleAvatarSelection("/UI/Swing/Images/a6.png", avatar6);
		}
		
		if(e.getSource() == confirmButton) {
			if (!player1Name.getText().isEmpty() && player1Avatar != null) {
			message2.setVisible(false);
			Player player1 = new Player(player1Name.getText(), player1Avatar);
	        hideAvatarButton(player1Avatar);
	        selected.setVisible(false);
			player1Label.setVisible(false);
			player1Name.setVisible(false);
			player2Label.setVisible(true);
			player2Name.setVisible(true);
			message1.setVisible(true);
			startGameButton.setVisible(true);
			confirmButton.setVisible(false);
			} else {
			message2.setVisible(true);
			}
		}
		
		if(e.getSource() == startGameButton) {
			if (!player2Name.getText().isEmpty() && player2Avatar != null) {
				Player player2 = new Player(player2Name.getText(), player2Avatar);
				this.setVisible(false);
				BoardScreen board = new BoardScreen();
				board.display();
			} else {
				message1.setVisible(false);
				message2.setVisible(true);
			}
		}		
		
	}
    
    private void handleAvatarSelection(String avatarPath, JButton button) {            
            if (button == avatar1 || button == avatar2 || button == avatar3 || button == avatar4 ||button == avatar5 ||button == avatar6) {
            	if(player1Label.isVisible()) {
            		player1Avatar = avatarPath;
                    selected.setBounds(button.getX(), button.getHeight(), button.getWidth()-8, 20);
                    selected.setVisible(true);
            	}
            	else {
                    player2Avatar = avatarPath;
                    selected.setBounds(button.getX(), button.getHeight(), button.getWidth()-8, 20);
                    selected.setVisible(true);
            	}
            }
    }
    
    private void hideAvatarButton(String avatarPath) {
        if (avatarPath.equals("/UI/Swing/Images/a1.png")) {
            avatar1.setVisible(false);
        } else if (avatarPath.equals("/UI/Swing/Images/a2.png")) {
            avatar2.setVisible(false);
        } else if (avatarPath.equals("/UI/Swing/Images/a3.png")) {
            avatar3.setVisible(false);
        } else if (avatarPath.equals("/UI/Swing/Images/a4.png")) {
            avatar4.setVisible(false);
        } else if (avatarPath.equals("/UI/Swing/Images/a5.png")) {
            avatar5.setVisible(false);
        } else if (avatarPath.equals("/UI/Swing/Images/a6.png")) {
            avatar6.setVisible(false);
        }
    }

}
