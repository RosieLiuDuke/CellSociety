import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The subclass Page to hold the Scene for the Game of Life simulation.
 * @author Yilin Gao
 *
 */
public class PageGameOfLife extends Page {
	
	private Cell[][] cells;
	private int rowNum;
	private int colNum;
	private double size;
	
	private double speed;	
	private int currentStep;
	
	private Text parameters;
	private Button back;
	private Button start;
	private Button step;
	private ChoiceBox<String> layoutChoice;
	
	public Button getStart(){
		return start;
	}
	
	public Button getStep(){
		return step;
	}
	
	public Button getBack(){
		return back;
	}
	
	public int getRow(){
		return rowNum - 2;
	}
	
	public int getCol(){
		return colNum - 2;
	}
	
	public Cell getCell(int i, int j){
		return cells[i+1][j+1];
	}

	public PageGameOfLife(Stage theStage) {
		super(theStage);
		// TODO without input from XML, hard-coded first
		// assume now the initial pattern is hard-coded
		rowNum = 32;
		colNum = 32;
		size = 10;
		cells = new Cell[rowNum][colNum];
		speed = 1;
		currentStep = 0;
		
		String text = "Number of rows: " + rowNum 
				+ "\nNumber of columns: " + colNum 
				+ "\nCell size: " + size
				+ "\nStep speed: " + speed
				+ "\nStep: " + currentStep;
		parameters = new Text(text);
		parameters.setLayoutX(0);
		parameters.setLayoutY(20);
		this.getRoot().getChildren().add(parameters);
		
		back = new Button("Back");
		back.setLayoutX(0);
		back.setLayoutY(100);
		back.setOnMouseReleased(e -> handleMouseInputBack(e));
		this.getRoot().getChildren().add(back);
		
		start = new Button("Start");
		start.setLayoutX(0);
		start.setLayoutY(250);
		start.setOnMouseReleased(e -> handleMouseInputStart(e));
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
	}

	private void handleMouseReleasedStep(MouseEvent e) {
		// TODO click the "step" button to run the simulation step by step
		
	}

	private void handleMouseInputStart(MouseEvent e) {
		// TODO click the "start" button to run the simulation consecutively
		
	}

	private void setoutLayout(String newValue) {
		// assume now the grid of cells starting from (0, 300)
		if (newValue.equals("default")){
			int rowMid = (rowNum - 2) / 2;
			int colMid = (colNum - 2) / 2;
 			for (int i = 0; i < rowNum; i++){
				for (int j = 0; j < colNum; j++){
					double xPosition = 0 + i * size;
					double yPosition = 300 + j * size;
					if (i == rowMid && j == colMid){
						cells[i][j] = new Cell(xPosition, yPosition, size, 1); // 1 stands for alive
						cells[i][j].getCell().setFill(Color.BLACK);
					}
					else if (i == rowMid + 1 && j == colMid + 1){
						cells[i][j] = new Cell(xPosition, yPosition, size, 1);
						cells[i][j].getCell().setFill(Color.BLACK);
					}
					else{
						cells[i][j] = new Cell(xPosition, yPosition, size, -1); // -1 stands for dead
					}
					if (i != 0 && i != rowNum - 1 && j != 0 && j != colNum - 1)
						this.getRoot().getChildren().add(cells[i][j].getCell());
				}
			}
		}
	}

	private void handleMouseInputBack(MouseEvent e) {
		// TODO click the "Back" button to return to main menu
		
	}

	@Override
	public void initializePage() {
		// TODO Auto-generated method stub
		
	}

}