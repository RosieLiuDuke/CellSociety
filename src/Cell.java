import javafx.scene.shape.Rectangle;

/**
* This is the Cell Super class file which defines the structure of a generic cell
* @author Harry Liu, Josh Kopen, and Yilin Gao
**/

public abstract class Cell {
	
	private double xPosition;
	private double yPosition;
	private double size;
	private int status;
	
	private Rectangle rectangle;
	
	/**
	* Create a cell with xPosition, yPosition, and a status, which would be used to indicate color.
	* @param xPosition
	* @param yPosition
	* @param size
	* @param status
	**/
	public Cell (double xPosition, double yPosition, double size, int status){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.status = status;	
		this.size = size;
	}
	
	/**
	* Create and return the rectangle variable for the cell to be added to scene in class Page
	* @return rectangle
	**/
	public Rectangle getCell(){
		rectangle = new Rectangle(xPosition, yPosition, size, size);
		return rectangle;
	}
	
	/**
	* Get status of the cell
	**/
	public int getStatus(){
		return this.status;
	}
	
	/**
	* Update the status of the cell
	**/
	public abstract void changeStatus();
	
}