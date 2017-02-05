package page;
import java.util.ArrayList;
import java.util.List;

import cell.Cell;
import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao, Harry Liu
 */

public class PageGameOfLife extends UIsetup {
	
	private List<String> myOptions;

	public PageGameOfLife(CellSociety cs) {
		super(cs);
		this.getColorMap().put(0, Color.TRANSPARENT);
		this.getColorMap().put(1, Color.BLACK);
	}

	@Override
	public void setupComponents(){
		myOptions = new ArrayList<String>();
		myOptions.add("input");
		myOptions.add("3 in line");
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
		else if (newValue.equals("3 in line")){
			int rowMid = getRow() / 2;
			int colMid = getCol() / 2;
 			for (int col = 0; col < getCol(); col++){
				for (int row = 0; row < getRow(); row++){
					double xPosition = 0 + col * getSize();
					double yPosition = 300 + row * getSize();
					if (col == colMid && row == rowMid){
						setCell(col,row, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else if (col == colMid && row == rowMid + 1){
						setCell(col, row, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else if (col == colMid && row == rowMid - 1){
						setCell(col, row, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else{
						setCell(col, row, new Cell(xPosition, yPosition, getSize(), 0));
					}
					getCell(col,row).changeColor(this.getColorMap().get(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getRectangle());
				}
			}
		}
	}
	

}