package page;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cell.Cell;
import cell.Indices;
import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
/**
 * The abstract subclass of Page, and super class of all specific pages for each simulation.
 * @author Joshua Kopen, Yilin Gao, Harry Liu
 *
 */
public class GamePage extends Page {
	private Group grid;
	private Map<Indices, Cell> cells;
	private int currentStep;
	private Boolean simulationSelected = false;
	private Button back;
	private Button start;
	private Button stop;
	private Button step;	
	private List<String> myOptions;
	private ComboBox<String> simulationChoice;
	private String text;
	private Text gameInfo;
	private Text gameTitle;
	private VBox slidersBox;
	private Slider speed;
	private VBox parametersBox;
	
	public GamePage (CellSociety cs, String language, Parameters p) {
		super(cs, language);
		this.setParametersController(p);
		grid = new Group();
		cells = new HashMap<Indices, Cell>();
		back = createButton(getMyResources().getString("BackCommand"), event-> backButton(event));
		start = createButton(getMyResources().getString("StartCommand"), event-> startButton(event));
		stop = createButton(getMyResources().getString("StopCommand"), event-> stopButton(event));
		step = createButton(getMyResources().getString("StepCommand"), event-> stepButton(event));
		myOptions = new ArrayList<String>();	
	}
	
	public Group getGrid(){
		return grid;
	}
	
	public Cell getCell(int col, int row){
		return cells.get(new Indices(col, row));
	}
	
	public int getCurrentStep () {
		return currentStep;
	}
	
	public Button getStart(){
		return start;
	}
	
	public Button getStop(){
		return stop;
	}
	
	public Button getStep(){
		return step;
	}
	
	public Button getBack(){
		return back;
	}
		
	protected int getCellStatus(int col, int row){
		return this.getParametersController().getDefaultStatus();
	}
	
	protected void addCell(int col, int row, Cell c) {
		Indices newKey = new Indices(col, row);
		cells.put(newKey, c);
	}
	
	public void setCurrentStep(int step){
		currentStep = step;
	}

	public List<String> getOptions(){
		return myOptions;
	}

	public Text getInfo(){
		return gameInfo;
	}

	public String getText(){
		return text;
	}
	
	public VBox getSliderBox(){
		return slidersBox;
	}
	
	/**
	 * The method to set up required components in the scene.
	 * Abstract.
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
		
		// updateSliders();

		parametersBox = new VBox(15);
		updateParameterBox();
		
		ScrollPane sp = new ScrollPane();
		sp.setContent(parametersBox);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setId("rightpanel");
		
		gameTitle = new Text(this.getCellSociety().getCurrentType());
		gameTitle.setId("gameTitle");
		
		VBox left = new VBox(10);
		left.getChildren().addAll(gameTitle,grid);
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
			simulationSelected = true;
		}
		this.getCellSociety().stopGameLoop();
		this.getGrid().getChildren().clear();
		this.setCurrentStep(0);
		updateTextInfo();
		double width = Parameters.gridWidth / this.getParametersController().getCol();
		double height = Parameters.gridHeight / this.getParametersController().getCol();
		if (newValue.equals("Input")){
			for (int col = 0; col < this.getParametersController().getCol(); col ++){  // x position - col
				for (int row = 0; row < this.getParametersController().getRow(); row++){  // y position - row
					double xPosition = col * width;
					double yPosition = row * height;
					int cellStatus = this.getCellStatus(col, row);
					addCell(col,row, new Cell(xPosition, yPosition, width, height, cellStatus));
					getCell(col,row).changeColor(this.getParametersController().getColor(getCell(col,row).getStatus()));
					this.getGrid().getChildren().add(getCell(col,row).getRectangle());
				}
			}
		}
	}

	/**
	 * The method to create a slider for the right side of the UI Screen
	 * @param min
	 * @param max
	 * @param increment
	 * @param showTick
	 */
	public Slider createSlider(double min, double max, double current, double increment, boolean showTick){
		Slider newSlider;
		newSlider = new Slider(min, max, current);
		newSlider.setShowTickLabels(showTick);
		newSlider.setShowTickMarks(showTick);
		newSlider.setMajorTickUnit(increment);
		newSlider.setBlockIncrement(increment);
		newSlider.setSnapToTicks(true);
		return newSlider;
	}
	
	public void updateSliders(){
		//Used if additional sliders are needed.
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
	 * The method that updates the parameters displayed at the top of the UI Screen
	 */
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
	 * When the slider is updated, change the speed of simulation by having a new timeline.
	 * @param nVal
	 */
	private void updateSpeed(int nVal) {
		this.getParametersController().setSpeed(nVal);
		this.getCellSociety().setDelay(nVal);
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().setupGameLoop();
		this.updateTextInfo();
	}
	
	/**
	 * The method to update the color on each step.
	 * Abstract.
	 */
	public void updateColor () {
		int i, j;
		for (i = 0; i < this.getParametersController().getRow(); i++) {
			for (j = 0; j < this.getParametersController().getCol(); j++) {
				getCell(i,j).changeColor(this.getParametersController().getColor(getCell(i,j).getStatus()));
			}
		}
	}
	
	/**
	 * The handler of the "BACK" button.
	 * When the button is pressed, the game will return to the splash screen.
	 * @param event
	 */
	private void backButton(ActionEvent event) {
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().loadPage("Welcome");
	}
	/**
	 * The handler of the "START" button.
	 * When the button is pressed, the simulation will run consecutively.
	 * @param event
	 */
	private void startButton(ActionEvent event) {
		if (simulationSelected){
			this.getCellSociety().setIsStep(false);
			this.getCellSociety().beginGameLoop();
		}
		else{
			displayAlert(getMyResources().getString("SelectCommand"));
		}
	}
	
	/**
	 * The handler of the "STOP" button.
	 * When the button is pressed, the simulation will stop. 
	 * Only if the "START" or the "STEP" button is pressed, the simulation will resume.
	 * @param event
	 */
	private void stopButton(ActionEvent event) {
		this.getCellSociety().stopGameLoop();
	}
	
	/**
	 * The handler of the "STEP" button.
	 * When the button is pressed, the simulation will perform the next step.
	 * @param event
	 */
	private void stepButton(ActionEvent event){
		if (!simulationSelected){
			displayAlert(getMyResources().getString("SelectCommand"));
		}	
		else{
			this.getCellSociety().setIsStep(true);
			this.getCellSociety().setNextStep(true);
			this.getCellSociety().beginGameLoop();
		}	
	}
	
}