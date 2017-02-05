package page;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import cellSociety.CellSociety;

public class PageSpreadingOfFire extends UIsetup {

	private List<String> myOptions;
	
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
		myOptions = new ArrayList<String>();
		myOptions.add("FillerText");
		setupComponents(myOptions, this);
	}


}
