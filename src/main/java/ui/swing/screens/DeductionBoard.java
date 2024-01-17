package ui.swing.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.controllers.GameController;
import ui.swing.screens.scenes.BoardScreen;

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
    private final ArrayList<Point> circleCenters = new ArrayList<>();
    private final int circleRadius = 32; //(2r)
    // Enum to represent different signs
    public enum Sign {
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
        setBounds(100, 100, 900, 505);
        setUndecorated(true);
        setResizable(false);
        initializeCircleCenters();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(new Color(231, 219, 182));
        contentPane.setLayout(null);

        // Load and scale the deduction board image
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/deductionBoardUI/deductionBoard.png"));
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
        
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               
            	dispose();
                    
                
            }
        });
        
     
        JComboBox<Sign> signSelectionComboBox = new JComboBox<>(Sign.values());
        signSelectionComboBox.setBounds(728, 98, 139, 33);
        contentPane.add(signSelectionComboBox);
        
        signSelectionComboBox.addActionListener(e -> selectedSign = (Sign) signSelectionComboBox.getSelectedItem());
        
        deductionBoardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                // Check if the click is within the radius of any circle centers
                //for (Point center : circleCenters) {
                    //if (clickPoint.distance(center) <= circleRadius) {
                        // Click is inside a circle, proceed with sign placement
                        GameController.getInstance().playerMadeDeduction(clickPoint.x, clickPoint.y, selectedSign.ordinal());
                        signPlacements.add(new SignPlacement(clickPoint, selectedSign));
                        deductionBoardPanel.repaint();
                        return; // Exit after placing the sign
                   //}
                //}
            }
        });
    }
    
    private void initializeCircleCenters() {
        // Populate this list with the coordinates of the center of each white circle.
        // The following are placeholder values, replace them with the actual coordinates.
        circleCenters.add(new Point(272, 97)); // Replace with actual x and y coordinates
        circleCenters.add(new Point(272-circleRadius, 50)); // Replace with actual x and y coordinates
        // ... Add all circle centers ...
    }
    public void display() {
        setVisible(true); // Show the board
    }
    
    public void clearSignPlacements() {
        signPlacements.clear();
        repaint(); // Repaint to update the UI
    }

    public void addSignPlacement(Point point, Sign sign) {
        // Add a new sign placement
        signPlacements.add(new SignPlacement(point, sign));
        // Repaint the panel to reflect the new sign
        repaint();
    }

    
}
