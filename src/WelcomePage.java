import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
		
		Button UPLOAD = createButton("SELECT FILE", event -> handleMouseReleasedUpload(event));
		Button START = createButton("START", event -> handleMouseReleasedStart(event));
		
		VBox buttonBox = new VBox(SPACING);
		buttonBox.getChildren().addAll(TITLE, UPLOAD, START);
		buttonBox.setAlignment(Pos.CENTER);
		
		this.getRoot().setBackground(bg);
		this.getRoot().setCenter(buttonBox);
	}
	
	private void handleMouseReleasedUpload(Event event){
		this.getXMLReader().chooseFile();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLParser userhandler = new XMLParser(this);
			saxParser.parse(this.getCellSociety().getFile(), userhandler);     
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleMouseReleasedStart(Event event) {
		String type = this.getCellSociety().getNextType();
		this.getCellSociety().setCurrrentType(type);
		GamePage thePage = (GamePage) this.getCellSociety().getPage(type);
		thePage.setoutComponents();
		thePage.showPage();
	}
}
