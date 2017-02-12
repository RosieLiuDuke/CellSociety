package page;

import java.util.Random;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * 
 */

public class PagePredator extends withProbability {
	
	public PagePredator(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
		this.getParametersController().addColor(0, Color.BLUE);
		this.getParametersController().addColor(1, Color.CORAL);
		this.getParametersController().addColor(2, Color.CHARTREUSE);
	}
	
	@Override
	protected void setupComponents(){
		super.setupComponents();
		// add special sliders to adjust reproduction rate
		getSliderBox().getChildren().add(new Text(getMyResources().getString("ReproductionAdjustor")));
		for (int size = 1; size<this.getParametersController().getNumberOfStates(); size++){
			int index = size;
			Slider rep = createSlider(1, 5, this.getParametersController().getItemTurnover(size), 1, true);
			rep.valueProperty().addListener((obs,oVal,nVal) -> updateReproduction(index, nVal.intValue()));
			getSliderBox().getChildren().add(rep);
		}
		// add special sliders to adjust percentage TODO
		getSliderBox().getChildren().add(new Text(getMyResources().getString("PercentageAdjustor")));
		for (int size = 0; size<this.getParametersController().getNumberOfStates(); size++){
			int index = size;
			Slider prob = createSlider(0, 1, this.getParametersController().getStatusPercentage(size), 0.25, true);
			prob.valueProperty().addListener((obs,oVal,nVal) -> updatePercentage(index, nVal.doubleValue()));
			getSliderBox().getChildren().add(prob);
		}
		updateParameterBox();
	}
	
	private void updateReproduction(int index, int value) {
		this.getCellSociety().stopGameLoop();
		this.getParametersController().updateReproductionRate(index, value);
	}

	private void updatePercentage(int index, double value) {
		value = Math.round(value * 100);
		value /= 100;
		this.getParametersController().setStatusPercentage(index, value);
		this.setupGrid(this.getOptions().get(0));
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
		for (int i = 1; i < this.getParametersController().getNumberOfStates(); i++){
			 myText += getMyResources().getString("ReproductionParameter")
					+ i + ": " + this.getParametersController().getItemTurnover(i) + "\n";
		}
		for (int i = 0; i < this.getParametersController().getNumberOfStates(); i++){
			myText += getMyResources().getString("PercentageParameter") 
		    		+ i + ": " + this.getParametersController().getStatusPercentage(i) + "\n";
		}
		this.getInfo().setText(myText);

	}	
}