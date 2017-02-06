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

    public Indices(int x, int y) {
        this.col = x;
        this.row = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Indices)) return false;
        Indices key = (Indices) o;
        return (col == key.col && row == key.row);
    }

    @Override
    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        return result;
    }
}
