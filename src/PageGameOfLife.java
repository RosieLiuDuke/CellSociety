import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao
 *
 */
public class PageGameOfLife extends GamePage {
	
	private Text parameters;
	private Button back;
	private Button start;
	private Button step;
	private ChoiceBox<String> layoutChoice;
	

	public PageGameOfLife(CellSociety cs) {
		super(cs);
		// TODO without input from XML, hard-coded first
		// assume now the initial pattern is hard-coded
		setRowNum(32);
		setColNum(32);
		setSize(10);
		setCells(getRow(), getCol());
		setSpeed(1);
		setCurrentStep(0);
		
		HBox parametersBox = new HBox();
		
		String text = "Number of rows: " + getRow() 
				+ "\nNumber of columns: " + getCol() 
				+ "\nCell size: " + getSize()
				+ "\nStep speed: " + getSpeed()
				+ "\nStep: " + getCurrentStep();
		
		parameters = new Text(text);	
		parametersBox.getChildren().add(parameters);
		
		this.getRoot().getChildren().add(parametersBox);
		
		back = new Button("Back");
		back.setLayoutX(0);
		back.setLayoutY(100);
		back.setOnMouseReleased(e -> handleMouseReleaseBack(e));
		this.getRoot().getChildren().add(back);
		
		start = new Button("Start");
		start.setLayoutX(0);
		start.setLayoutY(250);
		start.setOnMouseReleased(e -> handleMouseReleasedStart(e));
		this.getRoot().getChildren().add(start);
		
		step = new Button("Step");
		step.setLayoutX(200);
		step.setLayoutY(250);
		step.setOnMouseReleased(e -> handleMouseReleasedStep(e));
		this.getRoot().getChildren().add(step);
		
		layoutChoice = new ChoiceBox<String>(FXCollections.observableArrayList("default"));
		layoutChoice.setLayoutX(0);
		layoutChoice.setLayoutY(200);
		layoutChoice.valueProperty().addListener((obs, oVal, nVal) -> setoutLayout(nVal));
		this.getRoot().getChildren().add(layoutChoice);
		
		this.getCellSociety().setIsStep(true);
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
	}


	protected void setoutLayout(String newValue) {
		// TODO assume now the grid of cells starting from (0, 300)
		if (newValue.equals("default")){
			int rowMid = (getRow() - 2) / 2;
			int colMid = (getCol() - 2) / 2;
 			for (int i = 0; i < getRow(); i++){
				for (int j = 0; j < getCol(); j++){
					double xPosition = 0 + i * getSize();
					double yPosition = 300 + j * getSize();
					if (i == rowMid && j == colMid){
						setCell(i,j, new Cell(xPosition, yPosition, getSize(), 1)); // 1 stands for alive
						getCell(i, j).getCell().setFill(Color.BLACK);
					}
					else if (i == rowMid + 1 && j == colMid + 1){
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), 1));
						getCell(i,j).getCell().setFill(Color.BLACK);
					}
					else{
						setCell(i, j, new Cell(xPosition, yPosition, getSize(), -1)); // -1 stands for dead
					}
					if (i != 0 && i != getRow() - 1 && j != 0 && j != getCol() - 1)
						this.getRoot().getChildren().add(getCell(i,j).getCell());
				}
			}
		}
		this.getCellSociety().beginGameLoop();
	}
	
	public Button getStart(){
		return start;
	}
	
	public Button getStep(){
		return step;
	}
	
	public Button getBack(){
		return back;
	}

}