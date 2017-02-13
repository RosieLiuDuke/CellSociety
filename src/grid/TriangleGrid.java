package grid;

import java.util.ArrayList;

//currently not functional

import cell.Indices;

public class TriangleGrid extends ShapeGrid implements Grid {

	public TriangleGrid(boolean t) {
		super(t);
	}

	@Override
	public ArrayList<Indices> getImmediateNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList<Indices> ret = new ArrayList<Indices>();
		
		
		return ret;
	}

	@Override
	public ArrayList<Indices> getAllNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList<Indices> ret = getImmediateNeighbors(x,y,xMax,yMax);
		return ret;
	}

	
}
