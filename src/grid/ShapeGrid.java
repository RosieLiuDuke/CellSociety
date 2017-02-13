package grid;

public abstract class ShapeGrid {

	private boolean toroidal;
	
	public ShapeGrid (boolean t) {
		toroidal = t;
	}
	
	protected boolean getToroidal () {
		return toroidal;
	}
}