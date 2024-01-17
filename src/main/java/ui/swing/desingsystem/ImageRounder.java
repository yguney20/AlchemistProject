package ui.swing.desingsystem;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImageRounder {

    /**
     * Rounds the corners of a given ImageIcon.
     *
     * @param icon         The original ImageIcon.
     * @param cornerRadius The radius of the rounded corners.
     * @return A new ImageIcon with rounded corners.
     */
    public ImageIcon roundCorners(ImageIcon icon, int cornerRadius) {
        Image image = icon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage roundedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = roundedImage.createGraphics();

        // Apply rendering hints for quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle
        g2d.setComposite(AlphaComposite.Src);
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fill(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));

        // Draw the image over the rectangle
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.drawImage(image, 0, 0, null);

        // Dispose the graphics object
        g2d.dispose();

        return new ImageIcon(roundedImage);
    }
}
