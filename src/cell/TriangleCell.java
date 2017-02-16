package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * The sub class of Cell super class, to implement triangle shaped cells.
 * @author Yilin Gao
 *
 */
public class TriangleCell extends Cell {
	
	private Polygon triangle;


	/**
	 * Constructor of the TriangleCell class.
	 * @param centerX: the x coordinate of the cell's geometric center
	 * @param centerY: the y coordinate of the cell's geometric center
	 * @param width: the longest distance from left to right
	 * @param height: the longest distance from up to down
	 * @param status: the status of the cell
	 * @param visible: if the grid of the cell is visible
	 * @param up: if the triangle is upward or downward
	 */
	public TriangleCell(double centerX, double centerY, double width, double height, int status, boolean visible, boolean up) {
		super(status);
		triangle = new Polygon();
		Double[] points = new Double[6];
		if (up){
			points[0] = centerX;
			points[1] = centerY - 2 * height / 3;
			points[2] = centerX + width / 2;
			points[3] = centerY + height / 3;
			points[4] = centerX - width / 2;
			points[5] = centerY + height / 3;
		}
		else{
			points[0] = centerX;
			points[1] = centerY + 2 * height / 3;
			points[2] = centerX + width / 2;
			points[3] = centerY - height / 3;
			points[4] = centerX - width / 2;
			points[5] = centerY - height / 3;
		}
		triangle.getPoints().addAll(points);
		if (visible){
			triangle.setStroke(Color.BLACK);
		}
	}

	@Override
	public Shape getShape() {
		return triangle;
	}

	@Override
	public void changeColor(Color c) {
		triangle.setFill(c);
	}

}
