package ui.swing.desingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedCornerPanel extends JPanel {

    private int cornerRadius; // Radius of the rounded corners

    public RoundedCornerPanel(int cornerRadius) {
        super();
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Create a rounded rectangle the size of your panel
        Shape round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2d.setColor(getBackground());
        
        // Fill the shape with the background color of the panel
        g2d.fill(round);
        g2d.dispose();
    }

    // Rest of your class code
}
