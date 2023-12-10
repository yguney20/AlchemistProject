package UI.Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import UI.SoundPlayer;
import UI.Swing.GameActions.BuyArtifactScreen;
import UI.Swing.GameActions.SelectArtifactScreen;
import UI.Swing.GameActions.TransmuteIngredientScreen;
import domain.GameObjects.Player;
import domain.controllers.GameController;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PlayerDashboard extends JFrame {

    private JPanel contentPane;
    private GameController gameController;
    private Point initialClick;

    /**
     * Create the frame.
     */
    public PlayerDashboard(GameController gameController) {
    	

    	SoundPlayer musicPlayer = new SoundPlayer();
    	SoundPlayer soundPlayer = new SoundPlayer();
    	
    	setUndecorated(true);

    	musicPlayer.playSound("UI/Swing/Sounds/medivalSoundtrack.wav");
    	
        setTitle("Ku Alchemist Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 905, 550); // Adjust the size accordingly
        setResizable(false);


        RoundedBorder roundedBorder = new RoundedBorder(Color.BLACK, 10, 0, 4, new Color(0, 0, 0, 70));  //Custom Border Design.
        
        RoundedBorder roundedBorder2 = new RoundedBorder(Color.BLACK, 5, 0, 3, new Color(0, 0, 0, 120));  //Custom Border Design.
        
        ImageRounder imageRounder = new ImageRounder(); //Custom image rounder :)
        
        Font playerInfoFont = new Font("Neuropol", Font.BOLD, 16);
        
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }

            public void mouseDragged(MouseEvent e) {
                // get location of Window
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        };
        
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(new Color(255,250,205));
        contentPane.setLayout(null);
        
        
        RoundedCornerPanel titlePanel = new RoundedCornerPanel(30);
        titlePanel.setBorder(roundedBorder);

        titlePanel.setBackground(new Color(254, 255, 255));
        titlePanel.setBounds(280, 6, 340, 58);
        contentPane.add(titlePanel);
        titlePanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel playerDashboardImage = new JLabel("");
        playerDashboardImage.setBackground(new Color(255, 255, 255));
        playerDashboardImage.setHorizontalAlignment(SwingConstants.CENTER);
        playerDashboardImage.setIconTextGap(0);
        playerDashboardImage.setFocusable(false);
        
        
        ImageIcon preResizePlayerDashboardIcon = new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/playerDashboardUI/playerDashboardImage.png"));
        Image preResizePlayerDashboardImage = preResizePlayerDashboardIcon.getImage();

        // Resize the image
        Image resizedPlayerDashboardImage = preResizePlayerDashboardImage.getScaledInstance(titlePanel.getWidth(), titlePanel.getHeight(), Image.SCALE_SMOOTH);   
        ImageIcon postResizedPlayerDashboardImage = new ImageIcon(resizedPlayerDashboardImage);

        playerDashboardImage.setIcon(postResizedPlayerDashboardImage);      
        titlePanel.add(playerDashboardImage);
        
        JPanel chooseActionImagePanel = new JPanel();
        chooseActionImagePanel.setBackground(new Color(254, 255, 255));
        chooseActionImagePanel.setBounds(114, 86, 245, 71);
        contentPane.add(chooseActionImagePanel);
        chooseActionImagePanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel chooseActionImage = new JLabel("");
        chooseActionImage.setBorder(roundedBorder);
        chooseActionImage.setIconTextGap(0);
        chooseActionImage.setHorizontalAlignment(SwingConstants.CENTER);
        chooseActionImage.setFocusable(false);
        chooseActionImage.setBackground(Color.WHITE);
        chooseActionImagePanel.add(chooseActionImage);
        
        
        ImageIcon preResizeChooseActionIcon = new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/playerDashboardUI/chooseActionImage.png"));
        Image preResizeChooseActionImage = preResizeChooseActionIcon.getImage();

        // Resize the image
        Image resizedResizeChooseActionImage = preResizeChooseActionImage.getScaledInstance(chooseActionImagePanel.getWidth(), chooseActionImagePanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizeChooseActionIcon = new ImageIcon(resizedResizeChooseActionImage);

        // Set the resized icon to the JLabel
        chooseActionImage.setIcon(postResizeChooseActionIcon);
        chooseActionImagePanel.add(chooseActionImage);        
    
        JPanel chooseActionActionsPanel = new JPanel();
        chooseActionActionsPanel.setBackground(new Color(254, 255, 255));
        chooseActionActionsPanel.setBorder(roundedBorder);
        chooseActionActionsPanel.setBounds(23, 169, 436, 340);
        contentPane.add(chooseActionActionsPanel);
        chooseActionActionsPanel.setLayout(null);
        
        JLabel forageForIngredientButton = new RoundedJLabel("Forage For Ingredient");
        forageForIngredientButton.setOpaque(true);
        forageForIngredientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                forageForIngredientButton.setBackground(new Color(138,43,226));
                soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                forageForIngredientButton.setBackground(Color.WHITE);
            }
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Player currentPlayer = gameController.getCurrentPlayer();
    			gameController.forageForIngredient(currentPlayer);
    			PlayerDashboard.this.setVisible(false);
    			musicPlayer.stopSound();
        	}
        });
        forageForIngredientButton.setIcon(null);
        forageForIngredientButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forageForIngredientButton.setBounds(4, 4, 425, 36);
        forageForIngredientButton.setBorder(roundedBorder2);
        forageForIngredientButton.setIconTextGap(200);
        forageForIngredientButton.setHorizontalAlignment(SwingConstants.CENTER);
        forageForIngredientButton.setFocusable(false);
        forageForIngredientButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(forageForIngredientButton);
        
        JLabel transmuteIngredientButton = new RoundedJLabel("Transmute Ingredient");
        transmuteIngredientButton.setOpaque(true);
        transmuteIngredientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	transmuteIngredientButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	transmuteIngredientButton.setBackground(Color.WHITE);
            }
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		TransmuteIngredientScreen transmuteIngredientScreen = new TransmuteIngredientScreen();
    			transmuteIngredientScreen.display();
    			PlayerDashboard.this.setVisible(false);
        		musicPlayer.stopSound();
        	}
        });
        transmuteIngredientButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        transmuteIngredientButton.setBounds(4, 45, 425, 36);
        transmuteIngredientButton.setBorder(roundedBorder2);
        transmuteIngredientButton.setIconTextGap(0);
        transmuteIngredientButton.setHorizontalAlignment(SwingConstants.CENTER);
        transmuteIngredientButton.setFocusable(false);
        transmuteIngredientButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(transmuteIngredientButton);
        
        JLabel buyArtifactButton = new JLabel("Buy Artifact");
        buyArtifactButton.setOpaque(true);
        buyArtifactButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	buyArtifactButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	buyArtifactButton.setBackground(Color.WHITE);
            }
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		BuyArtifactScreen buyArtifactScreen = new BuyArtifactScreen();
    			buyArtifactScreen.display();
    			PlayerDashboard.this.setVisible(false);
    			musicPlayer.stopSound();
        		
        	}
        });
        buyArtifactButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buyArtifactButton.setBounds(4, 86, 425, 36);
        buyArtifactButton.setBorder(roundedBorder2);
        buyArtifactButton.setIconTextGap(0);
        buyArtifactButton.setHorizontalAlignment(SwingConstants.CENTER);
        buyArtifactButton.setFocusable(false);
        buyArtifactButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(buyArtifactButton);
        
        JLabel useArtifactButton = new JLabel("Use Artifact");
        useArtifactButton.setOpaque(true);
        useArtifactButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	useArtifactButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	useArtifactButton.setBackground(Color.WHITE);
            }

            @Override
        	public void mouseClicked(MouseEvent e) {
        		
        		SelectArtifactScreen selectArtifactScreen = new SelectArtifactScreen();
    			selectArtifactScreen.display();
    			PlayerDashboard.this.setVisible(false);
    			musicPlayer.stopSound();

       		
        	}
        });
        
        useArtifactButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        useArtifactButton.setBounds(4, 127, 425, 36);
        useArtifactButton.setBorder(roundedBorder2);
        useArtifactButton.setIconTextGap(0);
        useArtifactButton.setHorizontalAlignment(SwingConstants.CENTER);
        useArtifactButton.setFocusable(false);
        useArtifactButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(useArtifactButton);

        JLabel makeExperimentButton = new JLabel("Make Experiment");
        makeExperimentButton.setOpaque(true);
        makeExperimentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	makeExperimentButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	makeExperimentButton.setBackground(Color.WHITE);
            }
        });

        makeExperimentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        makeExperimentButton.setBounds(4, 168, 425, 36);
        makeExperimentButton.setBorder(roundedBorder2);
        makeExperimentButton.setIconTextGap(0);
        makeExperimentButton.setHorizontalAlignment(SwingConstants.CENTER);
        makeExperimentButton.setFocusable(false);
        makeExperimentButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(makeExperimentButton);
        
        JLabel sellPotionButton = new JLabel("Sell a Potion");
        sellPotionButton.setOpaque(true);
        sellPotionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	sellPotionButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	sellPotionButton.setBackground(Color.WHITE);
            }
        });
        sellPotionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sellPotionButton.setBounds(4, 210, 425, 36);
        sellPotionButton.setBorder(roundedBorder2);
        sellPotionButton.setIconTextGap(0);
        sellPotionButton.setHorizontalAlignment(SwingConstants.CENTER);
        sellPotionButton.setFocusable(false);
        sellPotionButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(sellPotionButton);
        
        JLabel publishTheoryButton = new JLabel("Publish Theory");
        publishTheoryButton.setOpaque(true);
        publishTheoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	publishTheoryButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	publishTheoryButton.setBackground(Color.WHITE);
            }
        });
        publishTheoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        publishTheoryButton.setBounds(4, 252, 425, 36);
        publishTheoryButton.setBorder(roundedBorder2);
        publishTheoryButton.setIconTextGap(0);
        publishTheoryButton.setHorizontalAlignment(SwingConstants.CENTER);
        publishTheoryButton.setFocusable(false);
        publishTheoryButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(publishTheoryButton);
        
        JLabel debunkTheoryButton = new JLabel("Debunk Theory");
        debunkTheoryButton.setOpaque(true);
        debunkTheoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	debunkTheoryButton.setBackground(new Color(138,43,226));
            	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	debunkTheoryButton.setBackground(Color.WHITE);
            }
        });
        
        debunkTheoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        debunkTheoryButton.setBounds(4, 292, 425, 36);
        debunkTheoryButton.setBorder(roundedBorder2);
        debunkTheoryButton.setIconTextGap(0);
        debunkTheoryButton.setHorizontalAlignment(SwingConstants.CENTER);
        debunkTheoryButton.setFocusable(false);
        debunkTheoryButton.setBackground(Color.WHITE);
        chooseActionActionsPanel.add(debunkTheoryButton);
        
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setBackground(new Color(254, 255, 255));
        ingredientsPanel.setBorder(roundedBorder);
        ingredientsPanel.setBounds(519, 457, 325, 52);
        contentPane.add(ingredientsPanel);
        ingredientsPanel.setLayout(null);
        
        JLabel showIngredientsButton = new JLabel("My Ingredients");
        showIngredientsButton.setBounds(6, 6, 307, 36);
        ingredientsPanel.add(showIngredientsButton);
        showIngredientsButton.setOpaque(true);
        showIngredientsButton.addMouseListener(new MouseAdapter() {
        	 @Override
             public void mouseEntered(MouseEvent e) {
        		showIngredientsButton.setBackground(new Color(138,43,226));
             	soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
             }

             @Override
             public void mouseExited(MouseEvent e) {
            	showIngredientsButton.setBackground(Color.WHITE);
             }

             @Override
         	public void mouseClicked(MouseEvent e) {
            	 
            	PlayerIngredientsScreen myIngredientsScreen = new PlayerIngredientsScreen();
            	myIngredientsScreen.display();         		
     			PlayerDashboard.this.setVisible(false);
     			musicPlayer.stopSound();

         	}
        });
        
        showIngredientsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showIngredientsButton.setBorder(roundedBorder2);
        showIngredientsButton.setIconTextGap(0);
        showIngredientsButton.setHorizontalAlignment(SwingConstants.CENTER);
        showIngredientsButton.setFocusable(false);
        showIngredientsButton.setBackground(Color.WHITE);
        
        
        JPanel returnBackButtonPanel = new JPanel();
        returnBackButtonPanel.setBackground(new Color(254, 255, 255));
        returnBackButtonPanel.setBounds(23, 6, 105, 41);
        contentPane.add(returnBackButtonPanel);
        returnBackButtonPanel.setLayout(new GridLayout(0, 1, 0, 0)); 
        
        JLabel returnBackImage = new JLabel("");
        returnBackImage.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		soundPlayer.playSound("UI/Swing/Sounds/buttonSound.wav");
        	}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		musicPlayer.stopSound();
        		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(returnBackImage);
        		
        		frame.dispose();
        		
        	}
        });
        returnBackImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnBackImage.setBorder(roundedBorder);
        returnBackButtonPanel.add(returnBackImage);
        returnBackImage.setIconTextGap(0);
        returnBackImage.setHorizontalAlignment(SwingConstants.CENTER);
        returnBackImage.setFocusable(false);
        returnBackImage.setBackground(Color.WHITE);
        
        
        ImageIcon preResizeReturnBackIcon = new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/playerDashboardUI/backImage.png"));
        Image preResizeReturnBackImage = preResizeReturnBackIcon.getImage();

        // Resize the image
        Image resizedResizeReturnBackImage = preResizeReturnBackImage.getScaledInstance(returnBackButtonPanel.getWidth(), returnBackButtonPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizeReturnBackIcon = new ImageIcon(resizedResizeReturnBackImage);

        // Set the resized icon to the JLabel
        returnBackImage.setIcon(postResizeReturnBackIcon);
        returnBackButtonPanel.add(returnBackImage);
        
        JPanel playerInfoImagePanel = new JPanel();
        playerInfoImagePanel.setBackground(new Color(254, 255, 255));
        playerInfoImagePanel.setBounds(554, 86, 245, 71);
        contentPane.add(playerInfoImagePanel);
        playerInfoImagePanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel playerInfoImage = new JLabel("");
        playerInfoImage.setBorder(roundedBorder);
        playerInfoImage.setIconTextGap(0);
        playerInfoImage.setHorizontalAlignment(SwingConstants.CENTER);
        playerInfoImage.setFocusable(false);
        playerInfoImage.setBackground(Color.WHITE);
        playerInfoImagePanel.add(playerInfoImage);
        
        
        ImageIcon preResizePlayerInfoIcon = new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/playerDashboardUI/playerInfoImage.png"));
        Image preResizePlayerInfoImage = preResizePlayerInfoIcon.getImage();

        // Resize the image
        Image resizedPlayerInfoImage = preResizePlayerInfoImage.getScaledInstance(playerInfoImagePanel.getWidth(), playerInfoImagePanel.getHeight(), Image.SCALE_SMOOTH);   
        ImageIcon postResizedPlayerInfoIcon = new ImageIcon(resizedPlayerInfoImage);

        playerInfoImage.setIcon(postResizedPlayerInfoIcon);      
        playerInfoImagePanel.add(playerInfoImage);
        
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setBorder(roundedBorder);
        playerInfoPanel.setBackground(new Color(254, 255, 255));
        playerInfoPanel.setBounds(554, 177, 290, 268);
        
        
        contentPane.add(playerInfoPanel);
        playerInfoPanel.setLayout(null);
        
        JPanel nicknamePanel = new JPanel();
        nicknamePanel.setBounds(5, 5, 280, 61);
        nicknamePanel.setBorder(roundedBorder);
        nicknamePanel.setBackground(new Color(255, 255, 255));
        playerInfoPanel.add(nicknamePanel);
        nicknamePanel.setLayout(null);
        
        JPanel nicknameImageSmallPanel = new JPanel();
        nicknameImageSmallPanel.setBounds(6, 8, 125, 45);
        nicknameImageSmallPanel.setBorder(roundedBorder2);
        nicknameImageSmallPanel.setBackground(new Color(254, 255, 255));
        nicknamePanel.add(nicknameImageSmallPanel);
        nicknameImageSmallPanel.setLayout(null);
        
        JLabel nicknameImageJLabel = new JLabel("");
        nicknameImageJLabel.setBounds(3, 1, 122, 38);
        nicknameImageSmallPanel.add(nicknameImageJLabel);
         
        ImageIcon resizedNicknameIcon = ImageResizer.getResizedIcon(nicknameImageSmallPanel, "/UI/Swing/Images/playerDashboardUI/nicknameImage.png");
        nicknameImageJLabel.setIcon(resizedNicknameIcon);
             
        JLabel nicknameLabel = new JLabel(gameController.getCurrentPlayer().getNickname());
        nicknameLabel.setBounds(141, 0, 133, 57);
        nicknamePanel.add(nicknameLabel);
        nicknameLabel.setFont(playerInfoFont);
        
        JPanel goldPanel = new JPanel();
        goldPanel.setBounds(5, 71, 280, 61);
        goldPanel.setBorder(roundedBorder);
        goldPanel.setBackground(new Color(255, 255, 255));
        playerInfoPanel.add(goldPanel);
        goldPanel.setLayout(null);
        
        JPanel goldImageSmallPanel = new JPanel();
        goldImageSmallPanel.setLayout(null);
        goldImageSmallPanel.setBorder(roundedBorder2);
        goldImageSmallPanel.setBackground(new Color(254, 255, 255));
        goldImageSmallPanel.setBounds(6, 6, 125, 45);
        goldPanel.add(goldImageSmallPanel);
        
        JLabel goldImageJLabel = new JLabel("");
        goldImageJLabel.setBounds(3, 1, 116, 40);
        goldImageSmallPanel.add(goldImageJLabel);
        
        ImageIcon resizedGoldIcon = ImageResizer.getResizedIcon(goldImageSmallPanel, "/UI/Swing/Images/playerDashboardUI/goldImage.png");
        
        goldImageJLabel.setIcon(resizedGoldIcon);
   
        JLabel goldLabel = new JLabel(String.valueOf(gameController.getCurrentPlayer().getGolds()));
        goldLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        goldLabel.setBounds(141, 0, 133, 57);
        goldPanel.add(goldLabel);
        
        JPanel sicknessPanel = new JPanel();
        sicknessPanel.setBounds(5, 136, 280, 61);
        sicknessPanel.setBorder(roundedBorder);
        sicknessPanel.setBackground(new Color(255, 255, 255));
        playerInfoPanel.add(sicknessPanel);
        sicknessPanel.setLayout(null);
        
        JLabel sicknessLabel = new JLabel(String.valueOf(gameController.getCurrentPlayer().getSicknessLevel()));
        sicknessLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sicknessLabel.setBounds(141, 0, 133, 57);
        sicknessPanel.add(sicknessLabel);
        
        JPanel sicknessImageSmallPanel = new JPanel();
        sicknessImageSmallPanel.setLayout(null);
        sicknessImageSmallPanel.setBorder(roundedBorder2);
        
        sicknessImageSmallPanel.setBackground(new Color(254, 255, 255));
        sicknessImageSmallPanel.setBounds(6, 8, 125, 45);
        sicknessPanel.add(sicknessImageSmallPanel);
        
        JLabel sicknessImageJLabel = new JLabel("");
        sicknessImageJLabel.setBounds(3, 1, 122, 38);
        
        ImageIcon resizedSicknessIcon = ImageResizer.getResizedIcon(goldImageSmallPanel, "/UI/Swing/Images/playerDashboardUI/sicknessImage.png");
        
        sicknessImageJLabel.setIcon(resizedSicknessIcon);
        
        
        sicknessImageSmallPanel.add(sicknessImageJLabel);
        
        JPanel reputationPanel = new JPanel();
        reputationPanel.setBounds(5, 201, 280, 61);
        reputationPanel.setBorder(roundedBorder2);
        reputationPanel.setBorder(roundedBorder);
        reputationPanel.setBackground(new Color(255, 255, 255));
        playerInfoPanel.add(reputationPanel);
        reputationPanel.setLayout(null);
        
        JLabel reputationLabel = new JLabel(String.valueOf(gameController.getCurrentPlayer().getReputationPoints()));
        reputationLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        reputationLabel.setBounds(141, 0, 133, 57);
        reputationPanel.add(reputationLabel);
        
        JPanel reputationImageSmallPanel = new JPanel();
        reputationImageSmallPanel.setLayout(null);
        reputationImageSmallPanel.setBorder(roundedBorder2);
        reputationImageSmallPanel.setBackground(new Color(254, 255, 255));
        reputationImageSmallPanel.setBounds(6, 8, 125, 45);
        reputationPanel.add(reputationImageSmallPanel);
        
        JLabel reputationImageJLabel = new JLabel("");
        reputationImageJLabel.setBounds(3, 1, 122, 38);
        reputationImageSmallPanel.add(reputationImageJLabel);
        
        ImageIcon resizedReputationIcon = ImageResizer.getResizedIcon(goldImageSmallPanel, "/UI/Swing/Images/playerDashboardUI/reputationImage.png");
        
        reputationImageJLabel.setIcon(resizedReputationIcon);
        
       
  
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Set initial off-screen positions
                goldPanel.setBounds(5, 400, 280, 61);
                nicknamePanel.setBounds(5, 500, 280, 61);
                sicknessPanel.setBounds(5, 600, 280, 61);
                reputationPanel.setBounds(5, 700, 280, 61);

                // Create and start animations
                Timer goldTimer = createPanelAnimationTimer(goldPanel, 71, 750);
                Timer nicknameTimer = createPanelAnimationTimer(nicknamePanel, 5, 750);
                Timer sicknessTimer = createPanelAnimationTimer(sicknessPanel, 136, 750);
                Timer reputationTimer = createPanelAnimationTimer(reputationPanel, 201, 750);
                
                
                // Create label animations
                Timer forageTimer = createLabelAnimationTimer(forageForIngredientButton, 4, 500);
                Timer transmuteTimer = createLabelAnimationTimer(transmuteIngredientButton, 4, 500);
                Timer buyTimer = createLabelAnimationTimer(buyArtifactButton, 4, 500);
                Timer experimentTimer = createLabelAnimationTimer(makeExperimentButton, 4, 500);
                Timer sellTimer = createLabelAnimationTimer(sellPotionButton, 4, 500);
                Timer publishTimer = createLabelAnimationTimer(publishTheoryButton, 4, 500);
                Timer debunkTimer = createLabelAnimationTimer(debunkTheoryButton, 4, 500);
                Timer useTimer = createLabelAnimationTimer(useArtifactButton, 4, 500);
                Timer ingredientTimer = createLabelAnimationTimer(showIngredientsButton, 4, 500);
                

                // Start label animations with delays
                int labelDelay = 100; // Delay between each label's animation
                forageTimer.start();
                new Timer(labelDelay, (e1) -> transmuteTimer.start()).start();
                new Timer(labelDelay * 2, (e2) -> buyTimer.start()).start();
                new Timer(labelDelay * 3, (e3) -> useTimer.start()).start();
                new Timer(labelDelay * 4, (e4) -> experimentTimer.start()).start();
                new Timer(labelDelay * 5, (e5) -> sellTimer.start()).start();
                new Timer(labelDelay * 6, (e6) -> publishTimer.start()).start();
                new Timer(labelDelay * 7, (e7) -> debunkTimer.start()).start();
                	
                new Timer(labelDelay * 8, (e8) -> {
                	ingredientTimer.start();
                    // Start panel animations after a further delay
                    
                }).start();
  
                int delay = 200; // Delay in milliseconds between each panel's animation

                nicknameTimer.start();

                new Timer(delay, (e1) -> {
                    goldTimer.start();
                }).start();

                new Timer(delay * 2, (e2) -> {
                    sicknessTimer.start();
                }).start();

                new Timer(delay * 3, (e3) -> {
                    reputationTimer.start();
                }).start();
            
            }
        });
        

    }
  
    
    private Timer createPanelAnimationTimer(JPanel panel, int targetY, int duration) {
        int startY = panel.getY(); // Starting Y position (off-screen)
        int distance = targetY - startY; // Total distance to move
        final long[] startTime = new long[1];
        startTime[0] = -1;

        return new Timer(1, e -> {
            if (startTime[0] < 0) {
                startTime[0] = System.currentTimeMillis();
            }

            long elapsed = System.currentTimeMillis() - startTime[0];
            float fraction = (float) elapsed / duration;
            if (fraction > 1f) {
                fraction = 1f;
                ((Timer) e.getSource()).stop(); // Stop the timer when the animation is complete
            }

            // Quadratic easing out
            float easing = fraction * (2 - fraction);
            int newY = startY + (int) (distance * easing);
            panel.setLocation(panel.getX(), newY);
        });
    }
    
    private Timer createLabelAnimationTimer(JLabel label, int targetX, int duration) {
        int startX = -label.getWidth(); // Start off-screen to the left
        int distance = targetX - startX;
        final long[] startTime = new long[1];
        startTime[0] = -1;

        return new Timer(1, e -> {
            if (startTime[0] < 0) {
                startTime[0] = System.currentTimeMillis();
            }

            long elapsed = System.currentTimeMillis() - startTime[0];
            float fraction = (float) elapsed / duration;
            if (fraction > 1f) {
                fraction = 1f;
                ((Timer) e.getSource()).stop();
            }

            float easing = fraction * (2 - fraction); // Quadratic easing
            int newX = startX + (int) (distance * easing);
            label.setLocation(newX, label.getY());
        });
        
    }
    


    public void display() {
        setVisible(true); // Show the board
       
    }

	
}

