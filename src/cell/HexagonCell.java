package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * The sub class of Cell super class, to implement hexagon shaped cells.
 * @author Yilin Gao
 *
 */
public class HexagonCell extends Cell {
	
	private Polygon hexagon;

	/**
	 * Constructor of the HexagonCell class.
	 * @param centerX: the x coordinate of the cell's geometric center
	 * @param centerY: the y coordinate of the cell's geometric center
	 * @param width: the longest distance from left to right
	 * @param height: the longest distance from up to down
	 * @param status: the status of the cell
	 * @param visible: if the grid of the cell is visible
	 */
	public HexagonCell(double centerX, double centerY, double width, double height, int status, boolean visible) {
		super(status);
		hexagon = new Polygon();
		Double[] points = new Double[12];
		points[0] = centerX - width / 4;
		points[1] = centerY - height / 2;
		points[2] = centerX + width / 4;
		points[3] = centerY - height / 2;
		points[4] = centerX + width / 2;
		points[5] = centerY;
		points[6] = centerX + width / 4;
		points[7] = centerY + height / 2;
		points[8] = centerX - width / 4;
		points[9] = centerY + height / 2;
		points[10] = centerX - width / 2;
		points[11] = centerY;
		hexagon.getPoints().addAll(points);
		if (visible){
			hexagon.setStroke(Color.BLACK);
		}
	}

	@Override
	public Polygon getShape() {
		return hexagon;
	}

	@Override
	public void changeColor(Color c) {
		hexagon.setFill(c);
	}

}
