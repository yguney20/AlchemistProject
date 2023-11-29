package UI.Swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
        contentPane.setLayout(null);

    }


    public void display() {
        setVisible(true); // Show the board
    }
}
