package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleCell extends Cell {
	
	private Polygon triangle;

	public TriangleCell(double centerX, double centerY, double sideX, double sideY, int status, boolean visible) {
		super(status);
		// TODO Auto-generated constructor stub
		triangle = new Polygon();
		Double[] points = new Double[6];
		points[0] = centerX;
		points[1] = centerY - sideY / 2;
		points[2] = centerX + sideX / 2;
		points[3] = centerY + sideY / 2;
		points[4] = centerX - sideX / 2;
		points[5] = centerY + sideY / 2;
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
