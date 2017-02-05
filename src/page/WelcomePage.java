package page;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cellSociety.CellSociety;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.XMLParser;

/**
 * The WelcomePage class for splash screen.
 * Parallel to the GamePage class.
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
	
	/**
	 * The handler of the "UPLOAD" button.
	 * When the button is pressed, a window to choose file will pop out.
	 * When a file is chosen, it will be parsed by XMLParser and the program will wait for further actions.
	 * When no file is chosen, the program will exit.
	 * @param event
	 */
	private void handleMouseReleasedUpload(Event event){
		this.getXMLReader().chooseFile();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLParser userhandler = new XMLParser(this);
			if (this.getCellSociety().getFile() != null){
				saxParser.parse(this.getCellSociety().getFile(), userhandler);  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The handler of the "START" button.
	 * If the XML file is loaded, it will setup buttons, texts and grid of the simulation scene, and change the scene of the stage to the scene of the corresponding simulation.
	 * Otherwise an alert dialog will pop out to advise the user to choose input file.
	 * @param event
	 */
	private void handleMouseReleasedStart(Event event) {
		String type = this.getCellSociety().getCurrentType();
		GamePage thePage = (GamePage) this.getCellSociety().getPage(type);
		if (thePage != null){
			thePage.setupComponents();
			thePage.showPage();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR, "Please choose an input file!");
			alert.showAndWait();
		}
	}
}
