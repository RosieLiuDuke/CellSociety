import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;

public class WelcomePage extends Page {
	
	/**
	 * The WelcomePage that contains the menu for selecting simulation
	 * @author Yilin Gao, Harry Liu
	 *
	 */
	
	private Text title;
	private ChoiceBox<String> typeOfCellSociety;
	private String BACKGROUND = "splash_bg.jpg";
	
	public WelcomePage(CellSociety cs) {
		
		super(cs);
		
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND));
		ImageView iV = new ImageView();
		iV.setImage(image);
		iV.setFitWidth(WIDTH);
		iV.setFitHeight(HEIGHT);
		this.getRoot().getChildren().add(iV);
		
		title = new Text("Cell Society");
		title.setX(WIDTH / 2 - title.getBoundsInParent().getWidth() / 2);
		title.setY(HEIGHT/3);
		title.setId("title");
		this.getRoot().getChildren().add(title);
		
		typeOfCellSociety = new ChoiceBox<String>(FXCollections.observableArrayList("Game of Life"));
		typeOfCellSociety.setLayoutX(WIDTH / 2 - typeOfCellSociety.getBoundsInParent().getWidth() / 2);
		typeOfCellSociety.setLayoutY(HEIGHT/2);
		typeOfCellSociety.valueProperty().addListener((obs, oVal, nVal) -> handleTypeChoice(nVal));
		this.getRoot().getChildren().add(typeOfCellSociety);
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
	}
	private void handleTypeChoice(String nVal) {
		this.getCellSociety().initializePage(nVal);
	}
}