import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.*;

public class WelcomePage extends Page {
	
	private Text title;
	private ChoiceBox<String> typeOfCellSociety;

	public WelcomePage(CellSociety cs) {
		super(cs);
		title = new Text("Cell Society");
		title.setX(WIDTH / 2 - title.getBoundsInParent().getWidth() / 2);
		title.setY(100);
		this.getRoot().getChildren().add(title);
		
		typeOfCellSociety = new ChoiceBox<String>(FXCollections.observableArrayList("Game of Life"));
		typeOfCellSociety.setLayoutX(WIDTH / 2 - typeOfCellSociety.getBoundsInParent().getWidth() / 2);
		typeOfCellSociety.setLayoutY(200);
		typeOfCellSociety.valueProperty().addListener((obs, oVal, nVal) -> handleTypeChoice(nVal));
		this.getRoot().getChildren().add(typeOfCellSociety);
	}

	private void handleTypeChoice(String nVal) {
		this.getCellSociety().initializeSimulationPage(nVal);
	}


}
