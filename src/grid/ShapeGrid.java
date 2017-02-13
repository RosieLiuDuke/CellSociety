package grid;

public abstract class ShapeGrid {

	private boolean toroidal;
	
	public ShapeGrid (boolean t) {
		toroidal = t;
	}
	
	/**
	 * Allows subclasses to see the value of toroidal
	 * @return a boolean telling whether the program should work toroidally
	 */
	protected boolean getToroidal () {
		return toroidal;
	}
}