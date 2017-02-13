package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleCell extends Cell {
	
	private Polygon triangle;

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
