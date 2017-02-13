package grid;

import java.util.ArrayList;

import cell.Indices;

public interface Grid {
	/**
	 * Gets only the neighbors facing a full side of the cell
	 * @param x the x value of the cell
	 * @param y the y value of the cell
	 * @param xMax the x length
	 * @param yMax the y length
	 * @return returns all neighbors facing the cell in an arraylist
	 */
	public ArrayList <Indices> getImmediateNeighbors(int x, int y, int xMax, int yMax);
	
	public ArrayList <Indices> getAllNeighbors(int x, int y, int xMax, int yMax);
}
