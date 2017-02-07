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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
/**
 * The abstract subclass of Page, and super class of all specific pages for each simulation.
 * @author Joshua Kopen, Yilin Gao, Harry Liu
 *
 */
public class GamePage extends Page {
	protected static final int gridWidth = 300;
	protected static final int gridHeight = 300;
	private Group grid;
	private Map<Indices, Cell> cells;
	private Map<Indices, Integer> cellsStatus;
	private Map <Integer, Color> colorMap;	
	private int colNum;
	private int rowNum;
	private double speed;	
	private int currentStep;
	private int defaultStatus;
	private Boolean simulationSelected = false;
	private Button back;
	private Button start;
	private Button stop;
	private Button step;
	private Text parameters;
	private List<String> myOptions;
	private ComboBox<String> layoutChoice;
	private Slider speedChoice;
	private String text;
	
	public GamePage (CellSociety cs) {
		super(cs);
		grid = new Group();
		cells = new HashMap<Indices, Cell>();
		cellsStatus = new HashMap<Indices, Integer>();
		back = createButton(getMyResources().getString("BackCommand"), event-> backButton(event));
		start = createButton(getMyResources().getString("StartCommand"), event-> startButton(event));
		stop = createButton(getMyResources().getString("StopCommand"), event-> stopButton(event));
		step = createButton(getMyResources().getString("StepCommand"), event-> stepButton(event));
		colorMap = new HashMap<Integer, Color>();
		parameters = new Text();
		myOptions = new ArrayList<String>();
	}
	
	public Group getGrid(){
		return grid;
	}
	
	public int getCol(){
		return colNum;
	}
	
	public int getRow(){
		return rowNum;
	}
	
	public Cell getCell(int col, int row){
		return cells.get(new Indices(col, row));
	}
	
	protected int getCellStatus(int col, int row){
		if (cellsStatus.containsKey(new Indices(col, row))){
			return cellsStatus.get(new Indices(col, row));
		}
		else{
			return defaultStatus;
		}
	}
	
	protected int getDefaultStatus(){
		return defaultStatus;
	}
	
	protected double getSpeed () {
		return speed;
	}
	
	public int getCurrentStep () {
		return currentStep;
	}
	
	public double getProb(){
		return 0;
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
	
	public Map<Integer, Color> getColorMap(){
		return colorMap;
	}
	
	public List<String> getOptions(){
		return myOptions;
	}
	
	public Text getParameters(){
		return parameters;
	}
	
	public void setColNum (int c) {
		colNum = c;
	}
	
	public void setRowNum (int r) {
		rowNum = r;
	}
	
	public void setDefaultStatus(int s){
		defaultStatus = s;
	}
	
	public void setSpeed (double s) {
		speed = s;
	}
	
	protected void setCell(int col, int row, Cell c) {
		Indices newKey = new Indices(col, row);
		cells.put(newKey, c);
	}
	
	public void setCellStatus(int col, int row, int state){
		Indices newKey = new Indices(col, row);
		cellsStatus.put(newKey, state);
	}
	
	public void setCurrentStep(int cs) {
		currentStep = cs;
	}
	
	public void setProb(double p){
//		prob = p;
	}
	
	public double getSatisfaction(){
		return 0;
	}
	
	public double getPercentage(int state){
		return 0;
	}
	
	public void setSatisfaction(double value){
	}
	
	public void setPercentage(int type, double value){
	}
	
	public String getItemName(int state) {
		return null;
	}

	public int getItemState(String name) {
		return 0;
	}

	public double getItemTurnover(int state) {
		return 0;
	}

	public double getItemTurnOver(String name) {
		return 0;
	}

	public void inputSeaItem(int state, String name, double turnover) {
		
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
		parameters = new Text();
		parameters.setId("parameters");
		parameters.setWrappingWidth(getWidth());
		parameters.setTextAlignment(TextAlignment.CENTER);
		
		parametersBox.getChildren().addAll(parameters, layoutChoice);
		parametersBox.setAlignment(Pos.CENTER);
		
		speedChoice = new Slider(1, 5, getSpeed());
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
		
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
	}
	
	/**
	 * When the slider is updated, change the speed of simulation by having a new timeline.
	 * @param nVal
	 */
	private void updateSpeed(int nVal) {
		setSpeed(nVal);
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
		double width = gridWidth / getCol();
		double height = gridHeight / getCol();

		if (newValue.equals("Input")){
			for (int col = 0; col < getCol(); col ++){  // x position - col
				for (int row = 0; row < getRow(); row++){  // y position - row
					double xPosition = col * width;
					double yPosition = row * height;
					setCell(col,row, new Cell(xPosition, yPosition, width, height, getCellStatus(col, row)));
					getCell(col,row).changeColor(this.getColorMap().get(getCell(col,row).getStatus()));
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
		text = getMyResources().getString("TitleParameter") + this.getCellSociety().getCurrentType() 
				+ "\n\n" + getMyResources().getString("RowParameter") + getRow() + " | " 
				+ getMyResources().getString("ColParameter") + getCol() + " | "
				+ getMyResources().getString("GridWidthParameter") + gridWidth + " | "
				+ getMyResources().getString("GridHeightParameter") + gridHeight + " | "
				+ getMyResources().getString("StepParameter") + getSpeed() + " | " 
				+ getMyResources().getString("CurrentStepParameter") + getCurrentStep()+ " | ";
		this.getParameters().setText(text);
	}
	
	/**
	 * The method to update the color on each step.
	 * Abstract.
	 */
	public void updateColor () {
		int i, j;
		for (i = 0; i < getRow(); i++) {
			for (j = 0; j < getCol(); j++) {
				getCell(i,j).changeColor(this.getColorMap().get(getCell(i,j).getStatus()));
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
			Alert alert = new Alert(AlertType.ERROR, getMyResources().getString("SelectCommand"));
			alert.showAndWait();
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
			Alert alert = new Alert(AlertType.ERROR, getMyResources().getString("SelectCommand"));
			alert.showAndWait();
		}
	}
	
}