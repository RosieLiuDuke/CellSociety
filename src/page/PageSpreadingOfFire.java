package page;
import javafx.scene.paint.Color;

import cellSociety.CellSociety;

public class PageSpreadingOfFire extends GamePage {

	public PageSpreadingOfFire(CellSociety cs) {
		super(cs);
		getColorMap().put(0, Color.YELLOW);
		getColorMap().put(1, Color.GREEN);
		getColorMap().put(2, Color.RED);
	}
	
	protected void setupGrid(String newValue) {
		
	}

	@Override
	protected void setupComponents() {
		// TODO Auto-generated method stub
		
	}


}
