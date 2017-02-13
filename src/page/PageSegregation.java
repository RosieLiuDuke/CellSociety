package page;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 */

public class PageSegregation extends UIsetupWithPercentage {
	
	
	public PageSegregation(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	protected void setupComponents(){
		super.setupComponents();
		// add a special slider to adjust satisfaction
		Slider satisfactionAdjustor = createSlider(0, 1, this.getParametersController().getSatisfaction(), 1, true);
		satisfactionAdjustor.valueProperty().addListener((obs,oVal,nVal) -> updateSatisfaction(nVal.doubleValue()));
		this.getSliderBox().getChildren().addAll(new Text(getMyResources().getString("SatisfactionAdjustor")), satisfactionAdjustor);
		// add sliders to adjust percentage
		addPercentageSlider(this.getParametersController().getStatusPercentageMap());
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
		this.getInfo().setText(myText);
	}	
}