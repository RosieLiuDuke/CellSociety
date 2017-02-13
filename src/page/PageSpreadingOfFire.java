package page;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import cellSociety.CellSociety;

/**
 * The subclass Page to hold the Scene for the spreading of fire simulation.
 * @author Harry Liu, Yilin Gao
 *
 */
public class PageSpreadingOfFire extends UIsetup {
	Slider probAdjustor;
	
	public PageSpreadingOfFire(CellSociety cs, String language, Parameters p) {
		super(cs, language, p);
	}
	
	@Override
	protected int getCellStatus(int col, int row){
		return this.getParametersController().getStatusDistribution(col, row);
	}
	
	@Override
	protected void setupComponents() {
		super.setupComponents();
		// add a special slider to adjust speed
		probAdjustor = createSlider(0, 1, this.getParametersController().getProb(), 0.2, true);
		probAdjustor.valueProperty().addListener((obs,oVal,nVal) -> updateProb(nVal.doubleValue()));
		this.getSliderBox().getChildren().addAll(new Text(getMyResources().getString("ProbabilityAdjustor")), probAdjustor);
		updateParameterBox();
	}
	
	private void updateProb(double value) {
		value = Math.round(value * 100);
		value /= 100;
		this.getParametersController().setProb(value);
		this.getCellSociety().stopGameLoop();
	}

	@Override
	protected void setupGrid(String newValue){
		super.setupGrid(newValue);
	}
	
	@Override
	public void updateTextInfo() {
		super.updateTextInfo();
		String myText = getText() 
				+ getMyResources().getString("ProbabilityParameter") 
				+ this.getParametersController().getProb() + "\n";
		this.getInfo().setText(myText);
	}

}