package UI.Swing.MenuScreens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class SettingsScreen extends JFrame {

    private JButton backButton = new JButton("Back");
    private JButton saveButton = new JButton("Save");
    private JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // Volume slider with a range of 0 to 100
    private JButton muteButton = new JButton("Mute/Unmute");
    
    private int initialX;
    private int initialY;

    private JPanel contentPane;

    public SettingsScreen(Frame frame) {
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(getClass().getResource("/UI/Swing/Images/screenBackground.jpg")).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the background color of the JFrame
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        
     // Add a MouseListener to make the frame movable
        contentPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        contentPane.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - initialX;
                int y = getLocation().y + e.getY() - initialY;
                setLocation(x, y);
            }
        });

        setSize(1000, 800);
        setUndecorated(true); // Remove title bar
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/UI/Swing/Images/logo.png"));
        setIconImage(icon.getImage());

        // Add ActionListener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Direct user to the previous screen (replace with actual implementation)
                frame.setVisible(true);
                dispose();
            }
        });

        // Add the Back button to the top left of the frame
        backButton.setBounds(10, 10, 75, 25);
        contentPane.add(backButton);

        // Add "Game Music Level" label
        JLabel volumeLabel = new JLabel("Game Music Level");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setBounds(10, 50, 150, 25);
        contentPane.add(volumeLabel);

        // Add Volume slider
        volumeSlider.setBounds(160, 50, 200, 25);
        contentPane.add(volumeSlider);

        // Add Mute/Unmute button
        muteButton.setBounds(370, 50, 120, 25);
        contentPane.add(muteButton);

        // Add Save button
        saveButton.setBounds(10, 100, 100, 25);
        contentPane.add(saveButton);

        // Add ActionListener to the Save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the settings (replace with actual implementation)
                JOptionPane.showMessageDialog(SettingsScreen.this, "Settings saved!");
            }
        });

        // Add ActionListener to the Mute/Unmute button
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement mute/unmute logic here (replace with actual implementation)
                if (volumeSlider.getValue() == 0) {
                    // Unmute to the last volume level
                    volumeSlider.setValue(50);
                } else {
                    // Mute
                    volumeSlider.setValue(0);
                }
            }
        });

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Set the content pane to the custom JPanel
        setContentPane(contentPane);
        setLayout(new BorderLayout()); // Set layout for JFrame
    }

    public void display() {
        // Make the frame visible
        setVisible(true);
    }

}