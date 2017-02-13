package grid;

import java.util.ArrayList;

import cell.Indices;

public class HexagonGrid extends ShapeGrid implements Grid{

	public HexagonGrid(boolean t) {
		super(t);
	}

	@Override
	public ArrayList<Indices> getImmediateNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList <Indices> ret = new ArrayList<Indices>();
		if (y > 0) {
			ret.add(new Indices(x, y-1));
		}
		if (y < (yMax-1)) {
			ret.add(new Indices(x, y+1));
		}
		if (x > 0) {
			if ((x%2) == 1) {
				ret.add(new Indices(x-1,y));
				if (y < (yMax-1)) {
					ret.add(new Indices(x-1, y+1));
				}
			}
			else {
				ret.add(new Indices(x-1, y));
				if (y > 0) {
					ret.add(new Indices(x-1, y-1));
				}
			}
		}
		if (x < (xMax-1)) {
			if ((x%2) == 1) {
				ret.add(new Indices(x+1,y));
				if (y < (yMax-1)) {
					ret.add(new Indices(x+1, y+1));
				}
			}
			else {
				ret.add(new Indices(x+1, y));
				if (y > 0) {
					ret.add(new Indices(x+1, y-1));
				}
			}
		}
		return ret;
	}

	@Override
	public ArrayList<Indices> getAllNeighbors(int x, int y, int xMax, int yMax) {
		return getImmediateNeighbors(x,y,xMax,yMax);
	}

}
