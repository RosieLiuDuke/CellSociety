package animation;

import java.util.ArrayList;
import java.util.List;

import cell.Cell;
import cellSociety.CellSociety;
import page.PagePredator;

public class AnimationPredator extends Animation{
	
	private final static int EMPTYNUMBER = 0;
	private final static int FISHNUMBER = 1;
	private final static int SHARKNUMBER = 2;
	
	private final static int sharkLife = 4;
	private final static int fishLife = 1;
	private boolean firstTime;
	private int [][] lives;
	
	private class Coord {
		int x;
		int y;
		
		private Coord (int w, int z) {
			x  = w;
			y = z;
		}
		
		private int getX () {
			return x;
		}
		private int getY() {
			return y;
		}
	}
	
	

	public AnimationPredator(CellSociety c) {
		super(c);
		firstTime = true;
	}
	
	public void calculateMove() {
		int [][] grid = getArray("Predator");
		
		
		if (firstTime) {
			lives = new int[grid.length][grid[0].length];
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
					if (lives[i][j] == 0) {
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
	
	private ArrayList <Coord> checkFor(int i, int j, int [][] grid, int type) {
		ArrayList <Coord> returnList = new ArrayList<Coord>();
		checkSpot(i-1, j,returnList, grid, type);
		checkSpot(i + 1, j, returnList, grid, type);
		checkSpot(i, j - 1, returnList, grid, type);
		checkSpot(i, j + 1, returnList, grid, type);
		return returnList;
	}
	private void checkSpot (int x, int y, ArrayList <Coord> returnList, int [][] grid, int type) {
		if ((x >= 0) && (y>= 0) && (x < grid.length) && (y < grid[0].length)) {
			if (grid[x][y] == type) {
				returnList.add(new Coord(x, y));
			}
		}
	}
	
	private int getRandomForList (int size) {
		return (int)((Math.random() * size) - .001);
	}

}
