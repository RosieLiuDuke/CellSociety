package animation;

import java.util.ArrayList;

import cellSociety.CellSociety;
import page.GamePage;
import page.PageGameOfLife;
import page.PageSegregation;

public class AnimationSegregation extends Animation{
	
	private final static int NOVALUE = 0;
	private final static int FIRSTVALUE = 1;
	private final static int SECONDVALUE = 2;
	
	private double neededNeighbors;
	
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

	public AnimationSegregation(CellSociety c) {
		super(c);
	}

	public void calculateMove() {
		String type = this.getCellSociety().getCurrentType();
		GamePage page = (GamePage) this.getCellSociety().getPage(type);
		neededNeighbors = page.getSatisfaction();
		
		int [][] grid = getArray("Segregation");
		boolean [][] shouldChange = new boolean[grid.length][grid[0].length];
		
		checkSurrounding(shouldChange, grid);
		
		changeCells(shouldChange,grid);
		
		setCells(grid);
	}

	private void checkSurrounding(boolean [][] shouldChange, int [][] grid) {
		int i, j, k, l;
		double agree = 0, disagree = 0;
		
		int [][] borderArray = new int[grid.length +2][grid[0].length+2];
		
		for (i = 1; i < grid.length+1; i++) {
			for (j = 1; j < grid[0].length+1; j++) {
				borderArray[i][j] = grid[i-1][j-1];
			}
		}
		for (i = 0; i < borderArray.length; i++) {
			borderArray[i][0] = NOVALUE;
			borderArray[i][borderArray[0].length -1] = NOVALUE;
		}
		for (i = 0; i < borderArray[0].length; i++) {
			borderArray[0][i] = NOVALUE;
			borderArray[borderArray.length - 1][i] = NOVALUE;
		}
		
		for (i = 1; i < borderArray.length - 1; i++) {
			for (j = 1; j < borderArray.length -1; j++) {
				
				for (k = i -1; k < i+2; k++) {
					for (l = j -1; l < j+2; l++) {
						if (borderArray[i][j] == borderArray[k][l]) {
							agree += 1;
						}
						else if (borderArray[k][l] != NOVALUE) {
							disagree += 1;
						}
					}
				}
				
				agree -= 1;
				
				
				shouldChange[i-1][j-1] = ((agree / disagree) < neededNeighbors);
				
				
				agree = 0; disagree = 0;
			}
		}
	}
	
	private void changeCells(boolean [][] shouldChange, int [][] grid) {
		int i, j, a, b, rand;
		ArrayList <Coord> emptyCells = new ArrayList<Coord>();
		ArrayList <Coord> changeCells = new ArrayList<Coord>();
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == NOVALUE) 
					emptyCells.add(new Coord(i, j));
				if (shouldChange[i][j])
					changeCells.add(new Coord(i, j));
			}
		}
		
		while (changeCells.size() > 0) {
			i = changeCells.get(0).getX();
			j = changeCells.get(0).getY();
			
			rand = (int) Math.random() * (emptyCells.size() - 1);
			a = emptyCells.get(rand).getX();
			b = emptyCells.get(rand).getY();
			
			grid[a][b] = grid[i][j];
			grid[i][j] = NOVALUE;
			emptyCells.remove(rand);
			emptyCells.add(new Coord(i, j));
			changeCells.remove(0);
		}
	}
	
	private void setCells (int [][] grid) {
		int i, j;
		PageSegregation p = (PageSegregation) getNeededPage("Segregation");
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				p.getCell(i, j).changeStatus(grid[i][j]);
			}
		}
	}
}
