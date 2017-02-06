package page;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 */

public class PageSegregation extends GamePage {
		
	public PageSegregation(CellSociety cs) {
		super(cs);
		this.getColorMap().put(0, Color.TRANSPARENT);
		this.getColorMap().put(1, Color.RED);
		this.getColorMap().put(2, Color.BLUE);
	}
	
	@Override
	protected void setupComponents(){
		this.getOptions().add("Input");
		super.setupComponents();
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		
	}
	
	@Override
	public void addTextInfo() {
		String myText = getText() + "Probability: " + getProb() + " | ";
		this.getParameters().setText(myText);
	}	
}