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
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	private Text info;
	private List<String> myOptions;
	private ComboBox<String> layoutChoice;
	private Slider speedChoice;
	private String text;
	
	public GamePage (CellSociety cs, Parameters p) {
		super(cs);
		this.setParametersController(p);
		grid = new Group();
		cells = new HashMap<Indices, Cell>();
		back = createButton(getMyResources().getString("BackCommand"), event-> backButton(event));
		start = createButton(getMyResources().getString("StartCommand"), event-> startButton(event));
		stop = createButton(getMyResources().getString("StopCommand"), event-> stopButton(event));
		step = createButton(getMyResources().getString("StepCommand"), event-> stepButton(event));
		info = new Text();
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
	
	public String getText(){
		return text;
	}

	public Text getInfoText(){
		return info;
	}
	
	public List<String> getOptions(){
		return myOptions;
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

	/**
	 * The method to set up required components in the scene.
	 * Abstract.
	 */
	protected void setupComponents(){
		VBox parametersBox = new VBox(15);
		ObservableList<String> options = FXCollections.observableArrayList(myOptions);
		layoutChoice = new ComboBox<String>(options);
		layoutChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
		layoutChoice.setTooltip(new Tooltip (getMyResources().getString("SelectCommand")));
		layoutChoice.setPromptText(getMyResources().getString("ChoicesCommand"));
		layoutChoice.setMaxWidth(getWidth()/2);
		info = new Text();
		info.setId("Parameters Information");
		info.setWrappingWidth(getWidth());
		info.setTextAlignment(TextAlignment.CENTER);
		
		parametersBox.getChildren().addAll(info, layoutChoice);
		parametersBox.setAlignment(Pos.CENTER);
		
		speedChoice = new Slider(1, 20, this.getParametersController().getSpeed());
		speedChoice.setShowTickLabels(true);
		speedChoice.setShowTickMarks(true);
		speedChoice.setMajorTickUnit(1);
		speedChoice.setBlockIncrement(1);
		speedChoice.setValue(3);
		speedChoice.valueProperty().addListener((obs,oVal,nVal) -> updateSpeed(nVal.intValue()));
		
		VBox controlBox = new VBox(15);
		controlBox.getChildren().addAll(speedChoice, addButtons());
		
		updateTextInfo();
		
		this.getRoot().setTop(parametersBox);
		this.getRoot().setCenter(this.getGrid());
		this.getRoot().setBottom(controlBox);
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		this.getCellSociety().setDelay(this.getParametersController().getSpeed());
		this.getCellSociety().setupGameLoop();
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
	 * The method to add Buttons to the bottom of the UI Screen
	 */
	private HBox addButtons(){
		HBox buttonBox = new HBox(5);
		buttonBox.getChildren().addAll(this.getBack(), this.getStart(), this.getStop(), this.getStep());
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;		
	}
	
	/**
	 * The method that updates the parameters displayed at the top of the UI Screen
	 */
	public void updateTextInfo() {
		text = getMyResources().getString("TitleParameter") + this.getParametersController().getType() 
				+ "\n\n" + getMyResources().getString("RowParameter") + this.getParametersController().getRow() + " | " 
				+ getMyResources().getString("ColParameter") + this.getParametersController().getCol() + " | "
				+ getMyResources().getString("GridWidthParameter") + Parameters.gridWidth + " | "
				+ getMyResources().getString("GridHeightParameter") + Parameters.gridHeight + " | "
				+ getMyResources().getString("StepParameter") + this.getParametersController().getSpeed() + " | " 
				+ getMyResources().getString("CurrentStepParameter") + getCurrentStep()+ " | ";
		this.getInfoText().setText(text);
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
			createAlert("SelectCommand");
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
	private void stepButton(ActionEvent event) {
		if (simulationSelected){
			this.getCellSociety().setIsStep(true);
			this.getCellSociety().setNextStep(true);
			this.getCellSociety().beginGameLoop();
		}
		else{
			createAlert("SelectCommand");
		}
	}
	
}