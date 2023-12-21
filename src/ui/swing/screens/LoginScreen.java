package ui.swing.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.util.UIScale;

import domain.controllers.LoginController;
import ui.swing.screens.screencomponents.LoginBackground;


public class LoginScreen extends JFrame{

	private JFrame frame;
    private JPanel contentPane;
    private JLabel selected;
    private String player1Avatar;
    private String player2Avatar;
    private int initialX;
    private int initialY;
    private LoginController loginController = LoginController.getInstance();

	 private LoginBackground loginBack;
	 /**
	  * @wbp.nonvisual location=507,309
	  */
	 private final JLabel label = new JLabel("New label");

	    public LoginScreen() {
	        init();
   
	    }

	    private void init() {
	    		    	
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        setUndecorated(true);
	        setSize(UIScale.scale(new Dimension(1356, 768)));
	        setLocationRelativeTo(null);

	        loginBack = new LoginBackground();
	        setContentPane(loginBack);

	        addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowOpened(WindowEvent e) {
	            	loginBack.initOverlay(LoginScreen.this);
	                loginBack.play();
	            }

	            @Override
	            public void windowClosing(WindowEvent e) {
	                loginBack.stop();
	            }
	        });
	    }

	    public static void main(String[] args) {
	

	        EventQueue.invokeLater(() -> new LoginScreen().setVisible(true));
	    }

	

    public void display() {
        setVisible(true);
    }
   }

