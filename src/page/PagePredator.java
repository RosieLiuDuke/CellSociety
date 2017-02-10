package page;

import java.util.Random;

import cellSociety.CellSociety;
import javafx.scene.paint.Color;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 */

public class PagePredator extends GamePage {
		
	public PagePredator(CellSociety cs, Parameters p) {
		super(cs,p);
		this.getParametersController().addColor(0, Color.BLUE);
		this.getParametersController().addColor(1, Color.CORAL);
		this.getParametersController().addColor(2, Color.CHARTREUSE);
	}
	
	@Override
	protected void setupComponents(){
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
	protected int getCellStatus(int col, int row){
		int status = 0;
		Random rn = new Random();
		double indicator = rn.nextDouble();
		int numberOfStates = this.getParametersController().getNumberOfStates();
		double prevStateProb = 0, nextStateProb = 0;
		for (int i = 0; i < numberOfStates; i++){
			nextStateProb += this.getParametersController().getStatusPercentage(i);
			if (indicator >= prevStateProb && indicator < nextStateProb){
				status = i;
				break;
			}
			prevStateProb += this.getParametersController().getStatusPercentage(i);
		}
		return status;
	}
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText();
		for (int i = 0; i < this.getParametersController().getNumberOfStates(); i++){
			myText += getMyResources().getString("PercentageParameter") 
		    		+ i + ": " + this.getParametersController().getStatusPercentage(i) + " | ";
		}
		this.getInfoText().setText(myText);

	}	
}