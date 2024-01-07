package ui.swing.desingsystem;


import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;

import domain.gameobjects.IngredientCard;
import ui.swing.screens.ElixirOfInsightArtifactScreen;

import java.awt.event.MouseEvent;

class DraggableIngredientLabel extends JLabel {
    private Point initialClick;
    private final IngredientCard ingredientCard;
    private final ElixirOfInsightArtifactScreen screen;

    public DraggableIngredientLabel(IngredientCard card, ElixirOfInsightArtifactScreen screen) {
        this.ingredientCard = card;
        this.screen = screen;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // get location of Window
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move image to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
    }

    public IngredientCard getIngredientCard() {
        return ingredientCard;
    }
}
