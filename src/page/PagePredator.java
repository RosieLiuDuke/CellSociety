package page;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 * @author Yilin Gao
 */

public class PagePredator extends UIsetupWithPercentage {
	
	/**
	 * Constructor of the page of wator simulation
	 * @param cs: the CellSociety instance
	 * @param language: a string representing user choice of language
	 * @param p: a Parameters instant from the calling class
	 */
	public PagePredator(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	/**
	 * The method to set up required components in the scene.
	 * For predator, an additional slider to adjust reproduction is needed.
	 */
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
	
	/**
	 * The method to set up the grid layout in the scene.
	 * The method calls the super class method as its whole function.
	 * If more coded layouts are expected, they can be placed in this method in the future.
	 * @param newValue
	 */
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);		
		// can add other grid layouts
	}
	
	/**
	 * The method to update the parameters displayed at the top of the UI Screen.
	 * For wator, the reproduction level needs to be added.
	 */
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