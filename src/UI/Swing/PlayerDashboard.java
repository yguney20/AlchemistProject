package UI.Swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.GameController;
import domain.GameObjects.ArtifactCard;
import domain.GameObjects.Player;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

public class PlayerDashboard extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton forageForIngredient;
	private JButton transmuteIngredient;
	private JButton buyArtifact;
	private GameController gameController;
	private JButton quitButton = new JButton("X");
	private BuyArtifactScreen buyArtifactScreen = new BuyArtifactScreen();

	/**
	 * Create the frame.
	 */

	public PlayerDashboard(GameController gameController) {
		this.gameController = gameController;
		setTitle("Player Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(950, 50, 450, 500);
		setUndecorated(true);  // Make the JFrame undecorated
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nickname");
		lblNewLabel.setBounds(36, 47, 49, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Golds");
		lblNewLabel_1.setBounds(36, 77, 49, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sickness Level");
		lblNewLabel_2.setBounds(36, 105, 80, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Reputation Points");
		lblNewLabel_3.setBounds(36, 130, 100, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("ACTIONS");
		lblNewLabel_4.setBounds(180, 173, 49, 14);
		contentPane.add(lblNewLabel_4);
		
		forageForIngredient = new JButton("Forage For Ingredient");
		forageForIngredient.setBounds(136, 209, 151, 23);
		contentPane.add(forageForIngredient);
		
		transmuteIngredient = new JButton("Transmute Ingredient");
		transmuteIngredient.setBounds(136, 243, 151, 23);
		contentPane.add(transmuteIngredient);
		
		buyArtifact = new JButton("Buy Artifact");
		buyArtifact.setBounds(136, 279, 151, 23);
		contentPane.add(buyArtifact);
		
		JButton btnNewButton_3 = new JButton("Make Experiment");
		btnNewButton_3.setBounds(136, 315, 151, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Sell a Potion");
		btnNewButton_4.setBounds(136, 354, 151, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Publish Theory");
		btnNewButton_5.setBounds(136, 393, 151, 23);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Debunk Theory");
		btnNewButton_6.setBounds(134, 427, 153, 23);
		contentPane.add(btnNewButton_6);
		
		addActionEvent();

		
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

	}
	
    public void addActionEvent() {
	       forageForIngredient.addActionListener(this);
	       transmuteIngredient.addActionListener(this);
	       buyArtifact.addActionListener(this);

    }
    
	public void actionPerformed(ActionEvent event) {

		if(event.getSource()== buyArtifact) {
			buyArtifactScreen.display();			
		}

		if(event.getSource()==forageForIngredient) {
			Player currentPlayer = gameController.getCurrentPlayer();
			gameController.forageForIngredient(currentPlayer);
			this.setVisible(false);
		}
	}
	
	public void display() {
		this.setVisible(true);
	}
}

