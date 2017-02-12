package animation;

import cellSociety.CellSociety;
import page.PageSlime;
import page.Parameters;

public class AnimationSlime extends Animation {
	
	private static int LOWESTCAMP = 0;
	private static int MEDIUMCAMP = 1;
	private static int HIGHCAMP = 2;
	private static int SLIME = 3;
	
	private int [][] cAMP;
	private boolean firstTime;
	
	public AnimationSlime(CellSociety c, Parameters p) {
		super(c, p);
		firstTime = true;
	}

	@Override
	public void calculateMove() {
		int [][] grid = getArray("Slime");
		
		if (firstTime) {
			int i, j;
			firstTime = false;
			cAMP = new int[grid.length][grid[0].length];
			
			for (i = 0; i < grid.length; i++) {
				for (j = 0; j < grid[0].length; j++) {
					cAMP[i][j] = 0;
				}
			}
		}
		
		changeGrid(grid);
		
		calculatecAMP(grid);
		
		evaluatecAMPValues(grid);
		
		setCells(grid, (PageSlime) getNeededPage("Slime"));
		
	}
	
	private void changeGrid(int [][] grid) {
		
	}
	
	private void calculatecAMP(int [][] grid) {
		
	}
	
	private void evaluatecAMPValues(int [][] grid) {
		
	}
}
