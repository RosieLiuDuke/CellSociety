package page;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cell.Cell;
import cell.Indices;
import cellSociety.CellSociety;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * The abstract subclass of Page, and super class of all specific pages for each simulation.
 * @author Joshua Kopen, Yilin Gao, Harry Liu
 *
 */
public abstract class GamePage extends Page {
	protected static final int gridWidth = 300;
	protected static final int gridHeight = 300;
	private Group grid;
	private Map<Indices, Cell> cells;
	private Map<Indices, Integer> cellsStatus;
	private Map <Integer, Color> colorMap;	
	private Map<Color, Integer> quantityMap;
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
	private BarChart<Number, String> populationChart;
	private NumberAxis xAxis;
	private CategoryAxis yAxis;
	private List<Color> colorKey;
	private Boolean chartCreated = false;

	public GamePage (CellSociety cs, String language) {
		super(cs, language);
		grid = new Group();
		xAxis = new NumberAxis();
		yAxis = new CategoryAxis();
		populationChart = new BarChart<Number,String>(xAxis,yAxis);
		cells = new HashMap<Indices, Cell>();
		cellsStatus = new HashMap<Indices, Integer>();
		back = createButton(getMyResources().getString("BackCommand"), event-> backButton(event));
		start = createButton(getMyResources().getString("StartCommand"), event-> startButton(event));
		stop = createButton(getMyResources().getString("StopCommand"), event-> stopButton(event));
		step = createButton(getMyResources().getString("StepCommand"), event-> stepButton(event));

		colorMap = new HashMap<Integer, Color>();
		quantityMap = new HashMap<Color, Integer>();
	}

	public Group getGrid(){
		return grid;
	}

	public BarChart<Number, String> getChart(){
		return populationChart;
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

	public Map<Integer, Color> getColorMap(){
		return colorMap;
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
	
	public abstract void addinGrid(String newValue);
	
	public abstract void updateTextInfo();

	/**
	 * Sets up the grid layout in the scene.
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
		addinGrid(newValue);
		quantityMap();
		if (!chartCreated){
			createChart();
		}
	}

	/**
	 * Creates Map which tracks the color quantities 
	 * @param newValue
	 */
	public void quantityMap () {
		int rowCell, colCell;
		for (rowCell = 0; rowCell < getRow(); rowCell++) {
			for (colCell = 0; colCell < getCol(); colCell++) {
				Color color = this.getColorMap().get(getCell(rowCell,colCell).getStatus());
				if (!quantityMap.containsKey(color)){
					quantityMap.put(color, 1);
				}
				else{
					quantityMap.put(color, quantityMap.get(color)+1);	
				}	
			}
		}
	}

	/**
	 * Updates the color and the display of the Bar Graph on each step.
	 * Abstract.
	 */
	public void updateColorandData () {
		int i, j;
		for (i = 0; i < getRow(); i++) {
			for (j = 0; j < getCol(); j++) {
				Color color = this.getColorMap().get(getCell(i,j).getStatus());
				getCell(i,j).changeColor(color);
				this.quantityMap.put(color, this.quantityMap.get(color)+1);	
			}	
		}
		updateChartDisplay();
		quantityMap.replaceAll((k,v) -> 0);;
	}

	public void createChart(){
		chartCreated=true;
		xAxis.setLabel("Quantity"); 
		yAxis.setLabel("Colors");

		XYChart.Series<Number, String> populationSeries = new Series<Number, String>();

		colorKey = new ArrayList<Color>(quantityMap.keySet());

		for (int x = 0; x<quantityMap.keySet().size(); x++){
			String color = colorKey.get(x).toString();
			Number quantity = quantityMap.get(colorKey.get(x));
			populationSeries.getData().add(new Data<Number, String>(quantity, color));
		}
		populationChart.getData().add(populationSeries);
		populationChart.setLegendVisible(false);
	}

	public void updateChartDisplay(){		
		for (Series<Number, String> series : populationChart.getData()) {
			for (int x = 0; x<series.getData().size(); x++) {
				XYChart.Data<Number, String> data = series.getData().get(x);
				Node node = data.getNode();
				node.setStyle("-fx-bar-fill:" + "#" + colorKey.get(x).toString().substring(2));
				data.setXValue(quantityMap.get(colorKey.get(x)));
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