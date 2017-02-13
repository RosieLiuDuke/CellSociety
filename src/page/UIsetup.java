package page;
import java.util.ArrayList;
import java.util.List;
import cell.Cell;
import cell.HexagonCell;
import cell.SquareCell;
import cell.TriangleCell;
import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The abstract subclass UIsetup sets up the layout of every GamePage (simulation) that will be run.
 * (Simulation of Front-End)
 * @author Harry Liu
 * @author Yilin Gao
 */
public abstract class UIsetup extends GamePage {

	private Text gameInfo;
	private List<String> mySimulations;
	private ComboBox<String> simulationChoice;
	private String text;
	private Text gameTitle;
	private VBox slidersBox;
	private Slider speed;
	private VBox parametersBox;
	private String shape = "Square";
	
	/**
	 * Constructor of the UIsetup class.
	 * @param cs: the CellSociety instance
	 * @param language: a string representing user choice of language
	 * @param p: a Parameters instant from the calling class
	 */
	public UIsetup(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
		mySimulations = new ArrayList<String>();
		shape = this.getParametersController().getCellShape();
		setupComponents();
	}
	
	public List<String> getOptions(){
		return mySimulations;
	}

	/**
	 * The method to get the simulation information text String.
	 * @return String
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * The method to get the simulation information display component Text.
	 * @return Text
	 */
	public Text getInfo(){
		return gameInfo;
	}
	
	/**
	 * The method to get the VBox to hold all sliders.
	 * @return VBox
	 */
	protected VBox getSliderBox(){
		return slidersBox;
	}
	/**
	 * The method to add all Buttons to the UI Screen.
	 * @return VBox contains all Buttons
	 */
	private VBox addButtons(){
		VBox buttonBox = new VBox(5);
		buttonBox.getChildren().addAll(this.getStart(), this.getStop(), this.getStep(), this.getBack());
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;		
	}
	
	/**
	 * The method to set up required components in the scene.
	 * The method can be override by sub classes if new components are required.
	 */
	protected void setupComponents(){
		gameInfo = new Text();
		gameInfo.setId("parameters");
		
		mySimulations.add("Input");
		
		createComboBox();
		
		slidersBox = new VBox(15);
		speed = createSlider(1, 5, this.getParametersController().getSpeed(), 1, true);
		speed.valueProperty().addListener((obs,oVal,nVal) -> updateSimulationSpeedOnSliderDrag(nVal.intValue()));
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

	/**
	 * The method to update the VBox on the left side of the panel.
	 */
	private void createComboBox(){
		ObservableList<String> options = FXCollections.observableArrayList(mySimulations);
		simulationChoice = initializeComboBox(options, getMyResources().getString("SelectCommand"), getMyResources().getString("ChoicesCommand"));
		simulationChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
	}

	private ComboBox<String> initializeComboBox(ObservableList<String> observable, String ToolTip, String Prompt){
		ComboBox<String> comboBox = new ComboBox<String>(observable);
		comboBox.setTooltip(new Tooltip (ToolTip));
		comboBox.setPromptText(Prompt);
		return comboBox;
	}
	
	protected void updateParameterBox() {
		parametersBox.getChildren().clear();
		parametersBox.getChildren().addAll(slidersBox, simulationChoice, addButtons(), gameInfo);
	}

	/**
	 * The method to set up the grid layout in the scene.
	 * The method can be override by sub classes for different grid layouts.
	 * @param newValue
	 */
	protected void setupGrid(String newValue){
		if (newValue!=null){
			this.setLayoutSelected(true); 
		}
		this.getCellSociety().stopGameLoop();
		this.getGrid().getChildren().clear();
		this.setCurrentStep(0);
		updateTextInfo();
		if (newValue.equals("Input")){
			generateAllCells();
		}
		quantityMap();
		createPopulationChart();
	}

	private void generateAllCells() {
		double centerX = 0;
		if (shape.equals("Square")){
			double width = Parameters.gridWidth / this.getParametersController().getCol();
			double height = Parameters.gridHeight / this.getParametersController().getRow();
			for (int col = 0; col < this.getParametersController().getCol(); col ++){
				double centerY = 0;
				for (int row = 0; row < this.getParametersController().getRow(); row++){  
					int cellStatus = this.getCellStatus(col, row);
					boolean visible = this.getParametersController().isGridVisible();
					Cell newCell = new SquareCell(centerX, centerY, width, height, cellStatus, visible);
					newCell.getShape().setOnMouseClicked(e -> updateCellStatusOnMouseReleased(newCell));
					addCell(col,row, newCell);
					getCell(col,row).changeColor(this.getParametersController().getColor(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getShape());
					centerY += height; 
				}
				centerX += width;
			}
		}
		else if (shape.equals("Triangle")){
			boolean up = true;
			double width = 2 * Parameters.gridWidth / (this.getParametersController().getCol() + 1);
			double height = Parameters.gridHeight / this.getParametersController().getRow();
			for (int col = 0; col < this.getParametersController().getCol(); col ++){
				double centerY = 0;
				if (!up){
					centerY -= 1 / 3 * height;
				}
				for (int row = 0; row < this.getParametersController().getRow(); row++){  
					int cellStatus = this.getCellStatus(col, row);
					boolean visible = this.getParametersController().isGridVisible();
					Cell newCell = new TriangleCell(centerX, centerY, width, height, cellStatus, visible, up);
					newCell.getShape().setOnMouseClicked(e -> updateCellStatusOnMouseReleased(newCell));
					addCell(col,row, newCell);
					getCell(col,row).changeColor(this.getParametersController().getColor(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getShape());
					if (up){
						centerY += 2 / 3 * height;
					}
					else{
						centerY += 4 / 3 * height;
					}
					up = !up;
				}
				centerX += width / 2;
			}
		}
		else if (shape.equals("Hexagon")){
			double width = 4 * Parameters.gridWidth / (4 * this.getParametersController().getCol() - 3);
			double height = 2* Parameters.gridHeight / (2 * this.getParametersController().getRow() + 1);
			for (int col = 0; col < this.getParametersController().getCol(); col ++){
				double centerY = 0;
				if (col % 2 == 1) {centerY += height / 2;}
				for (int row = 0; row < this.getParametersController().getRow(); row++){  
					int cellStatus = this.getCellStatus(col, row);
					boolean visible = this.getParametersController().isGridVisible();
					Cell newCell = new HexagonCell(centerX, centerY, width, height, cellStatus, visible);
					newCell.getShape().setOnMouseClicked(e -> updateCellStatusOnMouseReleased(newCell));
					addCell(col,row, newCell);
					getCell(col,row).changeColor(this.getParametersController().getColor(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getShape());
					centerY += height; 
				}
				centerX += width;
			}
		}
	}

	/**
	 * The method to update the status of a cell when it is clicked.
	 * @param cell
	 */
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
	 * The method to create a new slider for the right side of the UI Screen.
	 * @param min: the minimal value on the slider
	 * @param max: the maximal value on the slider
	 * @param current: the default value on the slider
	 * @param increment: the incremental step of the slider
	 * @param showTick: if the slider shows ticks
	 * @return Slider
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
	 * The method to update the parameters displayed at the top of the UI Screen
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
	 * The method to change the speed of simulation by having a new Timeline when the slider is manipulated.
	 * @param nVal: new value of the speed slider
	 */
	private void updateSimulationSpeedOnSliderDrag(int nVal) {
		this.getParametersController().setSpeed(nVal);
		this.getCellSociety().setDelay(nVal);
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().setupGameLoop();
		this.updateTextInfo();
	}

	/**
	 * The method to calculate status for a give cell based on fixed input.
	 * Overrides the super class method.
	 */
	@Override
	protected int getCellStatus(int col, int row) {
		return this.getParametersController().getStatusDistribution(col, row);
	}
	
}