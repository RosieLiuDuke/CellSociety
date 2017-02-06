package page;
import javafx.scene.paint.Color;
import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the spreading of fire simulation.
 * @author Harry Liu, Yilin Gao
 *
 */
public class PageSpreadingOfFire extends GamePage {
	
	public PageSpreadingOfFire(CellSociety cs) {
		super(cs);
		getColorMap().put(0, Color.YELLOW);
		getColorMap().put(1, Color.GREEN);
		getColorMap().put(2, Color.RED);
	}
	
	@Override
	protected void setupComponents() {
		this.getOptions().add("Input");
		super.setupComponents();
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		
		// can add other grid layouts
	}
	
	@Override
	public void updateTextInfo() {
		String text = "Simulation name: " + this.getCellSociety().getCurrentType() 
				+ "\nNumber of rows: " + getRow() + " | " 
				+ "Number of columns: " + getCol() + " | "  
				+ "Cell size: " + getSize() + " | "
				+ "Step speed: " + getSpeed() + " | " 
				+ "Probability: " + getProb() + " | " 
				+ "\nStep: " + getCurrentStep();
		this.getParameters().setText(text);
	}
}