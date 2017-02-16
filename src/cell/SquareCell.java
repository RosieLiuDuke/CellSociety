package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The sub class of Cell super class, to implement square shaped cells.
 * @author Yilin Gao
 *
 */
public class SquareCell extends Cell {
	
	private Rectangle rectangle;


	/**
	 * Constructor of the SquareCell class.
	 * @param centerX: the x coordinate of the cell's geometric center
	 * @param centerY: the y coordinate of the cell's geometric center
	 * @param width: the longest distance from left to right
	 * @param height: the longest distance from up to down
	 * @param status: the status of the cell
	 * @param visible: if the grid of the cell is visible
	 */
	public SquareCell (double centerX, double centerY, double width, double height, int status, boolean visible){
		super(status);		
		rectangle = new Rectangle(centerX - width / 2, centerY - height / 2, width, height);
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
