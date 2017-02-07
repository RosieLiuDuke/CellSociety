package page;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 */

public class PagePredator extends GamePage {
	
	public PagePredator(CellSociety cs) {
		super(cs);
		this.getColorMap().clear();
		this.getColorMap().put(0, Color.YELLOW);
		this.getColorMap().put(1, Color.BLUE);
		this.getColorMap().put(2, Color.GREEN);
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
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() + "FillerText";
		this.getParameters().setText(myText);
	}	
}