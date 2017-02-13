package page;
import java.util.Map;
import java.util.Random;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * Abstract sub class of UIsetup for simulations with percentage.
 * 
 * @author Harry Liu
 * @author Yilin Gao
 */
public abstract class UIsetupWithPercentage extends UIsetup {
	
	/**
	 * Constructor of the abstract class.
	 * @param cs
	 * @param language
	 * @param p
	 */
	public UIsetupWithPercentage(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	/**
	 * The method to add sliders to adjust percentages in the simulation.
	 * @param percentage: a Map between each status and its percentage.
	 */
	public void addPercentageSlider(Map<Integer, Double> percentage){
		getSliderBox().getChildren().add(new Text(getMyResources().getString("PercentageAdjustor")));
		for (int size = 0; size<this.getParametersController().getNumberOfStatus(); size++){
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

	/**
	 * The method to calculate status for a give cell based on percentage.
	 * Overrides the super class method.
	 */
	@Override
	protected int getCellStatus(int col, int row){
		int status = 0;
		Random rn = new Random();
		double indicator = rn.nextDouble();
		int numberOfStates = this.getParametersController().getNumberOfStatus();
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
	
	/**
	 * The method to add percentage information for the parameter display text.
	 * Overrides the super class method.
	 */
	@Override
	public void updateTextInfo(){
		super.updateTextInfo();
		String myText = getText();
		for (int i = 0; i < this.getParametersController().getNumberOfStatus(); i++){
			myText += getMyResources().getString("PercentageParameter") 
					+ i + ": " + this.getParametersController().getStatusPercentage(i) + "\n";
		}
		this.getInfo().setText(myText);
	}
}