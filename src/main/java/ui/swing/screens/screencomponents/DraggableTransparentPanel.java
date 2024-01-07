package ui.swing.screens.screencomponents;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DraggableTransparentPanel extends JPanel{
	
	
	private Point initialClick;

    public DraggableTransparentPanel() {
        setOpaque(false); // Make the panel transparent
        setupDragFunctionality();
    }

    private void setupDragFunctionality() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(DraggableTransparentPanel.this);
                if (topFrame != null) {
                    int thisX = topFrame.getLocation().x;
                    int thisY = topFrame.getLocation().y;

                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;

                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    topFrame.setLocation(X, Y);
                }
            }
        };
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

}

/**
* @Author -- H. Sarp Vulas
*/
