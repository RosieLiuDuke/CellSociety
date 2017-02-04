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
 * @author Harry Liu, Yilin Gao
 *
 */
public class WelcomePage extends Page {	
	
	private Text TITLE;
	private String BACKGROUND = "splash_bg.jpg";
	private int SPACING = 10;
	
	public WelcomePage(CellSociety cs) {
		
		super(cs);
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND));	
		BackgroundImage bgimg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background bg = new Background (bgimg);
		
		TITLE = new Text("Cell Society");
		TITLE.setId("title");
		
		Button UPLOAD = createButton("SELECT FILE", event -> handleTypeChoice("filler text"));
		Button START = createButton("START", event -> new PageGameOfLife(cs));
		
		VBox buttonBox = new VBox(SPACING);
		buttonBox.getChildren().addAll(TITLE, UPLOAD, START);
		buttonBox.setAlignment(Pos.CENTER);
		
		this.getRoot().setBackground(bg);
		this.getRoot().setCenter(buttonBox);
	}
	
	private void handleTypeChoice(String nVal) {
		this.getCellSociety().initializePage(nVal);
	}
}
