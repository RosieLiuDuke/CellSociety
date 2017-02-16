package cell;

/**
 * The (x,y) index for a cell.
 * Used to store (Indices, Cell) pair in Map.
 * @author Yilin Gao
 *
 */
public class Indices {

    private final int col;
    private final int row;

    /**
     * Constructor for Indices class.
     * @param x: x index
     * @param y: y index
     */
    public Indices(int x, int y) {
        this.col = x;
        this.row = y;
    }

    /**
     * The method to compare if two Indices instants are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Indices)) return false;
        Indices key = (Indices) o;
        return (col == key.col && row == key.row);
    }

    /**
     * The method to generate hash code for the Indices class.
     */
    @Override
    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        return result;
    }
    
    public int getX() {
		return col;
	}
	public int getY() {
		return row;
	}
}
