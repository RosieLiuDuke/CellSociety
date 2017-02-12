package page;

import java.util.Random;
import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 */

public class PageSegregation extends withProbability {
	
	
	public PageSegregation(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
		this.getParametersController().addColor(0, Color.TRANSPARENT);
		this.getParametersController().addColor(1, Color.RED);
		this.getParametersController().addColor(2, Color.BLUE);
	}
	
	// use percentage distribution to generate status for each cell
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
	
	protected void setupComponents(){
		super.setupComponents();
		// add a special slider to adjust satisfaction
		Slider satisfactionAdjustor = createSlider(0, 1, this.getParametersController().getSatisfaction(), 1, true);
		satisfactionAdjustor.valueProperty().addListener((obs,oVal,nVal) -> updateSatisfaction(nVal.doubleValue()));
		this.getSliderBox().getChildren().addAll(new Text(getMyResources().getString("SatisfactionAdjustor")), satisfactionAdjustor);
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
	
	private void updatePercentage(int index, double value) {
		value = Math.round(value * 100);
		value /= 100;
		this.getParametersController().setStatusPercentage(index, value);
		this.setupGrid(this.getOptions().get(0));
	}

	private void updateSatisfaction(double value) {
		value = Math.round(value * 100);
		value /= 100;
		this.getParametersController().setSatisfaction(value);
		this.getCellSociety().stopGameLoop();
	}

	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		// can add other choices of layouts
	}

	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() 
				+ getMyResources().getString("SatisfactionParameter") 
				+ this.getParametersController().getSatisfaction() + "\n";
		for (int i = 0; i < this.getParametersController().getNumberOfStates(); i++){
			myText += getMyResources().getString("PercentageParameter") 
		    		+ i + ": " + this.getParametersController().getStatusPercentage(i) + "\n";
		}
		this.getInfo().setText(myText);
	}	
}