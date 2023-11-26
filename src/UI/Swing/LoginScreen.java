package UI.Swing;
import javax.swing.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        // Set up the login screen window
        setTitle("Login - KU-Alchemist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the login window
        setSize(300, 200);
        // Add login components (text fields, buttons, etc.) here

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    public void display() {
        // Make the frame visible
        setVisible(true);
    }
}
