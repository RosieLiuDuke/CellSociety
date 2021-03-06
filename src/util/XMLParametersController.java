package util;

import javafx.scene.paint.Color;
import page.Parameters;

/**
 * The class to insert values from XML files into the Parameters class.
 * The class throws exceptions for wrong inputs.
 * @author Yilin Gao
 *
 */
public class XMLParametersController {
	
	Parameters parametersController;

	/**
	 * Constructor of the XMLParametersController class.
	 * @param pc: Parameters instance
	 */
	public XMLParametersController(Parameters pc) {
		parametersController = pc;
	}
	
	/**
	 * Set type of the simulation.
	 * @param type
	 */
	public void setType(String type){
		if (type == null){
			throwException("Please input a simulation name!");
		}
		else {
			parametersController.setType(type);
		}
	}
	
	/**
	 * Set total number of columns.
	 * @param col
	 */
	public void setCol(int col){
		if (col <= 0){
			throwException("Please enter number of columns larger than 0!");
		}
		else{
			parametersController.setColNum(col);
		}
	}
	
	/**
	 * Set total number of rows.
	 * @param row
	 */
	public void setRow(int row){
		if (row <= 0){
			throwException("Please enter number of rows larger than 0!");
		}
		else{
			parametersController.setRowNum(row);
		}
	}
	
	/**
	 * Set total number of status.
	 * @param total
	 */
	public void setTotal(int total){
		if (total <= 0){
			throwException("Please enter number of status larger than 0!");
		}
		else{
			parametersController.setNumberOfStatus(total);
		}
	}
	
	/**
	 * Set default status.
	 * @param status
	 */
	public void setDefaultStatus(int status){
		if (status < 0){
			throwException("Please enter a non-negative default status!");
		}
		else{
			parametersController.setDefaultStatus(status);
		}
	}
	
	public void setSpeed(double speed){
		if (speed < 0){
			throwException("Please enter a non-negative simulation speed!");
		}
		else{
			parametersController.setSpeed(speed);
		}
	}
	
	/**
	 * Set probability.
	 * @param prob
	 */
	public void setProbability(double prob){
		if (prob < 0 || prob > 1){
			throwException("Probability must be between [0,1]!");
		}
		else{
			parametersController.setProb(prob);
		}
	}
	
	/**
	 * Set satisfaction level.
	 * @param sat
	 */
	public void setSatisfaction(double sat){
		if (sat < 0 || sat > 1){
			throwException("Satisfaction level must be between [0,1]!");
		}
		else{
			parametersController.setSatisfaction(sat);
		}
	}
	
	/**
	 * Set status for a given cell.
	 * @param col
	 * @param row
	 * @param state
	 */
	public void setCellStatus(int col, int row, int state){
		if (state >= 0){
			if (col >= 0 && col < parametersController.getCol()){
				if (row >= 0 && row < parametersController.getRow()){
					parametersController.setCellStatus(col, row, state);
				}
				else{
					throwException("The row index of a given cell is out of boundary!");
				}
			}
			else{
				throwException("The column index of a given cell is out of boundary!");
			}
		}
		else {
			throwException("The state of a given cell must not be negative!");
		}
	}
	
	/**
	 * Set the percentage of cells of each status.
	 * @param state
	 * @param percentage
	 */
	public void setStatusPercentage(int state, double percentage){
		if (state >= 0){
			if (percentage >= 0 && percentage <= 1){
				parametersController.setStatusPercentage(state, percentage);
			}
			else{
				throwException("The probability of a statue must be [0,1]!");
			}
		}
		else {
			throwException("The state of a given cell must not be negative!");
		}
	}
	
	/**
	 * Set the turnover rate of each item.
	 * @param state
	 * @param turnover
	 */
	public void setSeaItemTurnover(int state, double turnover){
		if (state >= 0){
			if (turnover >= 0){
				parametersController.addSeaItem(state, turnover);
			}
			else{
				throwException("The reproduction rate of a sea item must not be negative!");
			}
		}
		else {
			throwException("The state of a given cell must not be negative!");
		}
	}
	
	/**
	 * Set the status and color map.
	 * @param state
	 * @param color
	 */
	public void setStateColor(int state, String color){
		parametersController.addColor(state, Color.valueOf(color));
	}
	
	/**
	 * Set the choice of shape in grids.
	 * @param shape
	 */
	public void setShape (String shape) {
		parametersController.setCellShape(shape);
	}
	
	/**
	 * Set the boolean variable if the grid is visible.
	 * @param is
	 */
	public void setGridVisible (boolean is){
		parametersController.setGridVisible(is);
	}
	
	/**
	 * Set the differentiate levels of slimers.
	 * @param s
	 * @param level
	 */
	public void setSlimeLevel(String s, double level){
		if (level >= 0){
			parametersController.addSlimeLevel(s, level);
		}
		else{
			throwException("The differentiate level of Slimers must not be negative!");
		}
	}
	
	private void throwException (String message) {
		try {
			throw new Exception (message);
		} catch (Exception e) {
			DisplayAlert.displayAlert(e.getMessage());
		}
	}
}
