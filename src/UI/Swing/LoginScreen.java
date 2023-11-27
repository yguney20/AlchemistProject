package UI.Swing;
import javax.swing.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.logging.Logger;
import java.io.FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javax.swing.JSeparator;
import java.awt.event.MouseMotionAdapter;


public class LoginScreen extends JFrame {


	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	
	private int mouseX, mouseY;
	


	 
	public LoginScreen() {
			
		setUndecorated(true);
		setBackground(new Color(255, 255, 255));
		setTitle("PhotoCloud Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 900);
		setLocationRelativeTo(null);
		
		
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				
				setLocation(getX() + e.getX() - mouseX, getY() + e.getY() - mouseY);
				
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				mouseX = e.getX();
				mouseY = e.getY();
				
			}
		});
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		
		ImageIcon logInMainIcon = new ImageIcon("src/UI/Swing/Images/wizardLogo.png");
		Image logInMainImage = logInMainIcon.getImage(); // transform it 
		Image newlogInMainImage = logInMainImage.getScaledInstance(550, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way 
		logInMainIcon = new ImageIcon(newlogInMainImage);  // transform it back
		
		
		lblNewLabel.setIcon(logInMainIcon);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(83, 226, 585, 429);
		contentPane.add(lblNewLabel);
		
		JPanel LogInPane = new JPanel();
		LogInPane.setBorder(null);
		LogInPane.setBackground(new Color(128, 0, 128));
		LogInPane.setBounds(798, 0, 602, 900);
		contentPane.add(LogInPane);
		LogInPane.setLayout(null);
		
		JPanel logInButtonPanel = new JPanel();
		logInButtonPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		logInButtonPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logInButtonPanel.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		logInButtonPanel.setBackground(new Color(128, 0, 128));
		logInButtonPanel.setBounds(139, 512, 144, 57);
		LogInPane.add(logInButtonPanel);
		logInButtonPanel.setLayout(null);
		
		JLabel logInLabel = new JLabel("Log In");
		logInLabel.setBounds(32, 11, 79, 46);
		logInButtonPanel.add(logInLabel);
		logInLabel.setFont(new Font("Sylfaen", Font.BOLD, 24));
		logInLabel.setForeground(new Color(255, 255, 255));
		
		JPanel signUpButtonPanel = new JPanel();
		signUpButtonPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		signUpButtonPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				
				
			}
		});
		
		
		signUpButtonPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signUpButtonPanel.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		signUpButtonPanel.setBackground(new Color(128, 0, 128));
		signUpButtonPanel.setBounds(355, 512, 144, 57);
		LogInPane.add(signUpButtonPanel);
		signUpButtonPanel.setLayout(null);
		
		JLabel signUpLabel = new JLabel("Sign Up");
		signUpLabel.setBounds(29, 11, 89, 46);
		signUpButtonPanel.add(signUpLabel);
		signUpLabel.setForeground(Color.WHITE);
		signUpLabel.setFont(new Font("Sylfaen", Font.BOLD, 24));
		
		usernameTextField = new JTextField();
		usernameTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				usernameTextField.setText("");
				usernameTextField.removeMouseListener(this);
				
			}
		});
		usernameTextField.setForeground(new Color(255, 255, 255));
		usernameTextField.setBackground(new Color(128, 0, 128));
		usernameTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
		usernameTextField.setText("Username");
		usernameTextField.setBounds(244, 317, 255, 36);
		LogInPane.add(usernameTextField);
		usernameTextField.setBorder(null);
		usernameTextField.setCaretColor(new Color(255, 255, 255));
		usernameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				passwordTextField.setText("");
				passwordTextField.removeMouseListener(this);
				
			}
		});
		passwordTextField.setCaretColor(new Color(255, 255, 255));
		passwordTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
		passwordTextField.setForeground(new Color(255, 255, 255));
		passwordTextField.setBackground(new Color(128, 0, 128));
		passwordTextField.setText("Password");
		passwordTextField.setBounds(244, 388, 255, 35);
		LogInPane.add(passwordTextField);
		passwordTextField.setBorder(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(159, 317, 42, 57);
		LogInPane.add(lblNewLabel_3);
		
		ImageIcon whiteUsernameImageIcon = new ImageIcon("src/UI/Swing/Images/Icons/white_username.png");
		Image whiteUsernameImage = whiteUsernameImageIcon.getImage(); // transform it 
		Image newWhiteUsernameImage = whiteUsernameImage.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		whiteUsernameImageIcon = new ImageIcon(newWhiteUsernameImage);  // transform it back
		
		
		lblNewLabel_3.setIcon(whiteUsernameImageIcon);
		
		JSeparator separator = new JSeparator();
		separator.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		separator.setBounds(244, 362, 213, 3);
		LogInPane.add(separator);
		separator.setForeground(new Color(255, 255, 255));
		separator.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_2 = new JLabel("Forget Password?");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_2.setBounds(244, 651, 185, 36);
		LogInPane.add(lblNewLabel_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBorder(new LineBorder(new Color(255, 255, 255), 4));
		separator_2.setBackground(Color.WHITE);
		separator_2.setBounds(244, 434, 213, 3);
		LogInPane.add(separator_2);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(159, 388, 50, 48);
		LogInPane.add(lblNewLabel_4);
		
		ImageIcon whiteKeyImageIcon = new ImageIcon("src/UI/Swing/Images/Icons/white_key.png");
		Image image = whiteKeyImageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		whiteKeyImageIcon = new ImageIcon(newimg);  // transform it back
		lblNewLabel_4.setIcon(whiteKeyImageIcon);
		
		JLabel exitLabel = new JLabel("X");
		exitLabel.setBounds(557, 11, 49, 51);
		LogInPane.add(exitLabel);
		exitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exitLabel.addMouseListener(new MouseAdapter() { //Exits when pushed to the red X button.
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		exitLabel.setForeground(new Color(255, 0, 0));
		exitLabel.setBackground(new Color(0, 0, 0));
		exitLabel.setFont(new Font("Segoe Print", Font.BOLD, 42));
		
		setVisible(true);
	}




/**
* @Author -- H. Sarp Vulas
*/

  

    public void display() {
        // Make the frame visible
        setVisible(true);
    }
}
