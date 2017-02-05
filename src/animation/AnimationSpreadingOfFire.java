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
		PageSpreadingOfFire p = (PageSpreadingOfFire) getCellSociety().getPage("Spreading of Fire");
		int [][] intArray = new int[ p.getRow()][p.getCol()];
		
		for (i = 0; i < intArray.length; i++) {
			for (j = 0; j < intArray[0].length; j++) {
				intArray[i][j] = p.getCell(i, j).getStatus();
			}
		}
		
		return intArray;
	}
	
	private void checkSurrounding(boolean [][] shouldChange, int [][] squares) {
		int i, j, k, l;
		
		int [][] surroundingArray = new int[squares.length +2][squares[0].length+2];
		
		for (i = 1; i < squares.length+1; i++) {
			for (j = 1; j < squares[0].length+1; j++) {
				surroundingArray[i][j] = squares[i-1][j-1];
			}
		}
		for (i = 0; i < surroundingArray.length; i++) {
			surroundingArray[i][0] = BURNEDVALUE;
			surroundingArray[i][surroundingArray[0].length -1] = BURNEDVALUE;
		}
		for (i = 0; i < surroundingArray[0].length; i++) {
			surroundingArray[0][i] = BURNEDVALUE;
			surroundingArray[surroundingArray.length - 1][i] = BURNEDVALUE;
		}
		
		for (i = 1; i < surroundingArray.length -1; i++) {
			for (j = 1; j < surroundingArray[0].length; j++) {
				if (!shouldChange[i-1][j-1])
					shouldChange[i-1][j-1] = (surroundingArray[i][j] == BURNINGVALUE);
				
				if (surroundingArray[i][j] == BURNINGVALUE) {
					for (k = i-1; k < i+2; k++) {
						for (l= j-1; l < j+2; l++) {
							if (!shouldChange[k-1][l-1])
								shouldChange[k-1][l-1] = (((k != i) && (l != j)) &&
									(surroundingArray[k][l] == UNBURNEDVALUE) &&
									(Math.random() >= probCatch));
						}
					}
				}
				
			}
		}
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
		PageSpreadingOfFire p = (PageSpreadingOfFire) getCellSociety().getPage("Spreading of Fire");
		for (i = 0; i < squares.length; i++) {
			for (j = 0; j < squares[0].length; j++) {
				p.getCell(i, j).changeStatus(squares[i][j]);
			}
		}
	}
}
