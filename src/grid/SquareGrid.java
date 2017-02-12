package animation;

import java.util.ArrayList;

public class SquareGrid implements Grid {
	
	public ArrayList<Coord> getImmediateNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList <Coord> ret = new ArrayList<Coord>();
		
		if (x > 0) {
			ret.add(new Coord(x-1, y));
		}
		if (y > 0) {
			ret.add(new Coord(x, y-1));
		}
		if (x < (xMax-1)) {
			ret.add(new Coord(x+1, y));
		}
		if (y < (yMax -1)) {
			ret.add(new Coord(x, y+1));
		}
		return ret;
	}

	
	public ArrayList<Coord> getAllNeighbors(int x, int y, int xMax, int yMax) {
		ArrayList<Coord> ret = getImmediateNeighbors(x, y, xMax, yMax);
		
		if ((x > 0) && (y > 0)) {
			ret.add(new Coord(x-1,y-1));
		}
		if ((x > 0) && (y < (yMax-1))) {
			ret.add(new Coord(x-1,y+1));
		}
		if ((x < (xMax -1)) && (y > 0)) {
			ret.add(new Coord(x+1, y -1));
		}
		if ((x < (xMax -1)) && (y < (yMax -1))) {
			ret.add(new Coord(x+1,y+1));
		}
		return ret;
	}

	
}
