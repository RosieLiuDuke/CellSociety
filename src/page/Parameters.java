package page;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cell.Indices;
import javafx.scene.paint.Color;

/**
 * The class to hold all parameters for all simulations.
 * @author Yilin Gao
 *
 */
public class Parameters {
	protected static final int gridWidth = 300;
	protected static final int gridHeight = 300;
	private String type;
	private int defaultStatus;
	private int numberOfStatus;
	// 1 default status, (n-1) other status
	private Map<Integer, Double> statusPercentage;  // store the percentage of cell of each status
	private Map<Indices, Integer> statusDistribution; // store specific locations and status of cells
	private Map <Integer, Color> colorMap;	
	private int colNum;
	private int rowNum;
	private double speed;
	private double probability; // Fire
	private double satisfaction;   // segregation 
	private Map<Integer, Double> seaItems; // reproduction rate of each item in predator simulation
	private String cellShape;
	private boolean gridVisible;
	private Map<String, Double> slimeDivisionLevels; // levels to indicate different levels in slimes
	
	/**
	 * Constructor of the Parameters class.
	 */
	public Parameters() {
		statusPercentage = new HashMap<Integer, Double>();
		statusDistribution = new HashMap<Indices, Integer>();
		colorMap = new HashMap<Integer, Color>();
		seaItems = new HashMap<Integer, Double>();
		slimeDivisionLevels = new HashMap<String, Double>();
	}
	
	public String getType(){
		return type;
	}

	public int getCol(){
		return colNum;
	}
	
	public int getRow(){
		return rowNum;
	}
	
	/**
	 * The method to get the status of a cell at given location.
	 * @param col: the index for column
	 * @param row: the index for row
	 * @return int: cell status
	 */
	protected int getStatusDistribution(int col, int row){
		if (statusDistribution.containsKey(new Indices(col, row))){
			return statusDistribution.get(new Indices(col, row));
		}
		else{
			return defaultStatus;
		}
	}

	/**
	 * The method to get the default status for the simulation
	 * @return int: default status
	 */
	protected int getDefaultStatus(){
		return defaultStatus;
	}
	
	protected double getSpeed(){
		return speed;
	}
	
	/**
	 * The method to get the total number of status
	 * @return int
	 */
	public int getNumberOfStatus(){
		return numberOfStatus;
	}
	
	/**
	 * The method to get the percentage of cells of a state
	 * @param state
	 * @return double: percentage of cells
	 */
	public double getStatusPercentage(int state){
		return statusPercentage.get(state);
	}
	
	/**
	 * The method to get the entire percentage map.
	 * @return Map<Integer, Double>
	 */
	public Map<Integer, Double> getStatusPercentageMap(){
		return statusPercentage;
	}
	
	public Color getColor(int state){
		return colorMap.get(state);
	}
	
	public Collection<Color> getColorSet(){
		return colorMap.values();
	}
	
	public int getColorStatus(Color color){
		for (int i: colorMap.keySet()){
			if (colorMap.get(i).equals(color)){
				return i;
			}
		}
		return 0;
	}
	
	public double getSatisfaction(){
		return satisfaction;
	}
	
	public String getCellShape(){
		return cellShape;
	}
	
	public void setCellShape(String cs){
		cellShape = cs;
	}
	
	public boolean isGridVisible(){
		return gridVisible;
	}
	
	public void setGridVisible(boolean v){
		gridVisible = v;
	}
	
	public void setType(String theType){
		type = theType;
	}
	
	public void setSatisfaction(double value){
		satisfaction = value;
	}
	
	public void setStatusPercentage(int state, double value){
		statusPercentage.put(state, value);
	}
	
	public void setColNum (int c) {
		colNum = c;
	}
	
	public void setRowNum (int r) {
		rowNum = r;
	}
	
	public void setNumberOfStatus(int t){
		numberOfStatus = t;
	}
	
	public void setDefaultStatus(int s){
		defaultStatus = s;
	}
	
	public void setCellStatus(int col, int row, int state){
		Indices newKey = new Indices(col, row);
		statusDistribution.put(newKey, state);
	}
	
	public void setSpeed (double s) {
		speed = s;
	}
	
	public double getProb(){
		return probability;
	}
	
	public void setProb(double p){
		probability = p;
	}
	
	public void addColor(int state, Color color){
		colorMap.put(state, color);
	}
	
	public double getItemTurnover(int state){
		for (int key: seaItems.keySet()){
			if (key == state)
				return seaItems.get(key);
		}
		return 0;
	}
	
	public void addSeaItem(int state, double turnover){
		seaItems.put(state, turnover);
	}
	
	public void updateReproductionRate(int state, double turnover){
		seaItems.replace(state, turnover);
	}
	
	public void addSlimeLevel(String s, double level){
		slimeDivisionLevels.put(s, level);
	}
	
	public double getSlimeLevel(String s){
		return slimeDivisionLevels.get(s);
	}
}
