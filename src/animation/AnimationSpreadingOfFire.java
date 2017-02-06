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
		
		// Josh: I changed the way of getting probCatch by storing the value in GamePage. Yilin
		String type = this.getCellSociety().getCurrentType();
		GamePage page = (GamePage) this.getCellSociety().getPage(type);
		probCatch = page.getProb();
		
		boolean [][] shouldChange;
		int [][] squares;
		
		squares = getArray();
		shouldChange = new boolean[squares.length][squares[0].length];
		
		checkSurrounding(shouldChange, squares);
		
		changeSquares(shouldChange, squares);
		
		setCells(squares);
	}
	
	public int [][] getArray() {
		int i, j;
		PageSpreadingOfFire p = (PageSpreadingOfFire) getCellSociety().getPage("Fire");
		int [][] intArray = new int[ p.getRow()][p.getCol()];
		
		for (i = 0; i < intArray.length; i++) {
			for (j = 0; j < intArray[0].length; j++) {
				intArray[i][j] = p.getCell(i, j).getStatus();
			}
		}
		
		return intArray;
	}
	
	private void checkSurrounding(boolean [][] shouldChange, int [][] squares) {
		int i, j;
		
		for (i = 0; i < squares.length; i++) {
			for (j = 0; j < squares[0].length; j++) {
				if (!shouldChange[i][j])
					shouldChange[i][j] = (squares[i][j] == BURNINGVALUE);
				
				if (squares[i][j] == BURNINGVALUE) {
					if ((i-1) >= 0)
						shouldChange[i-1][j] = figureShouldChange(i-1, j, shouldChange, squares);
					if ((j-1) >= 0)
						shouldChange[i][j-1] = figureShouldChange(i, j-1, shouldChange, squares);
					if ((i+1) < squares.length)
						shouldChange[i+1][j] = figureShouldChange(i+1, j, shouldChange, squares);
					if ((j+1) < squares[0].length)
						shouldChange[i][j+1] = figureShouldChange(i, j+1, shouldChange, squares);
				}
				
			}
		}
	}
	
	private boolean figureShouldChange(int i, int j, boolean [][] shouldChange, int [][] squares) {
		if (!shouldChange[i][j]) {
			return ((squares[i][j] == UNBURNEDVALUE) &&
				(Math.random() >= probCatch));
		}
		return shouldChange[i][j];
	}
	
	private void changeSquares(boolean [][] shouldChange, int [][] squares) {
		int i, j;
		
		for (i = 0; i < squares.length; i++) {
			for (j = 0; j < squares.length; j++) {
				if (shouldChange[i][j]) {
					if (squares[i][j] == BURNINGVALUE) {
						squares[i][j] = BURNEDVALUE;
					}
					else {
						squares[i][j] = BURNINGVALUE;
					}
				}
			}
		}
	}
	

	private void setCells (int [][] squares) {
		int i, j;
		PageSpreadingOfFire p = (PageSpreadingOfFire) getCellSociety().getPage("Fire");
		for (i = 0; i < squares.length; i++) {
			for (j = 0; j < squares[0].length; j++) {
				p.getCell(i, j).changeStatus(squares[i][j]);
			}
		}
	}
}
