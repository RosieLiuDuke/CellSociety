package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareCell extends Cell {
	
	private Rectangle rectangle;

	/**
	* Create a cell with xPosition, yPosition, and a status, which would be used to indicate color.
	* @param xPosition
	* @param yPosition
	* @param size
	* @param status
	**/
	public SquareCell (double centerX, double centerY, double sideX, double sideY, int status, boolean visible){
		super(status);		
		rectangle = new Rectangle(centerX - sideX / 2, centerY - sideY / 2, sideX, sideY);
		if (visible){
			rectangle.setStroke(Color.BLACK);
		}
	}

	@Override
	public Rectangle getShape() {
		return rectangle;
	}

	@Override
	public void changeColor(Color c) {
		rectangle.setFill(c);
	}

}
