package page;

import java.util.ArrayList;
import java.util.List;
import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class UIsetup extends GamePage {

	private List<String> myOptions;
	private ComboBox<String> layoutChoice;
	private String text;
	private Text parameters;
	private Text gameTitle;
	
	public UIsetup(CellSociety cs, String l) {
		super(cs, l);
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

	
	/**
	 * The method to set up required components in the scene.
	 * Abstract.
	 */
	protected void setupComponents(){
		VBox parametersBox = new VBox(15);
		myOptions.add("Input");
		ObservableList<String> options = FXCollections.observableArrayList(myOptions);
		layoutChoice = new ComboBox<String>(options);
		layoutChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
		layoutChoice.setTooltip(new Tooltip (getMyResources().getString("SelectCommand")));
		layoutChoice.setPromptText(getMyResources().getString("ChoicesCommand"));
		
		gameTitle = new Text(this.getCellSociety().getCurrentType());
		gameTitle.setId("gameTitle");
		
		parameters = new Text();
		parameters.setId("parameters");
		parameters.setTextAlignment(TextAlignment.CENTER);
		parameters.setWrappingWidth(getWidth()/3);
		
		VBox slidersBox = new VBox(15);
		Slider speed = createSlider(1, 5, 1, true);
		speed.valueProperty().addListener((obs,oVal,nVal) -> updateSpeed(nVal.intValue()));
		slidersBox.getChildren().addAll(new Text(getMyResources().getString("StepParameter")), speed);
		
		parametersBox.getChildren().addAll(parameters,layoutChoice, slidersBox);
		parametersBox.setMaxWidth(getWidth()/3);
		parametersBox.setId("pBox");
		
		VBox controlBox = new VBox(15);
		controlBox.getChildren().addAll(addButtons());
		controlBox.setId("cBox");
		
		VBox left = new VBox(10);
		left.getChildren().addAll(gameTitle,this.getGrid());
		left.setAlignment(Pos.CENTER);
		
		updateTextInfo();
		this.getRoot().setCenter(left);
		this.getRoot().setRight(parametersBox);
		this.getRoot().setBottom(controlBox);;
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
	}
	
	private Slider createSlider(int min, int max, int increment, boolean showTick){
		Slider speedChoice;
		speedChoice = new Slider(min, max, getSpeed());
		speedChoice.setShowTickLabels(showTick);
		speedChoice.setShowTickMarks(showTick);
		speedChoice.setMajorTickUnit(increment);
		speedChoice.setBlockIncrement(increment);
		return speedChoice;
	}
	
	/**
	 * The method to add Buttons to the bottom of the UI Screen
	 */
	private HBox addButtons(){
		HBox buttonBox = new HBox(5);
		buttonBox.getChildren().addAll(this.getBack(), this.getStart(), this.getStop(), this.getStep());
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;		
	}
	
	
	/**
	 * The method that updates the parameters displayed at the top of the UI Screen
	 */
	public void updateTextInfo() {
		text =  
				getMyResources().getString("RowParameter") + getRow() + "\n" 
				+ getMyResources().getString("ColParameter") + getCol() + "\n"
				+ getMyResources().getString("GridWidthParameter") + gridWidth + "\n"
				+ getMyResources().getString("GridHeightParameter") + gridHeight + "\n"
				+ getMyResources().getString("StepParameter") + getSpeed() + "\n"
				+ getMyResources().getString("CurrentStepParameter") + getCurrentStep()+ "\n";
		this.getParameters().setText(text);
	}
	
	/**
	 * When the slider is updated, change the speed of simulation by having a new timeline.
	 * @param nVal
	 */
	private void updateSpeed(int nVal) {
		setSpeed(nVal);
		this.getCellSociety().setDelay(nVal);
		this.getCellSociety().stopGameLoop();
		this.getCellSociety().setupGameLoop();
		this.updateTextInfo();
	}
}