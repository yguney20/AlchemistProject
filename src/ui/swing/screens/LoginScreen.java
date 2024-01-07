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
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.util.UIScale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import domain.controllers.LoginController;
import ui.swing.screens.screencomponents.LoginBackground;
import ui.swing.screens.screencontrollers.LoginScreenController;


public class LoginScreen extends JFrame{

	private JFrame frame;
    private JPanel contentPane;
    private JLabel selected;
    private String player1Avatar;
    private String player2Avatar;
    private int initialX;
    private int initialY;
    private LoginBackground loginBack;
    private LoginController loginController = LoginController.getInstance();
    
    private JFXPanel fxPanel;
	
	
	public LoginScreen() {
        initializeFrame();
        initializeJavaFXComponents();
    }
	
	private void initializeFrame() {
        setTitle("Ku Alchemist Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 50, 900, 505); // Adjust the size accordingly
        setResizable(false);
        fxPanel = new JFXPanel(); // This will prepare JavaFX toolkit and environment
        add(fxPanel);
    }
	
	private void initializeJavaFXComponents() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/swing/screens/fxmlfiles/LoginScreen.fxml"));
                Parent root = loader.load();
                LoginScreenController controller = loader.getController();
                controller.setLoginScreenFrame(this);
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
	 
	
	 /**
	  * @wbp.nonvisual location=507,309
	  */
	 
	 /*
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
    
    public void closeWindow() {
        this.dispose();
    }
    */

   }

