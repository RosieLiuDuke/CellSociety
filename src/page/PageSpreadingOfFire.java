package page;
import javafx.scene.paint.Color;
import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the spreading of fire simulation.
 * @author Harry Liu, Yilin Gao
 *
 */
public class PageSpreadingOfFire extends UIsetup {
	
	private double probCatch; 
	
	@Override
	public double getProb(){
		return probCatch;
	}
	
	@Override
	public void setProb(double p){
		probCatch = p;
	}
	
	public PageSpreadingOfFire(CellSociety cs) {
		super(cs);
		getColorMap().put(0, Color.YELLOW);
		getColorMap().put(1, Color.GREEN);
		getColorMap().put(2, Color.RED);
	}
	
	@Override
	protected void setupComponents() {
		super.setupComponents();
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		// can add other grid layouts
	}
	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() + getMyResources().getString("ProbabilityParameter") + getProb() + " | ";
		this.getParameters().setText(myText);
	}	
}