package UI.Swing;

import javax.swing.*;
import java.awt.*;

public class ImageResizer {

    /**
     * Resizes an image from the given path to fit the width and height of the provided JPanel.
     *
     * @param panel     the JPanel which dictates the size of the resized image.
     * @param imagePath the path to the image resource.
     * @return a new ImageIcon resized to fit the panel.
     */
    public static ImageIcon getResizedIcon(JPanel panel, String imagePath) {
        // Obtain the ImageIcon
        ImageIcon icon = new ImageIcon(ImageResizer.class.getResource(imagePath));
        
        // Resize the icon
        ImageIcon resizedIcon = resizeIcon(icon, panel.getWidth(), panel.getHeight());
        
        // Return the resized ImageIcon
        return resizedIcon;
    }

    /**
     * Resizes an ImageIcon to the given width and height.
     *
     * @param icon   the original ImageIcon.
     * @param width  the new width.
     * @param height the new height.
     * @return a new ImageIcon with the specified width and height.
     */
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width - 8, height , Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
