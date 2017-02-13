package cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
* This is the Cell Super class file which defines the structure of a generic cell
* @author Harry Liu, Josh Kopen, and Yilin Gao
**/

public abstract class Cell {

	private int status;

	public Cell(int status){
		this.status = status;
	}

	/**
	* Create and return the rectangle variable for the cell to be added to scene in class Page
	* @return rectangle
	**/
	public abstract Shape getShape();
	
	/**
	* Get status of the cell
	**/
	public int getStatus(){
		return this.status;
	}

	/**
	* Update the status of the cell
	* @param newStatus
	**/
	public void changeStatus(int newStatus) {
		this.status = newStatus;
	}
	
	public abstract void changeColor(Color c);
}
