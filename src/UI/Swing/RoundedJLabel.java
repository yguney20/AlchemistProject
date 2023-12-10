package UI.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedJLabel extends JLabel {

    private static final int ARC_WIDTH = 30; // Increased roundness
    private static final int ARC_HEIGHT = 30; // Increased roundness
    private Color backgroundColor = Color.WHITE; // Default background color

    public RoundedJLabel(String text) {
        super(text);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(SwingConstants.CENTER);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backgroundColor = new Color(10, 50, 50);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backgroundColor = Color.WHITE;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT));
        super.paintComponent(g2);
        g2.dispose();
    }
}
