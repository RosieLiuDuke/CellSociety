package page;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import cell.Cell;
import cellSociety.CellSociety;
public class PageSpreadingOfFire extends UIsetup {
	private List<String> myOptions;
	
	public PageSpreadingOfFire(CellSociety cs) {
		super(cs);
		getColorMap().put(0, Color.YELLOW);
		getColorMap().put(1, Color.GREEN);
		getColorMap().put(2, Color.RED);
	}
	
	@Override
	protected void setupComponents() {
		myOptions = new ArrayList<String>();
		myOptions.add("FillerText");
		setupComponents(myOptions, this);
	}
	/**
	 * The method to set up initial states of all cells.
	 * 2 kinds of choices: from input file or from hard-coded rules.
	 */
	protected void setupGrid(String newValue) {
		// TODO how to deal with multiple layouts
		this.getCellSociety().stopGameLoop();
		this.getGrid().getChildren().clear();
		this.setCurrentStep(0);
		updateTextInfo();
		
		if (newValue.equals("input")){
			for (int col = 0; col < getCol(); col ++){  // x position - col
				for (int row = 0; row < getRow(); row++){  // y position - row
					double xPosition = 0 + col * getSize();
					double yPosition = 300 + row * getSize();
					setCell(col,row, new Cell(xPosition, yPosition, getSize(), getCellStatus(col, row)));
					getCell(col,row).changeColor(this.getColorMap().get(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getRectangle());
				}
			}
		}
	}
}