package page;

import cellSociety.CellSociety;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * The subclass Page to hold the Scene for the Segregation simulation.
 * @author Harry Liu
 * @author Yilin Gao
 */
public class PageSegregation extends UIsetupWithPercentage {
	
	/**
	 * Constructor of the page of segregation simulation
	 * @param cs: the CellSociety instance
	 * @param language: a string representing user choice of language
	 * @param p: a Parameters instant from the calling class
	 */
	public PageSegregation(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	/**
	 * The method to set up required components in the scene.
	 * For segregation, an additional slider to adjust satisfaction level is needed.
	 */
	@Override
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

	/**
	 * The method to set up the grid layout in the scene.
	 * The method calls the super class method as its whole function.
	 * If more coded layouts are expected, they can be placed in this method in the future.
	 * @param newValue
	 */
	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
		// can add other choices of layouts
	}
	
	/**
	 * The method to update the parameters displayed at the top of the UI Screen.
	 * For segregation, the satisfaction level needs to be added.
	 */
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() 
				+ getMyResources().getString("SatisfactionParameter") 
				+ this.getParametersController().getSatisfaction() + "\n";
		this.getInfo().setText(myText);
	}	
}