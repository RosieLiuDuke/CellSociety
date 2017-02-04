import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public abstract class GamePage extends Page {

	private Cell[][] cells;
	private int rowNum;
	private int colNum;
	private double size;
	
	private double speed;	
	private int currentStep;
	
	
	public GamePage (CellSociety c) {
		super(c);
	}
	
	
	public int getRow(){
		return rowNum - 2;
	}
	
	public int getCol(){
		return colNum - 2;
	}
	
	public Cell getCell(int i, int j){
		return cells[i+1][j+1];
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
	
	protected abstract void setoutLayout(String newValue);
	
	protected void handleMouseReleasedBack(ActionEvent event) {
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().loadPage("Welcome");
	}

	protected void handleMouseReleasedStep(ActionEvent event) {
		this.getCellSociety().setIsStep(true);
	}

	protected void handleMouseReleasedStart(ActionEvent event) {
		this.getCellSociety().setIsStep(false);
	}
	
	protected void setRowNum (int r) {
		rowNum = r;
	}
	protected void setColNum (int c) {
		colNum = c;
	}
	protected void setSize (double s) {
		size = s;
	}
	protected void setSpeed (double s) {
		speed = s;
	}
	protected void setCell(int i, int j, Cell c) {
		cells[i][j] = c;
	}
	protected void setCurrentStep(int cs) {
		currentStep = cs;
	}
	protected void setCells(int i, int j) {
		cells = new Cell[i][j];
	}
	
}
