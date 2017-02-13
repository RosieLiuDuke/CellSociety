package animation;
import cellSociety.CellSociety;
import grid.Grid;
import page.GamePage;
import page.Parameters;

public abstract class Animation {
	
	private CellSociety cellSociety;
	private Parameters parametersController;
	private Grid g;
	
	public Animation(CellSociety c, Parameters p) {
		cellSociety = c;
		parametersController = p;
		g = parametersController.getGrid();
	}
	
	public abstract void calculateMove();
	
	public CellSociety getCellSociety() {
		return cellSociety;
	}
	
	public Parameters getParametersController(){
		return parametersController;
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
		int [][] intArray = new int[ parametersController.getRow()][parametersController.getCol()];
		
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
	
	protected void setCells (int [][] grid, GamePage p) {
		int i, j;
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[0].length; j++) {
				p.getCell(i, j).changeStatus(grid[i][j]);
			}
		}
	}
	
	protected Grid getGrid () {
		return g;
	}
}
