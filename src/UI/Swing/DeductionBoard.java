package UI.Swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DeductionBoard extends JFrame {

    private JPanel contentPane;
    private final ArrayList<Point> crosses = new ArrayList<>();
    private Image deductionBoardImage;
    private BoardScreen boardScreen; // Reference to the BoardScreen

    private final ArrayList<SignPlacement> signPlacements = new ArrayList<>();
    private Sign selectedSign = Sign.BLUE_PLUS; // Default sign

    // Enum to represent different signs
    enum Sign {
        BLUE_PLUS, RED_PLUS, GREEN_PLUS, BLUE_MINUS, RED_MINUS, GREEN_MINUS, NEUTRAL, RED_CROSS
    }

    // Inner class to hold sign placements
    private class SignPlacement {
        Point point;
        Sign sign;

        SignPlacement(Point point, Sign sign) {
            this.point = point;
            this.sign = sign;
        }
    }


    public DeductionBoard(BoardScreen boardScreen) {
    	this.boardScreen = boardScreen; 
        setTitle("Ku Alchemist Deduction Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 505);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(new Color(231, 219, 182));
        contentPane.setLayout(null);

        // Load and scale the deduction board image
        ImageIcon icon = new ImageIcon(getClass().getResource("/UI/Swing/Images/deductionBoard.png"));
        deductionBoardImage = icon.getImage().getScaledInstance(463, 465, Image.SCALE_SMOOTH);

        // Create a panel that will display the deduction board image and crosses
        JPanel deductionBoardPanel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(deductionBoardImage, 0, 0, this.getWidth(), this.getHeight(), this);

                // Draw the signs from the signPlacements list
                for (SignPlacement signPlacement : signPlacements) {
                    drawSign(g2d, signPlacement.point, signPlacement.sign);
                }
            }
        	private void drawSign(Graphics g, Point point, Sign sign) {
        		Graphics2D g2d = (Graphics2D) g.create();
                g2d.setStroke(new BasicStroke(2));
                int size = 10;

                switch (sign) {
                    case BLUE_PLUS:
                        g2d.setColor(Color.BLUE);
                        g2d.drawLine(point.x - size, point.y, point.x + size, point.y);
                        g2d.drawLine(point.x, point.y - size, point.x, point.y + size);
                        break;
                    case RED_PLUS:
                        g2d.setColor(Color.RED);
                        g2d.drawLine(point.x - size, point.y, point.x + size, point.y);
                        g2d.drawLine(point.x, point.y - size, point.x, point.y + size);
                        break;
                    case GREEN_PLUS:
                        g2d.setColor(Color.GREEN);
                        g2d.drawLine(point.x - size, point.y, point.x + size, point.y);
                        g2d.drawLine(point.x, point.y - size, point.x, point.y + size);
                        break;
                    case BLUE_MINUS:
                        g2d.setColor(Color.BLUE);
                        g2d.drawLine(point.x - size, point.y, point.x + size, point.y);
                        break;
                    case RED_MINUS:
                        g2d.setColor(Color.RED);
                        g2d.drawLine(point.x - size, point.y, point.x + size, point.y);
                        break;
                    case GREEN_MINUS:
                        g2d.setColor(Color.GREEN);
                        g2d.drawLine(point.x - size, point.y, point.x + size, point.y);
                        break;
                    case NEUTRAL:
                    	g2d.setColor(Color.BLACK); // Set color for neutral sign
                        g2d.drawOval(point.x - size/2, point.y - size/2, size, size);
                        break;
                    case RED_CROSS:
                    	g2d.setColor(Color.RED);
                        g2d.drawLine(point.x - size/2, point.y + size/2, point.x + size/2, point.y - size/2);
                        g2d.drawLine(point.x + size/2, point.y + size/2, point.x - size/2, point.y - size/2);
                        break;
                    	
                }
                g2d.dispose();
            }
        };
        
        deductionBoardPanel.setBounds(220, 6, 463, 465);
        deductionBoardPanel.setOpaque(false);
        

        contentPane.add(deductionBoardPanel);
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 10, 108, 42); // Set the position and size of the back button
        contentPane.add(backButton);
        
        JComboBox<Sign> signSelectionComboBox = new JComboBox<>(Sign.values());
        signSelectionComboBox.setBounds(728, 98, 139, 33);
        contentPane.add(signSelectionComboBox);
        
        signSelectionComboBox.addActionListener(e -> selectedSign = (Sign) signSelectionComboBox.getSelectedItem());
        
        deductionBoardPanel.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                // Add the new sign placement to the list
                signPlacements.add(new SignPlacement(e.getPoint(), selectedSign));
                // Repaint the panel to show the new sign
                deductionBoardPanel.repaint();
            }
        });
        backButton.addActionListener(e -> {
            this.setVisible(false); // Hide the deduction board
            boardScreen.setVisible(true); // Show the board screen
        });
    }
    
    public void display() {
        setVisible(true); // Show the board
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	BoardScreen boardScreen = new BoardScreen(); // You need to create this instance somewhere
                DeductionBoard frame = new DeductionBoard(boardScreen);
                frame.display();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
      
    }
}
