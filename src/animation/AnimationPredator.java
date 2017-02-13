package animation;

import java.util.ArrayList;

import cell.Indices;
import cellSociety.CellSociety;
import grid.Grid;
import grid.SquareGrid;
import page.PagePredator;
import page.Parameters;

public class AnimationPredator extends Animation{
	
	private final static int EMPTYNUMBER = 0;
	private final static int FISHNUMBER = 1;
	private final static int SHARKNUMBER = 2;
	
	private double sharkLife;
	private double fishLife;
	private boolean firstTime;
	private double [][] lives;
	

	public AnimationPredator(CellSociety c, Parameters p, Grid g) {
		super(c, p, g);
		firstTime = true;
		sharkLife = p.getItemTurnover(SHARKNUMBER);
		fishLife = p.getItemTurnover(FISHNUMBER);
	}
	
	public void calculateMove() {
		int [][] grid = getArray("Predator");
		
		
		if (firstTime) {
			lives = new double[grid.length][grid[0].length];
			createLives(grid);
		}
		
		progressThrough(grid);
		setCells(grid, (PagePredator)getNeededPage("Predator"));
	}
	
	private void createLives(int [][] grid) {
		int i,j;
		firstTime = false;
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == FISHNUMBER) {
					lives[i][j] = 0;
				}
				else if (grid[i][j] == SHARKNUMBER) {
					lives[i][j] = sharkLife;
				}
				else {
					lives[i][j] = -1;
				}
			}
		}
	}
	
	private void progressThrough(int [][] grid) {
		sharkProgress(grid);
		fishProgress(grid);
	}
	
	private void sharkProgress (int [][] grid) {
		int i, j, rand, x, y;
		
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == SHARKNUMBER) {
					if (lives[i][j] <= 0) {
						grid[i][j] = EMPTYNUMBER;
					}
					else if (checkFor(i, j, grid, FISHNUMBER).size() > 0) {
						lives[i][j] = sharkLife;
						rand = getRandomForList(checkFor(i, j, grid, FISHNUMBER).size());
						x = checkFor(i, j, grid, FISHNUMBER).get(rand).getX();
						y = checkFor(i, j, grid, FISHNUMBER).get(rand).getY();
						grid[x][y] = SHARKNUMBER;
						lives[x][y] = sharkLife;
					}
					else if (checkFor(i, j, grid, EMPTYNUMBER).size() > 0) {
						rand = getRandomForList(checkFor(i, j, grid, EMPTYNUMBER).size());
						x = checkFor(i, j, grid, EMPTYNUMBER).get(rand).getX();
						y = checkFor(i, j, grid, EMPTYNUMBER).get(rand).getY();
						grid[x][y] = SHARKNUMBER;
						lives[x][y] = lives[i][j] - 1;
						grid[i][j] = EMPTYNUMBER;
						lives[i][j] = -1;
					}
					else {
						lives[i][j]--;
					}
				}
			}
		}
	}
	private void fishProgress(int [][] grid) {
		int i, j, rand, x, y;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == FISHNUMBER) {
					if (checkFor(i, j, grid, EMPTYNUMBER).size() != 0) {
						if (lives[i][j] >= fishLife) {
							lives[i][j] = 0;
							rand = getRandomForList(checkFor(i, j, grid, EMPTYNUMBER).size());
							x = checkFor(i, j, grid, EMPTYNUMBER).get(rand).getX();
							y = checkFor(i, j, grid, EMPTYNUMBER).get(rand).getY();
							lives[x][y] = 0;
							grid[x][y] = FISHNUMBER;
						}
						else {
							lives[i][j]++;
							rand = getRandomForList(checkFor(i, j, grid, EMPTYNUMBER).size());
							x = checkFor(i, j, grid, EMPTYNUMBER).get(rand).getX();
							y = checkFor(i, j, grid, EMPTYNUMBER).get(rand).getY();
							lives[x][y] = lives[i][j];
							grid[x][y] = grid[i][j];
							lives[i][j] = -1;
							grid[i][j] = EMPTYNUMBER;
						}
					}
					else {
						lives[i][j]++;
					}
				}
			}
		}
	}
	
	private ArrayList <Indices> checkFor(int i, int j, int [][] grid, int type) {
		ArrayList <Indices> returnList = new ArrayList<Indices>();
		ArrayList <Indices> neighbors = getGrid().getImmediateNeighbors(i, j, grid.length, grid[0].length);
		
		for (int k = 0; k < neighbors.size(); k++) {
			checkSpot(neighbors.get(k).getX(), neighbors.get(k).getY(), returnList, grid, type);
		}
		return returnList;
	}
	private void checkSpot (int x, int y, ArrayList <Indices> returnList, int [][] grid, int type) {
			if (grid[x][y] == type) {
				returnList.add(new Indices(x, y));
			}
	}
	
	private int getRandomForList (int size) {
		return (int)((Math.random() * size) - .001);
	}

}
