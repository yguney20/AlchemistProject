package ui.swing.screens.screencomponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ui.swing.desingsystem.RoundedCornerPanel;
import ui.swing.model.CardModel;

public class AvatarCardScreen extends JPanel {


    private final CardModel data;
    private final Timer timer;
    private final Timer timerStop;
    private final AvatarDescription avatarDescription;
    private int y = 170;
    private int speed = 10;
    private boolean showing = false;

    private boolean isSelected = false;
    private boolean isHovered = false;
    

    public AvatarCardScreen(CardModel data) {

        this.data = data;
        initComponents();
        setOpaque(false);
        
        
        avatarDescription = new AvatarDescription(data.getTitle(), data.getDescription());
        avatarDescription.setLocation(0, y);
        setPreferredSize(new Dimension(180, 240));
        avatarDescription.setSize(new Dimension(180, 180));
        add(avatarDescription);
        
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (showing) {
                    y -= speed;
                    if (y <= 80) {
                        y = 80;
                        avatarDescription.setLocation(0, y);
                        timer.stop();
                    } else {
                        avatarDescription.setLocation(0, y);
                    }
                } else {
                    y += speed;
                    if (y >= 170) {
                        y = 170;
                        avatarDescription.setLocation(0, y);
                        timer.stop();
                    } else {
                        avatarDescription.setLocation(0, y);
                    }
                }
            }
        });
        //  200 for delay hide description
        timerStop = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                showing = false;
                timerStop.stop();
                timer.start();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                showing = true;
                timerStop.stop();
                timer.start();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                timerStop.start();
            }
        });
    }

    public CardModel getData() {
		return data;
	}

	public boolean isSelected() {
		return isSelected;
	}
    
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}

	@SuppressWarnings("unchecked")
    private void initComponents() {

        setLayout(null);
    }
	


    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Rectangle size = getAutoSize(data.getIcon());
        g2.drawImage(toImage(data.getIcon()), size.x, size.y, size.width, size.height, null);
        super.paintComponent(grphcs);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, getHeight(), new Color(100, 0, 245, 100), 0, getHeight() - 50, new Color(50, 0, 245, 10));
        g2.setPaint(g);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
         
        if (isSelected) {
            Graphics2D g2d = (Graphics2D) grphcs.create();
            GradientPaint g3 = new GradientPaint(0, getHeight(), new Color(215, 167, 0, 100), 0, getHeight() - 50, new Color(215, 167, 0, 250));
            g2d.setPaint(g3);
            g2d.setStroke(new BasicStroke(7)); // Adjust the border thickness
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2d.dispose();
        }
          
        
    }

    private Rectangle getAutoSize(Icon image) {
        int w = 180;
        int h = 240;
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }

}

/**
* @Author -- H. Sarp Vulas
*/
