package ui.swing.screens.screencomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class AvatarDescription extends JPanel {
	
	 private javax.swing.JLabel lbTitle;
	 private ui.swing.desingsystem.TextArea txt;
	
	public AvatarDescription(String title, String description) {
        initComponents();
        setOpaque(false);
        txt.setBackground(new Color(0, 0, 0, 0));
        lbTitle.setText(title);
        txt.setText(description);
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        txt = new ui.swing.desingsystem.TextArea();

        lbTitle.setFont(new Font("Serif", Font.BOLD, 20)); 
        lbTitle.setForeground(new java.awt.Color(100, 57, 250));
        lbTitle.setText("Title");
        lbTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));

        txt.setFont(new java.awt.Font("Serif", Font.BOLD, 15));
        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        txt.setColumns(20);
        txt.setRows(5);
         
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 200));
        Path2D.Float f = new Path2D.Float();
        f.moveTo(0, 30);
        f.curveTo(0, 30, // First control point (x1, y1)
                100, 30, // Second control point (x2, y2)
                150, 30); // End point of the curve (x3, y3)
        f.lineTo(150, getHeight());
        f.lineTo(0, getHeight());
        g2.fill(f);
        super.paintComponent(grphcs);
    }


   
	

}

/**
* @Author -- H. Sarp Vulas
*/
