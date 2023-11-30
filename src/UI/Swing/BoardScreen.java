package UI.Swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class BoardScreen extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public BoardScreen() {
        setTitle("Ku Alchemist Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 505); // Adjust the size accordingly
        setResizable(false);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
        
        JPanel publicationPanel = new JPanel();
        publicationPanel.setBackground(new Color(254, 255, 255));
        publicationPanel.setBounds(6, 62, 275, 291);
        contentPane.add(publicationPanel);
        
        JPanel potionBrewingPanel = new JPanel();
        potionBrewingPanel.setBackground(Color.WHITE);
        potionBrewingPanel.setBounds(6, 353, 444, 118);
        contentPane.add(potionBrewingPanel);
        potionBrewingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel cauldronImage = new JLabel("");
        cauldronImage.setIcon(new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/gameBoardUI/cauldronImage.png")));
        potionBrewingPanel.add(cauldronImage);
        
        JPanel playerIdNamePanel = new JPanel();
        playerIdNamePanel.setBackground(new Color(254, 255, 255));
        playerIdNamePanel.setBounds(6, 6, 275, 58);
        contentPane.add(playerIdNamePanel);
        
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBounds(619, 6, 275, 58);
        contentPane.add(menuPanel);
        menuPanel.setLayout(new GridLayout(0, 2, 20, 0));
        
        JButton menuButton = new JButton("Menu");
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuPanel.add(menuButton);
        
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
        ImageIcon preResizeMenuImageIcon = new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/gameBoardUI/kuAlchemistGameBoardTitleImage.png"));
        Image preResizeMenuImage = preResizeMenuImageIcon.getImage();

        // Resize the image
        Image resizedMenuImage = preResizeMenuImage.getScaledInstance(menuPanel.getWidth(), menuPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizeMenuImageIcon = new ImageIcon(resizedMenuImage);

        // Set the resized icon to the JLabel
        gameBoardImage.setIcon(postResizeMenuImageIcon);
        titlePanel.add(gameBoardImage);
        
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBounds(362, 88, 174, 58);
        contentPane.add(dashboardPanel);
        
        JPanel ingredientCardPanel = new JPanel();
        ingredientCardPanel.setBackground(Color.WHITE);
        ingredientCardPanel.setBounds(363, 158, 170, 183);
        contentPane.add(ingredientCardPanel);
        ingredientCardPanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel ingredientCardImage = new JLabel("");
        
        ImageIcon preResizeIngredientCardImageIcon = new ImageIcon(BoardScreen.class.getResource("/UI/Swing/Images/gameBoardUI/gameBoardIngredientCardBacksideImage.png"));
        Image preResizeIngredientCardImage = preResizeIngredientCardImageIcon.getImage();

        // Resize the image
        Image resizedIngredientCardImage = preResizeIngredientCardImage.getScaledInstance(ingredientCardPanel.getWidth(), ingredientCardPanel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon postResizedIngredientCardImageIcon = new ImageIcon(resizedIngredientCardImage);

        ingredientCardImage.setIcon(postResizedIngredientCardImageIcon);
        ingredientCardPanel.add(ingredientCardImage);
        
        JScrollPane DeductionBoardScrollPanel = new JScrollPane();
        DeductionBoardScrollPanel.setBounds(619, 62, 275, 291);
        contentPane.add(DeductionBoardScrollPanel);
        
        JPanel artifactCardPanel = new JPanel();
        artifactCardPanel.setBackground(Color.WHITE);
        artifactCardPanel.setBounds(450, 353, 444, 118);
        contentPane.add(artifactCardPanel);

    }


    public void display() {
        setVisible(true); // Show the board
    }
}
