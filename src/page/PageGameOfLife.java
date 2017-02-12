package page;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao, Harry Liu
 */
public class PageGameOfLife extends UIsetup {
		
	public PageGameOfLife(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
		this.getParametersController().addColor(0, Color.TRANSPARENT);
		this.getParametersController().addColor(1, Color.BLACK);
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