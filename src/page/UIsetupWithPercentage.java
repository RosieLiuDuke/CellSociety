package page;
import java.util.Map;
import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class UIsetupWithPercentage extends UIsetup {
	
	public UIsetupWithPercentage(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	public void addPercentageSlider(Map<Integer, Double> percentage){
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
	
}