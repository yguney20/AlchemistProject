package ui.swing.screens;

import java.awt.Color;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

//import com.formdev.flatlaf.util.UIScale;

import domain.Client;
import domain.Server;
import domain.controllers.LoginController;
import ui.swing.desingsystem.RoundedBorder;
import ui.swing.desingsystem.RoundedCornerPanel;
import ui.swing.model.CardModel;
import ui.swing.screens.screencomponents.AvatarCardScreen;

public class LoginOverlayForHost extends JFrame {
	private JFrame frame;
    public static String selectedPlayerName;
    public static String selectedAvatarPath;    
	
	private Color transparentColor;
	private JTextField nicknameTextField;
	
	private JLabel playerCreatedText;
	
    private AvatarCardScreen selectedCard = null; 

    private LoginController loginController = LoginController.getInstance();
	 
	private Map<ImageIcon, String> iconPathMap = new HashMap<>(); 

	
	public LoginOverlayForHost(Frame frame) {
	
		init();
		
	}

	private void init() {
        this.frame = this;
		setSize(1365, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Load the original gif as an image
        Image originalImage = new ImageIcon(this.getClass().getResource("/animations/loginBackground.gif")).getImage();
        
        // Resize it to fit the frame
        Image resizedImage = originalImage.getScaledInstance(1365, 768, Image.SCALE_DEFAULT);
        
        // Convert it back to an ImageIcon for the label
        Icon imgIcon = new ImageIcon(resizedImage);

        // Create the label and set its bounds to fill the frame
        JLabel label = new JLabel(imgIcon);
        label.setBounds(0, 0, 1365, 768);
        
        // Add the label to the frame
       
		
		transparentColor = new Color(0,0,0,50);
		
		RoundedBorder roundedBorder = new RoundedBorder(Color.BLACK, 15, 5, 4, new Color(0, 0, 0, 100));  //Custom Border Design.
		
		//setBackground(transparentColor);
		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1365, 768);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(transparentColor);
		
		JPanel cardPanel = new RoundedCornerPanel(15);
		cardPanel.setBackground(new Color(240, 240, 240));
		cardPanel.setBounds(10, 228, 969, 275);
		mainPanel.add(cardPanel);
		cardPanel.setLayout(null);
		cardPanel.setBorder(roundedBorder);
		
		
		ImageIcon avatarIcon1 = new ImageIcon(getClass().getResource("/images/avatar/a1.jpg"));	
		iconPathMap.put(avatarIcon1, "/images/avatar/a1.jpg");

        AvatarCardScreen avatarCardScreen = new AvatarCardScreen(new CardModel(avatarIcon1,
                "Cauldronist ",
                " Expert in ancient potion recipes."));
        avatarCardScreen.setBounds(10, 15, 180, 240);
        
        avatarCardScreen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardPanel.add(avatarCardScreen);
        
        ImageIcon avatarIcon2 = new ImageIcon(getClass().getResource("/images/avatar/a2.jpg"));
        iconPathMap.put(avatarIcon2, "/images/avatar/a2.jpg");

        AvatarCardScreen avatarCardScreen2 = new AvatarCardScreen(new CardModel(avatarIcon2,
                "Elixirist",
                "Creator of wisdom-enhancing elixirs."));
        avatarCardScreen2.setBounds(200, 15, 180, 240);
        
        avatarCardScreen2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardPanel.add(avatarCardScreen2);
        
        ImageIcon avatarIcon3 = new ImageIcon(getClass().getResource("/images/avatar/a3.jpg"));
        iconPathMap.put(avatarIcon3, "/images/avatar/a3.jpg");

        AvatarCardScreen avatarCardScreen3 = new AvatarCardScreen(new CardModel(avatarIcon3,
                "ArcBrewer ",
                " Maker of unpredictable potions."));
        avatarCardScreen3.setBounds(390, 15, 180, 240);
        cardPanel.add(avatarCardScreen3);
    
        avatarCardScreen3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
        ImageIcon avatarIcon4 = new ImageIcon(getClass().getResource("/images/avatar/a4.jpg"));
        iconPathMap.put(avatarIcon4, "/images/avatar/a4.jpg");

        AvatarCardScreen avatarCardScreen4 = new AvatarCardScreen(new CardModel(avatarIcon4,
                "HerbEnchanter",
                "Specialist in healing herbal potions."));
        avatarCardScreen4.setBounds(580, 15, 180, 240);
        cardPanel.add(avatarCardScreen4);
        
        avatarCardScreen4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        
        ImageIcon avatarIcon5 = new ImageIcon(getClass().getResource("/images/avatar/a5.jpg"));
        iconPathMap.put(avatarIcon5, "/images/avatar/a5.jpg");

        AvatarCardScreen avatarCardScreen5 = new AvatarCardScreen(new CardModel(avatarIcon5,
                "EssenceDistiller ",
                "Focused on potent, concentrated potions."));
        avatarCardScreen5.setBounds(770, 15, 180, 240);
        cardPanel.add(avatarCardScreen5);
       
        avatarCardScreen5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        MouseAdapter cardMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AvatarCardScreen clickedCard = (AvatarCardScreen) e.getSource();
                if (selectedCard != null && selectedCard != clickedCard) {
                    selectedCard.setSelected(false); // Deselect the previously selected card
                }
                clickedCard.setSelected(!clickedCard.isSelected());
                selectedCard = clickedCard.isSelected() ? clickedCard : null;
                repaint();
            }
        };

            // Attach this listener to all AvatarCardScreens
        avatarCardScreen.addMouseListener(cardMouseListener);
        avatarCardScreen2.addMouseListener(cardMouseListener);
        avatarCardScreen3.addMouseListener(cardMouseListener);
        avatarCardScreen4.addMouseListener(cardMouseListener);
        avatarCardScreen5.addMouseListener(cardMouseListener);
        
        
        RoundedCornerPanel avatarNamePanel = new RoundedCornerPanel(10);
        
        avatarNamePanel.setBounds(1008, 250, 340, 184);
        avatarNamePanel.setBorder(roundedBorder);
        
        
        mainPanel.add(avatarNamePanel);
        avatarNamePanel.setLayout(null);
        
        nicknameTextField = new JTextField();
        nicknameTextField.setBorder(new LineBorder(new Color(171, 173, 179), 2));
        nicknameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameTextField.setForeground(new Color(128, 0, 0));
        nicknameTextField.setFont(new Font("Verdana", Font.BOLD, 16));
        nicknameTextField.setBounds(43, 32, 257, 29);
        avatarNamePanel.add(nicknameTextField);
        nicknameTextField.setColumns(10);
        
        RoundedCornerPanel startButton = new RoundedCornerPanel(15);
        startButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(82, 82, 82), new Color(192, 192, 192), new Color(82, 82, 82), new Color(82, 82, 82)));
        startButton.setBackground(new Color(128, 0, 0));
        startButton.setBounds(58, 100, 231, 38);
        avatarNamePanel.add(startButton);
        startButton.setLayout(new GridLayout(1, 0, 0, 0));
        
        JLabel startLabel = new JLabel("Start");
        startLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		handleStartButtonClick();
 		
        		
        	}
        });
        startLabel.setForeground(new Color(255, 255, 255));
        startLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startLabel.setFont(new Font("Segoe Script", Font.BOLD, 24));
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        startButton.add(startLabel);
        
        playerCreatedText = new JLabel("Player created.");
        playerCreatedText.setFont(new Font("Verdana", Font.BOLD, 14));
        playerCreatedText.setBounds(43, 72, 257, 17);
        avatarNamePanel.add(playerCreatedText);
        playerCreatedText.setVisible(false);
        
        
		
        getContentPane().add(label);
		
		setVisible(true);
		
	}


	
	
	private void handleStartButtonClick() {
        if (nicknameTextField.getText().isEmpty() || selectedCard == null) {
        	
        	  JOptionPane.showMessageDialog(this,
        	            "Please enter a nickname and select an avatar.",
        	            "Error",
        	            JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedPlayerName = nicknameTextField.getText();
        ImageIcon selectedIcon = (ImageIcon) selectedCard.getData().getIcon(); 
        selectedAvatarPath = iconPathMap.get(selectedIcon);
    
        playerCreatedText.setText("Player " + selectedPlayerName + " created.");
        playerCreatedText.setVisible(true);

        startServer();

        HostGameScreen hostScreen = new HostGameScreen(frame);
        this.dispose();
        hostScreen.display();

        
    }
    public void display() {
        setVisible(true);
    }
    

     //when user passes to HostGameScreen, a server will be started
    private void startServer() {
        // Start the server in a new thread to prevent UI freezing
        new Thread(() -> {
            try {
                Server server = Server.getInstance(6666); // Replace 6666 with your actual server port
                server.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Failed to start the server: " + ex.getMessage(),
                        "Server Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

}


/**
* @Author -- H. Sarp Vulas
*/