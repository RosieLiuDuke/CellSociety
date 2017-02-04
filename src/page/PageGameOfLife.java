package page;
import cell.Cell;
import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao, Harry Liu
 *
 */

public class PageGameOfLife extends GamePage {

	private Text parameters;
	private ChoiceBox<String> layoutChoice;

	public PageGameOfLife(CellSociety cs) {
		super(cs);
		this.getColorMap().put(0, Color.TRANSPARENT);
		this.getColorMap().put(1, Color.BLACK);
	}

	public void setupComponents() {
		HBox parametersBox = new HBox(15);
		
		String text = "Number of rows: " + getRow() + " | " 
				+ "Number of columns: " + getCol() + " | "  
				+ "Cell size: " + getSize() + " | "
				+ "Step speed: " + getSpeed() + " | " 
				+ "Step: " + getCurrentStep();
		
		parameters = new Text(text);	
		layoutChoice = new ChoiceBox<String>(FXCollections.observableArrayList("default", "3 in line"));
		layoutChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
		parametersBox.getChildren().addAll(parameters, layoutChoice);
		parametersBox.setAlignment(Pos.CENTER);

		HBox buttonBox = new HBox(5);
		buttonBox.getChildren().addAll(this.getBack(), this.getStart(), this.getStop(), this.getStep());
		buttonBox.setAlignment(Pos.CENTER);
		
		this.getRoot().setBottom(buttonBox);
		this.getRoot().setTop(parametersBox);
		this.getRoot().setCenter(this.getGrid());
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
	}


	protected void setupGrid(String newValue) {
		// TODO how to deal with multiple layouts
		this.setCells(this.getRow(), this.getCol());
		this.getGrid().getChildren().clear();
		if (newValue.equals("default")){
			int rowMid = getRow() / 2;
			int colMid = getCol() / 2;
 			for (int i = 0; i < getRow(); i++){
				for (int j = 0; j < getCol(); j++){
					double xPosition = 0 + i * getSize();
					double yPosition = 300 + j * getSize();
					if (i == rowMid && j == colMid){
						setCell(i,j, new Cell(xPosition, yPosition, getSize(), 1)); // 1 stands for alive
					}
					else if (i == rowMid + 1 && j == colMid + 1){
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else{
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 0)); // 0 stands for dead
					}
					getCell(i,j).changeColor(this.getColorMap().get(getCell(i,j).getStatus()));
					this.getGrid().getChildren().add(getCell(i,j).getRectangle());
				}
			}
		}
		else if (newValue.equals("3 in line")){
			int rowMid = getRow() / 2;
			int colMid = getCol() / 2;
 			for (int i = 0; i < getRow(); i++){
				for (int j = 0; j < getCol(); j++){
					double xPosition = 0 + i * getSize();
					double yPosition = 300 + j * getSize();
					if (i == rowMid && j == colMid){
						setCell(i,j, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else if (i == rowMid && j == colMid + 1){
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else if (i == rowMid && j == colMid - 1){
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 1));
					}
					else{
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 0));
					}
					getCell(i,j).changeColor(this.getColorMap().get(getCell(i,j).getStatus()));
					this.getGrid().getChildren().add(getCell(i,j).getRectangle());
				}
			}
		}
	}
	
	public void updateColor () {
		int i, j;
		for (i = 0; i < getRow(); i++) {
			for (j = 0; j < getCol(); j++) {
				getCell(i,j).changeColor(this.getColorMap().get(getCell(i,j).getStatus()));
			}
		}
	}

}