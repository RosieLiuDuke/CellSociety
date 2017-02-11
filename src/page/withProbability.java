package page;

import java.util.Map;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
* This is a class which contains methods adding in probability values for pages that have probability
* @author Harry Liu
**/

public class withProbability extends UIsetup {

	public withProbability(CellSociety cs, String language) {
		super(cs, language);
	}

	/**
	* Adds a slider for each probability value. Adds sliders to VBox on the UIsetup page
	* @param percentage
	**/
	public void addProbability(Map<Integer, Double> percentage){
		getSliderBox().getChildren().add(new Text(getMyResources().getString("ProbabilityParameter")));
		for (int size = 0; size<percentage.size(); size++){
			int index = size;
			Slider prob = createSlider(0, 1, 0.25, true);
			prob.setValue(getPercentage(index));
			prob.valueProperty().addListener((obs,oVal,nVal) -> changeParameter(index, nVal.doubleValue()));
			getSliderBox().getChildren().add(prob);
		}
	}
	
	/**
	* Adjust percentage values when sliders are manipulated.
	* @param index
	* @param percent
	**/
	private void changeParameter(int index, double percent){
		setPercentage(index, percent);
		this.getCellSociety().setDelay(percent);
		this.getCellSociety().stopGameLoop();
	}
	
}
