package page;
import java.util.ArrayList;
import java.util.List;
import cell.Cell;
import cell.HexagonCell;
import cell.SquareCell;
import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The subclass UIsetup sets up the layout of every GamePage (simulation) that will be run.
 * (Simulation of Front-End)
 * 
 * @author Harry Liu
 */
public abstract class UIsetup extends GamePage {

	private Text gameInfo;
	private List<String> myOptions;
	private ComboBox<String> simulationChoice;
	private String text;
	private Text gameTitle;
	private VBox slidersBox;
	private Slider speed;
	private VBox parametersBox;
	
	public UIsetup(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
		myOptions = new ArrayList<String>();
		setupComponents();
	}
	
	public List<String> getOptions(){
		return myOptions;
	}

	public String getText(){
		return text;
	}
	
	public Text getInfo(){
		return gameInfo;
	}
	
	public VBox getSliderBox(){
		return slidersBox;
	}
	/**
	 * The method to add Buttons to the bottom of the UI Screen
	 */
	private VBox addButtons(){
		VBox buttonBox = new VBox(5);
		buttonBox.getChildren().addAll(this.getStart(), this.getStop(), this.getStep(), this.getBack());
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;		
	}
	
	/**
	 * The method to set up required components in the scene.
	 */
	protected void setupComponents(){
		gameInfo = new Text();
		gameInfo.setId("parameters");
		
		myOptions.add("Input");
		ObservableList<String> options = FXCollections.observableArrayList(myOptions);
		simulationChoice = new ComboBox<String>(options);
		simulationChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
		simulationChoice.setTooltip(new Tooltip (getMyResources().getString("SelectCommand")));
		simulationChoice.setPromptText(getMyResources().getString("ChoicesCommand"));
		
		slidersBox = new VBox(15);
		speed = createSlider(1, 5, this.getParametersController().getSpeed(), 1, true);
		speed.valueProperty().addListener((obs,oVal,nVal) -> updateSpeed(nVal.intValue()));
		slidersBox.getChildren().addAll(new Text(getMyResources().getString("SpeedAdjustor")), speed);

		parametersBox = new VBox(15);
		updateParameterBox();
		
		ScrollPane sp = new ScrollPane();
		sp.setContent(parametersBox);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setId("rightpanel");
		
		gameTitle = new Text(this.getCellSociety().getCurrentType());
		gameTitle.setId("gameTitle");
		
		VBox left = new VBox(10);
		left.getChildren().addAll(gameTitle,this.getGrid(), this.getChart());
		left.setAlignment(Pos.CENTER);
		
		updateTextInfo();
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		this.getRoot().setLeft(left);
		this.getRoot().setRight(sp);
		left.setPrefWidth(getWidth()*0.6);
		sp.setPrefWidth(getWidth()*0.4);
		
		this.getCellSociety().setDelay(this.getParametersController().getSpeed());
		this.getCellSociety().setupGameLoop();
	}

	protected void updateParameterBox() {
		parametersBox.getChildren().clear();
		parametersBox.getChildren().addAll(slidersBox, simulationChoice, addButtons(), gameInfo);
	}

	/**
	 * The method to set up the grid layout in the scene.
	 * Abstract.
	 * @param newValue
	 */
	protected void setupGrid(String newValue){
		if (newValue!=null){
			this.setSimulationSelected(true); 
		}
		this.getCellSociety().stopGameLoop();
		this.getGrid().getChildren().clear();
		this.setCurrentStep(0);
		updateTextInfo();
		double width = Parameters.gridWidth / this.getParametersController().getCol();
		double height = Parameters.gridHeight / this.getParametersController().getRow();
		if (newValue.equals("Input")){
			double xPosition = 0;
			for (int col = 0; col < this.getParametersController().getCol(); col ++){  // x position - col
				double yPosition = 0;
				if (col % 2 == 1) {yPosition += height / 2;}
				for (int row = 0; row < this.getParametersController().getRow(); row++){  // y position - row
					int cellStatus = this.getCellStatus(col, row);
					// TODO only for square cell
					HexagonCell newCell = new HexagonCell(xPosition, yPosition, width/2, height/2, cellStatus);
//					SquareCell newCell = new SquareCell(xPosition, yPosition, width, height, cellStatus);
					newCell.getShape().setOnMouseClicked(e -> updateCellStatusOnMouseReleased(newCell));
					addCell(col,row, newCell);
					getCell(col,row).changeColor(this.getParametersController().getColor(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getShape());
					yPosition += height; 
				}
				xPosition += width;
			}
		}
		quantityMap();
		createPopulationChart();
	}

	private void updateCellStatusOnMouseReleased(Cell cell) {
		int oldStatus = cell.getStatus();
		int newStatus;
		if (oldStatus < this.getParametersController().getNumberOfStatus() - 1){
			newStatus = oldStatus + 1;
			cell.changeStatus(newStatus);
		}
		else{
			newStatus = 0;
			cell.changeStatus(newStatus);
		}
		cell.changeColor(this.getParametersController().getColor(cell.getStatus()));
		updateTextInfo();
		updateColorandData();
	}

	/**
	 * The method to create a slider for the right side of the UI Screen
	 * @param min
	 * @param max
	 * @param increment
	 * @param showTick
	 */
	protected Slider createSlider(double min, double max, double current, double increment, boolean showTick){
		Slider newSlider;
		newSlider = new Slider(min, max, current);
		newSlider.setShowTickLabels(showTick);
		newSlider.setShowTickMarks(showTick);
		newSlider.setMajorTickUnit(increment);
		newSlider.setBlockIncrement(increment);
		newSlider.setSnapToTicks(true);
		return newSlider;
	}

	/**
	 * The method that updates the parameters displayed at the top of the UI Screen
	 */
	@Override
	public void updateTextInfo() {
		text =  getMyResources().getString("RowParameter") + this.getParametersController().getRow() + "\n" 
				+ getMyResources().getString("ColParameter") + this.getParametersController().getCol() + "\n"
				+ getMyResources().getString("GridWidthParameter") + Parameters.gridWidth + "\n"
				+ getMyResources().getString("GridHeightParameter") + Parameters.gridHeight + "\n"
				+ getMyResources().getString("StepParameter") + this.getParametersController().getSpeed() + "\n"
				+ getMyResources().getString("CurrentStepParameter") + getCurrentStep()+ "\n";
		this.getInfo().setText(text);
	}
	
	/**
	 * Change the speed of simulation by having a new timeline when the slider is manipulated.
	 * @param nVal
	 */
	public void updateSpeed(int nVal) {
		this.getParametersController().setSpeed(nVal);
		this.getCellSociety().setDelay(nVal);
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().setupGameLoop();
		this.updateTextInfo();
	}
	
}