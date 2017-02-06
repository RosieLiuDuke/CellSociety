package cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
* This is the Cell Super class file which defines the structure of a generic cell
* @author Harry Liu, Josh Kopen, and Yilin Gao
**/

public class Cell {

	private int status;
	private Rectangle rectangle;

	/**
	* Create a cell with xPosition, yPosition, and a status, which would be used to indicate color.
	* @param xPosition
	* @param yPosition
	* @param size
	* @param status
	**/
	public Cell (double xPosition, double yPosition, double width, double height, int status){
		this.status = status;
		rectangle = new Rectangle(xPosition, yPosition, width, height);
		rectangle.setStroke(Color.BLACK);
		// only for game of life
		//rectangle.setOnMouseReleased(e -> handleMouseRelease(e));
	}

//	private void handleMouseRelease(MouseEvent e) {
//		status *= -1;
//	}

	/**
	* Create and return the rectangle variable for the cell to be added to scene in class Page
	* @return rectangle
	**/
	public Rectangle getRectangle(){
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
	* @param newStatus
	**/
	public void changeStatus(int newStatus) {
		this.status = newStatus;
	}
	
	public void changeColor(Color c) {
		rectangle.setFill(c);
	}
}
