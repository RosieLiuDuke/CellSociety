import java.util.ArrayList;

public class GameOfLife extends Animation {
	
	private static final int ONVALUE = 1;
	private static final int OFFVALUE = 0;
	
	private int [][] squares;
	
	public void calculateMove(Cell [][] cellArray) {
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
		int i, j, k, l, total = 0;
		
		int [][] surroundingArray = new int[squares.length +2][squares[0].length+2];
		
		for (i = 1; i < squares.length+1; i++) {
			for (j = 1; j < squares[0].length+1; j++) {
				surroundingArray[i][j] = squares[i-1][j-1];
			}
		}
		for (i = 0; i < surroundingArray.length; i++) {
			surroundingArray[i][0] = OFFVALUE;
			surroundingArray[i][surroundingArray[0].length -1] = OFFVALUE;
		}
		for (i = 0; i < surroundingArray[0].length; i++) {
			surroundingArray[0][i] = OFFVALUE;
			surroundingArray[surroundingArray.length - 1][i] = OFFVALUE;
		}
		
		for (i = 1; i < surroundingArray.length - 1; i++) {
			for (j = 1; j < surroundingArray.length -1; j++) {
				
				for (k = i -1; k < i+2; k++) {
					for (l = j -1; l < j+2; l++) {
						total += surroundingArray[k][l];
					}
				}
				
				total -= surroundingArray[i][j];
				
				
				shouldChange[i-1][j-1] = (((squares[i][j] == ONVALUE) && ((total <2) || (total > 3))) ||
							((squares[i][j] == OFFVALUE) && ((total == 2) || (total == 3))));
				
				
				total = 0;
			}
		}
	}
	
	private void changeSquares(boolean [][] shouldChange) {
		int i, j;
		
		for (i = 0; i < squares.length; i++) {
			for (j = 0; j < squares.length; j++) {
				if (shouldChange[i][j]) {
					if (squares[i][j] == ONVALUE) {
						squares[i][j] = OFFVALUE;
					}
					else {
						squares[i][j] = ONVALUE;
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
