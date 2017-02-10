package page;
import javafx.scene.paint.Color;
import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the spreading of fire simulation.
 * @author Harry Liu, Yilin Gao
 *
 */
public class PageSpreadingOfFire extends GamePage {
	
	public PageSpreadingOfFire(CellSociety cs, Parameters p) {
		super(cs, p);
		this.getParametersController().addColor(0, Color.YELLOW);
		this.getParametersController().addColor(1, Color.GREEN);
		this.getParametersController().addColor(2, Color.RED);
	}
	
	// use specific setup for each status
	@Override
	protected int getCellStatus(int col, int row){
		return this.getParametersController().getStatusDistribution(col, row);
	}
	
	@Override
	protected void setupComponents() {
		this.getOptions().add("Input");
		super.setupComponents();
		// can add other choices of layouts
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		// can add other grid layouts
	}
	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() 
				+ getMyResources().getString("ProbabilityParameter") 
				+ this.getParametersController().getProb() + " | ";
		this.getInfoText().setText(myText);
	}	
}