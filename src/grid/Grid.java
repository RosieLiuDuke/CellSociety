package grid;

import java.util.ArrayList;

import cell.Indices;

public interface Grid {
	public ArrayList <Indices> getImmediateNeighbors(int x, int y, int xMax, int yMax);
	
	public ArrayList <Indices> getAllNeighbors(int x, int y, int xMax, int yMax);
}
