package animation;
import cellSociety.CellSociety;
import page.GamePage;
import page.PageSpreadingOfFire;

public abstract class Animation {
	
	private CellSociety cellSociety;
	
	public Animation(CellSociety c) {
		cellSociety = c;
	}
	
	public abstract void calculateMove();
	
	public CellSociety getCellSociety() {
		return cellSociety;
	}
	
	/**
	 * The method needed to get the array from the Page
	 * for the animation
	 * @param The String sent to get the correct page
	 * @return the array needed for the animation
	 */
	public int [][] getArray(String s) {
		int i, j;
		GamePage p = getNeededPage(s);
		int [][] intArray = new int[ p.getRow()][p.getCol()];
		
		for (i = 0; i < intArray.length; i++) {
			for (j = 0; j < intArray[0].length; j++) {
				intArray[i][j] = p.getCell(i, j).getStatus();
			}
		}
		
		return intArray;
	}
	
	/** 
	 * The method to get the right page using the proper string
	 * @param string needed to get page from cell society class
	 * @return the page
	 */
	public GamePage getNeededPage(String s) {
		return (GamePage) getCellSociety().getPage(s);
	}
}
