package page;

import java.util.List;

import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * The class which sets up the UI. This includes the buttons on the bottom of the screen and the parameters/file drop-down
 * menu on the top of the page
 * @author Harry Liu
 */

public abstract class UIsetup extends GamePage {
	
	private Text parameters = new Text();
	
	public UIsetup(CellSociety cs) {
		super(cs);
	}

	private ChoiceBox<String> layoutChoice;
	
	public void setupComponents(List<String> list, Page page ) {		
		HBox parametersBox = new HBox(15);
		layoutChoice = new ChoiceBox<String>(FXCollections.observableArrayList(list));
		layoutChoice.valueProperty().addListener((obs, oVal, nVal) -> setupGrid(nVal));	
		parameters = new Text();
		parametersBox.getChildren().addAll(parameters, layoutChoice);
		parametersBox.setAlignment(Pos.CENTER);

		updateTextInfo();
		
		page.getRoot().setTop(parametersBox);
		page.getRoot().setCenter(this.getGrid());
		page.getRoot().setBottom(addButtons());
		page.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		page.getCellSociety().setDelay(getSpeed());
		page.getCellSociety().setupGameLoop();
	}
	
	public HBox addButtons(){
		HBox buttonBox = new HBox(5);
		buttonBox.getChildren().addAll(this.getBack(), this.getStart(), this.getStop(), this.getStep());
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;		
	}
	
	public void updateTextInfo() {
		String text = "Simulation name: " + this.getCellSociety().getCurrentType() 
				+ "\nNumber of rows: " + getRow() + " | " 
				+ "Number of columns: " + getCol() + " | "  
				+ "Cell size: " + getSize() + " | "
				+ "Step speed: " + getSpeed() + " | " 
				+ "Step: " + getCurrentStep();
		parameters.setText(text);
	}
	
}