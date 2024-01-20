package ui.swing.screens.screencontrollers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import ui.swing.screens.scenes.DeductionBoard;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DeductionBoardController {

    @FXML
    private AnchorPane deductionBoardPane;
    @FXML
    private Canvas deductionBoardCanvas;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<Sign> signSelectionComboBox;
    @FXML
    private Button undoButton;


    private Image deductionBoardImage;
    private ArrayList<SignPlacement> signPlacements = new ArrayList<>();
    private final int signSize = 14;
    private JFrame deductionBoard;

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

    @FXML
    public void initialize() {
        // Ensure the canvas is properly sized before loading the image
        deductionBoardPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            deductionBoardCanvas.setWidth(newVal.doubleValue());
            loadDeductionBoardImage();
        });
        deductionBoardPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            deductionBoardCanvas.setHeight(newVal.doubleValue());
            loadDeductionBoardImage();
        });

        setupComboBox();
        setupCanvas();
    }

  
    private void loadDeductionBoardImage() {
        // Only load the image if the canvas has been sized
        if (deductionBoardCanvas.getWidth() > 0 && deductionBoardCanvas.getHeight() > 0) {
            deductionBoardImage = new Image("/images/deductionBoardUI/deductionBoard.png",
                                            deductionBoardCanvas.getWidth(),
                                            deductionBoardCanvas.getHeight(),
                                            true,
                                            false);
            // Check if the image was loaded successfully and draw it
            if (deductionBoardImage.isError()) {
                System.out.println("Error loading deduction board image.");
            } else {
                drawDeductionBoard();
            }
        }
    }
    
    public double getImageWidth() {
        return deductionBoardImage.getWidth();
    }

    public double getImageHeight() {
        return deductionBoardImage.getHeight();
    }

    public void clearSignPlacements() {
        signPlacements.clear();
        drawDeductionBoard();
    }
    
    public void addSignPlacement(Point point, Sign sign) {
        signPlacements.add(new SignPlacement(point, sign));
        drawDeductionBoard();
    }

    private void setupComboBox() {
        signSelectionComboBox.getItems().setAll(Sign.values());
        signSelectionComboBox.getSelectionModel().select(Sign.BLUE_PLUS); // Default sign
    }

    private void setupCanvas() {
        deductionBoardCanvas.setWidth(deductionBoardPane.getWidth());
        deductionBoardCanvas.setHeight(deductionBoardPane.getHeight());
        deductionBoardCanvas.setOnMouseClicked(e -> handleCanvasClick(e.getX(), e.getY()));
    }

    private void handleCanvasClick(double x, double y) {
        Sign selectedSign = signSelectionComboBox.getValue();
        signPlacements.add(new SignPlacement(new Point((int) x, (int) y), selectedSign));
        drawDeductionBoard(); // Redraw the board to include the new sign
    }

    private void drawDeductionBoard() {
        GraphicsContext gc = deductionBoardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, deductionBoardCanvas.getWidth(), deductionBoardCanvas.getHeight());
        gc.drawImage(deductionBoardImage, (deductionBoardCanvas.getWidth()/2)-(deductionBoardImage.getWidth()/2), (deductionBoardCanvas.getHeight()/2)-(deductionBoardImage.getHeight()/2));
        drawSigns(gc);
    }

    private void drawSigns(GraphicsContext gc) {
        for (SignPlacement signPlacement : signPlacements) {
            drawSign(gc, signPlacement.point, signPlacement.sign);
        }
    }

    private void drawSign(GraphicsContext gc, Point point, Sign sign) {
        // Set the stroke color based on the sign
        Color color = switch (sign) {
            case BLUE_PLUS, BLUE_MINUS -> Color.BLUE;
            case RED_PLUS, RED_MINUS, RED_CROSS -> Color.RED;
            case GREEN_PLUS, GREEN_MINUS -> Color.GREEN;
            case NEUTRAL -> Color.BLACK;
        };
        gc.setStroke(color);
        gc.setLineWidth(3);

        // Draw the sign based on its type
        switch (sign) {
            case BLUE_PLUS, RED_PLUS, GREEN_PLUS -> {
                gc.strokeLine(point.x - signSize, point.y, point.x + signSize, point.y);
                gc.strokeLine(point.x, point.y - signSize, point.x, point.y + signSize);
            }
            case BLUE_MINUS, RED_MINUS, GREEN_MINUS -> {
                gc.strokeLine(point.x - signSize, point.y, point.x + signSize, point.y);
            }
            case NEUTRAL -> {
                gc.strokeOval(point.x - signSize / 2.0, point.y - signSize / 2.0, signSize, signSize);
            }
            case RED_CROSS -> {
                gc.strokeLine(point.x - signSize / 2.0, point.y - signSize / 2.0, point.x + signSize / 2.0, point.y + signSize / 2.0);
                gc.strokeLine(point.x + signSize / 2.0, point.y - signSize / 2.0, point.x - signSize / 2.0, point.y + signSize / 2.0);
            }
        }
    }

 
    
    @FXML
    private void handleBackButtonAction() {
    	if (deductionBoard != null) {
    		deductionBoard.dispose(); // Close the JFrame
        }
    }

	public void setDeductionBoardFrame(DeductionBoard deductionBoard) {
		// TODO Auto-generated method stub
		this.deductionBoard = deductionBoard;
		
	}
	
	@FXML
	private void handleUndoButtonAction() {
	    undoLastSignPlacement();
	    drawDeductionBoard();
	}
	
	private void undoLastSignPlacement() {
	    if (!signPlacements.isEmpty()) {
	        signPlacements.remove(signPlacements.size() - 1);
	    }
	}

    // Additional methods for managing the board...
}
