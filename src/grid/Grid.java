package animation;

import java.util.ArrayList;

public interface Grid {
	public ArrayList <Coord> getImmediateNeighbors(int x, int y, int xMax, int yMax);
	
	public ArrayList <Coord> getAllNeighbors(int x, int y, int xMax, int yMax);
}
