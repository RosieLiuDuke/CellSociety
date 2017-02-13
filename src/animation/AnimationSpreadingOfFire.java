package animation;
import java.util.ArrayList;

import cell.Indices;
import cellSociety.CellSociety;
import grid.Grid;
import grid.SquareGrid;
import page.Page;
import page.PageSpreadingOfFire;
import page.Parameters;

public class AnimationSpreadingOfFire extends Animation {
	private final static int BURNINGVALUE = 2;
	private final static int UNBURNEDVALUE = 1;
	private final static int BURNEDVALUE = 0;
	
	private double probCatch;
	
	
	
	public AnimationSpreadingOfFire(CellSociety c, Parameters p, Grid g) {
		super(c, p, g);
	}
	
	public void calculateMove () {
		
		probCatch = this.getParametersController().getProb();
		
		boolean [][] shouldChange;
		int [][] grid= getArray("Fire");
		shouldChange = new boolean[grid.length][grid[0].length];
		
		checkChange(shouldChange, grid);
		
		changegrid(shouldChange, grid);
		
		setCells(grid, (PageSpreadingOfFire)getNeededPage("Fire"));
	}
	
	
	
	private void checkChange(boolean [][] shouldChange, int [][] grid) {
		int i, j, k;
		ArrayList <Indices> neighbors;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (!shouldChange[i][j])
					shouldChange[i][j] = (grid[i][j] == BURNINGVALUE);
				
				if (grid[i][j] == BURNINGVALUE) {
					neighbors = getGrid().getImmediateNeighbors(i,j,grid.length, grid[0].length);
					
					for (k = 0; k < neighbors.size(); k++) {
						shouldChange[neighbors.get(k).getX()][neighbors.get(k).getY()] = 
								figureShouldChange(neighbors.get(k).getX(),neighbors.get(k).getY(),shouldChange,grid);
					}
					
				}
				
			}
		}
	}
	
	private boolean figureShouldChange(int i, int j, boolean [][] shouldChange, int [][] grid) {
		if (!shouldChange[i][j]) {
			return ((grid[i][j] == UNBURNEDVALUE) &&
				(Math.random() <= probCatch));
		}
		return true;
	}
	
	private void changegrid(boolean [][] shouldChange, int [][] grid) {
		int i, j;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid.length; j++) {
				if (shouldChange[i][j]) {
					if (grid[i][j] == BURNINGVALUE) {
						grid[i][j] = BURNEDVALUE;
					}
					else {
						grid[i][j] = BURNINGVALUE;
					}
				}
			}
		}
	}
}
