package animation;
import cellSociety.CellSociety;
import page.GamePage;
import page.PageSpreadingOfFire;

public class AnimationSpreadingOfFire extends Animation {
	private final static int BURNINGVALUE = 2;
	private final static int UNBURNEDVALUE = 1;
	private final static int BURNEDVALUE = 0;
	
	private double probCatch;
	
	
	
	public AnimationSpreadingOfFire(CellSociety c) {
		super(c);
	}
	
	public void calculateMove () {
		
		String type = this.getCellSociety().getCurrentType();
		GamePage page = (GamePage) this.getCellSociety().getPage(type);
		probCatch = page.getProb();
		
		boolean [][] shouldChange;
		int [][] grid= getArray("Fire");
		shouldChange = new boolean[grid.length][grid[0].length];
		
		checkSurrounding(shouldChange, grid);
		
		changegrid(shouldChange, grid);
		
		setCells(grid, (PageSpreadingOfFire)getNeededPage("Fire"));
	}
	
	
	
	private void checkSurrounding(boolean [][] shouldChange, int [][] grid) {
		int i, j;
		
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				if (!shouldChange[i][j])
					shouldChange[i][j] = (grid[i][j] == BURNINGVALUE);
				
				if (grid[i][j] == BURNINGVALUE) {
					if ((i-1) >= 0)
						shouldChange[i-1][j] = figureShouldChange(i-1, j, shouldChange, grid);
					if ((j-1) >= 0)
						shouldChange[i][j-1] = figureShouldChange(i, j-1, shouldChange, grid);
					if ((i+1) < grid.length)
						shouldChange[i+1][j] = figureShouldChange(i+1, j, shouldChange, grid);
					if ((j+1) < grid[0].length)
						shouldChange[i][j+1] = figureShouldChange(i, j+1, shouldChange, grid);
				}
				
			}
		}
	}
	
	private boolean figureShouldChange(int i, int j, boolean [][] shouldChange, int [][] grid) {
		if (!shouldChange[i][j]) {
			return ((grid[i][j] == UNBURNEDVALUE) &&
				(Math.random() >= probCatch));
		}
		return shouldChange[i][j];
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
