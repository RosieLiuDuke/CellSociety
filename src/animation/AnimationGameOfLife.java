package animation;
import java.util.ArrayList;

import cell.Indices;
import cellSociety.CellSociety;
import grid.Grid;
import grid.SquareGrid;
import page.PageGameOfLife;
import page.Parameters;

public class AnimationGameOfLife extends Animation {
	
	private static final int ONVALUE = 1;
	private static final int OFFVALUE = 0;
	
	public AnimationGameOfLife(CellSociety c, Parameters p) {
		super(c, p);
	}
	
	public void calculateMove() {
		boolean [][] shouldChange;
		
		int [][] grid = getArray("Game of Life");
			
		shouldChange = new boolean[grid.length][grid[0].length];
		
		checkChange(shouldChange, grid);
		
		changeGrid(shouldChange, grid);
		
		setCells(grid, (PageGameOfLife) getNeededPage("Game of Life"));
	}
	
	private void checkChange(boolean [][] shouldChange, int [][] grid) {
		int i, j, k, total;
		
		ArrayList <Indices> neighbors;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				total = 0;
				neighbors = getGrid().getAllNeighbors(i, j, grid.length, grid[0].length);
				
				for (k = 0; k < neighbors.size(); k++) {
					total += grid[neighbors.get(k).getX()][neighbors.get(k).getY()];
				}
				
				shouldChange[i][j] = (((grid[i][j] == ONVALUE) && ((total < 2) || (total > 3))) || ((grid[i][j] == OFFVALUE) && (total == 3))); 
			}
		}
	}
	
	private void changeGrid(boolean [][] shouldChange, int [][] grid) {
		int i, j;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid.length; j++) {
				if (shouldChange[i][j]) {
					if (grid[i][j] == ONVALUE) {
						grid[i][j] = OFFVALUE;
					}
					else {
						grid[i][j] = ONVALUE;
					}
				}
			}
		}
	}
	
}
