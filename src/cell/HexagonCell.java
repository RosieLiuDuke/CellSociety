package cell;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexagonCell extends Cell {
	
	private Polygon hexagon;

	public HexagonCell(double centerX, double centerY, double sideX, double sideY, int status, boolean visible) {
		super(status);
		hexagon = new Polygon();
		Double[] points = new Double[12];
		points[0] = centerX - sideX / 2;
		points[1] = centerY - sideY / 2 * Math.sqrt(3);
		points[2] = centerX + sideX / 2;
		points[3] = centerY - sideY / 2 * Math.sqrt(3);
		points[4] = centerX + sideX;
		points[5] = centerY;
		points[6] = centerX + sideX / 2;
		points[7] = centerY + sideY / 2 * Math.sqrt(3);
		points[8] = centerX - sideX / 2;
		points[9] = centerY + sideY / 2 * Math.sqrt(3);
		points[10] = centerX - sideX;
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
