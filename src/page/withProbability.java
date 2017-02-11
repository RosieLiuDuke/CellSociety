package page;

import java.util.Map;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class withProbability extends UIsetup {

	public withProbability(CellSociety cs, String language) {
		super(cs, language);
	}

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
	
	private void changeParameter(int index, double percent){
		setPercentage(index, percent);
		this.getCellSociety().setDelay(percent);
		this.getCellSociety().stopGameLoop();
	}
	
}
