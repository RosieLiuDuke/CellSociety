import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
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

	private Group grid = new Group();
	private Text parameters;
	private Button back;
	private Button start;
	private Button step;
	private ChoiceBox<String> layoutChoice;
	private Map <Integer, Color> colorMap;
	
	public Button getStart(){
		return start;
	}
	
	public Button getStep(){
		return step;
	}
	
	public Button getBack(){
		return back;
	}

	public PageGameOfLife(CellSociety cs) {
		super(cs);
		colorMap = new HashMap<Integer, Color>();
		colorMap.put(0, Color.WHITE);
		colorMap.put(1, Color.BLACK);
	}

	public void setoutComponents() {
		HBox parametersBox = new HBox(15);
		
		String text = "Number of rows: " + getRow() + " | " 
				+ "Number of columns: " + getCol() + " | "  
				+ "Cell size: " + getSize() + " | "
				+ "Step speed: " + getSpeed() + " | " 
				+ "Step: " + getCurrentStep();
		
		parameters = new Text(text);	
		layoutChoice = new ChoiceBox<String>(FXCollections.observableArrayList("default"));
		layoutChoice.valueProperty().addListener((obs, oVal, nVal) -> setoutGrid(nVal));	
		parametersBox.getChildren().addAll(parameters, layoutChoice);
		parametersBox.setAlignment(Pos.CENTER);

		back = createButton("BACK", event-> handleMouseReleasedBack(event));
		start = createButton("START", event-> handleMouseReleasedStart(event));
		step = createButton("STEP", event-> handleMouseReleasedStep(event));

		HBox buttonBox = new HBox(5);
		buttonBox.getChildren().addAll(back, start, step);
		buttonBox.setAlignment(Pos.CENTER);
		
		this.getRoot().setBottom(buttonBox);
		this.getRoot().setTop(parametersBox);
		this.getRoot().setCenter(grid);
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		this.getCellSociety().setIsStep(true);
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
	}


	protected void setoutGrid(String newValue) {
		// TODO assume now the grid of cells starting from (0, 300)
		if (newValue.equals("default")){
			this.initializeCells();
			int rowMid = getRow() / 2;
			int colMid = getCol() / 2;
 			for (int i = 0; i < getRow(); i++){
				for (int j = 0; j < getCol(); j++){
					double xPosition = 0 + i * getSize();
					double yPosition = 300 + j * getSize();
					if (i == rowMid && j == colMid){
						setCell(i,j, new Cell(xPosition, yPosition, getSize(), 1)); // 1 stands for alive
						getCell(i, j).getCell().setFill(Color.BLACK);
					}
					else if (i == rowMid && j == colMid + 1){
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 1));
						getCell(i,j).getCell().setFill(Color.BLACK);
					}
					else if (i == rowMid && j == colMid - 1){
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 1));
						getCell(i,j).getCell().setFill(Color.BLACK);
					}
					else{
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 0)); // -1 stands for dead
					}
					grid.getChildren().add(getCell(i,j).getCell());
				}
			}
		}
		this.getCellSociety().beginGameLoop();
	}
	
	public void updateColor () {
		int i, j;
		for (i = 0; i < getRow(); i++) {
			for (j = 0; j < getCol(); j++) {
				getCell(i,j).changeColor(colorMap.get(getCell(i,j).getStatus()));
			}
		}
	}

}