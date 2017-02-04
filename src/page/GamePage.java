package page;
import java.util.HashMap;
import java.util.Map;

import cell.Cell;
import cellSociety.CellSociety;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * The abstract subclass of Page, and super class of all specific pages for each simulation.
 * @author Joshua Kopen, Yilin Gao
 *
 */
public abstract class GamePage extends Page {

	private Group grid;
	private Cell[][] cells;
	private int rowNum;
	private int colNum;
	private double size;
	private double speed;	
	private int currentStep;	// TODO where it is used
	private Button back;
	private Button start;
	private Button stop;
	private Button step;
	private Map <Integer, Color> colorMap;	
	
	public GamePage (CellSociety cs) {
		super(cs);
		grid = new Group();
		back = createButton("BACK", event-> handleMouseReleasedBack(event));
		start = createButton("START", event-> handleMouseReleasedStart(event));
		stop = createButton("STOP", event-> handleMouseReleasedStop(event));
		step = createButton("STEP", event-> handleMouseReleasedStep(event));
		colorMap = new HashMap<Integer, Color>();
	}

	public Group getGrid(){
		return grid;
	}
	
	public int getRow(){
		return rowNum;
	}
	
	public int getCol(){
		return colNum;
	}
	
	public Cell getCell(int i, int j){
		return cells[i][j];
	}
	
	protected double getSize () {
		return size;
	}
	
	protected double getSpeed () {
		return speed;
	}
	
	protected int getCurrentStep () {
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

	public Map<Integer, Color> getColorMap(){
		return colorMap;
	}
	
	public void setRowNum (int r) {
		rowNum = r;
	}
	
	public void setColNum (int c) {
		colNum = c;
	}
	
	public void setSize (double s) {
		size = s;
	}
	
	public void setSpeed (double s) {
		speed = s;
	}
	
	// TODO where it is used?
	protected void setCells(int i, int j) {
		cells = new Cell[i][j];
	}
	
	protected void setCell(int i, int j, Cell c) {
		cells[i][j] = c;
	}
	
	protected void setCurrentStep(int cs) {
		currentStep = cs;
	}
	
	/**
	 * The method to set up required components in the scene.
	 * Abstract.
	 */
	protected abstract void setupComponents();
	
	/**
	 * The method to set up the grid layout in the scene.
	 * Abstract.
	 * @param newValue
	 */
	protected abstract void setupGrid(String newValue);
	
	/**
	 * The handler of the "BACK" button.
	 * When the button is pressed, the game will return to the splash screen.
	 * @param event
	 */
	protected void handleMouseReleasedBack(ActionEvent event) {
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().loadPage("Welcome");
	}

	/**
	 * The handler of the "START" button.
	 * When the button is pressed, the simulation will run consecutively.
	 * @param event
	 */
	protected void handleMouseReleasedStart(ActionEvent event) {
		this.getCellSociety().setIsStep(false);
		this.getCellSociety().beginGameLoop();
	}
	
	/**
	 * The handler of the "STOP" button.
	 * When the button is pressed, the simulation will stop. 
	 * Only if the "START" or the "STEP" button is pressed, the simulation will resume.
	 * @param event
	 */
	private void handleMouseReleasedStop(ActionEvent event) {
		this.getCellSociety().stopGameLoop();
	}
	
	/**
	 * The handler of the "STEP" button.
	 * When the button is pressed, the simulation will perform the next step.
	 * @param event
	 */
	protected void handleMouseReleasedStep(ActionEvent event) {
		this.getCellSociety().setIsStep(true);
		this.getCellSociety().setNextStep(true);
		this.getCellSociety().beginGameLoop();
	}	
	
}
