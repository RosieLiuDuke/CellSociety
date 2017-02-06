package page;
import cell.Cell;
import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao, Harry Liu
 */
public class PageGameOfLife extends GamePage {
		
	public PageGameOfLife(CellSociety cs) {
		super(cs);
		this.getColorMap().put(0, Color.TRANSPARENT);
		this.getColorMap().put(1, Color.BLACK);
	}
	
	@Override
	protected void setupComponents(){
		this.getOptions().add("Input");
		this.getOptions().add("3 in line");
		super.setupComponents();
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		
		if (newValue.equals("3 in line")){
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