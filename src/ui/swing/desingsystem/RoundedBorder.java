package ui.swing.desingsystem;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder extends AbstractBorder {
    private int radius;
    private Color borderColor;
    private int thickness;
    private int shadowSize;
    private Color shadowColor;

    public RoundedBorder(Color borderColor, int radius, int thickness, int shadowSize, Color shadowColor) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.thickness = thickness;
        this.shadowSize = shadowSize;
        
        // Ensure shadow color has some transparency
        this.shadowColor = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowColor.getAlpha());
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Apply rendering hints for quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Coordinates for the shadow
        int shadowOffsetX = x + thickness + shadowSize;
        int shadowOffsetY = y + thickness + shadowSize;
        int shadowWidth = width - thickness * 2 - shadowSize;
        int shadowHeight = height - thickness * 2 - shadowSize;

        // Create the main shape
        RoundRectangle2D mainShape = new RoundRectangle2D.Float(
                x + thickness, y + thickness, shadowWidth, shadowHeight, radius, radius);
        
        // Create the shadow shape larger than the border to achieve the shadow effect
        Area shadowArea = new Area(new RoundRectangle2D.Float(
                shadowOffsetX, shadowOffsetY, shadowWidth, shadowHeight, radius, radius));
        shadowArea.subtract(new Area(mainShape));

        // Fill the shadow area
        g2.setColor(shadowColor);
        g2.fill(shadowArea);

        // Draw the main shape (border)
        g2.setColor(borderColor);
        g2.draw(mainShape);

        // Dispose graphics
        g2.dispose();
    }
    
    

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness + shadowSize, thickness + shadowSize);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = thickness;
        insets.top = thickness;
        insets.right = thickness + shadowSize;
        insets.bottom = thickness + shadowSize;
        return insets;
    }
}
