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
	private Slider speedChoice;
	private String text;
	private Text parameters;
	
	public UIsetup(CellSociety cs) {
		super(cs);
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
		
		parameters = new Text();
		parameters.setId("parameters");
		parameters.setWrappingWidth(getWidth());
		parameters.setTextAlignment(TextAlignment.CENTER);
		
		parametersBox.getChildren().addAll(parameters, layoutChoice);
		parametersBox.setAlignment(Pos.CENTER);
		
		speedChoice = new Slider(1, 5, getSpeed());
		speedChoice.setShowTickLabels(true);
		speedChoice.setShowTickMarks(true);
		speedChoice.setMajorTickUnit(1);
		speedChoice.setBlockIncrement(1);
		speedChoice.setValue(3);
		speedChoice.valueProperty().addListener((obs,oVal,nVal) -> updateSpeed(nVal.intValue()));
		
		VBox controlBox = new VBox(15);
		controlBox.getChildren().addAll(speedChoice, addButtons());
		
		updateTextInfo();
		
		this.getRoot().setTop(parametersBox);
		this.getRoot().setCenter(this.getGrid());
		this.getRoot().setBottom(controlBox);
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		this.getCellSociety().setDelay(getSpeed());
		this.getCellSociety().setupGameLoop();
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
		text = getMyResources().getString("TitleParameter") + this.getCellSociety().getCurrentType() 
				+ "\n\n" + getMyResources().getString("RowParameter") + getRow() + " | " 
				+ getMyResources().getString("ColParameter") + getCol() + " | "
				+ getMyResources().getString("GridWidthParameter") + gridWidth + " | "
				+ getMyResources().getString("GridHeightParameter") + gridHeight + " | "
				+ getMyResources().getString("StepParameter") + getSpeed() + " | " 
				+ getMyResources().getString("CurrentStepParameter") + getCurrentStep()+ " | ";
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