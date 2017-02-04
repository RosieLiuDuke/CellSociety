import java.util.ArrayList;

public class AnimationGameOfLife extends Animation {
	
	private static final int ONVALUE = 1;
	private static final int OFFVALUE = 0;
	
	
	public AnimationGameOfLife(CellSociety c) {
		super(c);
	}
	
	public void calculateMove() {
		boolean [][] shouldChange;
		int [][] grid;
		
		grid = getArray();
		shouldChange = new boolean[grid.length][grid[0].length];
		
		checkSurrounding(shouldChange, grid);
		
		changegrid(shouldChange, grid);
		
		setCells(grid);
	}
	
	public int [][] getArray() {
		int i, j;
		PageGameOfLife p = (PageGameOfLife) getCellSociety().getPage("Game of Life");
		int [][] intArray = new int[p.getRow()][p.getCol()];
		
		for (i = 0; i < intArray.length; i++) {
			for (j = 0; j < intArray[0].length; j++) {
				intArray[i][j] = p.getCell(i, j).getStatus();
			}
		}
		
		return intArray;
	}
	
	public void checkSurrounding(boolean [][] shouldChange, int [][] grid) {
		int i, j, k, l, total = 0;
		
		int [][] borderArray = new int[grid.length +2][grid[0].length+2];
		
		for (i = 1; i < grid.length+1; i++) {
			for (j = 1; j < grid[0].length+1; j++) {
				borderArray[i][j] = grid[i-1][j-1];
			}
		}
		for (i = 0; i < borderArray.length; i++) {
			borderArray[i][0] = OFFVALUE;
			borderArray[i][borderArray[0].length -1] = OFFVALUE;
		}
		for (i = 0; i < borderArray[0].length; i++) {
			borderArray[0][i] = OFFVALUE;
			borderArray[borderArray.length - 1][i] = OFFVALUE;
		}
		
		for (i = 1; i < borderArray.length - 1; i++) {
			for (j = 1; j < borderArray.length -1; j++) {
				
				for (k = i -1; k < i+2; k++) {
					for (l = j -1; l < j+2; l++) {
						total += borderArray[k][l];
					}
				}
				
				total -= borderArray[i][j];
				
				
				shouldChange[i-1][j-1] = (((grid[i-1][j-1] == ONVALUE) && ((total <2) || (total > 3))) ||
							((grid[i-1][j-1] == OFFVALUE) && (total == 3)));
				
				
				total = 0;
			}
		}
	}
	
	public void changegrid(boolean [][] shouldChange, int [][] grid) {
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
	
	public void setCells (int [][] grid) {
		int i, j;
		PageGameOfLife p = (PageGameOfLife) getCellSociety().getPage("Game of Life");
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				p.getCell(i, j).changeStatus(grid[i][j]);
			}
		}
	}
}
