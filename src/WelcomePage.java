import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

/**
 * The WelcomePage that contains the menu for selecting simulation
 * @author Yilin Gao, Harry Liu
 *
 */
public class WelcomePage extends Page {	
	
	private Text title;
	private String BACKGROUND = "splash_bg.jpg";
	private int SPACING = 10;
	
	public WelcomePage(CellSociety cs) {
		
		super(cs);
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND));	
		BackgroundImage bgimg = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background bg = new Background (bgimg);
		
		title = new Text("Cell Society");
		title.setId("title");
		
		Button UPLOAD = createButton("SELECT FILE", event -> handleMouseReleasedUpload(event));
		Button START = createButton("START", event -> handleMouseReleasedStart(event));
		
		VBox center = new VBox(SPACING);
		center.getChildren().addAll(title, UPLOAD, START);
		center.setAlignment(Pos.CENTER);
		
		this.getRoot().setCenter(center);
		this.getRoot().setBackground(bg);
		
	}
	
	private void handleMouseReleasedUpload(Event e){
		this.getXMLReader().chooseFile();
	}
	
	private void handleMouseReleasedStart(Event e) {
		this.getCellSociety().initializePage("Game of Life");
	}
}
