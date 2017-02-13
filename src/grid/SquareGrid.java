package grid;

import java.util.ArrayList;

import cell.Indices;

public class SquareGrid extends ShapeGrid implements Grid {
	
	public SquareGrid(boolean t) {
		super(t);
	}


	public ArrayList<Indices> getImmediateNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList <Indices> ret = new ArrayList<Indices>();
		
		if (x > 0) {
			ret.add(new Indices(x-1, y));
		}
		else if (getToroidal()) {
			ret.add(new Indices(xMax-1, y));
		}
		
		if (y > 0) {
			ret.add(new Indices(x, y-1));
		}
		else if (getToroidal()) {
			ret.add(new Indices(x, yMax-1));
		}
		
		if (x < (xMax-1)) {
			ret.add(new Indices(x+1, y));
		}
		else if (getToroidal()) {
			ret.add(new Indices(0, y));
		}
		
		if (y < (yMax -1)) {
			ret.add(new Indices(x, y+1));
		}
		else if (getToroidal()) {
			ret.add(new Indices(x, 0));
		}
		
		return ret;
	}

	
	public ArrayList<Indices> getAllNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList<Indices> ret = getImmediateNeighbors(x, y, xMax, yMax);
		
		if ((x > 0) && (y > 0)) {
			ret.add(new Indices(x-1,y-1));
		}
		else if (getToroidal()) {
			ret.add(new Indices(xMax-1, yMax-1));
		}
		
		if ((x > 0) && (y < (yMax-1))) {
			ret.add(new Indices(x-1,y+1));
		}
		else if (getToroidal()) {
			ret.add(new Indices(xMax-1, 0));
		}
		
		if ((x < (xMax -1)) && (y > 0)) {
			ret.add(new Indices(x+1, y -1));
		}
		else if (getToroidal()) {
			ret.add(new Indices(0, yMax-1));
		}
		
		if ((x < (xMax -1)) && (y < (yMax -1))) {
			ret.add(new Indices(x+1,y+1));
		}
		else if (getToroidal()) {
			ret.add(new Indices(0,0));
		}
		
		return ret;
	}

	
}
