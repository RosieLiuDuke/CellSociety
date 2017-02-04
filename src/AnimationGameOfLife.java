import java.util.ArrayList;

public class AnimationGameOfLife extends Animation {
	
	private static final int ONVALUE = 1;
	private static final int OFFVALUE = 0;
	
	
	public AnimationGameOfLife(CellSociety c) {
		super(c);
	}
	
	public void calculateMove() {
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
		PageGameOfLife p = (PageGameOfLife) getCellSociety().getPage("Game Of Life");
		int [][] intArray = new int[ p.getRow()][p.getCol()];
		
		for (i = 0; i < intArray.length; i++) {
			for (j = 0; j < intArray[0].length; j++) {
				intArray[i][j] = p.getCell(i, j).getStatus();
			}
		}
		
		return intArray;
	}
	
	public void checkSurrounding(boolean [][] shouldChange, int [][] squares) {
		int i, j, k, l, total = 0;
		
		int [][] surroundingArray = new int[squares.length +2][squares[0].length+2];
		
		for (i = 1; i < squares.length+1; i++) {
			for (j = 1; j < squares[0].length+1; j++) {
				surroundingArray[i][j] = squares[i][j];
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
	
	public void changeSquares(boolean [][] shouldChange, int [][] squares) {
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
	
	public void setCells (int [][] squares) {
		int i, j;
		PageGameOfLife p = (PageGameOfLife) getCellSociety().getPage("Game Of Life");
		for (i = 0; i < squares.length; i++) {
			for (j = 0; j < squares[0].length; j++) {
				p.getCell(i, j).changeStatus(squares[i][j]);
			}
		}
	}
}
