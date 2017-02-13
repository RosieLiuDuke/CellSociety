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
	private Group grid;
	private Map<Indices, Cell> cells;
	private int currentStep;
	private Button back;
	private Button start;
	private Button stop;
	private Button step;	
	private Boolean simulationSelected = false;
	private BarChart<Number, String> populationChart;
	private NumberAxis xAxis;
	private CategoryAxis yAxis;
	private List<Color> colorKey;
	private Map<Color, Integer> quantityMap;

	public GamePage (CellSociety cs, String language, Parameters p) {
		super(cs, language);
		this.setParametersController(p);
		grid = new Group();
		xAxis = new NumberAxis();
		yAxis = new CategoryAxis();
		populationChart = new BarChart<Number,String>(xAxis,yAxis);
		cells = new HashMap<Indices, Cell>();
		back = createButton(getMyResources().getString("BackCommand"), event-> backButton(event));
		start = createButton(getMyResources().getString("StartCommand"), event-> startButton(event));
		stop = createButton(getMyResources().getString("StopCommand"), event-> stopButton(event));
		step = createButton(getMyResources().getString("StepCommand"), event-> stepButton(event));
		quantityMap = new HashMap<Color, Integer>();

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
	
	protected void setSimulationSelected(boolean value){
		simulationSelected = value;
	}
	
	public BarChart<Number, String> getChart(){
		return populationChart;
	}

	public abstract void updateTextInfo();

	/**
	 * Creates Map which tracks the color quantities 
	 * @param newValue
	 */
	public void quantityMap () {
		int rowCell, colCell;
		for (rowCell = 0; rowCell < this.getParametersController().getRow(); rowCell++) {
			for (colCell = 0; colCell < this.getParametersController().getCol(); colCell++) {
				Color color = this.getParametersController().getColor(getCell(rowCell,colCell).getStatus());
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
	 */
	public void updateColorandData () {
		int i, j;
		for (i = 0; i < this.getParametersController().getRow(); i++) {
			for (j = 0; j < this.getParametersController().getCol(); j++) {
				Color color = this.getParametersController().getColor(getCell(i,j).getStatus());
				getCell(i,j).changeColor(this.getParametersController().getColor(getCell(i,j).getStatus()));
				this.quantityMap.put(color, this.quantityMap.get(color)+1);	
			}	
		}
		updateChartDisplay();
		quantityMap.replaceAll((k,v) -> 0);;
	}

	/**
	 * Create new BarChart and add in initial data points based off color map
	 */
	public void createPopulationChart(){
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

	/**
	 * Update graph as quantity map changes over time to reflect accurate count of colors
	 */
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