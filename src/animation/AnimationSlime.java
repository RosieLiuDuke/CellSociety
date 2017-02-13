package animation;

import java.util.ArrayList;

import cell.Indices;
import cellSociety.CellSociety;
import grid.Grid;
import grid.SquareGrid;
import page.PageSlime;
import page.Parameters;

public class AnimationSlime extends Animation {
	
	private static int LOWCAMP = 0;
	private static int MEDIUMCAMP = 1;
	private static int HIGHCAMP = 2;
	private static int SLIME = 3;
	private static double HIGHMULTIPLIER = 1.1;
	private static double LOWMULTIPLIER = .9;
	
	private int [][] cAMP;
	private boolean firstTime;
	private Grid g;
	
	public AnimationSlime(CellSociety c, Parameters p) {
		super(c, p);
		firstTime = true;
		g = new SquareGrid();
	}

	@Override
	public void calculateMove() {
		int [][] grid = getArray("Slime");
		
		if (firstTime) {
			int i, j;
			firstTime = false;
			cAMP = new int[grid.length][grid[0].length];
			setToZero(cAMP);
		}
		
		changeGrid(grid);
		
		calculatecAMP(grid);
		
		evaluatecAMPValues(grid);
		
		setCells(grid, (PageSlime) getNeededPage("Slime"));
		
	}
	
	private void changeGrid(int [][] grid) {
		int i, j, k;
		ArrayList <Indices> neighbors;
		Indices highCell = new Indices(-1, -1);
		int highNum;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == SLIME) {
					neighbors = g.getImmediateNeighbors(i, j, grid.length, grid[0].length);
					
					for (k = 0; k < neighbors.size(); k++) {
						if (grid[neighbors.get(k).getX()][neighbors.get(k).getY()] == SLIME) {
							neighbors.remove(k);
							k--;
						}
					}
					
					
					if (neighbors.size() > 0) {
						highNum = -1;
						for (k = 0; k < neighbors.size(); k++) {
							if (cAMP[neighbors.get(k).getX()][neighbors.get(k).getY()] > highNum) {
								highNum = cAMP[neighbors.get(k).getX()][neighbors.get(k).getY()];
								highCell = new Indices(neighbors.get(k).getX(), neighbors.get(k).getY());
							}
						}
						
						grid[i][j] = LOWCAMP;
						grid[highCell.getX()][highCell.getY()] = SLIME;
					}
				}
			}
		}
	}
	
	private void calculatecAMP(int [][] grid) {
		int i, j, k;
		ArrayList <Indices> neighbors;
		int [][] shouldAdd = new int[cAMP.length][cAMP[0].length];
		
		setToZero(shouldAdd);
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == SLIME) {
					
					shouldAdd[i][j] += 1;
					neighbors = g.getImmediateNeighbors(i, j, grid.length, grid[0].length);
					
					for (k = 0; k < neighbors.size(); k++) {
						shouldAdd[neighbors.get(k).getX()][neighbors.get(k).getY()] += 1;
					}
				}
				else {
					neighbors = g.getImmediateNeighbors(i, j, grid.length, grid[0].length);
					for (k = 0; k < neighbors.size(); k++) {
						if (cAMP[i][j] > cAMP[neighbors.get(k).getX()][neighbors.get(k).getY()]) {
							shouldAdd[neighbors.get(k).getX()][neighbors.get(k).getY()] += 1;
						}
					}
				}
			}
		}
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				cAMP[i][j] += shouldAdd[i][j];
			}
		}
		
	}
	
	private void evaluatecAMPValues(int [][] grid) {
		int i, j;
		double highVal, lowVal, average;
		double total = 0;
		
		
		for (i = 0; i < cAMP.length; i++) {
			for (j = 0; j < cAMP[0].length; j++) {
				total += cAMP[i][j];
			}
		}
		
		average = total / (cAMP.length * cAMP[0].length);
		highVal = average * HIGHMULTIPLIER;
		lowVal = average * LOWMULTIPLIER;
		
		for (i = 0; i < cAMP.length; i++) {
			for (j = 0; j < cAMP.length; j++) {
				if (grid[i][j] != SLIME) {
					if (cAMP[i][j] > highVal) {
						grid[i][j] = HIGHCAMP;
					}
					else if (cAMP[i][j] < lowVal) {
						grid[i][j] = LOWCAMP;
					}
					else {
						grid[i][j] = MEDIUMCAMP;
					}
				}
			}
		}
	}
	
	private void setToZero(int [][] numbers) {
		int i, j;
		for (i = 0; i < numbers.length; i++) {
			for (j = 0; j < numbers[0].length; j++) {
				numbers[i][j] = 0;
			}
		}
	}
}
