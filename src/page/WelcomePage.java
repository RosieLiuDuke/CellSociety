package page;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cellSociety.CellSociety;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
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
	private String BACKGROUND = "bg.png";
	private int SPACING = 10;
	private List<String> myLanguages;
	private ComboBox<String> languageChoice;
	
	public WelcomePage(CellSociety cs, String l) {
		super(cs, l);
		myLanguages = new ArrayList<String>();
		addLanguages();
		ObservableList<String> languages = FXCollections.observableArrayList(myLanguages);
		languageChoice = new ComboBox<String>(languages);
		languageChoice.valueProperty().addListener((obs, oVal, nVal) -> changeLanguage(nVal));
		languageChoice.setValue(myLanguages.get(0));
		languageChoice.setTooltip(new Tooltip("Select a language for the simulation page"));
		
		this.getScene().getStylesheets().add(Page.class.getResource("styles.css").toExternalForm());
		
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BACKGROUND));	
		BackgroundImage bgimg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background bg = new Background (bgimg);

		TITLE = new Text("Cell Society");
		TITLE.setId("title");
		
		Button UPLOAD = createButton(getMyResources().getString("FileSelectCommand"), event -> handleMouseReleasedUpload(event));
		Button START = createButton(getMyResources().getString("StartCommand"), event -> handleMouseReleasedStart(event));
		
		VBox buttonBox = new VBox(SPACING);
		buttonBox.getChildren().addAll(TITLE, languageChoice, UPLOAD, START);
		buttonBox.setAlignment(Pos.CENTER);
		
		this.getRoot().setBackground(bg);
		this.getRoot().setCenter(buttonBox);
	}
	
	private void addLanguages(){
		myLanguages.add("English");
		myLanguages.add("Spanish");
	}
	
	/**
	 * The handler of the "UPLOAD" button.
	 * When the button is pressed, a window to choose file will pop out.
	 * When a file is chosen, it will be parsed by XMLParser and the program will wait for further actions.
	 * When no file is chosen, the program will exit.
	 * @param event
	 */
	private void handleMouseReleasedUpload(Event event){
		this.initializeParameterController();
		this.getXMLReader().chooseFile();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLParser userhandler = new XMLParser(this.getParametersController());
			if (this.getCellSociety().getFile() != null){
				saxParser.parse(this.getCellSociety().getFile(), userhandler);  
			}
		} catch (Exception e) {
			displayAlert(e.getMessage());
		}
	}
	
	/**
	 * The handler of the "START" button.
	 * If the XML file is loaded, it will setup buttons, texts and grid of the simulation scene, and change the scene of the stage to the scene of the corresponding simulation.
	 * Otherwise an alert dialog will pop out to advise the user to choose input file.
	 * @param event
	 */
	private void handleMouseReleasedStart(Event event) {
		String type = this.getParametersController().getType();
		this.getCellSociety().setNextType(type);
		this.getCellSociety().initializePage(type);
		GamePage thePage = (GamePage)this.getCellSociety().getPage(type);
		if (thePage != null){
			thePage.showPage();
		}
		else{
			displayAlert("UploadCommand");
		}
	}
}
