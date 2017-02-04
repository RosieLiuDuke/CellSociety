
public class SpreadingOfFire extends Animation {

	private final static int BURNINGVALUE = 2;
	private final static int UNBURNEDVALUE = 1;
	private final static int BURNEDVALUE = 0;
	
	private double minValue;
	
	private int [][] squares;
	
	public void calculateMove (Cell [][] cellArray) {
		boolean [][] shouldChange;
		
		squares = convertArray(cellArray);
		shouldChange = new boolean[squares.length][squares[0].length];
		
		checkSurrounding(shouldChange);
		
		changeSquares(shouldChange);
		
		setCells(cellArray);
	}
	
	private int [][] convertArray(Cell [][] cellArray) {
		int i, j;
		int [][] intArray = new int[cellArray.length][cellArray[0].length];
		
		for (i = 0; i < cellArray.length; i++) {
			for (j = 0; j < cellArray[0].length; j++) {
				squares[i][j] = cellArray[i][j].getStatus();
			}
		}
		
		return intArray;
	}
	
	private void checkSurrounding(boolean [][] shouldChange) {
		int i, j, k, l;
		
		int [][] surroundingArray = new int[squares.length +2][squares[0].length+2];
		
		for (i = 1; i < squares.length+1; i++) {
			for (j = 1; j < squares[0].length+1; j++) {
				surroundingArray[i][j] = squares[i][j];
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
									(Math.random() >= minValue));
						}
					}
				}
				
			}
		}
	}
	
	private void changeSquares(boolean [][] shouldChange) {
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
	

	private void setCells (Cell [][] cellArray) {
		int i, j;
		for (i = 0; i < cellArray.length; i++) {
			for (j = 0; j < cellArray[0].length; j++) {
				cellArray[i][j].changeStatus(squares[i][j]);
			}
		}
	}
}
