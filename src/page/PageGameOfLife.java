package page;

import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao, Harry Liu
 */
public class PageGameOfLife extends UIsetup {

	public PageGameOfLife(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	// use specific setup for each status
	@Override
	protected int getCellStatus(int col, int row){
		return this.getParametersController().getStatusDistribution(col, row);
	}
		
	@Override
	protected void setupComponents(){
		super.setupComponents();
		// can add other choices of layouts
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		// can add other layouts
	}
	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		// do nothing here
	}
}