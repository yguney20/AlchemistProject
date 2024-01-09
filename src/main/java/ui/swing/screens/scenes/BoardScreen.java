package ui.swing.screens.scenes;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import domain.controllers.GameController;
import ui.swing.screens.screencontrollers.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class BoardScreen extends JFrame{

    private JPanel contentPane;
    private JLabel currentPlayerLabel;
    private JLabel currentTurnLabel;
    private JLabel currentRoundLabel;
    private GameController gameController = GameController.getInstance();
    private JFXPanel fxPanel;
    private JFrame frame;
    private static BoardScreen instance;
    
    public static synchronized BoardScreen getInstance() {
        if (instance == null) {
            instance = new BoardScreen();
        }
        return instance;
    }

    /*
    public BoardScreen() {
    	DeductionBoard deductionBoard = new DeductionBoard(this);
    	gameController.setBoardScreen(this);// Pass 'this' as the reference to the BoardScreen
        setTitle("Ku Alchemist Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 900, 505); // Adjust the size accordingly
        setResizable(false);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
        
        JPanel publicationPanel = new JPanel();
        publicationPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        publicationPanel.setBackground(new Color(254, 255, 255));
        publicationPanel.setBounds(6, 62, 275, 291);
        contentPane.add(publicationPanel);
        
        JLabel publicationNameLabel = new JLabel("Publication Area");
        publicationPanel.add(publicationNameLabel);
        
        JPanel potionBrewingPanel = new JPanel();
        potionBrewingPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        potionBrewingPanel.setBackground(Color.WHITE);
        potionBrewingPanel.setBounds(6, 353, 444, 118);
        contentPane.add(potionBrewingPanel);
        potionBrewingPanel.setLayout(null);
        
        JPanel cauldronPanel = new JPanel();
        cauldronPanel.setBackground(new Color(255, 255, 255));
        cauldronPanel.setBounds(6, 6, 150, 105);
        potionBrewingPanel.add(cauldronPanel);
        
        JLabel cauldronLabel = new JLabel("");
        
        ImageIcon preResizeCauldronImageIcon = new ImageIcon(BoardScreen.class.getResource("/ui/swing/resources/images/gameBoardUI/cauldronImage.png"));
        Image preResizeCauldronImage = preResizeCauldronImageIcon.getImage();

        // Resize the image
        Image resizedCauldronImage = preResizeCauldronImage.getScaledInstance(cauldronPanel.getWidth(), cauldronPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizedCauldronImageIcon = new ImageIcon(resizedCauldronImage);
        
        cauldronLabel.setIcon(postResizedCauldronImageIcon);
        cauldronPanel.add(cauldronLabel);
        
        JLabel potionBrewingNameLabel = new JLabel("Potion Brewing Area");
        potionBrewingNameLabel.setBounds(234, 6, 137, 16);
        potionBrewingPanel.add(potionBrewingNameLabel);
        
        JPanel playerIdNamePanel = new JPanel();
        playerIdNamePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        playerIdNamePanel.setBackground(new Color(254, 255, 255));
        playerIdNamePanel.setBounds(6, 6, 275, 58);
        contentPane.add(playerIdNamePanel);
        playerIdNamePanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        //JLabel playerIdNameLabel = new JLabel("Player ID:");
        //playerIdNamePanel.add(playerIdNameLabel);
        currentPlayerLabel = new JLabel("Current Player: ");
        currentPlayerLabel.setText("Current Player: " + gameController.getCurrentPlayer().getNickname());
        playerIdNamePanel.add(currentPlayerLabel);

        currentTurnLabel = new JLabel("Current Turn: ");
        currentTurnLabel.setText("Current Turn: " + gameController.getCurrentTurn());
        playerIdNamePanel.add(currentTurnLabel);

        currentRoundLabel = new JLabel("Current Round: ");
        currentRoundLabel.setText("Current Round: " + gameController.getCurrentRound());
        playerIdNamePanel.add(currentRoundLabel);
        
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBounds(619, 6, 275, 58);
        contentPane.add(menuPanel);
        menuPanel.setLayout(new GridLayout(0, 2, 20, 0));
        
        JButton menuButton = new JButton("Menu");
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuPanel.add(menuButton);
        
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                MenuScreen menuScreen = new MenuScreen(BoardScreen.this); // Pass 'this' instead of 'frame'
                menuScreen.display();
            }
        });

        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(254, 255, 255));
        titlePanel.setBounds(280, 6, 340, 58);
        contentPane.add(titlePanel);
        titlePanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel gameBoardImage = new JLabel("");
        gameBoardImage.setBackground(new Color(255, 255, 255));
        gameBoardImage.setHorizontalAlignment(SwingConstants.CENTER);
        gameBoardImage.setIconTextGap(0);
        gameBoardImage.setFocusable(false);
        // Load the original image
        ImageIcon preResizeMenuImageIcon = new ImageIcon(BoardScreen.class.getResource("/ui/swing/resources/images/gameBoardUI/kuAlchemistGameBoardTitleImage.png"));
        Image preResizeMenuImage = preResizeMenuImageIcon.getImage();

        // Resize the image
        Image resizedMenuImage = preResizeMenuImage.getScaledInstance(menuPanel.getWidth(), menuPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizeMenuImageIcon = new ImageIcon(resizedMenuImage);

        // Set the resized icon to the JLabel
        gameBoardImage.setIcon(postResizeMenuImageIcon);
        titlePanel.add(gameBoardImage);
        
        JButton dashboardPanel = new JButton();
        dashboardPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBounds(362, 70, 174, 58);
        contentPane.add(dashboardPanel);
        dashboardPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        			PlayerDashboard playerDashboard = new PlayerDashboard(gameController);
        			playerDashboard.display();			
            }
        });
        
        JLabel playerDashboardNameLabel = new JLabel("Player Dashboard");
        dashboardPanel.add(playerDashboardNameLabel);
        
        JPanel ingredientCardPanel = new JPanel();
        ingredientCardPanel.setBackground(Color.WHITE);
        ingredientCardPanel.setBounds(363, 158, 170, 183);
        contentPane.add(ingredientCardPanel);
        ingredientCardPanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel ingredientCardImage = new JLabel("");
        
        ImageIcon preResizeIngredientCardImageIcon = new ImageIcon(BoardScreen.class.getResource("/ui/swing/resources/images/gameBoardUI/gameBoardIngredientCardBacksideImage.png"));
        Image preResizeIngredientCardImage = preResizeIngredientCardImageIcon.getImage();

        // Resize the image
        Image resizedIngredientCardImage = preResizeIngredientCardImage.getScaledInstance(ingredientCardPanel.getWidth(), ingredientCardPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizedIngredientCardImageIcon = new ImageIcon(resizedIngredientCardImage);

        ingredientCardImage.setIcon(postResizedIngredientCardImageIcon);
        ingredientCardPanel.add(ingredientCardImage);
        
        JPanel artifactCardPanel = new JPanel();
        artifactCardPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        artifactCardPanel.setBackground(Color.WHITE);
        artifactCardPanel.setBounds(450, 353, 444, 118);
        contentPane.add(artifactCardPanel);
        
        JLabel artifactCardsNameLabel = new JLabel("Artifact Cards");
        artifactCardPanel.add(artifactCardsNameLabel);
        

        JPanel deductionBoardPanel = new JPanel();
        deductionBoardPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        deductionBoardPanel.setBackground(Color.WHITE);
        deductionBoardPanel.setBounds(619, 62, 275, 291);
        contentPane.add(deductionBoardPanel);
        
        JLabel deductionBoardNameLabel = new JLabel("Deduction Board");
        deductionBoardPanel.add(deductionBoardNameLabel);
        
        JButton deductionPageButton = new JButton("Click to go to the deduction board!");
        deductionBoardPanel.add(deductionPageButton);
        
        deductionPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of DeductionBoard and display it              
                //deductionBoard.display();
            	gameController.displayDeductionBoardForCurrentPlayer();
            }
        });
        
        //to update the game state when a player performs an action
        JButton actionPerformedButton = new JButton("Action Performed");
        actionPerformedButton.setBounds(362, 130, 174, 20);
        contentPane.add(actionPerformedButton);
        
        actionPerformedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameController.getActionPerformed()) {
                	gameController.updateState();
                    currentPlayerLabel.setText("Current Player: " + gameController.getCurrentPlayer().getNickname());
                    currentTurnLabel.setText("Current Turn: " + gameController.getCurrentTurn());
                    currentRoundLabel.setText("Current Round: " + gameController.getCurrentRound());

                }
            }
        });
    }*/
    
    public BoardScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }
    
    private void initializeFrame() {
        setTitle("Ku Alchemist Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 900, 505); // Adjust the size accordingly
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
    }
    
    private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/BoardScreen.fxml"));
                Parent root = loader.load();
                BoardScreenController controller = loader.getController();
                controller.setBoardScreenFrame(this);
                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

	
	private void playSound(String soundFilePath) {
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
	
	/*
	public void display() {
	    JFrame frame = new JFrame("Board Screen"); // Create a new JFrame
	    frame.setSize(900, 505); // Set the size of the frame

	    JFXPanel fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
	    frame.add(fxPanel); // Add JFXPanel to JFrame

	    Platform.runLater(() -> {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("/ui/swing/screens/fxmlfiles/BoardScreen.fxml"));
	            Scene scene = new Scene(root);
	            fxPanel.setScene(scene);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });

	    frame.setVisible(true); // Make the frame visible
	}*/

}
