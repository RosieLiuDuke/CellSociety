import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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

	public PageGameOfLife() {
		// TODO Auto-generated constructor stub
		super();
		// TODO without input from XML, hard-coded first
		// assume now the initial pattern is hard-coded
		// TODO mouse click to set status
		rowNum = 12;
		colNum = 12;
		size = 1;
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
		parameters.setLayoutY(0);
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
		
		layoutChoice = new ChoiceBox<String>(FXCollections.observableArrayList("default"));
		layoutChoice.setLayoutX(0);
		layoutChoice.setLayoutY(200);
		layoutChoice.getSelectionModel().selectFirst();
		layoutChoice.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					setoutLayout(newValue);
					
				}
				
			});
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
		if ( newValue.equals("default")){
			this.getRoot().getChildren().clear();
			for (int i = 0; i < rowNum; i++){
				for (int j = 0; j < colNum; j++){
					double xPosition = 0 + i * size;
					double yPosition = 300 + j * size;
					if (i == 6 && i == 6){
						cells[i][j] = new Cell(xPosition, yPosition, size, 1); // 1 stands for alive
					}
					else if (i == 7 && j == 7){
						cells[i][j] = new Cell(xPosition, yPosition, size, 1);
					}
					else{
						cells[i][j] = new Cell(xPosition, yPosition, size, -1); // -1 stands for dead
					}
					this.getRoot().getChildren().add(cells[i][j].getCell());
				}
			}
		}
	}

	private void handleMouseInputBack(MouseEvent e) {
		// TODO click the "Back" button to return to main menu
		
	}

}
