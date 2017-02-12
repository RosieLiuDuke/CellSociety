package page;

import java.util.ArrayList;
import java.util.List;
import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The subclass UIsetup sets up the layout of every GamePage (simulation) that will be run.
 * (Simulation of Front-End)
 * 
 * @author Harry Liu
 */
public abstract class UIsetup extends GamePage {

	private List<String> myOptions;
	private ComboBox<String> simulationChoice;
	private String text;
	private Text parameters;
	private Text gameTitle;
	private VBox slidersBox;

	public UIsetup(CellSociety cs, String language) {
		super(cs, language);
		myOptions = new ArrayList<String>();
	}

	public List<String> getOptions(){
		return myOptions;
	}

	public Text getParameters(){
		return parameters;
	}

	public String getText(){
		return text;
	}
	
	public VBox getSliderBox(){
		return slidersBox;
	}

	/**
	 * Creates a slider for the right side of the UI Screen
	 * @param min
	 * @param max
	 * @param increment
	 * @param showTick
	 */
	public Slider createSlider(int min, int max, double increment, boolean showTick){
		Slider newSlider;
		newSlider = new Slider(min, max, getSpeed());
		newSlider.setShowTickLabels(showTick);
		newSlider.setShowTickMarks(showTick);
		newSlider.setMajorTickUnit(increment);
		newSlider.setBlockIncrement(increment);
		newSlider.setSnapToTicks(true);
		return newSlider;
	}

	/**
	 * Adds Buttons to the bottom of the UI Screen
	 */
	private VBox addButtons(){
		VBox buttonBox = new VBox(5);
		buttonBox.getChildren().addAll(this.getStart(), this.getStop(), this.getStep(), this.getBack());
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;		
	}
	
	/**
	 * Updates the parameters displayed at the top of the UI Screen
	 */
	public void updateTextInfo() {
		text =  getMyResources().getString("RowParameter") + getRow() + "\n" 
				+ getMyResources().getString("ColParameter") + getCol() + "\n"
				+ getMyResources().getString("GridWidthParameter") + gridWidth + "\n"
				+ getMyResources().getString("GridHeightParameter") + gridHeight + "\n"
				+ getMyResources().getString("StepParameter") + getSpeed() + "\n"
				+ getMyResources().getString("CurrentStepParameter") + getCurrentStep()+ "\n";
		this.getParameters().setText(text);
	}

	/**
	 * Change the speed of simulation by having a new timeline when the slider is manipulated.
	 * @param nVal
	 */
	private void updateSpeed(int nVal) {
		setSpeed(nVal);
		this.getCellSociety().setDelay(nVal);
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().setupGameLoop();
		this.updateTextInfo();
	}
	
	@Override
	public void addinGrid(String newValue) {
		// Necessary only if additional grids are added in
	}
	
	public abstract void addSliders();
	
	/**
	 * Sets up required components in the scene.
	 */
	protected void setupComponents(){
		parameters = new Text();
		parameters.setId("parameters");
		
		myOptions.add("Input");
		ObservableList<String> options = FXCollections.observableArrayList(myOptions);
		simulationChoice = new ComboBox<String>(options);
		simulationChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
		simulationChoice.setTooltip(new Tooltip (getMyResources().getString("SelectCommand")));
		simulationChoice.setPromptText(getMyResources().getString("ChoicesCommand"));
		
		slidersBox = new VBox(15);
		Slider speed = createSlider(1, 5, 1, true);
		speed.valueProperty().addListener((obs,oVal,nVal) -> updateSpeed(nVal.intValue()));
		slidersBox.getChildren().addAll(new Text(getMyResources().getString("StepParameter")), speed);
		
		addSliders();
		
		VBox parametersBox = new VBox(15);
		parametersBox.getChildren().addAll(slidersBox, simulationChoice, addButtons(), parameters);
		
		ScrollPane sp = new ScrollPane();
		sp.setContent(parametersBox);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setId("rightpanel");
		
		gameTitle = new Text(this.getCellSociety().getCurrentType());
		gameTitle.setId("gameTitle");
		
		VBox left = new VBox(10);
		left.getChildren().addAll(gameTitle, this.getGrid(), this.getChart());
		left.setAlignment(Pos.CENTER);
		
		updateTextInfo();
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		this.getRoot().setLeft(left);
		this.getRoot().setRight(sp);
		left.setPrefWidth(getWidth()*0.6);
		sp.setPrefWidth(getWidth()*0.4);
		
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
	}
	
}