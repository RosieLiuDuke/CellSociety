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
		
		double width = gridWidth / getCol();
		double height = gridHeight / getCol();
		
		if (newValue.equals("3 in line")){
			int rowMid = getRow() / 2;
			int colMid = getCol() / 2;
 			for (int col = 0; col < getCol(); col++){
				for (int row = 0; row < getRow(); row++){
					double xPosition = col * width;
					double yPosition = row * height;
					if (col == colMid && row == rowMid){
						setCell(col,row, new Cell(xPosition, yPosition, width, height, 1));
					}
					else if (col == colMid && row == rowMid + 1){
						setCell(col, row, new Cell(xPosition, yPosition, width, height, 1));
					}
					else if (col == colMid && row == rowMid - 1){
						setCell(col, row, new Cell(xPosition, yPosition, width, height, 1));
					}
					else{
						setCell(col, row, new Cell(xPosition, yPosition, width, height, 0));
					}
					getCell(col,row).changeColor(this.getColorMap().get(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getRectangle());
				}
			}
		}
	}
	
	public void updateTextInfo() {
		String text = "Simulation name: " + this.getCellSociety().getCurrentType() 
				+ "\nNumber of columns: " + getCol() + " | " 
				+ "Number of rows: " + getRow() + " | " 
				+ "Grid width: " + gridWidth + " | "
				+ "Grid height: " + gridHeight + " | "
				+ "\nStep speed: " + getSpeed() + " | " 
				+ "Step: " + getCurrentStep();
		this.getParameters().setText(text);
	}
}