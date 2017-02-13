package page;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the spreading of fire simulation.
 * @author Harry Liu
 * @author Yilin Gao
 */
public class PageSpreadingOfFire extends UIsetup {
	private Slider probAdjustor;
	
	/**
	 * Constructor of the page of fire simulation
	 * @param cs: the CellSociety instance
	 * @param language: a string representing user choice of language
	 * @param p: a Parameters instant from the calling class
	 */
	public PageSpreadingOfFire(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	/**
	 * The method to set up required components in the scene.
	 * For Fire, a slider is additionally needed to adjust speed.
	 */
	@Override
	protected void setupComponents() {
		super.setupComponents();
		// add a special slider to adjust speed
		probAdjustor = createSlider(0, 1, this.getParametersController().getProb(), 0.2, true);
		probAdjustor.valueProperty().addListener((obs,oVal,nVal) -> updateProbOnProbSliderDrag(nVal.doubleValue()));
		this.getSliderBox().getChildren().addAll(new Text(getMyResources().getString("ProbabilityAdjustor")), probAdjustor);
		updateParameterBox();
	}
	
	private void updateProbOnProbSliderDrag(double value) {
		value = Math.round(value * 100);
		value /= 100;
		this.getParametersController().setProb(value);
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
		// can add other layouts
	}
	
	/**
	 * The method to update the parameters displayed at the top of the UI Screen.
	 * For fire, the probability of catching fire needs to be added.
	 */
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() 
				+ getMyResources().getString("ProbabilityParameter") 
				+ this.getParametersController().getProb() + "\n";
		this.getInfo().setText(myText);
	}

}