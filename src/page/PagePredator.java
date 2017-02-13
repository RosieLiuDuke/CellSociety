package page;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * 
 */

public class PagePredator extends UIsetupWithPercentage {
	
	public PagePredator(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	@Override
	protected void setupComponents(){
		super.setupComponents();
		// add special sliders to adjust reproduction rate
		getSliderBox().getChildren().add(new Text(getMyResources().getString("ReproductionAdjustor")));
		for (int size = 1; size<this.getParametersController().getNumberOfStatus(); size++){
			int index = size;
			Slider rep = createSlider(1, 5, this.getParametersController().getItemTurnover(size), 1, true);
			rep.valueProperty().addListener((obs,oVal,nVal) -> updateReproduction(index, nVal.doubleValue()));
			getSliderBox().getChildren().add(rep);
		}
		// add sliders to adjust percentage
		addPercentageSlider(this.getParametersController().getStatusPercentageMap());
	}
	
	private void updateReproduction(int index, double value) {
		value = Math.round(value * 100);
		value /= 100;
		this.getCellSociety().stopGameLoop();
		this.getParametersController().updateReproductionRate(index, value);
	}
	
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);		
		// can add other grid layouts
	}
	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText();
		for (int i = 1; i < this.getParametersController().getNumberOfStatus(); i++){
			 myText += getMyResources().getString("ReproductionParameter")
					+ i + ": " + this.getParametersController().getItemTurnover(i) + "\n";
		}
		this.getInfo().setText(myText);
	}	
}